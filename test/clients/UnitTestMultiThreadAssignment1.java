package clients;

import org.junit.Test;

import threads.AdministratorStatusThread;
import threads.PlayerSignInOutThread;

public class UnitTestMultiThreadAssignment1 {

	/**
	 * This tests that concurrent access to the servers does not create deadlock.
	 * I created three administrators, one on each server.
	 * I created four players, one on each server, 
	 * and a two on the North American server.
	 * The administrator threads call five statuses.
	 * The player threads create their player, and then 
	 * they sign in and sign out in a loop 10 times.
	 * 
	 * You need to manually inspect the logged results.
	 */
	@Test
	public void test_multi_thread_signIn_signOut() {
		AdministratorStatusThread a1 = new AdministratorStatusThread("Admin", "Admin", "182.0.0.0");
		AdministratorStatusThread a2 = new AdministratorStatusThread("Admin", "Admin", "93.0.0.0");
		AdministratorStatusThread a3 = new AdministratorStatusThread("Admin", "Admin", "132.0.0.0");
		
		PlayerSignInOutThread p1 = new PlayerSignInOutThread("mike", "burkat", 26, "multiThrAS", "123456", "182.0.0.0");
		PlayerSignInOutThread p2 = new PlayerSignInOutThread("mike", "burkat", 26, "multiThrEU", "123456", "93.0.0.0");
		PlayerSignInOutThread p3 = new PlayerSignInOutThread("mike", "burkat", 26, "multiThrNA", "123456", "132.0.0.0");
		PlayerSignInOutThread p4 = new PlayerSignInOutThread("mike", "burkat", 26, "multiThrNA2", "123456", "132.0.0.0");
		
		Thread t1 = new Thread(p1);
		Thread t2 = new Thread(a1);
		Thread t3 = new Thread(p2);
		Thread t4 = new Thread(a2);
		Thread t5 = new Thread(p3);
		Thread t6 = new Thread(a3);
		Thread t7 = new Thread(p4);
		
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
		t6.start();
		t7.start();
		
		try {
			t1.join();
			t2.join();
			t3.join();
			t4.join();
			t5.join();
			t6.join();
			t7.join();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		

	}

}
