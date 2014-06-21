package frontend;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UdpFrontEndServer implements Runnable {
	private int portNum;
	private CallBack c;
	private int clientId;
	private boolean serverStatus = false;
	private Thread t;

	public UdpFrontEndServer(int portNumServer, CallBack c, int clientId) {
		t = new Thread(this, "UDP FE Thread");
		this.portNum = portNumServer;
		this.c = c;
		this.clientId = clientId;
	}

	@Override
	public void run() {
		// Code for UDP server
		DatagramSocket FEsock;
		try {
			FEsock = new DatagramSocket(portNum);
			byte[] buffer = new byte[65536];
			DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);
			byte[] data;

			System.out.println("Server Started and waiting for reply");
			FEsock.receive(incoming);
			data = incoming.getData();
			// Process Data
			c.callback(clientId, data.toString());

		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void start() {
		if (!serverStatus) {
			serverStatus = true;
			t.start();
		}
	}

}
