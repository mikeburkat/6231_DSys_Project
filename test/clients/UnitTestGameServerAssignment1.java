package clients;

import static org.junit.Assert.*;

import org.junit.Test;

import client.AdministratorClient;
import client.PlayerClient;

public class UnitTestGameServerAssignment1 {
	
	/**
	 * Tests that a player is created successfully,
	 * Then it tests that the same user name can't be used again.
	 */
	@Test
	public void test_create_player_accountNA_deny_double_creation() {
		PlayerClient p = new PlayerClient("mikewcd", "burkat", 26, "mikeNAtwo", "mmmmmm", "132.0.0.0");
		assertTrue(p.createPlayerAccount());
	
		PlayerClient p2 = new PlayerClient("mikewcd", "burkat", 26, "mikeNAtwo", "mmmmmm", "132.0.0.0");
		assertFalse(p2.createPlayerAccount());
	}
	
	/**
	 * this tests that a player can be created on the European server.
	 */
	@Test
	public void test_create_player_accountEU() {
		PlayerClient p = new PlayerClient("mikewcd", "burkat", 26, "mikeEU", "mmmmmm", "93.0.0.0");
		assertTrue(p.createPlayerAccount());
	}
	
	/**
	 * this tests that a player can be created on the Asian server.
	 */
	@Test
	public void test_create_player_accountAS() {
		PlayerClient p = new PlayerClient("mikewcd", "burkat", 26, "mikeAS", "mmmmmm", "182.0.0.0");
		assertTrue(p.createPlayerAccount());
	}
	
	/**
	 * This tests first the creation of a player, and then
	 * the Sign In and Sign Out function.
	 */
	@Test
	public void test_signIn_and_signOut(){
		PlayerClient p = new PlayerClient("mike", "burkat", 26, "anotherAS", "mmmmmm", "182.0.0.0");
		assertTrue(p.createPlayerAccount());
		assertTrue(p.playerSignIn());
		assertTrue(p.playerSignOut());
	}
	
	
	/**
	 * This tests that the server prevents Sign In of an online player.
	 */
	@Test
	public void test_signIn_deny_double_signIn(){
		PlayerClient p = new PlayerClient("mike", "burkat", 26, "anotheroneEU", "mmmmmm", "93.0.0.0");
		assertTrue(p.createPlayerAccount());
		assertTrue(p.playerSignIn());
		assertFalse(p.playerSignIn());
	}
	
	/**
	 * This tests that the server prevents Sign Out on an offline player.
	 */
	@Test
	public void test_deny_signOut_on_offline_user(){
		PlayerClient p = new PlayerClient("mike", "burkat", 26, "anotherNA", "mmmmmm", "132.0.0.0");
		assertTrue(p.createPlayerAccount());
		assertFalse(p.playerSignOut());
	}
	
	/**
	 * This tests that a user name of less than 6 char and more then 15 char
	 * is prevented from being created.
	 */
	@Test
	public void test_create_player_deny_short_or_long_userName(){
		PlayerClient p1 = new PlayerClient("mike", "burkat", 26, "a2345", "mmmmmm", "132.0.0.0");
		assertFalse(p1.createPlayerAccount());
		
		PlayerClient p2 = new PlayerClient("mike", "burkat", 26, "a234567890123456", "mmmmmm", "132.0.0.0");
		assertFalse(p2.createPlayerAccount());
	}
	
	/**
	 * This tests that a user name of 6 char and then 15 char
	 * are accepted to create new users.
	 */
	@Test
	public void test_create_player_accept_6_char_or_15_char_userName(){
		PlayerClient p1 = new PlayerClient("mike", "burkat", 26, "a23456", "mmmmmm", "132.0.0.0");
		assertTrue(p1.createPlayerAccount());
		
		PlayerClient p2 = new PlayerClient("mike", "burkat", 26, "a23456789012345", "mmmmmm", "132.0.0.0");
		assertTrue(p2.createPlayerAccount());
	}
	
	/**
	 * This tests that a password shorter than 6 char is denied.
	 */
	@Test
	public void test_create_player_deny_short_password(){
		PlayerClient p1 = new PlayerClient("mike", "burkat", 26, "short", "12345", "132.0.0.0");
		assertFalse(p1.createPlayerAccount());
	}
	
	/**
	 * This tests that a password of 6 char is accepted.
	 */
	@Test
	public void test_create_player_accept_6_char_password(){
		PlayerClient p1 = new PlayerClient("mike", "burkat", 26, "goodUname", "123456", "132.0.0.0");
		assertTrue(p1.createPlayerAccount());
	}
	
	/**
	 * This tests that a status can be retrieved from the North American server.
	 * You need to manually inspect the logged result.
	 */
	@Test
	public void test_get_status_NA(){
		AdministratorClient a = new AdministratorClient("Admin", "Admin", "132.0.0.0");
		a.getPlayerStatus();
	}
	
	/**
	 * This tests that a status can be retrieved from the European server.
	 * You need to manually inspect the logged result.
	 */
	@Test
	public void test_get_status_EU(){
		AdministratorClient a = new AdministratorClient("Admin", "Admin", "93.0.0.0");
		a.getPlayerStatus();
	}
	
	/**
	 * This tests that a status can be retrieved from the Asian server.
	 * You need to manually inspect the logged result.
	 */
	@Test
	public void test_get_status_AS(){
		AdministratorClient a = new AdministratorClient("Admin", "Admin", "182.0.0.0");
		a.getPlayerStatus();
	}
	

}
