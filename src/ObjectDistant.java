import java.rmi.RemoteException;



public class ObjectDistant extends java.rmi.server.UnicastRemoteObject implements Distant {

    private static Candidate candidate;

    public ObjectDistant(int port) throws RemoteException {
        super(port);
    }


    public static synchronized Candidate getInstanceCandidate() throws RemoteException {
        if (candidate == null) {
            candidate = new Candidate("Jean", "Dupont", "");
        }
        return candidate;
    }

    public Candidate retrieveCandidate() throws RemoteException {
        return getInstanceCandidate();
    }


}
