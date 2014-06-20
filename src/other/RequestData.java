package other;

import java.io.Serializable;

public class RequestData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public String command = " ";
	public int clientId = 0;
	public int requestId = 0;
	
	public String firstName = " ";
	public String lastName = " ";
	public short age = 0;
	public String userName = " ";
	public String password = " ";
	public String ipAddress = " ";
	
	public String userToSuspend = " ";
	public String newIpAddress = " ";
	
	public String toString() {
		return "Client ID: " +clientId+ " Request ID: " +requestId+ " Command: " +command+" UserName: " +userName;
	}
	

}
