package other;

import java.io.Serializable;

public class ReplyData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public int clientId = 0;
	public int requestId = 0;
	public int replicaId = 0;
	public String reply = " ";
	
	public String toString() {
		return "Client ID: " + clientId + " Request ID: " + requestId + " Replica ID: " + replicaId + " Reply: " + reply;
	}

}
