import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {


    public static void main(String[] args) {
        Distant server;
        Candidate candidate;
        try {
            server = (Distant) Naming.lookup("rmi://localhost:10001/echo");
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        try {
            candidate = server.retrieveCandidate();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        System.out.println(candidate.toString());
    }
}
