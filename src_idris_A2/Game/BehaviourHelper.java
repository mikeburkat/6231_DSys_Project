package Game;

/** 
 * Helper class for : Behaviour
 *  
 * @author OpenORB Compiler
 */ 
public class BehaviourHelper
{
    /**
     * Insert Behaviour into an any
     * @param a an any
     * @param t Behaviour value
     */
    public static void insert(org.omg.CORBA.Any a, Game.Behaviour t)
    {
        a.insert_Object(t , type());
    }

    /**
     * Extract Behaviour from an any
     *
     * @param a an any
     * @return the extracted Behaviour value
     */
    public static Game.Behaviour extract( org.omg.CORBA.Any a )
    {
        if ( !a.type().equivalent( type() ) )
        {
            throw new org.omg.CORBA.MARSHAL();
        }
        try
        {
            return Game.BehaviourHelper.narrow( a.extract_Object() );
        }
        catch ( final org.omg.CORBA.BAD_PARAM e )
        {
            throw new org.omg.CORBA.MARSHAL(e.getMessage());
        }
    }

    //
    // Internal TypeCode value
    //
    private static org.omg.CORBA.TypeCode _tc = null;

    /**
     * Return the Behaviour TypeCode
     * @return a TypeCode
     */
    public static org.omg.CORBA.TypeCode type()
    {
        if (_tc == null) {
            org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init();
            _tc = orb.create_interface_tc( id(), "Behaviour" );
        }
        return _tc;
    }

    /**
     * Return the Behaviour IDL ID
     * @return an ID
     */
    public static String id()
    {
        return _id;
    }

    private final static String _id = "IDL:Game/Behaviour:1.0";

    /**
     * Read Behaviour from a marshalled stream
     * @param istream the input stream
     * @return the readed Behaviour value
     */
    public static Game.Behaviour read(org.omg.CORBA.portable.InputStream istream)
    {
        return(Game.Behaviour)istream.read_Object(Game._BehaviourStub.class);
    }

    /**
     * Write Behaviour into a marshalled stream
     * @param ostream the output stream
     * @param value Behaviour value
     */
    public static void write(org.omg.CORBA.portable.OutputStream ostream, Game.Behaviour value)
    {
        ostream.write_Object((org.omg.CORBA.portable.ObjectImpl)value);
    }

    /**
     * Narrow CORBA::Object to Behaviour
     * @param obj the CORBA Object
     * @return Behaviour Object
     */
    public static Behaviour narrow(org.omg.CORBA.Object obj)
    {
        if (obj == null)
            return null;
        if (obj instanceof Behaviour)
            return (Behaviour)obj;

        if (obj._is_a(id()))
        {
            _BehaviourStub stub = new _BehaviourStub();
            stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
            return stub;
        }

        throw new org.omg.CORBA.BAD_PARAM();
    }

    /**
     * Unchecked Narrow CORBA::Object to Behaviour
     * @param obj the CORBA Object
     * @return Behaviour Object
     */
    public static Behaviour unchecked_narrow(org.omg.CORBA.Object obj)
    {
        if (obj == null)
            return null;
        if (obj instanceof Behaviour)
            return (Behaviour)obj;

        _BehaviourStub stub = new _BehaviourStub();
        stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
        return stub;

    }

}
