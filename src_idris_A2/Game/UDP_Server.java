/*
 * @author Adigun Jide Idris (7128525) and Nneoma Egwuonwu (7139217)
 * Course: COMP6231 - Distributed System Design
 * School: Concordia University
 * Class Name: UDP_Server.java
 * Class Function: This Class is specifically responsible for creating an UDP server and
 *  replying the client of the number of client online and offline
 * in each server
 * Reference: http://osamasays.wordpress.com/2014/02/05/distributed-player-status-system-in-java/
 */
package Game;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDP_Server {

	public static void main(String[] args) throws Exception {
	
		DatagramSocket server_socket = new DatagramSocket(2345);
		byte[] buffer = new byte[1000];
		System.out.println("Server is waiting...");
		while(true)
		{
			DatagramPacket request = new DatagramPacket(buffer, buffer.length);
			server_socket.receive(request);
			
			DatagramPacket reply = new DatagramPacket(request.getData(), request.getLength(), request.getAddress(), request.getPort());
			server_socket.send(reply);
		
		}
		
	}

}