import static java.rmi.Naming.rebind;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

public class ServerDriver {
    public static void main(String[] args) throws RemoteException, MalformedURLException {

        rebind("RMIServer", new Server());
    }    
}
