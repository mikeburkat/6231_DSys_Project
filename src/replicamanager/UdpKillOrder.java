package replicamanager;

public class UdpKillOrder {

	private int replica0basePort = 6100;
	
	public void shutdown(int i) {
		
		// calculate port number of the replica
		int port = replica0basePort + i;
		
		// TODO send a shutdown notice to the proper replica
	}

}
