package gameserver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramSocket;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.ObjectNotActive;
import org.omg.PortableServer.POAPackage.ServantAlreadyActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

public class EuropeServer implements Runnable {

	private int replica = 0;
	private ORB orb;
	private GameServerImpl gs;

	public EuropeServer(int r) {
		replica = r;
	}

	public void setup() throws InvalidName, ServantAlreadyActive, WrongPolicy,
			ObjectNotActive, FileNotFoundException, AdapterInactive {

		orb = ORB.init((String[]) null, null);
		POA rootPOA = POAHelper.narrow(orb
				.resolve_initial_references("RootPOA"));

		gs = new GameServerImpl("EU" + replica,
				2031 + (100 * replica), 2030 + (100 * replica),
				2032 + (100 * replica));
		byte[] id = rootPOA.activate_object(gs);
		org.omg.CORBA.Object ref = rootPOA.id_to_reference(id);

		String ior = orb.object_to_string(ref);
		System.out.println(ior);

		PrintWriter file = new PrintWriter("EU" + replica + ".txt");
		file.println(ior);
		file.close();

		rootPOA.the_POAManager().activate();
		orb.run();

	}

	@Override
	public void run() {
		try {
			setup();
		} catch (InvalidName | ServantAlreadyActive | WrongPolicy
				| ObjectNotActive | FileNotFoundException | AdapterInactive e) {
			e.printStackTrace();
		}
	}
	
	public void shutdown() {
		gs.shutdown();
		orb.shutdown(false);
		System.out.println("ORB was shutdown");
	}
}
