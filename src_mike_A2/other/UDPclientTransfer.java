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

	DatagramSocket socket;
	DatagramPacket request;
	int udpPort;
	InetAddress host;

	public UDPclientTransfer(int udp) {
		socket = null;
		udpPort = udp;
	}

	public String getStatus() {
		String out = "";
		try {
			String s = "status";
			byte[] a = s.getBytes();
			socket = new DatagramSocket();
			host = InetAddress.getByName("localhost");
			request = new DatagramPacket(a, s.length(), host, udpPort);

			socket.send(request);
			byte[] buffer = new byte[1000];
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

	public String transferPlayer(PlayerData pd) {
		String out;
		ByteArrayOutputStream byteOutStream;
		ObjectOutputStream objOut;
		byte[] pBytes = null;

		try {
			byteOutStream = new ByteArrayOutputStream();
			objOut = new ObjectOutputStream(byteOutStream);

			objOut.writeObject(pd);
			objOut.flush();
			pBytes = byteOutStream.toByteArray();

			socket = new DatagramSocket();
			host = InetAddress.getByName("localhost");
			System.out.println("playerData length: " + pBytes.length);
			request = new DatagramPacket(pBytes, pBytes.length, host, udpPort);
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
