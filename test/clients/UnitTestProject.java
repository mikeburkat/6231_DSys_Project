package clients;

import static org.junit.Assert.*;

import org.junit.Test;

import client.PlayerClient;

public class UnitTestProject {

	/**
	 * test wrong response on replica 0
	 */
	@Test
	public void test_wrong_on_0() {
		PlayerClient p = new PlayerClient("mikewcd", "burkat", 26, "crash0", "mmmmmm", "132.0.0.0");
		assertTrue(p.createPlayerAccount());
	}
	
	/**
	 * test wrong response on replica 1
	 */
	@Test
	public void test_wrong_on_1() {
		PlayerClient p = new PlayerClient("mikewcd", "burkat", 26, "crash1", "mmmmmm", "93.0.0.0");
		assertTrue(p.createPlayerAccount());
	}

	/**
	 * test wrong response on replica 2
	 */
	@Test
	public void test_wrong_on_2() {
		PlayerClient p = new PlayerClient("mikewcd", "burkat", 26, "crash2", "mmmmmm", "182.0.0.0");
		assertTrue(p.createPlayerAccount());
	}
	
	/**
	 * test crash replica 2
	 * and restart it.
	 */
	@Test
	public void test_crash_on_2() {
		PlayerClient p = new PlayerClient("mikewcd", "burkat", 26, "crash2", "mmmmmm", "182.0.0.0");
		assertTrue(p.createPlayerAccount());
		assertTrue(p.playerSignIn());
		assertTrue(p.playerSignOut());
		try {
		    Thread.sleep(1000);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		assertTrue(p.playerSignIn());
		assertTrue(p.playerSignIn());
	}
	
	/**
	 * test crash replica 1
	 * and restart it.
	 */
	@Test
	public void test_reset_on_1() {
		PlayerClient p1 = new PlayerClient("mikewcd", "burkat", 26, "crash1", "mmmmmm", "182.0.0.0");
		assertTrue(p1.createPlayerAccount());
		assertTrue(p1.playerSignIn());
//		assertTrue(p.playerSignOut());
		
		PlayerClient p2 = new PlayerClient("mikewcd", "burkat", 26, "avoidCrash", "mmmmmm", "182.0.0.0");
		assertTrue(p2.createPlayerAccount());
		
		assertTrue(p1.playerSignOut());
		
	}


}
