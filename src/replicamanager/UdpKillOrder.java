package replicamanager;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UdpKillOrder {

	private int replica0basePort = 6100;
	String killOrder = "KILL";
	
	public void shutdown(int i) {
		
		// calculate port number of the replica
		int port = replica0basePort + i;
		DatagramSocket replicaSocket = null;
		try
		{
			replicaSocket = new DatagramSocket();
			byte[] order  = killOrder.getBytes();
			InetAddress host = InetAddress.getByName("localhost");
			DatagramPacket requestKillOrder = new DatagramPacket(order, order.length, host, port);
		// TODO send a shutdown notice to the proper replica
			replicaSocket.send(requestKillOrder);
		}
		catch (SocketException e) 
		{
			System.out.println("Socket" + e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
