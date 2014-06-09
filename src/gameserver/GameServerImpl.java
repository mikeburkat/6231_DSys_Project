package gameserver;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;

import other.PlayerData;
import other.ServerLog;
import other.UDPclientStatus;
import other.UDPclientTransfer;
import other.UDPserverStatus;
import other.UDPserverTransfer;

public class GameServerImpl extends GameServerPOA {

	// ------------------------------------------------------------------------
	// Variables to quickly manage ports for servers
	// ------------------------------------------------------------------------
	protected final static int RMI_PORT = 2020;

	protected final static int NORTH_AMERICA_UDP_PORT = 2030;
	protected final static int EUROPE_UDP_PORT = 2031;
	protected final static int ASIA_UDP_PORT = 2032;

	// ------------------------------------------------------------------------
	// Fields to easily set ports and run UDP server and
	// set the server name string when extending this class
	// ------------------------------------------------------------------------
	private String serverName;
	private int UDPclientServer1Port;
	private int UDPclientServer2Port;
	private int UDPserverPort;
	private UDPserverStatus udpSstatus;
	private UDPclientStatus udpCstatus1;
	private UDPclientStatus udpCstatus2;
	private UDPserverTransfer udpStransfer;
	private int myUDPtransferPort;
	// ------------------------------------------------------------------------
	// hash table and other data
	private Hashtable<Character, ArrayList<PlayerData>> ht;
	private int playersOnline;
	private int playersOffline;
	private int totalPlayers;
	private ServerLog log;
	// ------------------------------------------------------------------------
	// Locks for synchronization
	private Object statusLock = new Object();
	private Object statusStringLock = new Object();
	private Object createLock = new Object();
	private Object transferSuspendLock = new Object();
	private Object hashTableLock = new Object();
	private Lock transferUdpLock = new ReentrantLock(true);
	private Condition notTransferring = transferUdpLock.newCondition();
	// ------------------------------------------------------------------------

	/**
	 * This constructor gets called by the parent class which extends the
	 * GameServer. It sets all the important variables that distinguish each
	 * server.
	 * 
	 * @param sName
	 *            Server Name
	 * @param rmiP
	 *            RMI port
	 * @param usp
	 *            UDP server port
	 * @param uc1
	 *            UDP client port 1
	 * @param uc2
	 *            UDP client port 2
	 */
	public GameServerImpl(String sName, int usp, int uc1, int uc2) {
		System.out.println("Creating game server: " + sName + ", UDPserver: "
				+ usp + ", UDPclient1: " + uc1 + ", UDPclient2: " + uc2);

		serverName = sName;
		UDPserverPort = usp;
		UDPclientServer1Port = uc1;
		UDPclientServer2Port = uc2;
		myUDPtransferPort = usp + 10;

		playersOnline = 0;
		playersOffline = 0;
		totalPlayers = 0;

		log = new ServerLog(serverName);

		initHashTable();
		initUDPclients();
		initUDPserver();
	}

	// ------------------------------------------------------------------------

	@Override
	public synchronized String getPlayerStatus(String adminUserName, String adminPassword,
			String ipAddress) {
//		synchronized (statusLock) {

			if (adminUserName.equals("Admin") && adminPassword.equals("Admin")) {

				log.addToAdminLog(adminUserName, "requested player status.");

				String myStatus = getPlayerStatusString();
				String s2 = udpCstatus1.getStatus();
				String s3 = udpCstatus2.getStatus();

				String s = myStatus + ", " + s2 + ", " + s3 + ".";

				log.addToAdminLog(adminUserName, "got status back: " + s);
				return s;
			} else {
				log.addToServerLog("Admin: "
						+ adminUserName
						+ " requested player status, but user name or password was wrong.");
				return "Not allowed, wrong user name or password.";
			}
//		}
	}

	// ------------------------------------------------------------------------

