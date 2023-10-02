import java.rmi.RemoteException;
import java.util.Scanner;


public class ObjectDistant extends java.rmi.server.UnicastRemoteObject implements Distant {

    private static Candidate candidate;
    private int number;
    private Scanner scanner = new Scanner(System.in);


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

    public void getVotingMaterials(clientStub clientStubElement) throws RemoteException{
        String password = clientStubElement.getCredentials();
        boolean userWasAbleToLogIn = this.checkCredentials(password);
        if (userWasAbleToLogIn) {
            clientStubElement.goodCredentials();
        } else {
            clientStubElement.badCredentials();
        }
    }


    private boolean checkCredentials(String password) {
        return true;
    }




}
