package Game;

/**
 * Interface definition: Behaviour.
 * 
 * @author OpenORB Compiler
 */
public interface BehaviourOperations
{
    /**
     * Operation createPlayerAccount
     */
    public String createPlayerAccount(String FirstName, String LastName, short Age, String Username, String Password, String IPAddress);

    /**
     * Operation playerSignIn
     */
    public String playerSignIn(String Username, String Password, String IPAddress);

    /**
     * Operation playerSignOut
     */
    public String playerSignOut(String Username, String IPAddress);

    /**
     * Operation getPlayerStatus
     */
    public String getPlayerStatus(String Username, String Password, String IPAddress);

    /**
     * Operation suspendAccount
     */
    public String suspendAccount(String admin_username, String admin_password, String admin_ip_address, String username);

    /**
     * Operation transferAccount
     */
    public String transferAccount(String username, String password, String old_IP_address, String new_IP_address);

}