	@Override
	public synchronized String createPlayerAccount(String firstName, String lastName,
			short age, String userName, String password, String ipAddress) {
//		synchronized (createLock) {
			PlayerData pd = getPlayer(userName);
			if (pd != null) {
				String s = "Player: " + userName
						+ " already exists, try a different User Name.";
				log.addToServerLog(s);
				return s;
			}

			try {
				PlayerData p = new PlayerData(firstName, lastName, age,
						userName, password);
				String upperF = userName.substring(0, 1).toUpperCase();
				char firstLetter = upperF.charAt(0);
				ht.get(firstLetter).add(p);
				totalPlayers++;
				log.addToPlayerLog(userName, "created.");
				return "Created";
			} catch (Exception e) {
				String s = "Player: " + userName + " creation failed: "
						+ e.getMessage();
				log.addToServerLog(s);
				return s;
			}
//		}
	}

	// ------------------------------------------------------------------------

	@Override
	public synchronized String playerSignIn(String userName, String password,
			String ipAddress) {
//		synchronized (statusLock) {
			PlayerData pd = getPlayer(userName);
			if (pd == null) {
				log.addToServerLog("Sign In Failed, player " + userName
						+ " not found");
				return "Sign In Failed, player not found";
			}

			String s = pd.signIn(password);

			if (s.equals("Success")) {
				playersOffline--;
				playersOnline++;
				log.addToPlayerLog(userName, "signed in.");
				return "Signed In";
			} else {
				log.addToPlayerLog(userName, s);
				return s;
			}
//		}
	}

	// ------------------------------------------------------------------------

	@Override
	public synchronized String playerSignOut(String userName, String ipAddress) {
//		synchronized (statusLock) {
			PlayerData pd = getPlayer(userName);
			if (pd == null) {
				log.addToServerLog("Sign Out Failed, player " + userName
						+ " not found");
				return "Sign In Failed, player not found";
			}

			String s = pd.signOut();

			if (s.equals("Success")) {
				playersOffline++;
				playersOnline--;
				log.addToPlayerLog(userName, "signed out.");
				return "Signed Out";
			} else {
				log.addToPlayerLog(userName,
						"Sign Out Failed, user not currently signed in");
				return "Sign Out Failed, user not currently signed in";
			}
//		}
	}

	// ------------------------------------------------------------------------

	@Override
	public synchronized String transferAccount(String userName, String password,
			String oldIpAddress, String newIpAddress) {
//		synchronized (transferSuspendLock) {
			PlayerData pd = getPlayer(userName);
			if (pd == null) {
				log.addToServerLog("Transfer failed, player " + userName
						+ " not found.");
				return "Transfer failed, player not found.";
			}
			
			boolean allowed = pd.validatePassword(password);
			if (!allowed) {
				log.addToPlayerLog(userName, "Transfer Failed, password authentification failed");
				return "Transfer Failed, password authentification failed";
			}

			log.addToPlayerLog(userName, "requested transfer from "
					+ oldIpAddress + " to " + newIpAddress);

			int transferPort = getTransferClient(newIpAddress);

			if (transferPort == -1) {
				log.addToPlayerLog(userName, "Invalid transfer IP address "
						+ newIpAddress);
				return "Invalid transfer IP address " + newIpAddress;
			
			} else if (transferPort == myUDPtransferPort) {
				log.addToPlayerLog(userName, "Invalid transfer IP address "
						+ newIpAddress + " can't transfer to myself.");
				return "Invalid transfer IP address " + newIpAddress
						+ " can't transfer to myself.";
			}
			
			UDPclientTransfer transferClient = new UDPclientTransfer(transferPort);
			String outcome = transferClient.transferPlayer(pd);
			

			if (outcome.equals("Created")) {
				log.addToPlayerLog(userName, "Transfer to "+newIpAddress+" successfully.");
				String success = suspendAccount("Admin", "Admin", oldIpAddress,
						userName);
				
				if (success.equals("Success")) {
					log.addToPlayerLog(userName,
							"Transfer account suspended from this server.");
					return "Player was Transfered.";
				
				} else {
					log.addToPlayerLog(userName, success);
					return success;
				}
			
			} else {
				log.addToPlayerLog(userName, outcome);
				return outcome;
			}
//		}
	}

