package gameserver;

import java.io.File;

public class InitServers {

	Thread naT;
	Thread euT;
	Thread asT;
	
	NorthAmericaServer na;
	EuropeServer eu;
	AsiaServer as;
	
	public InitServers(int replica) {
		
		deleteDIR("NA"+replica);
		deleteDIR("EU"+replica);
		deleteDIR("AS"+replica);
		
		na = new NorthAmericaServer(replica);
		eu = new EuropeServer(replica);
		as = new AsiaServer(replica);

		naT = new Thread(na);
		euT = new Thread(eu);
		asT = new Thread(as);
		
		naT.start();
		euT.start();
		asT.start();
	}
	
	private static void deleteDIR(String in) {
		
		File dir = new File(in);
		if (dir.isDirectory()) {
			for (File log : dir.listFiles()){
				log.delete();
			}
		}
	}
	
	public void shutdown() {
		na.shutdown();
		eu.shutdown();
		as.shutdown();
	}
}
