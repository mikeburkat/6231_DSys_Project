package leader;

import java.io.IOException;
import java.net.MulticastSocket;

import other.RequestData;

public class IpMulticastLeaderSender {

	
	public IpMulticastLeaderSender(int port) {
		
		
		try {
			MulticastSocket multicast = new MulticastSocket();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void send(RequestData data) {
		
		
	}
	

}
