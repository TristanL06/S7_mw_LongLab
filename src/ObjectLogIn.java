import java.rmi.RemoteException;

public class ObjectLogIn extends java.rmi.server.UnicastRemoteObject implements LogIn {


    public ObjectLogIn() throws RemoteException {
        super();
    }

    @Override
    public void displayMessage(String message) throws RemoteException {
        System.out.println("\nMessage reçu :" + message);
    }

    public void getResultVote(Result result) {
        System.out.println(result.toString());
    }

}
