package frontend;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import gameserver.GameServer;
import gameserver.GameServerHelper;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.ObjectNotActive;
import org.omg.PortableServer.POAPackage.ServantAlreadyActive;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

public class FrontEnd implements Runnable {
	private String[] args;
	public FrontEnd(String[] args) {
		// TODO Auto-generated constructor stub
		this.args = args;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		ORB orb = ORB.init(args, null);
		POA rootPOA;
		try {
			rootPOA = POAHelper.narrow(orb
					.resolve_initial_references("RootPOA"));
			rootPOA.the_POAManager().activate();

			// create servant and register it with the ORB
			GameServerImpl fe = new GameServerImpl();
			
			byte[] id = rootPOA.activate_object(fe);
			org.omg.CORBA.Object ref = rootPOA.id_to_reference(id);
			
			String ior = orb.object_to_string(ref);
			System.out.println(ior);
			
			PrintWriter file = new PrintWriter("FE.txt");
			file.println(ior);
			file.close();
			
			rootPOA.the_POAManager().activate();
			orb.run();
			
			
//			addobj.setORB(orb);
//
//			// get object reference from the servant
//			org.omg.CORBA.Object ref = rootpoa.servant_to_reference(addobj);
//			GameServer href = GameServerHelper.narrow(ref);
//
//			org.omg.CORBA.Object objRef = orb
//					.resolve_initial_references("NameService");
//			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
//
//			NameComponent path[] = ncRef.to_name("ABC");
//			ncRef.rebind(path, href);
//			
//			
//			System.out.println("Front End CORBA Server ready and waiting");
//
//			// wait for invocations from clients
//			orb.run();

		} catch (InvalidName | ServantAlreadyActive | WrongPolicy
				| ObjectNotActive | FileNotFoundException | AdapterInactive e) {
			e.printStackTrace();
		}

	}

}
