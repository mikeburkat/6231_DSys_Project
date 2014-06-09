package gameserver;

/**
 * Interface definition: GameServer.
 * 
 * @author OpenORB Compiler
 */
public interface GameServerOperations
{
    /**
     * Operation getPlayerStatus
     */
    public String getPlayerStatus(String adminUserName, String adminPassword, String ipAddress);

    /**
     * Operation createPlayerAccount
     */
    public String createPlayerAccount(String firstName, String lastName, short age, String userName, String password, String ipAddress);

    /**
     * Operation playerSignIn
     */
    public String playerSignIn(String userName, String password, String ipAddress);

    /**
     * Operation playerSignOut
     */
    public String playerSignOut(String userName, String ipAddress);

    /**
     * Operation transferAccount
     */
    public String transferAccount(String userName, String password, String oldIpAddress, String newIpAddress);

    /**
     * Operation suspendAccount
     */
    public String suspendAccount(String adminUserName, String adminPassword, String ipAddress, String userNameToSuspend);

}
