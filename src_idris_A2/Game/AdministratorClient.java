/*
 * @author Adigun Jide Idris (7128525) and Nneoma Egwuonwu (7139217)
 * Course: COMP6231 - Distributed System Design
 * School: Concordia University
 * Class Name: User.java
 * Class Function: This class represent general user and responsible for communicating with the Server interface
 */
package Game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.omg.CORBA.ORB;


public class AdministratorClient{
	
	static String NA_IP = "132"; //IP for North America
	static String EU_IP = "93"; //IP for Europe
	static String AS_IP = "182"; //IP for Asia
	static UserForm form = new UserForm();
	static String[] ip_Id;
	static String user_log_details; //to hold user log details
	static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); // Formating date to write all the actions of the clients on a file following the time.
	static Date date = new Date(); //date for the log
	static String answer = "";
	
	public static void main(String[] args) throws  IOException, NotBoundException {
		// TODO Auto-generated method stub
		ORB orb = ORB.init(args, null);
		
		BufferedReader nA = new BufferedReader (new FileReader("iorNA.txt"));
		String ior_NA = nA.readLine();
		nA.close();
		
		BufferedReader aS = new BufferedReader (new FileReader("iorAS.txt"));
		String ior_AS = aS.readLine();
		aS.close();
		
		BufferedReader eU = new BufferedReader (new FileReader("iorEU.txt"));
		String ior_EU = eU.readLine();
		eU.close();
		
		org.omg.CORBA.Object nA_Server = orb.string_to_object(ior_NA);
		org.omg.CORBA.Object aS_Server = orb.string_to_object(ior_AS);
		org.omg.CORBA.Object eU_Server = orb.string_to_object(ior_EU);
		
		
		Behaviour nA_Server_Behaviour = BehaviourHelper.narrow(nA_Server);
		
		Behaviour aS_Server_Behaviour = BehaviourHelper.narrow(aS_Server);
		Behaviour eU_Server_Behaviour = BehaviourHelper.narrow(eU_Server);
		
		start_process(nA_Server_Behaviour, aS_Server_Behaviour,
				eU_Server_Behaviour);
	}

	public static void start_process(Behaviour nA_Server_Behaviour,
			Behaviour aS_Server_Behaviour, Behaviour eU_Server_Behaviour)
			throws IOException {
		form.admin_menu();
		int choice = form.number_selected;
		switch(choice)
		{
		case 1:
			player_status(nA_Server_Behaviour, aS_Server_Behaviour,
					eU_Server_Behaviour);
			start_process(nA_Server_Behaviour, aS_Server_Behaviour,
					eU_Server_Behaviour);
			break;
	
		case 2:
			suspend_Account(nA_Server_Behaviour, aS_Server_Behaviour,
					eU_Server_Behaviour);
			start_process(nA_Server_Behaviour, aS_Server_Behaviour,
					eU_Server_Behaviour);
			break;
	
		case 3:
			sign_out(nA_Server_Behaviour, aS_Server_Behaviour,
					eU_Server_Behaviour);
			break;
		}
	}
	
	public static void suspend_Account(Behaviour nA_Server_Behaviour,
			Behaviour aS_Server_Behaviour, Behaviour eU_Server_Behaviour)
			throws IOException {
		
		form.suspendAccount();
		if(form.admin_username.equals("admin") && form.admin_password.equals("admin"))
		{
			ip_Id = form.IP_address.split("\\.");
			if(ip_Id[0].equals(NA_IP)) //comparing users ip_address identifier with the one of North America
			{
				answer = nA_Server_Behaviour.suspendAccount(form.admin_username, form.admin_password, form.IP_address, form.username);
				user_log_details ="\nTime:" + dateFormat.format(date) + "\n+++ Player Created +++ " + "\nName: " + form.first_name + " " + form.last_name + "\nUsername: " + form.username + "\nServer: NA"; //creating log for creating users
				System.out.print(answer);
				create_log(user_log_details);
			}
			else if(ip_Id[0].equals(EU_IP))
			{
				answer = eU_Server_Behaviour.suspendAccount(form.admin_username, form.admin_password, form.IP_address, form.username);
				user_log_details = "\nTime:" + dateFormat.format(date) + "\n+++ Player Created +++ " + "\nName: " + form.first_name + " " + form.last_name + "\nUsername: " + form.username + "\nServer: EU";
				System.out.print(answer);
				create_log(user_log_details);
			}
			else if(ip_Id[0].equals(AS_IP))
			{
				answer = eU_Server_Behaviour.suspendAccount(form.admin_username, form.admin_password, form.IP_address, form.username);
				user_log_details ="\nTime:" + dateFormat.format(date) + "\n+++ Player Created +++ " + "\nName: " + form.first_name + " " + form.last_name + "\nUsername: " + form.username + "\nServer: AS";
				System.out.print(answer);
				create_log(user_log_details);
			}
			else
			{
				System.out.println("The Ip address you entered is invalid");
			}
			
		}
		else
		{
			System.out.println("Your are not authorize to suspend user");
		}
	}
	
	
	public static void player_status(Behaviour nA_Server_Behaviour,
			Behaviour aS_Server_Behaviour, Behaviour eU_Server_Behaviour)
			throws IOException {
		form.checkStatus();
		if(form.username.equals("admin") && form.password.equals("admin"))
		{
			ip_Id = form.IP_address.split("\\.");
			if(ip_Id[0].equals(NA_IP))
			{
				answer = nA_Server_Behaviour.getPlayerStatus(form.username, form.password, form.IP_address);
				user_log_details = "\nTime: " + dateFormat.format(date) + "\n++ Admin Checked +++" + "\nPlayer: " + form.username  + "\nServer: NA";
				System.out.print(answer);
				create_log(user_log_details);
			}
	
			else if(ip_Id[0].equals(EU_IP))
			{
				answer = eU_Server_Behaviour.getPlayerStatus(form.username, form.password, form.IP_address);
				user_log_details = "\nTime: " + dateFormat.format(date) + "\n++ Admin Checked +++" + "\nPlayer: " + form.username  + "\nServer: EU";
				System.out.print(answer);
				create_log(user_log_details);
			}
			else if(ip_Id[0].equals(AS_IP))
			{
				answer = aS_Server_Behaviour.getPlayerStatus(form.username, form.password, form.IP_address);
				user_log_details = "\nTime: " + dateFormat.format(date) + "\n++ Admin Checked +++" + "\nPlayer: " + form.username  + "\nServer: AS";
				System.out.print(answer);
				create_log(user_log_details);
				
			}
			else
			{
				System.out.println("Invalid IP Address");
			}
		}
		else
		{
			System.out.println("you are not authurize to perform this operation");
		}
				
	}
	public static void sign_out(Behaviour nA_Server_Behaviour,
			Behaviour aS_Server_Behaviour, Behaviour eU_Server_Behaviour)
			throws IOException {
		form.sign_out();
		ip_Id = form.IP_address.split("\\.");
		if(ip_Id[0].equals(NA_IP))
		{
			answer = nA_Server_Behaviour.playerSignOut(form.username, form.IP_address);
			user_log_details = "\nTime:" + dateFormat.format(date) + "\n+++ Player SignOut +++ " + "\nUsername: " + form.username + "\nServer: NA";
			System.out.print(answer);
			create_log(user_log_details);
			
		}

		else if(ip_Id[0].equals(EU_IP))
		{
			answer =eU_Server_Behaviour.playerSignOut(form.username, form.IP_address);
			user_log_details = "\nTime:" + dateFormat.format(date) + "\n+++ Player SignOut +++ " + "\nUsername: " + form.username + "\nServer: EU";
			System.out.print(answer);
			create_log(user_log_details);
		}
		else if(ip_Id[0].equals(AS_IP))
		{
			answer = aS_Server_Behaviour.playerSignOut(form.username, form.IP_address);
			user_log_details = "\nTime:" + dateFormat.format(date) + "\n+++ Player SignOut +++ " + "\nUsername: " + form.username + "\nServer: AS";
			System.out.print(answer);
			create_log(user_log_details);
		
			
		}
		else
		{
			System.out.println("Invalid IP Address");
		}
	}
	
	public static void create_log(String client_Content) throws IOException //Creating client and admin logs
	{
		String[] ip = form.IP_address.split("\\.");
		File client_log = new File("client_Log-"+form.username+"-"+ip[0]+".txt"); //Creating log files with names including username and IP Identifier of the user.
		FileWriter fw = new FileWriter(client_log, true);
		BufferedWriter buffer = new BufferedWriter(fw);
		buffer.write(client_Content);
		buffer.close();
		
	}

}
