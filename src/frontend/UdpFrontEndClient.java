package frontend;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UdpFrontEndClient {
	int portNum;
	DatagramSocket clientSocket;
	InetAddress IPAddress;
	byte[] sendData;
	DatagramPacket sendPacket;

	public UdpFrontEndClient(int portNum) {
		this.portNum = portNum;
	}

	public void send(String request) {
		try {
			clientSocket = new DatagramSocket();
			IPAddress = InetAddress.getByName("localhost");
			sendData = new byte[1024];
			sendData = request.getBytes();
			sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, portNum);
			clientSocket.send(sendPacket);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
