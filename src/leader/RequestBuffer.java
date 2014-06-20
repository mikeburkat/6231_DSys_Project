package leader;

import java.util.ArrayList;

import other.RequestData;

public class RequestBuffer {
	
	private IpMulticastLeaderSender multicast;
	private ArrayList<RequestData> requests;
	
	public RequestBuffer(IpMulticastLeaderSender send) {
		multicast = send;
		requests = new ArrayList<RequestData>();
	}
	
	public void push(RequestData request) {
		System.out.println("Pushing Request: " + request.toString());
		requests.add(request);
		if (requests.size() == 1) {
			sendCurrentRequest();
		}
	}
	
	public RequestData pop() {
		return requests.remove(0);
	}
	
	public RequestData top() {
		return requests.get(0);
	}
	
	public void sendCurrentRequest() {
		if (requests.size() > 0) {
			multicast.send( top() );
		}
	}
	
	public void sendNextRequest() {
		pop();
		if (requests.size() > 0) 
			multicast.send( top() );
	}
}
