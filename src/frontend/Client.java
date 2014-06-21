package frontend;

public class Client {

	private int clientId;
	private String firstName;
	private String lastName;
	private short age;
	private String userName;
	private String password;
	private String ipAddress;
	private String oldIpAddress;
	private String newIpAddress;
	private String usernameToSuspend;
	private boolean status;
	private String result;

	public Client(String userName, String password, String ipAddress) {
		this.ipAddress = ipAddress;
		this.password = password;
		this.userName = userName;
	}

	public Client(String userName, String ipAddress) {
		this.ipAddress = ipAddress;
		this.userName = userName;
	}

	public Client(String firstName, String lastName, short age,
			String userName, String password, String ipAddress) {
		this.ipAddress = ipAddress;
		this.password = password;
		this.userName = userName;
		this.age = age;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Client(String userName, String password, String oldIpAddress,
			String newIpAddress, int varient) {
		if (varient == 1) {
			this.password = password;
			this.userName = userName;
			this.oldIpAddress = oldIpAddress;
			this.newIpAddress = newIpAddress;
		} else if (varient == 2) {
			this.password = password;
			this.userName = userName;
			this.ipAddress = oldIpAddress;
			this.usernameToSuspend = newIpAddress;
		}

	}

	public int getClientId() {
		return clientId;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public String getPassword() {
		return password;
	}

	public String getUserName() {
		return userName;
	}

	public short getAge() {
		return age;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getNewIpAddress() {
		return newIpAddress;
	}

	public String getOldIpAddress() {
		return oldIpAddress;
	}

	public String getUsernameToSuspend() {
		return usernameToSuspend;
	}

	public boolean isStatus() {
		return status;
	}
	
	public String getResult() {
		return result;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setAge(short age) {
		this.age = age;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setNewIpAddress(String newIpAddress) {
		this.newIpAddress = newIpAddress;
	}

	public void setOldIpAddress(String oldIpAddress) {
		this.oldIpAddress = oldIpAddress;
	}

	public void setUsernameToSuspend(String usernameToSuspend) {
		this.usernameToSuspend = usernameToSuspend;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public void setResult(String result) {
		this.result = result;
	}

}
