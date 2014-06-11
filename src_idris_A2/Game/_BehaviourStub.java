package Game;

/**
 * Interface definition: Behaviour.
 * 
 * @author OpenORB Compiler
 */
public class _BehaviourStub extends org.omg.CORBA.portable.ObjectImpl
        implements Behaviour
{
    static final String[] _ids_list =
    {
        "IDL:Game/Behaviour:1.0"
    };

    public String[] _ids()
    {
     return _ids_list;
    }

    private final static Class _opsClass = Game.BehaviourOperations.class;

    /**
     * Operation createPlayerAccount
     */
    public String createPlayerAccount(String FirstName, String LastName, short Age, String Username, String Password, String IPAddress)
    {
        while(true)
        {
            if (!this._is_local())
            {
                org.omg.CORBA.portable.InputStream _input = null;
                try
                {
                    org.omg.CORBA.portable.OutputStream _output = this._request("createPlayerAccount",true);
                    _output.write_string(FirstName);
                    _output.write_string(LastName);
                    _output.write_short(Age);
                    _output.write_string(Username);
                    _output.write_string(Password);
                    _output.write_string(IPAddress);
                    _input = this._invoke(_output);
                    String _arg_ret = _input.read_string();
                    return _arg_ret;
                }
                catch(org.omg.CORBA.portable.RemarshalException _exception)
                {
                    continue;
                }
                catch(org.omg.CORBA.portable.ApplicationException _exception)
                {
                    String _exception_id = _exception.getId();
                    throw new org.omg.CORBA.UNKNOWN("Unexpected User Exception: "+ _exception_id);
                }
                finally
                {
                    this._releaseReply(_input);
                }
            }
            else
            {
                org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke("createPlayerAccount",_opsClass);
                if (_so == null)
                   continue;
                Game.BehaviourOperations _self = (Game.BehaviourOperations) _so.servant;
                try
                {
                    return _self.createPlayerAccount( FirstName,  LastName,  Age,  Username,  Password,  IPAddress);
                }
                finally
                {
                    _servant_postinvoke(_so);
                }
            }
        }
    }

    /**
     * Operation playerSignIn
     */
    public String playerSignIn(String Username, String Password, String IPAddress)
    {
        while(true)
        {
            if (!this._is_local())
            {
                org.omg.CORBA.portable.InputStream _input = null;
                try
                {
                    org.omg.CORBA.portable.OutputStream _output = this._request("playerSignIn",true);
                    _output.write_string(Username);
                    _output.write_string(Password);
                    _output.write_string(IPAddress);
                    _input = this._invoke(_output);
                    String _arg_ret = _input.read_string();
                    return _arg_ret;
                }
                catch(org.omg.CORBA.portable.RemarshalException _exception)
                {
                    continue;
                }
                catch(org.omg.CORBA.portable.ApplicationException _exception)
                {
                    String _exception_id = _exception.getId();
                    throw new org.omg.CORBA.UNKNOWN("Unexpected User Exception: "+ _exception_id);
                }
                finally
                {
                    this._releaseReply(_input);
                }
            }
            else
            {
                org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke("playerSignIn",_opsClass);
                if (_so == null)
                   continue;
                Game.BehaviourOperations _self = (Game.BehaviourOperations) _so.servant;
                try
                {
                    return _self.playerSignIn( Username,  Password,  IPAddress);
                }
                finally
                {
                    _servant_postinvoke(_so);
                }
            }
        }
    }

    /**
     * Operation playerSignOut
     */
    public String playerSignOut(String Username, String IPAddress)
    {
        while(true)
        {
            if (!this._is_local())
            {
                org.omg.CORBA.portable.InputStream _input = null;
                try
                {
                    org.omg.CORBA.portable.OutputStream _output = this._request("playerSignOut",true);
                    _output.write_string(Username);
                    _output.write_string(IPAddress);
                    _input = this._invoke(_output);
                    String _arg_ret = _input.read_string();
                    return _arg_ret;
                }
                catch(org.omg.CORBA.portable.RemarshalException _exception)
                {
                    continue;
                }
                catch(org.omg.CORBA.portable.ApplicationException _exception)
                {
                    String _exception_id = _exception.getId();
                    throw new org.omg.CORBA.UNKNOWN("Unexpected User Exception: "+ _exception_id);
                }
                finally
                {
                    this._releaseReply(_input);
                }
            }
            else
            {
                org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke("playerSignOut",_opsClass);
                if (_so == null)
                   continue;
                Game.BehaviourOperations _self = (Game.BehaviourOperations) _so.servant;
                try
                {
                    return _self.playerSignOut( Username,  IPAddress);
                }
                finally
                {
                    _servant_postinvoke(_so);
                }
            }
        }
    }

    /**
     * Operation getPlayerStatus
     */
    public String getPlayerStatus(String Username, String Password, String IPAddress)
    {
        while(true)
        {
            if (!this._is_local())
            {
                org.omg.CORBA.portable.InputStream _input = null;
                try
                {
                    org.omg.CORBA.portable.OutputStream _output = this._request("getPlayerStatus",true);
                    _output.write_string(Username);
                    _output.write_string(Password);
                    _output.write_string(IPAddress);
                    _input = this._invoke(_output);
                    String _arg_ret = _input.read_string();
                    return _arg_ret;
                }
                catch(org.omg.CORBA.portable.RemarshalException _exception)
                {
                    continue;
                }
                catch(org.omg.CORBA.portable.ApplicationException _exception)
                {
                    String _exception_id = _exception.getId();
                    throw new org.omg.CORBA.UNKNOWN("Unexpected User Exception: "+ _exception_id);
                }
                finally
                {
                    this._releaseReply(_input);
                }
            }
            else
            {
                org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke("getPlayerStatus",_opsClass);
                if (_so == null)
                   continue;
                Game.BehaviourOperations _self = (Game.BehaviourOperations) _so.servant;
                try
                {
                    return _self.getPlayerStatus( Username,  Password,  IPAddress);
                }
                finally
                {
                    _servant_postinvoke(_so);
                }
            }
        }
    }

    /**
     * Operation suspendAccount
     */
    public String suspendAccount(String admin_username, String admin_password, String admin_ip_address, String username)
    {
        while(true)
        {
            if (!this._is_local())
            {
                org.omg.CORBA.portable.InputStream _input = null;
                try
                {
                    org.omg.CORBA.portable.OutputStream _output = this._request("suspendAccount",true);
                    _output.write_string(admin_username);
                    _output.write_string(admin_password);
                    _output.write_string(admin_ip_address);
                    _output.write_string(username);
                    _input = this._invoke(_output);
                    String _arg_ret = _input.read_string();
                    return _arg_ret;
                }
                catch(org.omg.CORBA.portable.RemarshalException _exception)
                {
                    continue;
                }
                catch(org.omg.CORBA.portable.ApplicationException _exception)
                {
                    String _exception_id = _exception.getId();
                    throw new org.omg.CORBA.UNKNOWN("Unexpected User Exception: "+ _exception_id);
                }
                finally
                {
                    this._releaseReply(_input);
                }
            }
            else
            {
                org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke("suspendAccount",_opsClass);
                if (_so == null)
                   continue;
                Game.BehaviourOperations _self = (Game.BehaviourOperations) _so.servant;
                try
                {
                    return _self.suspendAccount( admin_username,  admin_password,  admin_ip_address,  username);
                }
                finally
                {
                    _servant_postinvoke(_so);
                }
            }
        }
    }

    /**
     * Operation transferAccount
     */
    public String transferAccount(String username, String password, String old_IP_address, String new_IP_address)
    {
        while(true)
        {
            if (!this._is_local())
            {
                org.omg.CORBA.portable.InputStream _input = null;
                try
                {
                    org.omg.CORBA.portable.OutputStream _output = this._request("transferAccount",true);
                    _output.write_string(username);
                    _output.write_string(password);
                    _output.write_string(old_IP_address);
                    _output.write_string(new_IP_address);
                    _input = this._invoke(_output);
                    String _arg_ret = _input.read_string();
                    return _arg_ret;
                }
                catch(org.omg.CORBA.portable.RemarshalException _exception)
                {
                    continue;
                }
                catch(org.omg.CORBA.portable.ApplicationException _exception)
                {
                    String _exception_id = _exception.getId();
                    throw new org.omg.CORBA.UNKNOWN("Unexpected User Exception: "+ _exception_id);
                }
                finally
                {
                    this._releaseReply(_input);
                }
            }
            else
            {
                org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke("transferAccount",_opsClass);
                if (_so == null)
                   continue;
                Game.BehaviourOperations _self = (Game.BehaviourOperations) _so.servant;
                try
                {
                    return _self.transferAccount( username,  password,  old_IP_address,  new_IP_address);
                }
                finally
                {
                    _servant_postinvoke(_so);
                }
            }
        }
    }

}