	// ------------------------------------------------------------------------

	private synchronized int getTransferClient(String newIpAddress) {
//		synchronized (transferSuspendLock) {
		boolean matches = Pattern.matches(
				"^[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}$",
				newIpAddress);
		if (!matches) {
			System.out.println("Invalid IP address: " + newIpAddress);
			return -1;
		}

		String s = null;
		s = newIpAddress.substring(0, 3);
		switch (s) {
		case "132":
			return NORTH_AMERICA_UDP_PORT + 10;
		case "93.":
			return EUROPE_UDP_PORT + 10;
		case "182":
			return ASIA_UDP_PORT + 10;
		default:
			System.out.println("Invalid IP address: " + newIpAddress);
			break;
		}
		;
		return -1;
//		}
	}

	// ------------------------------------------------------------------------

	@Override
	public synchronized String suspendAccount(String adminUserName, String adminPassword,
			String ipAddress, String userNameToSuspend) {
//		synchronized (transferSuspendLock) {
			
			if (!adminUserName.equals("Admin") || !adminPassword.equals("Admin")) {
				log.addToServerLog("Admin: " + adminUserName
						+ " requested player suspend on : " + userNameToSuspend
						+ ", but user name or password was wrong.");
				return "Not allowed, wrong user name or password.";
			}

			PlayerData pd = getPlayer(userNameToSuspend);
			if (pd == null) {
				log.addToServerLog("Suspend failed, because player "
						+ userNameToSuspend + " was not found.");
				return "Suspension failed, account not found.";
			}

			String upperF = userNameToSuspend.substring(0, 1).toUpperCase();
			char firstLetter = upperF.charAt(0);
			ArrayList<PlayerData> pdArray = ht.get(firstLetter);
			for (PlayerData p : pdArray) {
				if (userNameToSuspend.equals(p.getUserName())) {
					boolean success = pdArray.remove(p);
					if (success) {
						log.addToPlayerLog(userNameToSuspend, "account suspended successfully.");
						return "Success";
					} else {
						log.addToPlayerLog(userNameToSuspend, "account suspension failed, error in hash table.");
						return "Failed";
					}
				}
			}
			return "Humm weird, technically you shouldn't be here";
//		}
	}

	// ------------------------------------------------------------------------

	private synchronized PlayerData getPlayer(String userName) {
//		synchronized (hashTableLock) {
			String upperF = userName.substring(0, 1).toUpperCase();
			char firstLetter = upperF.charAt(0);
			ArrayList<PlayerData> pd = ht.get(firstLetter);
			for (PlayerData p : pd) {
				if (userName.equals(p.getUserName())) {
					return p;
				}
			}
			return null;
//		}
	}

	// ------------------------------------------------------------------------

	public String getPlayerStatusString() {
//		synchronized (statusStringLock) {
			String s = "";
			s += serverName + ": ";
			s += playersOnline + " online, ";
			s += (totalPlayers - playersOnline) + " offline";

			return s;
//		}
	}

	// ------------------------------------------------------------------------

	void initHashTable() {
		ht = new Hashtable<Character, ArrayList<PlayerData>>();
		for (int i = 0; i < 26; i++) {
			ht.put((char) ('A' + i), new ArrayList<PlayerData>());
		}
	}

	// ------------------------------------------------------------------------
	void initUDPserver() {
		udpSstatus = new UDPserverStatus(this, UDPserverPort);
		new Thread(udpSstatus).start();

		udpStransfer = new UDPserverTransfer(this, myUDPtransferPort);
		new Thread(udpStransfer).start();
	}

	// ------------------------------------------------------------------------

	void initUDPclients() {
		udpCstatus1 = new UDPclientStatus(UDPclientServer1Port);
		udpCstatus2 = new UDPclientStatus(UDPclientServer2Port);

	}

	// ------------------------------------------------------------------------

	// ------------------------------------------------------------------------

}
