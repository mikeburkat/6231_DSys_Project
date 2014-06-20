package threads;

import gameserver.GameServer;
import gameserver.GameServerHelper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

import org.omg.CORBA.ORB;

//----------------------------------------------------------------------------
/**
 * This is the player client. This represents one player. A player can create
 * its self on the server, sign in and sign out, by calling the appropriate
 * methods.
 * 
 * @author Mike
 */
public class PlayerTransferThread implements Runnable{

	private String firstName;
	private String lastName;
	private short age;
	private String userName;
	private String password;
	private String ipAddress;
	private String newIpAddress;

	// ------------------------------------------------------------------------

	public PlayerTransferThread(String fName, String lName, int a, String userN,
			String pass, String ip, String newIp) {
		firstName = fName;
		lastName = lName;
		age = (short) a;
		userName = userN;
		password = pass;
		ipAddress = ip;
		newIpAddress = newIp;
	}

	// ------------------------------------------------------------------------

	public boolean createPlayerAccount() {
		System.out.println("create:" + userName + " " + password + " " + ipAddress + " ");
		GameServer server = findServer(ipAddress);

		String out = server.createPlayerAccount(firstName, lastName, age,
				userName, password, ipAddress);
		System.out.println(out + "\n");
		boolean result = out.equals("Created") ? true : false;
		return result;

	}
	
	// ------------------------------------------------------------------------
	
	public boolean transferAccount() {
		
		GameServer server = findServer(ipAddress);
		System.out.println("transfer:" + userName + " " + ipAddress + " ");

		String out = server.transferAccount(userName, password, ipAddress, newIpAddress);
		System.out.println(out + "\n");
		
		return false;
	}

	// ------------------------------------------------------------------------

	private GameServer findServer(String ip) {
		GameServer server = null;
		String s = null;
		
		boolean matches = Pattern.matches(
				"^[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}$", ip);
		if (!matches) {
			System.out.println("Invalid IP address: " + ip);
			return null;
		}

		s = ip.substring(0, 3);
		try {
			switch (s) {
			case "132":
				server = getServer("NA");
				break;
			case "93.":
				server = getServer("EU");
				break;
			case "182":
				server = getServer("AS");
				break;
			default:
				System.out.println("Invalid IP address: " + ip);
				break;
			}
			;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return server;
	}

	// ------------------------------------------------------------------------
	
	public GameServer getServer(String serverName) {
		
		String[] args = new String[1];
		ORB orb = ORB.init(args, null);
		BufferedReader br;
		String na = null;
		try {
			br = new BufferedReader(new FileReader(serverName+".txt"));
			na = br.readLine();
			br.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		org.omg.CORBA.Object naObj = orb.string_to_object(na);
		GameServer serv = GameServerHelper.narrow(naObj);
		
		return serv;
	
	}
	
	// ------------------------------------------------------------------------
		/**
		 * This is only used for testing concurrency. It is called from the UnitTestClients
		 */
		@Override
		public void run() {
			
			for (int i = 0; i < 10; i++) {
				createPlayerAccount();
				
				try {
				    Thread.sleep(50);
				} catch(InterruptedException ex) {
				    Thread.currentThread().interrupt();
				}
				
				transferAccount();
				try {
				    Thread.sleep(50);
				} catch(InterruptedException ex) {
				    Thread.currentThread().interrupt();
				}
			}
			
		}
		
		// ------------------------------------------------------------------------

}
