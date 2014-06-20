package client;

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
public class PlayerClient {

	private String firstName;
	private String lastName;
	private short age;
	private String userName;
	private String password;
	private String ipAddress;

	// ------------------------------------------------------------------------

	public PlayerClient(String fName, String lName, int a, String userN,
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
		System.out.println("create:" + userName + " " + password + " "
				+ ipAddress + " ");
		GameServer server = findServer(ipAddress);

		String out = server.createPlayerAccount(firstName, lastName, age,
				userName, password, ipAddress);
		System.out.println(out + "\n");
		boolean result = out.equals("Created");
		return result;

	}

	// ------------------------------------------------------------------------

	public boolean playerSignIn() {
		String out = "";
		GameServer server = findServer(ipAddress);
		System.out.println("signIn:" + userName + " " + password + " "
				+ ipAddress + " ");

		out = server.playerSignIn(userName, password, ipAddress);
		System.out.println(out + "\n");
		boolean result = out.equals("Signed In");
		return result;

	}

	// ------------------------------------------------------------------------

	public boolean playerSignOut() {
		GameServer server = findServer(ipAddress);
		System.out.println("signOut:" + userName + " " + ipAddress + " ");

		String out = server.playerSignOut(userName, ipAddress);
		System.out.println(out + "\n");
		boolean result = out.equals("Signed Out");
		return result;

	}

	// ------------------------------------------------------------------------

	public boolean transferAccount(String newIpAddress) {

		GameServer server = findServer(ipAddress);
		System.out.println("transfer:" + userName + " " + ipAddress + " ");

		String out = server.transferAccount(userName, password, ipAddress,
				newIpAddress);
		System.out.println(out);

		boolean result = out.equals("Player was Transfered.");
		return result;
	}

	// ------------------------------------------------------------------------
	// TODO: convert this to find the front end server
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
		try {
			server = getServer("FE");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return server;
	}

	// ------------------------------------------------------------------------
	// TODO: convert this to get the front end server
	// ------------------------------------------------------------------------
	public GameServer getServer(String serverName) {

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
		GameServer serv = GameServerHelper.narrow(naObj);

		return serv;

	}

	// ------------------------------------------------------------------------

}
