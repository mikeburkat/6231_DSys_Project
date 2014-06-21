package leader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

import other.ReplyData;
import other.RequestData;

public class UdpFrontEndRequestServer implements Runnable {

	private RequestBuffer requests;
	private int frontEndSendsFromPort;
	private int frontEndRecieveInPort;
	private DatagramSocket socketRecieve;
	private DatagramSocket socketSend;
	private int requestID;

	public UdpFrontEndRequestServer(int requestPort, int replyPort, RequestBuffer req) {
		requests = req;
		frontEndRecieveInPort = requestPort;
		frontEndSendsFromPort = replyPort;
		requestID = 0;
		
		try {
			socketRecieve = new DatagramSocket(frontEndRecieveInPort);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			while (true) {
				byte[] buffer = new byte[1024];
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				socketRecieve.receive(packet);

				String req = new String(packet.getData());
				req = req.trim();
				String[] request = req.split(",");
				for (String r : request) {
					System.out.print(r + "|");
				}
				System.out.println();

				// format the request data obtained.
				RequestData rd = new RequestData();
				rd.clientId = Integer.parseInt(request[0]);
				rd.command = request[1];
				switch (request[1]) {
				case "create":
					rd.firstName = request[2];
					rd.lastName = request[3];
					rd.age = Short.parseShort(request[4]);
					rd.userName = request[5];
					rd.password = request[6];
					rd.ipAddress = request[7];
					break;
				case "signIn":
					rd.userName = request[2];
					rd.password = request[3];
					rd.ipAddress = request[4];
					break;
				case "signOut":
					rd.userName = request[2];
					rd.ipAddress = request[3];
					break;
				case "getStatus":
					rd.userName = request[2];
					rd.password = request[3];
					rd.ipAddress = request[4];
					break;
				case "transfer":
					rd.userName = request[2];
					rd.password = request[3];
					rd.ipAddress = request[4];
					rd.newIpAddress = request[5];
					break;
				case "suspend":
					rd.userName = request[2];
					rd.password = request[3];
					rd.ipAddress = request[4];
					rd.userToSuspend = request[5];
					break;
				};
				rd.requestId = requestID;
				requestID++;
				requests.push(rd);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (socketRecieve != null) {
				socketRecieve.close();
			}
		}
	}

	public void sendResponse(ReplyData consensus) {
		
		try {	
			String s = consensus.clientId + "," + consensus.reply;
			byte[] a = s.getBytes();
			socketSend = new DatagramSocket();
			InetAddress host = InetAddress.getByName("localhost");
			DatagramPacket request = new DatagramPacket(a, s.length(), host, frontEndSendsFromPort);
			socketSend.send(request);
			System.out.println("Leader sending response to Front end: " + s);
		} catch (IOException e) {
			System.out.println("crash in send response from leader to front end udp call");
			e.printStackTrace();
		} finally { 
			if (socketSend != null) {
				socketSend.close();
			}
		}
	}
}
