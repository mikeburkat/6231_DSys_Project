package other;

public class RequestData {
	
	public String command;
	public String clientId;
	public String requestId;
	
	public String firstName;
	public String lastName;
	public short age;
	public String userName;
	public String password;
	public String ipAddress;
	
	public String userToSuspend;
	public String newIpAddress;
	
	public String toString() {
		return "Client ID: " +clientId+ " Request ID: " +requestId+ " Command: " +command+" UserName: " +userName;
	}
	

}
