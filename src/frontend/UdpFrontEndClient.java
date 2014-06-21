package frontend;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpFrontEndClient {
	String getPlayerStatusRequest;
	int portNum;
	DatagramSocket clientSocket;
	InetAddress IPAddress;
	byte[] sendData;
	byte[] receiveData;
	DatagramPacket sendPacket;
	DatagramPacket receivePacket;
	String result;
	public UdpFrontEndClient(String getPlayerStatusRequest, int portNum) {
		// TODO Auto-generated constructor stub
		this.getPlayerStatusRequest = getPlayerStatusRequest;
		this.portNum = portNum;
	}
	public void UDPSend() throws Exception{
		clientSocket = new DatagramSocket();
		IPAddress = InetAddress.getByName("localhost");
		sendData = new byte[1024];
		sendData = getPlayerStatusRequest.getBytes();
		sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, portNum);
		clientSocket.send(sendPacket);
	}
}
