package leader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

public class UdpReplicaManager {
	
	private DatagramSocket socket;
	private DatagramPacket request;
	private InetAddress host;
	private int RMport;
	
	public UdpReplicaManager(int port) {
		RMport = port;
	}
	
	public void report(ArrayList<Integer> wrongs) {
		ByteArrayOutputStream byteOutStream;
		ObjectOutputStream objOut;
		byte[] objectBytes = null;

		try {
			byteOutStream = new ByteArrayOutputStream();
			objOut = new ObjectOutputStream(byteOutStream);

			objOut.writeObject(wrongs);
			objOut.flush();
			objectBytes = byteOutStream.toByteArray();

			socket = new DatagramSocket();
			host = InetAddress.getByName("localhost");
			System.out.println("playerData length: " + objectBytes.length);
			request = new DatagramPacket(objectBytes, objectBytes.length, host, RMport);
			socket.send(request);
		} catch (IOException e) {
			System.out.println("crash in the report to Replica Manger method");
			e.printStackTrace();
		} finally {
			if (socket != null) {
				socket.close();
			}
		}
		
	}

}
