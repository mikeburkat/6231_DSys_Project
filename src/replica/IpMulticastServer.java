package replica;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class IpMulticastServer implements Runnable {
	
	private MulticastSocket reciever = null;
	private MulticastSocket sender = null;
	
	public IpMulticastServer(int recievePort, int sendPort) {
		
		try {
			reciever = new MulticastSocket(recievePort);
			InetAddress group = InetAddress.getByName("localhost");
			reciever.joinGroup(group);
			
			sender = new MulticastSocket(sendPort);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void run() {
		
		byte objectBytes[] = new byte[1024];
		
		while (true) {
			// wait for request.
			DatagramPacket reply = new DatagramPacket(objectBytes, objectBytes.length);
			try {
				reciever.receive(reply);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			
			
			// send back response.
		}
	}

}
