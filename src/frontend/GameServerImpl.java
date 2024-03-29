package frontend;
import org.omg.CORBA.ORB;

import gameserver.GameServerPOA;

public class GameServerImpl extends GameServerPOA {

	ORB orb;

	public void setORB(ORB orb_val) {
		orb = orb_val;
	}

	@Override
	public String getPlayerStatus(String adminUserName, String adminPassword,
			String ipAddress) {
		// TODO Auto-generated method stub
		String getPlayerStatusRequest ="getStatus" + "," + adminUserName + "," + adminPassword + "," + ipAddress;
		int portNum = 6200;
		String getPlayerStatusResult;
		String GET_STATUS_FAIL = "GET_STATUS_FAIL";
		UdpFrontEndClient udpClient = new UdpFrontEndClient(getPlayerStatusRequest, portNum);
		try {
			udpClient.UDPSend();
			getPlayerStatusResult = udpClient.UDPReceive();
			return getPlayerStatusResult;
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
		int portNum = 6200;
		String createPlayerAccountResult;
		String CREATE_PLAYER_ACCOUNT_FAIL = "CREATE_PLAYER_ACCOUNT_FAIL";
		String createPlayerAccountRequest = "create" + "," + firstName + "," + lastName + "," + age + "," + userName + "," + password + "," + ipAddress;
		UdpFrontEndClient udpClient = new UdpFrontEndClient(createPlayerAccountRequest, portNum);
		try {
			udpClient.UDPSend();
			createPlayerAccountResult = udpClient.UDPReceive();
			return createPlayerAccountResult;
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
		int portNum = 6200;
		String playerSignInResult;
		String PLAYER_SIGNIN_FAIL = "PLAYER_SIGNIN_FAIL";
		String playerSignInRequest = "signIn" + "," + userName + "," + password + "," + ipAddress;
		UdpFrontEndClient udpClient = new UdpFrontEndClient(playerSignInRequest, portNum);
		try {
			udpClient.UDPSend();
			playerSignInResult = udpClient.UDPReceive();
			return playerSignInResult;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return PLAYER_SIGNIN_FAIL;
	}

	@Override
	public String playerSignOut(String userName, String ipAddress) {
		// TODO Auto-generated method stub
		int portNum = 6200;
		String playerSignOutResult;
		String PLAYER_SIGNOUT_FAIL = "PLAYER_SIGNOUT_FAIL";
		String playerSignOutRequest = "signOut" + "," + userName + "," + ipAddress;
		UdpFrontEndClient udpClient = new UdpFrontEndClient(playerSignOutRequest, portNum);
		try {
			udpClient.UDPSend();
			playerSignOutResult = udpClient.UDPReceive();
			return playerSignOutResult;
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
		int portNum = 6200;
		String transferAccountResult;
		String TRANSFER_ACCOUNT_FAIL = "TRANSFER_ACCOUNT_FAIL";
		String tranferAccountRequest = "transfer" + "," + userName + "," + password + "," + oldIpAddress + "," + newIpAddress;
		UdpFrontEndClient udpClient = new UdpFrontEndClient(tranferAccountRequest, portNum);
		try {
			udpClient.UDPSend();
			transferAccountResult = udpClient.UDPReceive();
			return transferAccountResult;
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
		int portNum = 6200;
		String suspendAccountResult;
		String SUSPEND_ACCOUNT_FAIL = "SUSPEND_ACCOUNT_FAIL";
		String suspendAccountRequest = "suspend" + "," + adminUserName + "," + adminPassword + "," + ipAddress + "," + userNameToSuspend;
		UdpFrontEndClient udpClient = new UdpFrontEndClient(suspendAccountRequest, portNum);
		try {
			udpClient.UDPSend();
			suspendAccountResult = udpClient.UDPReceive();
			return suspendAccountResult;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUSPEND_ACCOUNT_FAIL;
	}

}
