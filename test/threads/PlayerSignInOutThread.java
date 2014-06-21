package threads;

import frontend.FrontEnd;
import frontend.FrontEndHelper;
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
public class PlayerSignInOutThread implements Runnable{

	private String firstName;
	private String lastName;
	private short age;
	private String userName;
	private String password;
	private String ipAddress;

	// ------------------------------------------------------------------------

	public PlayerSignInOutThread(String fName, String lName, int a, String userN,
			String pass, String ip) {
		firstName = fName;
		lastName = lName;
		age = (short) a;
		userName = userN;
		password = pass;
		ipAddress = ip;
	}

	// ------------------------------------------------------------------------

	public boolean createPlayerAccount() {
		System.out.println("create:" + userName + " " + password + " " + ipAddress + " ");
		FrontEnd server = findServer(ipAddress);

		String out = server.createPlayerAccount(firstName, lastName, age,
				userName, password, ipAddress);
		System.out.println(out + "\n");
		boolean result = out.equals("Created") ? true : false;
		return result;

	}

	// ------------------------------------------------------------------------

	public boolean playerSignIn() {
		String out = "";
		FrontEnd server = findServer(ipAddress);
		System.out.println("signIn:" + userName + " " + password + " " + ipAddress + " ");

		out = server.playerSignIn(userName, password, ipAddress);
		System.out.println(out + "\n");
		boolean result = out.equals("Signed In") ? true : false;
		return result;

	}

	// ------------------------------------------------------------------------

	public boolean playerSignOut() {
		FrontEnd server = findServer(ipAddress);
		System.out.println("signOut:" + userName + " " + ipAddress + " ");

		String out = server.playerSignOut(userName, ipAddress);
		System.out.println(out + "\n");
		boolean result = out.equals("Signed Out") ? true : false;
		return result;

	}

	// ------------------------------------------------------------------------

		private FrontEnd findServer(String ip) {
			FrontEnd server = null;
			String s = null;

			boolean matches = Pattern.matches(
					"^[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}$", ip);
			if (!matches) {
				System.out.println("Invalid IP address: " + ip);
				return null;
			}
			try {
				server = getServer("FE");
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			return server;
		}

		// ------------------------------------------------------------------------

		public FrontEnd getServer(String serverName) {

			String[] args = new String[1];
			ORB orb = ORB.init(args, null);
			BufferedReader br;
			String na = null;
			try {
				br = new BufferedReader(new FileReader(serverName + ".txt"));
				na = br.readLine();
				br.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			org.omg.CORBA.Object naObj = orb.string_to_object(na);
			FrontEnd serv = FrontEndHelper.narrow(naObj);

			return serv;

		}

		// ------------------------------------------------------------------------
		
		/**
		 * This is only used for testing concurrency. It is called from the UnitTestClients
		 */
		@Override
		public void run() {
			createPlayerAccount();
			
			for (int i = 0; i < 10; i++) {
				playerSignIn();
				playerSignOut();
			}
			
		}
		
		// ------------------------------------------------------------------------

}
