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
		PlayerClient p = new PlayerClient("mikewcd", "burkat", 26, "crash0", "mmmmmm", "93.0.0.0");
		assertTrue(p.createPlayerAccount());
	}

	/**
	 * test wrong response on replica 2
	 */
	@Test
	public void test_wrong_on_2() {
		PlayerClient p = new PlayerClient("mikewcd", "burkat", 26, "crash0", "mmmmmm", "182.0.0.0");
		assertTrue(p.createPlayerAccount());
	}


}
