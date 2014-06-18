package replicamanager;

public class UdpLeaderComm {
	
	private int port = 6000;
	private ReplicaManager rm;

	public UdpLeaderComm(ReplicaManager replicaManager) {
		rm = replicaManager;
		// TODO setup udp channel
	}
	
	public void waitForCommand() {
		
		// TODO get a request from the udp
		// parse the request to get the command.
		// call the command on the RM
		// return a response.
		
	}

}
