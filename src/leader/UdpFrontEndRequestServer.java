package leader;

import other.ReplyData;
import other.RequestData;

public class UdpFrontEndRequestServer implements Runnable {
	
	RequestBuffer requests;
	
	public UdpFrontEndRequestServer(RequestBuffer req) {
	
		requests = req;
	}
	
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		
		RequestData request = null; // de-serialized object
		
		
		
		
	}



	public void sendResponse(ReplyData consensus) {
		// TODO Auto-generated method stub
		
	}

}
