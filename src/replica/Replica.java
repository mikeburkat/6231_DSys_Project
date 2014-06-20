package replica;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

import leader.IpMulticastLeader;
import leader.IpMulticastLeaderReciever;

import org.omg.CORBA.ORB;

import gameserver.GameServer;
import gameserver.GameServerHelper;
import gameserver.InitServers;

//------------------------------------------------------------------------

public class Replica {

	private int replica;
	private int multicastReplicaRecieve = 4000;
	private int multicastReplicaSend = 4001;
	
	// ------------------------------------------------------------------------
	
	public Replica(int rep) {
		replica = rep;
		InitServers servers = new InitServers(replica);
		
		if (replica == 0) {
			// do leader code: 
			// setup link to get messages from front end.
			// setup link to Replica Manger.
			// setup IP multicast group to send messages to other replicas.
			IpMulticastLeaderSender multicastSender = new IpMulticastLeaderSender(multicastReplicaRecieve);
			IpMulticastLeaderReciever multicastReciever = new IpMulticastLeaderReciever(multicastReplicaSend);
			
			
		} else {
			// do backup code:
			// join the IP multicast group to get messages from leader.
			
		}
		
		// common code: 
		// setup a udp killswitch.
		
		
	}
	
	// ------------------------------------------------------------------------
	
	public String getPlayerStatus(String adminUserName, String adminPassword,
			String ipAddress) {
		GameServer server = findServer(ipAddress);
		System.out.println(adminUserName +" "+ adminPassword +" "+ ipAddress + " ");
		
		String out = server.getPlayerStatus(adminUserName, adminPassword, ipAddress);
		System.out.println(out +"\n");
		return out;
		
	}
	
	// ------------------------------------------------------------------------
	
	public String createPlayerAccount(String firstName, String lastName,
			short age, String userName, String password, String ipAddress) {
		GameServer server = findServer(ipAddress);

		String out = server.createPlayerAccount(firstName, lastName, age,
				userName, password, ipAddress);
		System.out.println(out + "\n");
		return out;
	}
	
	// ------------------------------------------------------------------------
	
	public String playerSignIn(String userName, String password,
			String ipAddress) {
		String out = "";
		GameServer server = findServer(ipAddress);
		System.out.println("signIn:" + userName + " " + password + " " + ipAddress + " ");

		out = server.playerSignIn(userName, password, ipAddress);
		System.out.println(out + "\n");
		return out;
	}
	
	// ------------------------------------------------------------------------
	
	public String playerSignOut(String userName, String ipAddress) {
		GameServer server = findServer(ipAddress);
		System.out.println("signOut:" + userName + " " + ipAddress + " ");

		String out = server.playerSignOut(userName, ipAddress);
		System.out.println(out + "\n");
		return out;
	}
	
	// ------------------------------------------------------------------------
	
	public String transferAccount(String userName, String password,
			String oldIpAddress, String newIpAddress) {
		GameServer server = findServer(oldIpAddress);
		System.out.println("transfer:" + userName + " " + newIpAddress + " ");
		
		String out = server.transferAccount(userName, password, oldIpAddress, newIpAddress);
		System.out.println(out);
		return out;
	}
	
	// ------------------------------------------------------------------------
	
	public synchronized String suspendAccount(String adminUserName, String adminPassword,
			String ipAddress, String userNameToSuspend) {
		GameServer server = findServer(ipAddress);
		System.out.println(adminUserName +" "+ adminPassword +" "+ ipAddress + " "+ userNameToSuspend);
		
		String out = server.suspendAccount(adminUserName, adminPassword, ipAddress, userNameToSuspend);
		System.out.println(out +"\n");
		return out;
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
					server = getServer("NA"+replica);
					break;
				case "93.":
					server = getServer("EU"+replica);
					break;
				case "182":
					server = getServer("AS"+replica);
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
			String ior = null;
			try {
				br = new BufferedReader(new FileReader(serverName+".txt"));
				ior = br.readLine();
				br.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			org.omg.CORBA.Object naObj = orb.string_to_object(ior);
			GameServer serv = GameServerHelper.narrow(naObj);
			
			return serv;
		
		}
		
		// ------------------------------------------------------------------------

}
