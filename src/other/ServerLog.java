package other;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

//------------------------------------------------------------------------
/**
 * This class serves as the logger of the server and player.
 * It can save to both the player logs and server logs by calling the
 * appropriate methods.
 * 
 * @author Mike
 */
public class ServerLog {

	String serverName;
	
	//------------------------------------------------------------------------

	public ServerLog(String sN) {
		serverName = sN;
		initServerLog();
	}

	//------------------------------------------------------------------------

	public void addToServerLog(String s) {
		
		BufferedWriter bw = openFile("server");
		write(s, bw);
		System.out.println(s);
	}
	
	//------------------------------------------------------------------------
	
	public void addToPlayerLog(String userName, String s) {
		s = "Player: " + userName + " " + s;
		
		BufferedWriter bw = openFile(userName);
		write(s, bw);
		addToServerLog(s);
	}
	
	//------------------------------------------------------------------------
	
	public void addToAdminLog(String userName, String s) {
		s = "Admin: " + userName + " " + s;
		
		BufferedWriter bw = openFile(userName);
		write(s, bw);
		addToServerLog(s);
	}
	
	//------------------------------------------------------------------------
	
	private BufferedWriter openFile(String fileName) {
		FileWriter fw = null;
		try {
			fw = new FileWriter("" + serverName + "/" + serverName + fileName + ".log", true);
		} catch (IOException e) {
			System.out.println("Failed to create server log for " + serverName);
			e.printStackTrace();
		}
		BufferedWriter bw = new BufferedWriter(fw);
		return bw;
	}
	
	//------------------------------------------------------------------------
	
	private void write(String s, BufferedWriter bw) {
		String out = "";
		out += new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS").format(Calendar.getInstance().getTime());
		out += " " + s;
		
		try {
			bw.write(out + "\n");
			bw.close();
		} catch (IOException e) {
			System.out.println("Failed to write to player log.");
			e.printStackTrace();
		}
	}
	
	//------------------------------------------------------------------------

	private void initServerLog() {

		File serverFolder = new File("" + serverName);
		if (!serverFolder.exists()) {
			boolean success = (serverFolder).mkdirs();
			if (!success) {
				System.out.println("Failed to create server folder for maintaining logs for " + serverName);
			} else {
				System.out.println("Server log folder for " + serverName
						+ " was created at location: "
						+ serverFolder.getAbsolutePath());
			}
		}
	}
	
	//------------------------------------------------------------------------
}
