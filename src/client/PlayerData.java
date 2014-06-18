package client;

import java.io.Serializable;

//------------------------------------------------------------------------
/**
 * This class is a data model for the player. It validates lengh of the user name
 * and password. It also contains the online or offline status of the player, and 
 * it validates sign in and sign out actions.
 * 
 * @author Mike
 */
public class PlayerData implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String userName;
	private String firstName;
	private String lastName;
	private int age;
	private String password;
	private boolean online;
	
	//------------------------------------------------------------------------
	
	public PlayerData (String fN, String lN, int a, String uN, String p) throws Exception {
		userName = uN;
		password = p;
		firstName = fN;
		lastName = lN;
		age = a;
		online = false;
	}
	
	//------------------------------------------------------------------------
	
	public String toString() {
		return "UserName: " + userName + " Password: " + password +" First: "+ firstName + " Last: " + lastName + " Age: " + age + " Online:" + online ;
	}
	
	//------------------------------------------------------------------------
}
