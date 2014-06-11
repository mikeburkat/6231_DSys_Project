/*
 * @author Adigun Jide Idris (7128525) and Nneoma Egwuonwu (7139217)
 * Course: COMP6231 - Distributed System Design
 * School: Concordia University
 * Class Name: AdministratorClient.java
 * Class Function: This class is responsible for general form filling perform by clients (either player or admin) and it also checks
 * and validate users choice.
 */
package Game;
import java.util.InputMismatchException;
import java.util.Scanner;


public class UserForm {
	String first_name;
	String last_name;
	String password;
	String username;
	String IP_address;
	static int maxim = 15; //maximum and minimum username
	static int minim = 6;
	Scanner input = new Scanner(System.in);
	int age;
	int number_selected=0;
	String admin_username;
	String admin_password;
	String old_IP_address;
	
//Main Menu
	public void main_menu() throws InputMismatchException
	{
		System.out.println("Select user type");
		System.out.println("1. Player");
		System.out.println("2. Admin");
		try{
			
			int choice = input.nextInt();
			chk_first_choice(choice);
		}
		catch(Exception e)
		{
			System.out.println("Wrong! selection, please select again");
			System.out.println("please start cliet again and enter the correct input");
		}
		
		
	}
	public void chk_first_choice(int choice)
	{
		if(choice == 1)
		{
			start_up_menu();
		}
		else if (choice ==2)
		{
			admin_sign_in();
		}
	}
//Startup menu
	public void start_up_menu() throws InputMismatchException
	{
		System.out.println("-----Welcome, select your option------");
		System.out.print("\nPlease select the number associated with your option of interest");
		System.out.println("\n1. Sign in");
		System.out.println("2. Sign out");
		System.out.println("3. Sign up");
		System.out.println("4. Transfer Account");
		try
		{
			int choice = input.nextInt();
			if(choice == 1)
			{
				number_selected = 1;
			}
			else if(choice == 2)
			{
				number_selected = 2;
			}
			else if(choice == 3)
			{
				number_selected = 3;
			}
			else if(choice == 4)
			{
				number_selected = 4;
			}
		}
		catch(Exception e)
		{
			System.out.println("Wrong! selection, please select again");
			System.out.println("please start cliet again and enter the correct input");
		}
	}
	
	public void user_type_validation(String username, String password) //check type of user (admin or player) and validate username and password
	{

		//check if username and password meet the criteria
		if(!(username.equals("admin") && password.equals("admin")))
		{
			if(username.length() > minim || username.length() < maxim)
			{
				if(password.length() < 6)
				{
					System.out.print("password should be at least 6 character");
				}
			}
			else
			{
				System.out.print("user should be at least 6 characters and not more than 15 characters");
			}
		}
		else
		{
			admin_menu();
		}
	}

	//General Sign in function
	public void sign_in()
	{
		System.out.println("\n------SignIn Page------");
		System.out.println("Enter your username");
		username = input.next();
		System.out.println("Enter your password");
		password = input.next();
		System.out.println("Enter your IP Address");
		IP_address = input.next();
		user_type_validation(username, password);
	}
	//Sign In for Admin
	public void admin_sign_in()
	{
		System.out.println("\n------Admin SignIn Page------");
		System.out.println("Enter your username");
		username = input.next();
		System.out.println("Enter your password");
		password = input.next();
		user_type_validation(username, password);
	}
	public void admin_menu()
	{
		//collect username and password
		System.out.println("\n-----Welcome Admin, select your option------");
		System.out.println("1. Check Player Status (Make sure to start UDP server first)");
		System.out.println("2. Suspend Account");
		System.out.println("3. Sign Out");
		
		try
		{
			int choice = input.nextInt();
			if(choice == 1)
			{
				number_selected = 1;
			}
			else if(choice == 2)
			{
				number_selected = 2;
			}
			else if(choice == 3)
			{
				number_selected = 3;
			}
		}
		catch(Exception e)
		{
			System.out.println("Wrong! selection, please select again");
			System.out.println("please start admin again and enter the correct input");
		}
		
	}

	//Sign Up
	public void sign_up()
	{
		System.out.println("\n------SignUP Page------");
		System.out.println("Enter your Last name");
		last_name = input.next();
		System.out.println("Enter your First name");
		first_name = input.next();
		System.out.println("Enter your age");
		age = input.nextInt();
		System.out.println("Enter your username");
		username = input.next();
		System.out.println("Enter your password");
		password = input.next();
		user_type_validation(username, password);
		System.out.println("Enter your IP Address");
		IP_address = input.next();
	}
	public void sign_out()
	{
		System.out.println("Enter your username");
		username = input.next();
		System.out.println("Enter your password");
		password = input.next();
		System.out.println("Enter your IP Address");
		IP_address = input.next();
		
	}
	public void checkStatus()
	{
		System.out.println("Enter username");
		username = input.next();
		System.out.println("Enter password");
		password = input.next();
		System.out.println("Enter  IP Address");
		IP_address = input.next();
	}
	
	public void suspendAccount()
	{
		System.out.println("Enter Admin's username");
		admin_username = input.next();
		System.out.println("Enter Admin's password");
		admin_password = input.next();
		System.out.println("Enter  IP Address");
		IP_address = input.next();
		System.out.println("Enter User's username");
		username = input.next();
		
	}
	
	public void transferAccount()
	{
		System.out.println("Enter  username");
		username = input.next();
		System.out.println("Enter password");
		password = input.next();
		System.out.print("Enter old IP address");
		old_IP_address = input.next();
		System.out.println("Enter new Ip address");
		IP_address = input.next();
	}
	public void asiaTransfer()
	{
		System.out.println("\n---Welcome to Asia Server---");
		System.out.println("Enter your Last name");
		last_name = input.next();
		System.out.println("Enter your First name");
		first_name = input.next();
		System.out.println("Enter your age");
		age = input.nextInt();
	}
	public void europeTransfer()
	{
		System.out.println("\n---Welcome to Europe Server---");
		System.out.println("Enter your Last name");
		last_name = input.next();
		System.out.println("Enter your First name");
		first_name = input.next();
		System.out.println("Enter your age");
		age = input.nextInt();
	}
	public void nAmericanTransfer()
	{
		System.out.println("\n---Welcome to North America Server---");
		System.out.println("Enter your Last name");
		last_name = input.next();
		System.out.println("Enter your First name");
		first_name = input.next();
		System.out.println("Enter your age");
		age = input.nextInt();
	}
}

