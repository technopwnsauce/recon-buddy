import java.util.*;
import java.io.*;

public class Dirb
{
	//Main
	public static void main(String[] args)
	{
		Dirb dirb = new Dirb("http://","192.168.1.88","/usr/share/dirb/wordlists/big.txt");
		dirb.write();
		dirb.execute();
	}

	//Fields
	public String tool = "dirb";
	public String protocol;
	public String target;
	public String dictionary;
	public ArrayList<String> commandList = new ArrayList<String>();

	//Constructor
	public Dirb(String protocol, String target, String dictionary)
	{
		this.protocol = protocol;
		this.target = target;
		this.dictionary = dictionary;

		//Write the commandList
		commandList.add(tool);
		commandList.add(protocol);
		commandList.add(target);
		commandList.add(dictionary);
	}

	//Writes the appropriate command to a bash script
	public void write()
	{
		//Build command string based on array list
		String command = "";
		for(int i=0; i<commandList.size(); i++)
		{
			command = command + commandList.get(i);
			if(i!=commandList.size()-1 && i!=commandList.size()-3)
			{
				command = command + " ";
			}
		}

		//Write the command string to a bash script
		try
		{
	    	PrintWriter writer = new PrintWriter("dirb.sh", "UTF-8");
	    	writer.println("#!/bin/bash" + "\n");
	    	writer.print(command);
	    	writer.print(" > " + tool + "_" + target + ".txt");
	    	writer.close();
    	} 		
    	catch (Exception e){}
    	
    	//Make the bash script executable
    	try
		{
			Process proc = Runtime.getRuntime().exec("chmod +x dirb.sh");                        
			proc.waitFor();
		}
		catch (Exception e){}
	}

	//Executes the bash script
	public void execute()
	{
		try
		{
			Process proc = Runtime.getRuntime().exec("./dirb.sh");                        
			proc.waitFor();
		}
		catch (Exception e){}
	}
}