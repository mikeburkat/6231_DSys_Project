package clients;

import static org.junit.Assert.*;

import org.junit.Test;

import client.AdministratorClient;
import client.PlayerClient;

public class UnitTestGameServerAssignment2 {
	
	/**
	 * Tests that a player account was suspended.
	 * - Player is created, TRUE
	 * - Player signs in, TRUE
	 * - Player suspend, TRUE
	 * - Player sign in, FALSE
	 */
	@Test
	public void test_suspend_account() {
		PlayerClient p = new PlayerClient("mikewcd", "burkat", 26, "mikeSuspend", "mmmmmm", "132.0.0.0");
		assertTrue(p.createPlayerAccount());
		assertTrue(p.playerSignIn());
		
		AdministratorClient a = new AdministratorClient("Admin", "Admin", "132.0.0.0");
		assertTrue(a.suspendAccount("mikeSuspend"));
		assertFalse(p.playerSignIn());
	}
	
	/**
	 * Tests that a player account can't be suspended twice.
	 * - Player is created, TRUE
	 * - Player suspend, TRUE
	 * - Player suspend, FALSE
	 */
	@Test
	public void test_suspend_account_double_suspend() {
		PlayerClient p = new PlayerClient("mikewcd", "burkat", 26, "mikeSuspend2", "mmmmmm", "132.0.0.0");
		assertTrue(p.createPlayerAccount());
		
		AdministratorClient a = new AdministratorClient("Admin", "Admin", "132.0.0.0");
		assertTrue(a.suspendAccount("mikeSuspend2"));
		
		assertFalse(a.suspendAccount("mikeSuspend2"));
	}
	
	/**
	 * Tests that suspend fails on an non existant account.
	 * - Player suspend, FALSE
	 */
	@Test
	public void test_suspend_account_nonExistant_player() {
		AdministratorClient a = new AdministratorClient("Admin", "Admin", "132.0.0.0");
		assertFalse(a.suspendAccount("mikeSuspend3"));
	}
	
	/**
	 * Tests that a player account can be transfered.
	 * - Player is created, TRUE
	 * - Player signs in, TRUE
	 * - Player transfer, TRUE
	 * - Player sign in, FALSE
	 * - Player sign in new server, TRUE
	 */
	@Test
	public void test_transfer_account() {
		PlayerClient p1 = new PlayerClient("mikewcd", "burkat", 26, "mikeTransfer2", "mmmmmm", "132.0.0.0");
		assertTrue(p1.createPlayerAccount());
		assertTrue(p1.playerSignIn());
		assertTrue(p1.transferAccount("93.0.0.0"));
		assertFalse(p1.playerSignIn());
		
		PlayerClient p2 = new PlayerClient("mikewcd", "burkat", 26, "mikeTransfer2", "mmmmmm", "93.0.0.0");
		assertTrue(p2.playerSignIn());
	}
	
	/**
	 * Tests that a transfer fails with wrong password.
	 * - Player is created, TRUE
	 * - Player transfer, FALSE
	 */
	@Test
	public void test_deny_wrong_pass_on_transfer() {
		PlayerClient p1 = new PlayerClient("mikewcd", "burkat", 26, "mikePassTest", "rightPass", "132.0.0.0");
		assertTrue(p1.createPlayerAccount());
		
		PlayerClient p2 = new PlayerClient("mikewcd", "burkat", 26, "mikePassTest", "wrongPass", "132.0.0.0");
		assertFalse(p2.transferAccount("93.0.0.0"));
	}
	
}
