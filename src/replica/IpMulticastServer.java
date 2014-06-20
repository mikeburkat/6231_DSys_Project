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
	private InetAddress groupRecieve = null;
	private InetAddress groupSend = null;
	int inPort;
	int outPort;

	public IpMulticastServer(Replica rep, int recievePort, int sendPort) {
		replica = rep;
		inPort = recievePort;
		outPort = sendPort;
		try {
			groupRecieve = InetAddress.getByName("224.0.222.0");
			reciever = new MulticastSocket(inPort);
			reciever.joinGroup(groupRecieve);

			groupSend = InetAddress.getByName("224.0.221.0");
			sender = new MulticastSocket();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {

		
		
		try {
			while (true) {
				byte objectBytes[] = new byte[4096];
				ByteArrayInputStream baos = new ByteArrayInputStream(objectBytes);
				DatagramPacket message = new DatagramPacket(objectBytes, objectBytes.length);
				reciever.receive(message);
				// de-serialize request object
				RequestData request = null;
				try {
					ObjectInputStream oos = new ObjectInputStream(baos);
					RequestData r = (RequestData) oos.readObject();
					message.setLength(objectBytes.length); // must reset length field!
					baos.reset();
					System.out.println(r.toString());
					
				} catch (ClassNotFoundException | IOException e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
				if (request == null) { return ;}

				// process request
				String result = null;
				switch (request.command) {
				case "create":
					result = replica.createPlayerAccount(request.firstName,
							request.lastName, request.age, request.userName,
							request.password, request.ipAddress);
					break;
				case "signIn":
					result = replica.playerSignIn(request.userName,
							request.password, request.ipAddress);
					break;
				case "signOut":
					result = replica.playerSignOut(request.userName,
							request.ipAddress);
					break;
				case "getStatus":
					result = replica.getPlayerStatus(request.userName,
							request.password, request.ipAddress);
					break;
				case "transfer":
					result = replica.transferAccount(request.userName,
							request.password, request.ipAddress,
							request.newIpAddress);
					break;
				case "suspend":
					result = replica.suspendAccount(request.userName,
							request.password, request.ipAddress,
							request.userToSuspend);
					break;
				}

				// form reply
				ReplyData reply = new ReplyData();
				reply.requestId = request.requestId;
				reply.clientId = request.clientId;
				reply.replicaId = replica.getID();
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
				DatagramPacket replyMessage = new DatagramPacket(replyBytes,
						replyBytes.length, groupSend, outPort);
				sender.send(replyMessage);

			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (sender != null) {
				sender.close();
			}
		}

	}

}
