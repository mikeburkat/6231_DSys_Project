package frontend;

import java.util.ArrayList;
import java.util.List;

import org.omg.CORBA.ORB;

import gameserver.GameServerPOA;

public class GameServerImpl extends GameServerPOA implements CallBack {

	private ORB orb;
	private List<Client> clientList = new ArrayList();
	private UdpFrontEndServer udpServer;

	public void setORB(ORB orb_val) {
		orb = orb_val;
	}

	@Override
	public String getPlayerStatus(String adminUserName, String adminPassword,
			String ipAddress) {
		// Making the client obj to be stored in the list
		Client client = new Client(adminUserName, adminPassword, ipAddress);
		int addedClientId = addToClientRequestList(client);

		// Making a callback instance to pass to the leader
		CallBack clientCallBack = new GameServerImpl();
		String getPlayerStatusRequest = addedClientId + "," + "getStatus" + ","
				+ adminUserName + "," + adminPassword + "," + ipAddress;
		int portNumClient = 6200;
		int portNumServer = 6201;
		String GET_STATUS_FAIL = "GET_STATUS_FAIL";
		UdpFrontEndClient udpClient = new UdpFrontEndClient(
				getPlayerStatusRequest, portNumClient);
		udpServer = new UdpFrontEndServer(portNumServer, clientCallBack,
				addedClientId);
		try {
			udpServer.start();
			udpClient.UDPSend();
			System.out.println("Fetching Result....");
			while (!clientList.get(addedClientId).isStatus()) {
				
			}
			return clientList.get(addedClientId).getResult();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return GET_STATUS_FAIL;
	}

	@Override
	public String createPlayerAccount(String firstName, String lastName,
			short age, String userName, String password, String ipAddress) {
		// TODO Auto-generated method stub
		int portNumClient = 6200;
		int portNumServer = 6201;
		// Making the client obj to be stored in the list
		Client client = new Client(firstName, lastName, age, userName,
				password, ipAddress);
		int addedClientId = addToClientRequestList(client);

		// Making a callback instance to pass to the leader
		CallBack clientCallBack = new GameServerImpl();

		String CREATE_PLAYER_ACCOUNT_FAIL = "CREATE_PLAYER_ACCOUNT_FAIL";
		String createPlayerAccountRequest = addedClientId + "," + "create"
				+ "," + firstName + "," + lastName + "," + age + "," + userName
				+ "," + password + "," + ipAddress;
		UdpFrontEndClient udpClient = new UdpFrontEndClient(
				createPlayerAccountRequest, portNumClient);
		udpServer = new UdpFrontEndServer(portNumServer, clientCallBack,
				addedClientId);
		try {
			udpClient.UDPSend();
			udpServer.start();
			System.out.println("Fetching Result....");
			while (!clientList.get(addedClientId).isStatus()) {
				
			}
			return clientList.get(addedClientId).getResult();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return CREATE_PLAYER_ACCOUNT_FAIL;
	}

	@Override
	public String playerSignIn(String userName, String password,
			String ipAddress) {
		// TODO Auto-generated method stub
		int portNumClient = 6200;
		int portNumServer = 6201;
		// Making the client obj to be stored in the list
		Client client = new Client(userName, password, ipAddress);
		int addedClientId = addToClientRequestList(client);

		// Making a callback instance to pass to the leader
		CallBack clientCallBack = new GameServerImpl();

		String PLAYER_SIGNIN_FAIL = "PLAYER_SIGNIN_FAIL";
		String playerSignInRequest = addedClientId + "," + "signIn" + ","
				+ userName + "," + password + "," + ipAddress;
		UdpFrontEndClient udpClient = new UdpFrontEndClient(
				playerSignInRequest, portNumClient);
		udpServer = new UdpFrontEndServer(portNumServer, clientCallBack,
				addedClientId);
		try {
			udpClient.UDPSend();
			udpServer.start();
			System.out.println("Fetching Result....");
			while (!clientList.get(addedClientId).isStatus()) {
				
			}
			return clientList.get(addedClientId).getResult();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return PLAYER_SIGNIN_FAIL;
	}

	@Override
	public String playerSignOut(String userName, String ipAddress) {
		// TODO Auto-generated method stub
		int portNumClient = 6200;
		int portNumServer = 6201;

		// Making the client obj to be stored in the list
		Client client = new Client(userName, ipAddress);
		int addedClientId = addToClientRequestList(client);

		// Making a callback instance to pass to the leader
		CallBack clientCallBack = new GameServerImpl();

		String PLAYER_SIGNOUT_FAIL = "PLAYER_SIGNOUT_FAIL";
		String playerSignOutRequest = addedClientId + "," + "signOut" + ","
				+ userName + "," + ipAddress;
		UdpFrontEndClient udpClient = new UdpFrontEndClient(
				playerSignOutRequest, portNumClient);
		udpServer = new UdpFrontEndServer(portNumServer, clientCallBack,
				addedClientId);
		try {
			udpServer.start();
			udpClient.UDPSend();
			System.out.println("Fetching Result....");
			while (!clientList.get(addedClientId).isStatus()) {
				
			}
			return clientList.get(addedClientId).getResult();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return PLAYER_SIGNOUT_FAIL;
	}

	@Override
	public String transferAccount(String userName, String password,
			String oldIpAddress, String newIpAddress) {
		// TODO Auto-generated method stub
		int portNumClient = 6200;
		int portNumServer = 6201;
		int varient = 1;

		// Making the client obj to be stored in the list
		Client client = new Client(userName, password, oldIpAddress,
				newIpAddress, varient);
		int addedClientId = addToClientRequestList(client);

		// Making a callback instance to pass to the leader
		CallBack clientCallBack = new GameServerImpl();

		String TRANSFER_ACCOUNT_FAIL = "TRANSFER_ACCOUNT_FAIL";
		String tranferAccountRequest = addedClientId + "," + "transfer" + ","
				+ userName + "," + password + "," + oldIpAddress + ","
				+ newIpAddress;
		UdpFrontEndClient udpClient = new UdpFrontEndClient(
				tranferAccountRequest, portNumClient);
		udpServer = new UdpFrontEndServer(portNumServer, clientCallBack,
				addedClientId);
		try {
			udpServer.start();
			udpClient.UDPSend();
			System.out.println("Fetching Result....");
			while (!clientList.get(addedClientId).isStatus()) {
				
			}
			return clientList.get(addedClientId).getResult();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return TRANSFER_ACCOUNT_FAIL;
	}

	@Override
	public String suspendAccount(String adminUserName, String adminPassword,
			String ipAddress, String userNameToSuspend) {
		// TODO Auto-generated method stub
		int portNumClient = 6200;
		int portNumServer = 6201;
		int varient = 2;
		// Making the client obj to be stored in the list
		Client client = new Client(adminUserName, adminPassword, ipAddress,
				userNameToSuspend, varient);
		int addedClientId = addToClientRequestList(client);

		// Making a callback instance to pass to the leader
		CallBack clientCallBack = new GameServerImpl();

		String SUSPEND_ACCOUNT_FAIL = "SUSPEND_ACCOUNT_FAIL";
		String suspendAccountRequest = addedClientId + "," + "suspend" + ","
				+ adminUserName + "," + adminPassword + "," + ipAddress + ","
				+ userNameToSuspend;
		UdpFrontEndClient udpClient = new UdpFrontEndClient(
				suspendAccountRequest, portNumClient);
		udpServer = new UdpFrontEndServer(portNumServer, clientCallBack,
				addedClientId);
		try {
			udpServer.start();
			udpClient.UDPSend();
			System.out.println("Fetching Result....");
			while (!clientList.get(addedClientId).isStatus()) {
				
			}
			return clientList.get(addedClientId).getResult();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUSPEND_ACCOUNT_FAIL;
	}

	@Override
	public void callback(int id, String result) {
		// TODO Auto-generated method stub
		clientList.get(id).setStatus(true);
		clientList.get(id).setResult(result);
	}

	public int addToClientRequestList(Client client) {
		int clientId = clientList.size();
		client.setClientId(clientId);
		clientList.add(client);
		return clientId;
	}

}
