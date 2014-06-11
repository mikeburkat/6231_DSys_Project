package other;

import gameserver.GameServerImpl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * This class accepts requests for status, and it sends the status of its
 * GameServer back to the requesting client.
 * 
 * @author Mike
 */
public class UDPserverTransfer implements Runnable {

	DatagramSocket socket;
	GameServerImpl gameServer;
	int UDPserverPort;

	public UDPserverTransfer(GameServerImpl gs, int udp) {
		gameServer = gs;
		UDPserverPort = udp;
	}

	@Override
	public void run() {
		try {

			byte[] buffer = new byte[1024];
			socket = new DatagramSocket(UDPserverPort);
			System.out.println("UDP server is up on port: " + UDPserverPort);

			while (true) {
				DatagramPacket request = new DatagramPacket(buffer,
						buffer.length);
				socket.receive(request);

				ByteArrayInputStream baos;
				ObjectInputStream oos;
				try {
					baos = new ByteArrayInputStream(buffer);
					oos = new ObjectInputStream(baos);
					PlayerData pd = (PlayerData) oos.readObject();
					System.out.println("Player data: " + pd.toString());

					String created = gameServer.createPlayerAccount(pd.getFirstName(),
							pd.getLastName(), (short) pd.getAge(),
							pd.getUserName(), pd.getPassword(), "");
					
					byte[] r = created.getBytes();
					DatagramPacket reply = new DatagramPacket(r, r.length,
					request.getAddress(), request.getPort());
					socket.send(reply);

				} catch (IOException | ClassNotFoundException e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
			}

		} catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
			System.out.println("crash in UDP server");
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		} finally {
			if (socket != null) {
				socket.close();
			}
		}

	}
}
