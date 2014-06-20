package replica;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

import leader.IpMulticastLeaderReciever;
import leader.IpMulticastLeaderSender;
import leader.ReplyBuffer;
import leader.RequestBuffer;
import leader.UdpFrontEndRequestServer;
import leader.UdpReplicaManager;

import org.omg.CORBA.ORB;

import gameserver.GameServer;
import gameserver.GameServerHelper;
import gameserver.InitServers;

//------------------------------------------------------------------------

public class Replica {

	private int replicaID;
	private int multicastReplicaRecievePort = 4000;
	private int multicastReplicaSendPort = 4001;
	private int replicaManagerPort = 4002;
	private int frontEndSendsFromPort = 6200;
	private int frontEndRecieveInPort = 6201;
	int basePort = 6210;
	
	// ------------------------------------------------------------------------
	
	public Replica(int rep) throws IOException {
		replicaID = rep;
		InitServers servers = new InitServers(replicaID);
		
		if (replicaID == 0) {
			// do leader code: 
			// setup link to Replica Manger.
			UdpReplicaManager manager = new UdpReplicaManager(4002);
			
			// setup IP multicast group to send messages to other replicas.
			IpMulticastLeaderSender multicastSender = new IpMulticastLeaderSender(multicastReplicaRecievePort);
			
			// setup request buffer
			RequestBuffer requests = new RequestBuffer(multicastSender);
			
			// setup link to get messages from front end.
			UdpFrontEndRequestServer frontEnd = new UdpFrontEndRequestServer(frontEndSendsFromPort, frontEndRecieveInPort, requests);
			new Thread(frontEnd).start();
			
			// setup replyBuffer
			ReplyBuffer replyBuffer = new ReplyBuffer(3, manager, frontEnd, requests);
			
			// setup IP multicast group to recieve messages to other replicas.
			IpMulticastLeaderReciever multicastReciever = new IpMulticastLeaderReciever(multicastReplicaSendPort, replyBuffer);
			new Thread(multicastReciever).start();
			
		} else {
			// do backup code:
			
		}
		
		// common code: 
		// join the IP multicast group to get messages from leader.
		IpMulticastServer multicastServer = new IpMulticastServer(this, multicastReplicaRecievePort, multicastReplicaSendPort);
		new Thread(multicastServer).start();
		
		// setup a udp killswitch.
		UdpKillSwitch killSwitch = new UdpKillSwitch(this, basePort+replicaID);
		
		
		
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
					server = getServer("NA"+replicaID);
					break;
				case "93.":
					server = getServer("EU"+replicaID);
					break;
				case "182":
					server = getServer("AS"+replicaID);
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

		public int getID() {
			return replicaID;
		}
		
		// ------------------------------------------------------------------------
		
		public void shutdown() {
			
		}
		
}
