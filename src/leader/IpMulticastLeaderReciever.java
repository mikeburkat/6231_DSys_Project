package leader;

import java.io.IOException;
import java.net.MulticastSocket;

public class IpMulticastLeaderReciever implements Runnable {

	
	public IpMulticastLeaderReciever(int port) {
		
		
		try {
			MulticastSocket multicast = new MulticastSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		
		
		
		
	}
}
