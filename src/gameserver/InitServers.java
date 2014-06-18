package gameserver;

import java.io.File;

public class InitServers {

	public static void main(String[] args) {
		
		deleteDIR("NA");
		deleteDIR("EU");
		deleteDIR("AS");
		
		
		NorthAmericaServer na = new NorthAmericaServer();
		EuropeServer eu = new EuropeServer();
		AsiaServer as = new AsiaServer();

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
