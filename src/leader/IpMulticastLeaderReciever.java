package leader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import other.ReplyData;
import other.RequestData;

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

		try {
			byte[] buffer = new byte[1024];
			DatagramPacket message = new DatagramPacket(buffer, buffer.length);

			while (true) {
				multicast.receive(message);

				// de-serialize request object
				ByteArrayInputStream baos;
				ObjectInputStream oos;
				ReplyData reply = null;
				try {
					baos = new ByteArrayInputStream(buffer);
					oos = new ObjectInputStream(baos);
					reply = (ReplyData) oos.readObject();
					System.out.println(reply.toString());
					replyBuffer.add(reply);
				} catch (ClassNotFoundException | IOException e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		} finally {
			if (multicast != null) {
				multicast.close();
			}
		}

	}
}
