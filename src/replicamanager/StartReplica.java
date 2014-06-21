package replicamanager;

import java.io.IOException;

import replica.Replica;

public class StartReplica {
	
	public void boot(int rep) {
		try {
			Replica r = new Replica(rep);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
