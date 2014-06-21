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
 * This is the administrator client. This represents one administrator.
 * An administrator can get the player status.
 * 
 * @author Mike
 */
public class AdministratorSuspendThread implements Runnable {

	private String adminUserName;
	private String adminPassword;
	private String ipAddress;
	private String userNameToSuspend;
	
	// ------------------------------------------------------------------------
	
	public AdministratorSuspendThread(String aUserN, String aPass, String ip, String userSuspend) {
		adminUserName = aUserN;
		adminPassword = aPass;
		ipAddress = ip;
		userNameToSuspend = userSuspend;
	}

	// ------------------------------------------------------------------------
	
	public boolean suspendAccount() {
		FrontEnd server = findServer(ipAddress);
		System.out.println("suspend:" + adminUserName +" "+ adminPassword +" "+ ipAddress + " "+ userNameToSuspend);
		
		String out = server.suspendAccount(adminUserName, adminPassword, ipAddress, userNameToSuspend);
		System.out.println(out +"\n");
		
		boolean result = out.equals("Success") ? true : false;
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
		for (int i = 0; i < 10; i++) {
			suspendAccount();
			
			try {
			    Thread.sleep(150);
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
		}
	}
	
	// ------------------------------------------------------------------------

}
