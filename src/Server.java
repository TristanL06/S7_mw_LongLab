import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Server {


    public static ObjectDistant objectDistant;



    public static void main(String[] args){
        try {
            objectDistant = new ObjectDistant(10001);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        try {
            LocateRegistry.createRegistry(10001);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        try {
            Naming.rebind("rmi://localhost:10001/echo", objectDistant);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Server is ready");
    }




}
