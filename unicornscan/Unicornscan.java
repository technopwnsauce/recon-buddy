import java.util.*;
import java.io.*;

public class Unicornscan
{
	//Main
	public static void main(String[] args)
	{
		Unicornscan unicornscan = new Unicornscan("192.168.1.88","-mU","-v");
		unicornscan.write();
		unicornscan.execute();
	}

	//Fields
	public String tool = "unicornscan";
	public String target;
	public String protocol;
	public String verbosity;
	public ArrayList<String> commandList = new ArrayList<String>();

	//Constructor
	public Unicornscan(String target, String protocol, String verbosity)
	{
		this.target = target;
		this.protocol = protocol;
		this.verbosity = verbosity;

		//Write the commandList
		commandList.add(tool);
		commandList.add(target);
		commandList.add(protocol);
		commandList.add(verbosity);
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
	    	PrintWriter writer = new PrintWriter("unicornscan.sh", "UTF-8");
	    	writer.println("#!/bin/bash" + "\n");
	    	writer.print(command);
	    	writer.print(" > " + tool + "_" + target + ".txt");
	    	writer.close();
    	} 		
    	catch (Exception e){}
    	
    	//Make the bash script executable
    	try
		{
			Process proc = Runtime.getRuntime().exec("chmod +x unicornscan.sh");                        
			proc.waitFor();
		}
		catch (Exception e){}
	}

	//Executes the bash script
	public void execute()
	{
		try
		{
			Process proc = Runtime.getRuntime().exec("./unicornscan.sh");                        
			proc.waitFor();
		}
		catch (Exception e){}
	}
}