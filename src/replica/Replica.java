package replica;

import gameserver.InitServers;

public class Replica implements Runnable {

	private int replica;
	
	public Replica(int rep) {
		replica = rep;
		InitServers servers = new InitServers(replica);
		
		if (replica == 0) {
			// do leader code.
		}
	}
	
	
	
	@Override
	public void run() {
		
	}

}
