package replicamanager;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UdpKillOrder {

	private int replicaBasePort = 6500;
	String killOrder = "KILL";
	
	public void shutdown(int i) {
		
		// calculate port number of the replica
		int port = replicaBasePort + i;
		DatagramSocket replicaSocket = null;
		try
		{
			replicaSocket = new DatagramSocket();
			byte[] order  = killOrder.getBytes();
			InetAddress host = InetAddress.getByName("localhost");
			DatagramPacket requestKillOrder = new DatagramPacket(order, order.length, host, port);
			replicaSocket.send(requestKillOrder);
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
