package gameserver;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.ObjectNotActive;
import org.omg.PortableServer.POAPackage.ServantAlreadyActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;


public class NorthAmericaServer implements Runnable {
	
	private int replica = 0;
	
	public NorthAmericaServer(int r) {
		replica = r;
	}

	public void setup() throws InvalidName, ServantAlreadyActive, WrongPolicy, ObjectNotActive, FileNotFoundException, AdapterInactive {
		
		ORB orb = ORB.init((String[]) null, null);
		POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
		
		
		GameServerImpl gs = new GameServerImpl("NA"+replica, 2030+(100*replica), 2031+(100*replica), 2032+(100*replica));
		byte[] id = rootPOA.activate_object(gs);
		org.omg.CORBA.Object ref = rootPOA.id_to_reference(id);
		
		String ior = orb.object_to_string(ref);
		System.out.println(ior);
		
		PrintWriter file = new PrintWriter("NA"+replica+".txt");
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
	
}
