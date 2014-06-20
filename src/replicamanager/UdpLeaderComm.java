package replicamanager;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;

public class UdpLeaderComm implements Runnable {

	private int RMport;
	private ReplicaManager rm;
	private DatagramSocket socket;

	public UdpLeaderComm(ReplicaManager replicaManager, int port) {
		rm = replicaManager;
		RMport = port;
	}

	@Override
	public void run() {
		try {
			byte[] buffer = new byte[1024];
			socket = new DatagramSocket(RMport);
			
			while (true) {
				DatagramPacket request = new DatagramPacket(buffer,
						buffer.length);
				socket.receive(request);

				ByteArrayInputStream baos;
				ObjectInputStream oos;
				try {
					baos = new ByteArrayInputStream(buffer);
					oos = new ObjectInputStream(baos);
					ArrayList<Integer> wrongs = (ArrayList<Integer>) oos.readObject();
					rm.incrementWrongs(wrongs);
					
				} catch (IOException | ClassNotFoundException e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (socket != null) {
				socket.close();
			}
		}

	}
}
