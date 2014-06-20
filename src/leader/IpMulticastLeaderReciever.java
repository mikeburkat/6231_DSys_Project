package leader;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class IpMulticastLeaderReciever implements Runnable {

	private int inPort;
	private InetAddress group = null;
	private MulticastSocket multicast;
	private ReplyBuffer replyBuffer;
	
	public IpMulticastLeaderReciever(int port, ReplyBuffer replies) {
		inPort = port;
		replyBuffer = replies;
		try {
			group = InetAddress.getByName("224.0.221.0");
			multicast = new MulticastSocket(inPort);
			multicast.joinGroup(group);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

	@Override
	public void run() {
		
		while (true) {
		
			
			
			
		}
	}
}
