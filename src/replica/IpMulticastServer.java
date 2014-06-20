package replica;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import other.ReplyData;
import other.RequestData;

public class IpMulticastServer implements Runnable {
	
	private MulticastSocket reciever = null;
	private MulticastSocket sender = null;
	private Replica replica;
	InetAddress group = null;
	int inPort;
	int outPort;
	
	public IpMulticastServer(Replica rep, int recievePort, int sendPort) {
		replica = rep;
		inPort = recievePort;
		outPort = sendPort;
		try {
			reciever = new MulticastSocket(recievePort);
			group = InetAddress.getByName("224.0.222.0");
			reciever.joinGroup(group);
			
			sender = new MulticastSocket();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		
		byte objectBytes[] = new byte[1024];
		
		while (true) {
			// wait for request.
			DatagramPacket message = new DatagramPacket(objectBytes, objectBytes.length);
			try {
				reciever.receive(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// de-serialize request object
			ByteArrayInputStream baos;
			ObjectInputStream oos;
			RequestData request = null;
			try {
				baos = new ByteArrayInputStream(objectBytes);
				oos = new ObjectInputStream(baos);
				request = (RequestData) oos.readObject();
				System.out.println(request.toString());
			} catch (ClassNotFoundException | IOException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			
			// process request
			String result = null;
			switch(request.command){
			case "create":
				result = replica.createPlayerAccount(request.firstName, request.lastName, request.age, request.userName, request.password, request.ipAddress);
				break;
			case "signIn":
				result = replica.playerSignIn(request.userName, request.password, request.ipAddress);
				break;
			case "signOut":
				result = replica.playerSignOut(request.userName, request.ipAddress);
				break;
			case "getStatus":
				result = replica.getPlayerStatus(request.userName, request.password, request.ipAddress);
				break;
			case "transfer":
				result = replica.transferAccount(request.userName, request.password, request.ipAddress, request.newIpAddress);
				break;
			case "suspend":
				result = replica.suspendAccount(request.userName, request.password, request.ipAddress, request.userToSuspend);
				break;
			}
			
			// form reply
			ReplyData reply = new ReplyData();
			reply.requestId = request.requestId;
			reply.clientId = request.clientId;
			reply.reply = result;
			
			ByteArrayOutputStream byteOutStream;
			ObjectOutputStream objOut;
			byte[] replyBytes = null;

			try {
				byteOutStream = new ByteArrayOutputStream();
				objOut = new ObjectOutputStream(byteOutStream);

				objOut.writeObject(reply);
				objOut.flush();
				replyBytes = byteOutStream.toByteArray();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// send back response.
			DatagramPacket replyMessage = new DatagramPacket(replyBytes, replyBytes.length, group, outPort);
			try {
				sender.send(replyMessage);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (sender != null) {
					sender.close();
				}
			}
			
			
		}
	}

}
