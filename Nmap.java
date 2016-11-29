import java.util.*;
import java.io.*;

public class Nmap
{
	//Main
	public static void main(String[] args)
	{
		Nmap nmap = new Nmap("192.168.1.88","-p 21","-sV","-O","-sC","-T5","-vvv");
		nmap.write();
		nmap.execute();
	}

	//Fields
	public String tool = "nmap";
	public String target;
	public String port;
	public String serviceFlag;
	public String osFlag;
	public String scriptFlag;
	public String speedFlag;
	public String verbosityFlag;
	public ArrayList<String> commandList = new ArrayList<String>();

	//Constructor
	public Nmap(String target, String port, String serviceFlag, String osFlag, String scriptFlag, String speedFlag, String verbosityFlag)
	{
		this.tool = tool;
		this.target = target;
		this.port = port;
		this.serviceFlag = serviceFlag;
		this.osFlag = osFlag;
		this.scriptFlag = scriptFlag;
		this.speedFlag = speedFlag;
		this.verbosityFlag = verbosityFlag;

		//Write the commandList
		commandList.add(tool);
		commandList.add(target);
		commandList.add(port);
		commandList.add(serviceFlag);
		commandList.add(osFlag);
		commandList.add(scriptFlag);
		commandList.add(speedFlag);
		commandList.add(verbosityFlag);
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
	    	PrintWriter writer = new PrintWriter("nmap.sh", "UTF-8");
	    	writer.println("#!/bin/bash" + "\n");
	    	writer.print(command);
	    	writer.print(" > " + tool + "_" + target + ".txt");
	    	writer.close();
    	} 		
    	catch (Exception e){}
    	
    	//Make the bash script executable
    	try
		{
			Process proc = Runtime.getRuntime().exec("chmod +x nmap.sh");                        
			proc.waitFor();
		}
		catch (Exception e){}
	}

	//Executes the bash script
	public void execute()
	{
		try
		{
			Process proc = Runtime.getRuntime().exec("./nmap.sh");                        
			proc.waitFor();
		}
		catch (Exception e){}
	}
}