package replicamanager;

import replica.Replica;




public class StartReplica {
	
	public void boot(int rep) {
		Replica replica = new Replica(rep);
	}

}
