package Game;

/**
 * Holder class for : Behaviour
 * 
 * @author OpenORB Compiler
 */
final public class BehaviourHolder
        implements org.omg.CORBA.portable.Streamable
{
    /**
     * Internal Behaviour value
     */
    public Game.Behaviour value;

    /**
     * Default constructor
     */
    public BehaviourHolder()
    { }

    /**
     * Constructor with value initialisation
     * @param initial the initial value
     */
    public BehaviourHolder(Game.Behaviour initial)
    {
        value = initial;
    }

    /**
     * Read Behaviour from a marshalled stream
     * @param istream the input stream
     */
    public void _read(org.omg.CORBA.portable.InputStream istream)
    {
        value = BehaviourHelper.read(istream);
    }

    /**
     * Write Behaviour into a marshalled stream
     * @param ostream the output stream
     */
    public void _write(org.omg.CORBA.portable.OutputStream ostream)
    {
        BehaviourHelper.write(ostream,value);
    }

    /**
     * Return the Behaviour TypeCode
     * @return a TypeCode
     */
    public org.omg.CORBA.TypeCode _type()
    {
        return BehaviourHelper.type();
    }

}
