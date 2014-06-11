package Game;

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

public class BehaviourServer {

	/**
	 * @param args
	 * @throws WrongPolicy 
	 * @throws ServantAlreadyActive 
	 * @throws InvalidName 
	 * @throws ObjectNotActive 
	 * @throws FileNotFoundException 
	 * @throws AdapterInactive 
	 */
	public static void main(String[] args) throws ServantAlreadyActive, WrongPolicy, InvalidName, ObjectNotActive, FileNotFoundException, AdapterInactive {
		// TODO Auto-generated method stub
		ORB orb = ORB.init(args, null);
		POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
		
		GameImpl aS_Behaviour = new GameImpl();
		byte[] id_AS = rootPOA.activate_object(aS_Behaviour);
		org.omg.CORBA.Object ref_AS = rootPOA.id_to_reference(id_AS);
		
		GameImpl eU_Behaviour = new GameImpl();
		byte[] id_EU = rootPOA.activate_object(eU_Behaviour);
		org.omg.CORBA.Object ref_EU = rootPOA.id_to_reference(id_EU);
		
		GameImpl nA_Behaviour = new GameImpl();
		byte[] id_NA = rootPOA.activate_object(nA_Behaviour);
		org.omg.CORBA.Object ref_NA = rootPOA.id_to_reference(id_NA);
		
		String ior_AS = orb.object_to_string(ref_AS);
		System.out.println(ior_AS);
		
		String ior_EU = orb.object_to_string(ref_EU);
		System.out.println(ior_EU);
		
		String ior_NA = orb.object_to_string(ref_NA);
		System.out.println(ior_NA);
		
		PrintWriter file_NA = new PrintWriter("iorNA.txt");
		PrintWriter file_AS = new PrintWriter("iorAS.txt");
		PrintWriter file_EU = new PrintWriter("iorEU.txt");
		file_NA.println(ior_AS);
		file_EU.println(ior_EU);
		file_AS.println(ior_NA);
		file_NA.close();
		file_EU.close();
		file_AS.close();
		
		rootPOA.the_POAManager().activate();
		orb.run();
		// TODO Auto-generated method stub

	}
	

}

