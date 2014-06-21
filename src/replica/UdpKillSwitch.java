package replica;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UdpKillSwitch implements Runnable {
	Replica replica_global;
	int portNum;
	String order = "";

	public UdpKillSwitch(Replica replica, int i) throws IOException {
		replica_global = replica;
		portNum = i;
	}

	@Override
	public void run() {
		DatagramSocket server_socket = null;

		try {
			server_socket = new DatagramSocket(portNum);
			System.out.println("UDP killSwitch is up on port: " + portNum);
			byte[] buffer = new byte[1000];

			DatagramPacket request = new DatagramPacket(buffer, buffer.length);

			server_socket.receive(request);

			order = new String(request.getData());
			order = order.trim();
			accessOrder(order);

		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			if (server_socket != null) {
				server_socket.close();
			}
		}
	}

	public void accessOrder(String order) {
		if (order.equals("KILL")) {
			replica_global.shutdown();
		} else {
			System.out.print("Kill can not be initiated");
		}
	}

}
