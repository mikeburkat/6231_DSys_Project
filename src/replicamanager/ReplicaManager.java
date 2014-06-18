package replicamanager;

public class ReplicaManager implements Runnable {

	private int replicas[];
	private UdpKillOrder kill; 
	private StartReplica starter;
	private UdpLeaderComm udpWithLeader;
	
	public ReplicaManager() {
		replicas = new int[3];
		resetAllCounts();
		kill = new UdpKillOrder();
		starter = new StartReplica();
	}
	
	public void replicaError(int replica) {
		if ( replica <= 0 && replica < replicas.length) {
			int temp = ++replicas[replica];
			resetAllCounts();
			
			if (temp == 3) {
				reboot(replica);
			} else {
				replicas[replica] = temp;
			}
		}
	}
	
	public void resetAllCounts() {
		for (int i = 0; i < replicas.length; i++) {
			replicas[i] = 0;
		}
	}

	public void reboot(int replica) {
		kill.shutdown(1);
		starter.boot(1);
	}
	
	
	@Override
	public void run() {
		
		udpWithLeader = new UdpLeaderComm(this);
		udpWithLeader.waitForCommand();
		
	}

}
