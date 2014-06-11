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
import java.util.InputMismatchException;

import org.omg.CORBA.ORB;

public class GamePlayerClient {

	/**
	 * @param args
	 */

	static String NA_IP = "132"; //IP for North America
	static String EU_IP = "93"; //IP for Europe
	static String AS_IP = "182"; //IP for Asia
	static UserForm form = new UserForm();
	static String[] ip_Id;
	static String user_log_details; //to hold user log details
	static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); // Formating date to write all the actions of the clients on a file following the time.
	static Date date = new Date(); //date for the log
	static String answer = ""; //answer from the server
	
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
			throws InputMismatchException, IOException {
		form.start_up_menu();
		int choice = form.number_selected;
		switch(choice)
		{
		case 1:
			sign_in(nA_Server_Behaviour, aS_Server_Behaviour,
					eU_Server_Behaviour);
			start_process(nA_Server_Behaviour, aS_Server_Behaviour,
					eU_Server_Behaviour);
			break;
			
		case 2:
			sign_out(nA_Server_Behaviour, aS_Server_Behaviour,
					eU_Server_Behaviour);
			break;
		case 3:
			sign_up(nA_Server_Behaviour, aS_Server_Behaviour,
					eU_Server_Behaviour);
			start_process(nA_Server_Behaviour, aS_Server_Behaviour,
					eU_Server_Behaviour);
			break;
			
		case 4:
			transferAccount(nA_Server_Behaviour, aS_Server_Behaviour,
					eU_Server_Behaviour);
			start_process(nA_Server_Behaviour, aS_Server_Behaviour,
					eU_Server_Behaviour);
			break;
		}
	}
	
	public static void transferAccount(Behaviour nA_Server_Behaviour,
			Behaviour aS_Server_Behaviour, Behaviour eU_Server_Behaviour)
			throws IOException {
		form.transferAccount();
		ip_Id = form.old_IP_address.split("\\.");
		if(ip_Id[0].equals(NA_IP))
		{
			answer = nA_Server_Behaviour.transferAccount(form.username, form.password, form.old_IP_address, form.IP_address);
			System.out.println(answer);
			if(ip_Id[0].equals(EU_IP))
			{    
				System.out.println("----Welcome to Europe Server------");
				form.asiaTransfer();
				answer = eU_Server_Behaviour.createPlayerAccount(form.first_name, form.last_name, (short) form.age, form.username, form.password, form.IP_address);
				user_log_details = "\nTime:" + dateFormat.format(date) + "\n+++ Player Transfer +++ " + "\nUsername: " + form.username + "from NA to EU Server";
				create_log(user_log_details);
			}
			if(ip_Id[0].equals(AS_IP))
			{    
				System.out.println("----Welcome to Asia Server------");
				form.asiaTransfer();
				aS_Server_Behaviour.createPlayerAccount(form.first_name, form.last_name, (short) form.age, form.username, form.password, form.IP_address);
				user_log_details = "\nTime:" + dateFormat.format(date) + "\n+++ Player Transfer +++ " + "\nUsername: " + form.username + "from NA to AS Server";
				create_log(user_log_details);
			}
			else
			{
				System.out.println("The new server you have selected does not exist");
			}
		}
		else if(ip_Id[0].equals(EU_IP))
		{
			answer = eU_Server_Behaviour.transferAccount(form.username, form.password, form.old_IP_address, form.IP_address);
			System.out.println(answer);
			ip_Id = form.IP_address.split("\\.");
			if(ip_Id[0].equals(NA_IP))
			{    
				System.out.println("----Welcome to North America Server------");
				form.asiaTransfer();
				nA_Server_Behaviour.createPlayerAccount(form.first_name, form.last_name, (short) form.age, form.username, form.password, form.IP_address);
				user_log_details = "\nTime:" + dateFormat.format(date) + "\n+++ Player Transfer +++ " + "\nUsername: " + form.username + "from EU to NA Server";
				create_log(user_log_details);
				create_log(answer);
			}
			if(ip_Id[0].equals(AS_IP))
			{    
				System.out.println("----Welcome to Asia Server------");
				form.asiaTransfer();
				aS_Server_Behaviour.createPlayerAccount(form.first_name, form.last_name, (short) form.age, form.username, form.password, form.IP_address);
				user_log_details = "\nTime:" + dateFormat.format(date) + "\n+++ Player Transfer +++ " + "\nUsername: " + form.username + "from EU to AS Server";
				create_log(user_log_details);
				
			}
			else
			{
				System.out.println("The new server you have entered does not exist");
			}
		}
		else if(ip_Id[0].equals(AS_IP))
		{
			answer = aS_Server_Behaviour.transferAccount(form.username, form.password, form.old_IP_address, form.IP_address);
			System.out.println(answer);
			ip_Id = form.IP_address.split("\\.");
			if(ip_Id[0].equals(NA_IP))
			{    
				System.out.println("----Welcome to North America Server------");
				form.asiaTransfer();
				eU_Server_Behaviour.createPlayerAccount(form.first_name, form.last_name, (short) form.age, form.username, form.password, form.IP_address);
				user_log_details = "\nTime:" + dateFormat.format(date) + "\n+++ Player Transfer +++ " + "\nUsername: " + form.username + "from AS to NA Server";
				create_log(user_log_details);
			}
			if(ip_Id[0].equals(EU_IP))
			{    
				System.out.println("----Welcome to North America Server------");
				form.asiaTransfer();
				eU_Server_Behaviour.createPlayerAccount(form.first_name, form.last_name, (short) form.age, form.username, form.password, form.IP_address);
				user_log_details = "\nTime:" + dateFormat.format(date) + "\n+++ Player Transfer +++ " + "\nUsername: " + form.username + "from NA to EU Server";
				create_log(user_log_details);
			}
			else
			{
				System.out.println("The new server you have selected does not exist");
			}
		}
		else
		{
			System.out.println("The old IP address does not exist");
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
			aS_Server_Behaviour.playerSignOut(form.username, form.IP_address);
			answer = user_log_details = "\nTime:" + dateFormat.format(date) + "\n+++ Player SignOut +++ " + "\nUsername: " + form.username + "\nServer: AS";
			System.out.print(answer);
			create_log(user_log_details);
		}
		else
		{System.out.println("Invalid IP Address");}
	}
	
	public static void sign_in(Behaviour nA_Server_Behaviour,
			Behaviour aS_Server_Behaviour, Behaviour eU_Server_Behaviour)
			throws IOException {
		form.sign_in();
		ip_Id = form.IP_address.split("\\.");
		if(ip_Id[0].equals(NA_IP)) //If IP address is North America
		{
			if(form.username.equals("admin") && form.password.equals("admin"))
			{
				answer = nA_Server_Behaviour.playerSignIn(form.username, form.password, form.IP_address);
				user_log_details ="\nTime:" + dateFormat.format(date) + "\n+++ Admin Signed In +++ " + "\nUsername: " + form.username + "\nServer: NA";
				System.out.print(answer);
				create_log(user_log_details);
			}
			else
			{	
				answer = nA_Server_Behaviour.playerSignIn(form.username, form.password, form.IP_address);
				user_log_details = "\nTime:" + dateFormat.format(date) + "\n+++ Player Signed In +++ " + "\nName: " + form.first_name + " " + form.last_name + "\nUsername: " + form.username + "\nServer: NA";
				System.out.print(answer);
				create_log(user_log_details);
			}
		}
		else if(ip_Id[0].equals(EU_IP))
		{
			if(form.username.equals("admin") && form.password.equals("admin"))
			{
				answer = eU_Server_Behaviour.playerSignIn(form.username, form.password, form.IP_address);
				user_log_details ="\nTime:" + dateFormat.format(date) + "\n+++ Admin Signed In +++ " + "\nUsername: " + form.username + "\nServer: EU";
				System.out.print(answer);
				create_log(user_log_details);
			}
			else
			{	
				answer = eU_Server_Behaviour.playerSignIn(form.username, form.password, form.IP_address);
				user_log_details = "\nTime:" + dateFormat.format(date) + "\n+++ Player Signed In +++ " + "\nName: " + form.first_name + " " + form.last_name + "\nUsername: " + form.username + "\nServer: EU";
				System.out.print(answer);
				create_log(user_log_details);
			}
		}
		else if(ip_Id[0].equals(AS_IP))
		{
			if(form.username.equals("admin") && form.password.equals("admin"))
			{
				answer = aS_Server_Behaviour.playerSignIn(form.username, form.password, form.IP_address);
				user_log_details = "\nTime: " + dateFormat.format(date) + "\n+++ Admin Signed In +++ " + "\nUsername: " + form.username + " " + "\nServer: AS";
				create_log(user_log_details);
				System.out.print(answer);
			}
			else
			{
				answer = aS_Server_Behaviour.playerSignIn(form.username, form.password, form.IP_address);
				user_log_details = "\nTime:" + dateFormat.format(date) + "\n+++ Player Signed In +++ " + "\nName: " + form.first_name + " " + form.last_name + "\nUsername: " + form.username + "\nServer: AS";
				System.out.print(answer);
				create_log(user_log_details);
			}
		}
		else
		{
				System.out.println("Sorry! This user does not exist");
		}
	}
	
	public static void sign_up(Behaviour nA_Server_Behaviour,
			Behaviour aS_Server_Behaviour, Behaviour eU_Server_Behaviour)
			throws IOException {
		form.sign_up();
		ip_Id = form.IP_address.split("\\.");
		if(ip_Id[0].equals(NA_IP)) //comparing users ip_address identifier with the one of North America
		{
			answer =nA_Server_Behaviour.createPlayerAccount(form.first_name, form.last_name, (short) form.age, form.username, form.password, form.IP_address);
			user_log_details ="\nTime:" + dateFormat.format(date) + "\n+++ Player Created +++ " + "\nName: " + form.first_name + " " + form.last_name + "\nUsername: " + form.username + "\nServer: NA"; //creating log for creating users
			System.out.print(answer);
			create_log(user_log_details);
		}
		else if(ip_Id[0].equals(EU_IP))
		{
			answer = eU_Server_Behaviour.createPlayerAccount(form.first_name, form.last_name, (short) form.age, form.username, form.password, form.IP_address);
			user_log_details = "\nTime:" + dateFormat.format(date) + "\n+++ Player Created +++ " + "\nName: " + form.first_name + " " + form.last_name + "\nUsername: " + form.username + "\nServer: EU";
			System.out.print(answer);
			create_log(user_log_details);
		}
		else if(ip_Id[0].equals(AS_IP))
		{
			answer = aS_Server_Behaviour.createPlayerAccount(form.first_name, form.last_name, (short) form.age, form.username, form.password, form.IP_address);
			user_log_details ="\nTime:" + dateFormat.format(date) + "\n+++ Player Created +++ " + "\nName: " + form.first_name + " " + form.last_name + "\nUsername: " + form.username + "\nServer: AS";
			System.out.print(answer);
			create_log(user_log_details);
		}
		else
		{
			System.out.println("The Ip address you entered is invalid");
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



