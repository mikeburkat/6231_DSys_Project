package replicamanager;

import java.util.ArrayList;

import frontend.FrontEndServer;

public class ReplicaManager {

	private int numberOfReplicas = 3;
	private int replicas[];
	private UdpKillOrder kill;
	private StartReplica starter;
	private UdpLeaderComm udpWithLeader;
	private int replicaManagerPort = 4002;

	public ReplicaManager() {
		replicas = new int[numberOfReplicas];

		resetAllCounts();
		kill = new UdpKillOrder();
		starter = new StartReplica();
		
		
		for (int i = 0; i < numberOfReplicas; i++) {
			starter.boot(i);
		}
		
		udpWithLeader = new UdpLeaderComm(this, replicaManagerPort);
		new Thread(udpWithLeader).start();
		
		
	}
	

	public void incrementWrongs(ArrayList<Integer> wrong) {
		
		for (int i = 0; i < numberOfReplicas; i++) {
			if (wrong.contains(i)) {
				replicas[i]++;
				if (replicas[i] == 3) {
					reboot(i);
				}
			} else {
				replicas[i] = 0;
			}
		}
	}

	public void resetAllCounts() {
		for (int i = 0; i < replicas.length; i++) {
			replicas[i] = 0;
		}
	}

	public void reboot(int replica) {
		kill.shutdown(replica);
		try {
		    Thread.sleep(500);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		starter.boot(replica);
	}

	public static void main(String[] args) {
		ReplicaManager rm = new ReplicaManager();
		
		FrontEndServer fe = new FrontEndServer();
		new Thread(fe).start();
	}

}
