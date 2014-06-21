package client;

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
 * This is the administrator client. This represents one administrator. An
 * administrator can get the player status.
 * 
 * @author Mike
 */
public class AdministratorClient {

	private String adminUserName;
	private String adminPassword;
	private String ipAddress;

	// ------------------------------------------------------------------------

	public AdministratorClient(String aUserN, String aPass, String ip) {
		adminUserName = aUserN;
		adminPassword = aPass;
		ipAddress = ip;
	}

	// ------------------------------------------------------------------------

	public String getPlayerStatus(String aUserN, String aPass, String ip) {
		adminUserName = aUserN;
		adminPassword = aPass;
		ipAddress = ip;
		return getPlayerStatus();
	}

	// ------------------------------------------------------------------------

	public String getPlayerStatus() {
		FrontEnd server = findServer(ipAddress);
		System.out.println(adminUserName + " " + adminPassword + " "
				+ ipAddress + " ");

		String s = server.getPlayerStatus(adminUserName, adminPassword,
				ipAddress);
		System.out.println(s + "\n");
		return s;
	}

	// ------------------------------------------------------------------------

	public boolean suspendAccount(String userNameToSuspend) {
		FrontEnd server = findServer(ipAddress);
		System.out.println(adminUserName + " " + adminPassword + " "
				+ ipAddress + " " + userNameToSuspend);

		String out = server.suspendAccount(adminUserName, adminPassword,
				ipAddress, userNameToSuspend);
		System.out.println(out + "\n");

		boolean result = out.equals("Success") ? true : false;
		return result;
	}

	// ------------------------------------------------------------------------
	// TODO: convert this to find the front end server
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
	// TODO: convert this to get the front end server
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

}
