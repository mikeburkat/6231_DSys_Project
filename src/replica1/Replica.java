package replica1;

import gameserver.GameServer;
import gameserver.GameServerHelper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

import org.omg.CORBA.ORB;

public class Replica implements Runnable {

	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	
	// TODO add all the mothods that need to be called on servers through the
	// UDP multicast
	
	
	// ------------------------------------------------------------------------

	private GameServer findServer(String ip) {
		GameServer server = null;
		String s = null;
		
		boolean matches = Pattern.matches(
				"^[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}$", ip);
		if (!matches) {
			System.out.println("Invalid IP address: " + ip);
			return null;
		}

		s = ip.substring(0, 3);
		try {
			switch (s) {
			case "132":
				server = getServer("NA");
				break;
			case "93.":
				server = getServer("EU");
				break;
			case "182":
				server = getServer("AS");
				break;
			default:
				System.out.println("Invalid IP address: " + ip);
				break;
			}
			;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return server;
	}

	// ------------------------------------------------------------------------
	
	public GameServer getServer(String serverName) {
		
		String[] args = new String[1];
		ORB orb = ORB.init(args, null);
		BufferedReader br;
		String na = null;
		try {
			br = new BufferedReader(new FileReader(serverName+".txt"));
			na = br.readLine();
			br.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		org.omg.CORBA.Object naObj = orb.string_to_object(na);
		GameServer serv = GameServerHelper.narrow(naObj);
		
		return serv;
	
	}
	
	// ------------------------------------------------------------------------

}
