package election.global.objectInterface;

import election.global.Result;
import election.global.Interface.LogIn;

import java.rmi.RemoteException;

public class ObjectLogIn extends java.rmi.server.UnicastRemoteObject implements LogIn {


    public ObjectLogIn() throws RemoteException {
        super();
    }

    @Override
    public void displayMessage(String message) throws RemoteException {
        System.out.println("\nMessage reçu :" + message);
    }

    @Override
    public void displayMessageFromServer(String message) throws RemoteException {
        System.out.println("\n\n\n\u001B[34m<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\nMessage reçu du server : "
                + message
                + ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\u001B[0m\n");
    }

    public void getResultVote(Result result) {
        System.out.println(result);
    }

}
