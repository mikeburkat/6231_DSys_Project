package other;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * This class sends a request for a status to be returned from a UDP server. The
 * target port is set in the constructor. It returns the status of the target
 * server.
 * 
 * @author Mike
 */
public class UDPclientTransfer {

	private DatagramSocket socket;
	private DatagramPacket request;
	private InetAddress host;
	private int udpPort;
	
	public UDPclientTransfer(int udp) {
		socket = null;
		udpPort = udp;
	}

	public String transferPlayer(PlayerData pd) {
		String out;
		ByteArrayOutputStream byteOutStream;
		ObjectOutputStream objOut;
		byte[] objectBytes = null;

		try {
			byteOutStream = new ByteArrayOutputStream();
			objOut = new ObjectOutputStream(byteOutStream);

			objOut.writeObject(pd);
			objOut.flush();
			objectBytes = byteOutStream.toByteArray();

			socket = new DatagramSocket();
			host = InetAddress.getByName("localhost");
			System.out.println("playerData length: " + objectBytes.length);
			request = new DatagramPacket(objectBytes, objectBytes.length, host, udpPort);
			socket.send(request);
			
			byte[] buffer = new byte[1024];
			DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
			socket.receive(reply);
			String status = new String(reply.getData());
			out = status.trim();
			
		} catch (IOException e) {
			out = "crash in client call";
			e.printStackTrace();
		} finally {
			if (socket != null) {
				socket.close();
			}
		}

		return out;
	}

}
