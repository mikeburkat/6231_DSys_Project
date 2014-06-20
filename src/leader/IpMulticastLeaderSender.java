package leader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import other.RequestData;

public class IpMulticastLeaderSender {

	private MulticastSocket multicast;
	private InetAddress group = null;
	private int outPort;
	
	public IpMulticastLeaderSender(int port) {
		outPort = port;
		try {
			group = InetAddress.getByName("224.0.222.0");
			multicast = new MulticastSocket();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void send(RequestData data) {
		System.out.println("Sending Multicast Request: " + data.toString());
		// Serialize the message
		ByteArrayOutputStream byteOutStream;
		ObjectOutputStream objOut;
		byte[] request = null;

		try {
			byteOutStream = new ByteArrayOutputStream();
			objOut = new ObjectOutputStream(byteOutStream);
			objOut.writeObject(data);
			objOut.flush();
			request = byteOutStream.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// send back response.
		DatagramPacket message = new DatagramPacket(request, request.length, group, outPort);
		try {
			multicast.send(message);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (multicast != null) {
				multicast.close();
			}
		}
	}
	

}
