package frontend;


public class FrontEndImpl extends FrontEndPOA {

	private UdpFrontEndClient udpClient;
	private UdpFrontEndServer udpServer;
	private int clientID = 0;
	private int portNumClient = 6200;
	private int portNumServer = 6201;
	
	public FrontEndImpl() {
		udpClient = new UdpFrontEndClient(portNumClient);
		udpServer = new UdpFrontEndServer(portNumServer);
	}
	
	@Override
	public synchronized String getPlayerStatus(String adminUserName, String adminPassword,
			String ipAddress) {
		
		String request = clientID + "," + "getStatus" + ","
				+ adminUserName + "," + adminPassword + "," + ipAddress;
		udpClient.send(request);
		String reply = udpServer.getReply();
		System.out.println("Front End Got Reply: " + reply);
		return reply;
	}

	@Override
	public synchronized String createPlayerAccount(String firstName, String lastName,
			short age, String userName, String password, String ipAddress) {
		
		String request = clientID + "," + "create"
				+ "," + firstName + "," + lastName + "," + age + "," + userName
				+ "," + password + "," + ipAddress;
		udpClient.send(request);
		String reply = udpServer.getReply();
		System.out.println("Front End Got Reply: " + reply);
		return reply;
		
	}

	@Override
	public synchronized String playerSignIn(String userName, String password,
			String ipAddress) {
		
		String request = clientID + "," + "signIn" + ","
				+ userName + "," + password + "," + ipAddress;
		udpClient.send(request);
		String reply = udpServer.getReply();
		System.out.println("Front End Got Reply: " + reply);
		return reply;
	}

	@Override
	public synchronized String playerSignOut(String userName, String ipAddress) {
		
		String request = clientID + "," + "signOut" + ","
				+ userName + "," + ipAddress;
		
		udpClient.send(request);
		String reply = udpServer.getReply();
		System.out.println("Front End Got Reply: " + reply);
		return reply;
	}

	@Override
	public synchronized String transferAccount(String userName, String password,
			String oldIpAddress, String newIpAddress) {
		
		String request = clientID + "," + "transfer" + ","
				+ userName + "," + password + "," + oldIpAddress + ","
				+ newIpAddress;
		
		udpClient.send(request);
		String reply = udpServer.getReply();
		System.out.println("Front End Got Reply: " + reply);
		return reply;
	}

	@Override
	public synchronized String suspendAccount(String adminUserName, String adminPassword,
			String ipAddress, String userNameToSuspend) {
		
		String request = clientID + "," + "suspend" + ","
				+ adminUserName + "," + adminPassword + "," + ipAddress + ","
				+ userNameToSuspend;
		
		udpClient.send(request);
		String reply = udpServer.getReply();
		System.out.println("Front End Got Reply: " + reply);
		return reply;
	}

}
