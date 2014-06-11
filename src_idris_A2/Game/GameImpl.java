package Game;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.LinkedList;

public class GameImpl extends BehaviourPOA {
	Hashtable<Character, LinkedList<String>> server_HashTable = new Hashtable<Character, LinkedList<String>>();
	LinkedList<String> list_Server = new LinkedList<String>();
	String playerStatus = "0";
	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	Date date = new Date();
	int active_count;
	int inactive_count;
	
	@Override
	public String createPlayerAccount(String FirstName, String LastName,
			short Age, String Username, String Password, String IPAddress) {
		// TODO Auto-generated method stub
		char first_char = Username.charAt(0);
		boolean flag = false;
		try
		{
			flag = chkUserExist(Username, first_char, flag); //checks if user exist
			
			if(flag == false) //if flag is false, then user does not exist
			{
				//create new user
				String data_string = Username + " " + FirstName + " " + LastName + " " + Age + " " + Password + " " + IPAddress + " " + playerStatus; //Pass user details to a string
				synchronized(this){list_Server.add(data_string);} //Add user details to the linked list
				server_HashTable.put(first_char, list_Server);
				create_log("\nTime:" + dateFormat.format(date) + "\nServer NA created a user." + "\nName: " + FirstName + " " + LastName + "\nusername: " + Username, Username);
				//create log of users
				return("Dear " + FirstName + ", You have successfully registered");
			}
			else
			{
				create_log("\nTime:" + dateFormat.format(date) + "\nThis user already exist on this Server: ", Username);
				return("username already exists");
			}
			
		}
		catch(Exception e)
		{
	
			String data_string = Username + " " + FirstName + " " + LastName + " " + Age + " " + Password + " " + IPAddress + " " + playerStatus; //Pass user details to a string
			synchronized(this){list_Server.add(data_string);} //Add user details to the linked list
			server_HashTable.put(first_char, list_Server);
			try {
				create_log("\nTime:" + dateFormat.format(date) + "\nServer NA created a user." + "\nName: " + FirstName + " " + LastName + "\nusername: " + Username, Username);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//create log of users
			return("Dear " + FirstName + ", You have successfully registered");
		}
		
	}
			
	

	@Override
	public String playerSignIn(String Username, String Password,
			String IPAddress) {
		// TODO Auto-generated method stub
		char first_char = Username.charAt(0);
		int user_count = -1;
		int flag = 0;

		try
		{
			String[] split_user_profile = new String[list_Server.size()];
			for(int i=0;i<list_Server.size();i++)
			{
			
			user_count++;
			split_user_profile = (server_HashTable.get(first_char).get(i)).split(" ");
			if(split_user_profile[0].equals(Username))
			{
				flag = 1;
				break;
			}
		}
		if(flag == 1)
		{
			playerStatus = "1";
			if(Password.equals(split_user_profile[4]) && split_user_profile[6].equals("0"))
			{
				list_Server.remove(server_HashTable.get(first_char).get(user_count));
				String data_string = split_user_profile[0] + " " + split_user_profile[1] + " " + split_user_profile[2] + " " + split_user_profile[3] + " " + split_user_profile[4] + " " + split_user_profile[5] + " " + playerStatus;
				synchronized(this){list_Server.add(data_string);}
				server_HashTable.put(first_char, list_Server);
				create_log("\nTime:" + dateFormat.format(date) + "\nServer signed in a user." + "\nName: " + split_user_profile[0] + " " + split_user_profile[1] + "\nusername: " + Username, Username);
				return "You have successfully logged in" + "\nName: " + split_user_profile[1] + "\nLast Name: " + split_user_profile[2] + "\nAge: " + split_user_profile[3] + "\nIP address: " + split_user_profile[5];
			}
			else
			{
				create_log("\nTime:" + dateFormat.format(date) + "\nUser is already logged in on Server.", Username);
				return("You are already logged in");
			}
		}
		else
		{
			return("Incorrect username or password or user has been suspended");
		}
		}
		catch(Exception e){return "Incorrent username or password";}
	}

	@Override
	public String playerSignOut(String Username, String IPAddress) {
		// TODO Auto-generated method stub
		char first_char = Username.charAt(0);
		int user_count = -1;
		int flag=0;
			try
			{	
				String[] user_data = new String[list_Server.size()];
				for(int i=0;i<list_Server.size();i++)
				{
					
					user_count++;
					user_data = (server_HashTable.get(first_char).get(i)).split(" ");
					if(user_data[0].equals(Username))
					{
						flag = 1;
						break;
					}
				}
				if(flag == 1)
				{
					playerStatus = "0";
					if(user_data[6].equals("1"))
					{
						list_Server.remove(server_HashTable.get(first_char).get(user_count));
						String data_string = user_data[0] + " " + user_data[1] + " " + user_data[2] + " " + user_data[3] + " " + user_data[4] + " " + user_data[5] + " " + playerStatus;
						list_Server.add(data_string);
						server_HashTable.put(first_char, list_Server);
						return "You have successfully signed out";
					}
					else
					{
						return("You are not logged in");
					}
				}
				else
				{
					return("Please login first.");
				}
			}
			catch(Exception e){ return "Please login first"; 
			}
	}

	@Override
	public String getPlayerStatus(String Username, String Password,
			String IPAddress) {
		try
		{
			for(int i=0; i<list_Server.size(); i++)
			{	
				String user_data_list_NA = list_Server.get(i);
				
				String[] user_profile_NA = user_data_list_NA.split(" ");
				
				if(user_profile_NA[6].equals("1"))
				{
					active_count++;
				}
				else
				{
					inactive_count++;
				}
			}
			
			String count_EU_AS = "\nInactive Users: " + inactive_count + "| Active users: " + active_count;
			DatagramSocket socket = new DatagramSocket();
			byte[] send_result = count_EU_AS.getBytes();
			InetAddress ip_address = InetAddress.getByName("localhost");
			int server_port = 2345;
			DatagramPacket request = new DatagramPacket(send_result, send_result.length, ip_address, server_port);
			socket.send(request);
			byte[] buffer = 	new byte[send_result.length];
			DatagramPacket Reply = new DatagramPacket(buffer, buffer.length);
			socket.receive(Reply);
			String count_ALL =  new String(Reply.getData());
			create_log(count_ALL, "admin");
			return(count_ALL);
		}	
			catch(Exception e){return "User status not available";}
	}

	@Override
	public String suspendAccount(String admin_username, String admin_password,
			String admin_ip_address, String username) {
		// TODO Auto-generated method stub
		char first_char = username.charAt(0);
		boolean flag = false;
		try
		{
			flag = chkUserExist(username, first_char, flag); //checks if user exist
			
			if(flag == true) //if flag is true, then user exists
			{
				//Suspend user by removing
				deleteUser(username, first_char);
				create_log("\nTime:" + dateFormat.format(date) + "\nServer NA deleted a user." + "\nName: " + admin_username + "Removed ", "\nusername: " + username);
				//create log  
				return("Dear " + admin_username + ", You have successfully Suspended " + username + "from the server");
				
			}
			else
			{
				create_log("\nTime:" + dateFormat.format(date) + "\nThis user does not exist on NA Server: ", username);
				return("username does not exists");
			}
			
		}
		catch(Exception e)
		{
			//Suspend user by removing
			deleteUser(username, first_char);
			try {
				create_log("\nTime:" + dateFormat.format(date) + "\nServer NA deleted a user." + "\nName: " + admin_username + "Removed ", "\nusername: " + username);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//create log  
			return("Dear " + admin_username + ", You have successfully Suspended " + username + "from the server.");
		}
	}



	public void deleteUser(String username, char first_char) {
		String[] user_data = new String[list_Server.size()]; 
		for(int i=0;i<list_Server.size();i++) //split user data and transverse list to check if the user already exist
		{
			user_data = server_HashTable.get(first_char).get(i).split(" ");
			if(user_data[0].equals(username))
			{
				synchronized(this){list_Server.remove(i);};
			}
		}
	}

	@Override
	public String transferAccount(String username, String password,
			String old_IP_address, String new_IP_address) {
		// TODO Auto-generated method stub
		boolean flag = false;
		char first_char = username.charAt(0);
		flag = chkUserExist(username, first_char, flag);
		if(flag = true)
		{
			
			deleteUser(username, first_char);		
			return("user has been deleted from the first server. Transfering user...");
		}
		else
		{
			return("The account you are trying to transfer does not exist");
		}
		
	}
	
	public synchronized void create_log(String details, String username)
			throws Exception {
			
			File logFile = new File("Server_Log-"+username+".txt");
			FileWriter fw = new FileWriter(logFile, true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(details);
			bw.close();
		}

	public boolean chkUserExist(String username, char first_char, boolean flag) {
		String[] user_data = new String[list_Server.size()]; 
		for(int i=0;i<list_Server.size();i++) //split user data and transverse list to check if the user already exist
		{
			user_data = server_HashTable.get(first_char).get(i).split(" ");
			if(user_data[0].equals(username))
			{
				flag = true; // flag 1 if the user already exist
				break;
			}
		}
		return flag;
	}


}
