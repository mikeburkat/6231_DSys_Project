package other;

public class ReplyData {
	
	public String clientId;
	public int requestId;
	public int replicaId;
	public String reply;
	
	public String toString() {
		return "Client ID: " + clientId + " Request ID: " + requestId + " Replica ID: " + replicaId + " Reply: " + reply;
	}

}
