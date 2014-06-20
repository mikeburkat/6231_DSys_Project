package clients;

import org.junit.Test;

import threads.AdministratorSuspendThread;
import threads.PlayerTransferThread;

public class UnitTestMultiThreadAssignment2 {

	
	/**
	 * This tests that concurrent suspend and transfer on servers does not
	 * create deadlock nor inconsistencies in suspension and transfer.
	 * 
	 * I created two administrator threads, that suspend the users on a delay.
	 * I created one player thread that creates a player and transfers itself.
	 * 
	 * The threads are on a loop to create transfer and suspend 10 times.
	 * 
	 * You need to manually inspect the logged results.
	 */
	@Test 
	public void test_multi_thread_transfer_suspend() {
		
		PlayerTransferThread ptt = new PlayerTransferThread("mikewcd", "burkat", 26, "multiTransfer", "mmmmmm", "93.0.0.0", "182.0.0.0");
		AdministratorSuspendThread ast1 = new AdministratorSuspendThread("Admin", "Admin", "93.0.0.0", "multiTransfer");
		AdministratorSuspendThread ast2 = new AdministratorSuspendThread("Admin", "Admin", "182.0.0.0", "multiTransfer");
		
		Thread t1 = new Thread(ptt);
		Thread t2 = new Thread(ast1);
		Thread t3 = new Thread(ast2);
		
		t1.start();
		t2.start();
		t3.start();
		
		try {
			t1.join();
			t2.join();
			t3.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}
