package gameserver;

import java.io.File;

public class InitServers {

	public InitServers(int replica) {
		
		deleteDIR("NA"+replica);
		deleteDIR("EU"+replica);
		deleteDIR("AS"+replica);
		
		
		NorthAmericaServer na = new NorthAmericaServer(replica);
		EuropeServer eu = new EuropeServer(replica);
		AsiaServer as = new AsiaServer(replica);

		new Thread (na).start();
		new Thread (eu).start();
		new Thread (as).start();
		
	}
	
	private static void deleteDIR(String in) {
		
		File dir = new File(in);
		if (dir.isDirectory()) {
			for (File log : dir.listFiles()){
				log.delete();
			}
		}
	}
}
