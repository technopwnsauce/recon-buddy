import java.util.*;
import java.io.*;

public class Enum4linux
{
	//Main
	public static void main(String[] args)
	{
		Enum4linux enum4linux = new Enum4linux("-a","192.168.1.88");
		enum4linux.write();
		enum4linux.execute();
	}

	//Fields
	public String tool = "enum4linux";
	public String action;
	public String target;
	public ArrayList<String> commandList = new ArrayList<String>();

	//Constructor
	public Enum4linux(String action, String target)
	{
		this.action = action;
		this.target = target;

		//Write the commandList
		commandList.add(tool);
		commandList.add(action);
		commandList.add(target);
	}

	//Writes the appropriate command to a bash script
	public void write()
	{
		//Build command string based on array list
		String command = "";
		for(int i=0; i<commandList.size(); i++)
		{
			command = command + commandList.get(i);
			if(i!=commandList.size()-1)
			{
				command = command + " ";
			}
		}

		//Write the command string to a bash script
		try
		{
	    	PrintWriter writer = new PrintWriter("enum4linux.sh", "UTF-8");
	    	writer.println("#!/bin/bash" + "\n");
	    	writer.print(command);
	    	writer.print(" > " + tool + "_" + target + ".txt");
	    	writer.close();
    	} 		
    	catch (Exception e){}
    	
    	//Make the bash script executable
    	try
		{
			Process proc = Runtime.getRuntime().exec("chmod +x enum4linux.sh");                        
			proc.waitFor();
		}
		catch (Exception e){}
	}

	//Executes the bash script
	public void execute()
	{
		try
		{
			Process proc = Runtime.getRuntime().exec("./enum4linux.sh");                        
			proc.waitFor();
		}
		catch (Exception e){}
	}
}