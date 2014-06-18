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
		setUserName(uN);
		setPassword(p);
		firstName = fN;
		lastName = lN;
		age = a;
		online = false;
	}
	
	//------------------------------------------------------------------------
	
	public void setUserName (String uN) throws Exception {
		if (uN.length() >= 6 && uN.length() <= 15) {
			userName = uN;
		} else {
			throw new Exception("User name too short or too long, must be 6 or more and 15 or less characters.");
		}
	}
	
	//------------------------------------------------------------------------
	
	public void setPassword (String pass) throws Exception {
		if (pass.length() >= 6) {
			password = pass;
		} else {
			throw new Exception("Password too short, must be at least 6 characters.");
		}
	}
	
	//------------------------------------------------------------------------
	
	public String signIn(String pass) {
		if (online) {
			return "Sign In Failed, player already signed in";
		} else if ( password.equals(pass) ) {
			online = true;
			return "Success";
		} else {
			return "Sign In Failed, password authentification failed";
		}
	}
	
	//------------------------------------------------------------------------
	
	public String signOut() {
		if (!online) {
			return "Sign out failed, user already signed out";
		} else {
			online = false;
			return "Success";
		}
	}
	
	//------------------------------------------------------------------------

	public String getUserName() {
		return userName;
	}
	
	//------------------------------------------------------------------------
	
	public String getFirstName() {
		return firstName;
	}

	//------------------------------------------------------------------------
	
	public String getLastName() {
		return lastName;
	}

	//------------------------------------------------------------------------
	
	public int getAge() {
		return age;
	}

	//------------------------------------------------------------------------
	
	public String getPassword() {
		return password;
	}
	
	//------------------------------------------------------------------------
	
	public boolean validatePassword(String pass) {
		if ( password.equals(pass) ) {
			return true;
		} else {
			return false;
		}
	}

	//------------------------------------------------------------------------
	
	public String toString() {
		return "UserName: " + userName + " Password: " + password +" First: "+ firstName + " Last: " + lastName + " Age: " + age + " Online:" + online ;
	}
	
	//------------------------------------------------------------------------
}
