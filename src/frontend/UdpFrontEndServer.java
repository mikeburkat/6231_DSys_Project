package frontend;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UdpFrontEndServer {
	private int portNum;

	public UdpFrontEndServer(int portNumServer) {
		this.portNum = portNumServer;
	}

	public synchronized String getReply() {
		DatagramSocket FEsock = null;
		try {
			FEsock = new DatagramSocket(portNum);
			byte[] buffer = new byte[2048];
			DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);
		
			System.out.println("Server Started and waiting for reply");
			FEsock.receive(incoming);
			
			String result = new String(buffer);
			result = result.substring(2);
			return result.trim();
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (FEsock != null) {
				FEsock.close();
			}
		}
		return null;
	}
}
