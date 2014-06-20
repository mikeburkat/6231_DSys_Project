package leader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

import other.ReplyData;
import other.RequestData;

public class UdpFrontEndRequestServer implements Runnable {

	private RequestBuffer requests;
	private int frontEndSendsFromPort;
	private int frontEndRecieveInPort;
	private DatagramSocket socket;
	private int requestID;

	public UdpFrontEndRequestServer(int requestPort, int replyPort, RequestBuffer req) {
		requests = req;
		frontEndRecieveInPort = requestPort;
		frontEndSendsFromPort = replyPort;
		requestID = 0;
	}

	@Override
	public void run() {
		try {
			byte[] buffer = new byte[1024];
			socket = new DatagramSocket(frontEndRecieveInPort);

			while (true) {
				DatagramPacket packet = new DatagramPacket(buffer,
						buffer.length);
				socket.receive(packet);

				String req = new String(packet.getData());
				String[] request = req.split(",");
				for (String r : request) {
					System.out.print(r + "|");
				}
				System.out.println();

				// format the request data obtained.
				RequestData rd = new RequestData();
				rd.command = request[0];
				switch (request[0]) {
				case "create":
					rd.firstName = request[1];
					rd.lastName = request[2];
					rd.age = Short.parseShort(request[3]);
					rd.userName = request[4];
					rd.password = request[5];
					rd.ipAddress = request[6];
					break;
				case "signIn":
					rd.userName = request[1];
					rd.password = request[2];
					rd.ipAddress = request[3];
					break;
				case "signOut":
					rd.userName = request[1];
					rd.ipAddress = request[2];
					break;
				case "getStatus":
					rd.userName = request[1];
					rd.password = request[2];
					rd.ipAddress = request[3];
					break;
				case "transfer":
					rd.userName = request[1];
					rd.password = request[2];
					rd.ipAddress = request[3];
					rd.newIpAddress = request[4];
					break;
				case "suspend":
					rd.userName = request[1];
					rd.password = request[2];
					rd.ipAddress = request[3];
					rd.userToSuspend = request[4];
					break;
				};
				rd.requestId = requestID;
				requestID++;
				requests.push(rd);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (socket != null) {
				socket.close();
			}
		}
	}

	public void sendResponse(ReplyData consensus) {
		try {	
			String s = consensus.clientId + "," + consensus.reply;
			byte[] a = s.getBytes();
			socket = new DatagramSocket();
			InetAddress host = InetAddress.getByName("localhost");
			DatagramPacket request = new DatagramPacket(a, s.length(), host, frontEndSendsFromPort);
			socket.send(request);
		} catch (IOException e) {
			System.out.println("crash in send response from leader to front end udp call");
			e.printStackTrace();
		} finally { 
			if (socket != null) {
				socket.close();
			}
		}
	}
}
