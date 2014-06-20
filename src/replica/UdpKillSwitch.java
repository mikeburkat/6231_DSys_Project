package replica;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UdpKillSwitch implements Runnable {
	Replica replica_global; 
	int portNum;
	String order="";
	public UdpKillSwitch(Replica replica, int i) throws IOException {
		// TODO Auto-generated constructor stub
		replica_global = replica;
		portNum = i;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		DatagramSocket server_socket = null;
		
		try {
			server_socket = new DatagramSocket(portNum);
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		byte[] buffer = new byte[1000];
		while(true)
		{
			DatagramPacket request = new DatagramPacket(buffer, buffer.length);
			try {
				server_socket.receive(request);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			order = request.getData().toString();
			accessOrder(order);
		}
	}
	
	public void accessOrder(String order)
	{
		if(order.equals("KILL"))
		{
			replica_global.shutdown();
		}
		else
		{
			System.out.print("Kill can not be initiated");
		}
	}

}
