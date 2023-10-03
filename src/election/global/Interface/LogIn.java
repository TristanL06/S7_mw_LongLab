package election.global.Interface;


import election.global.Result;

public interface LogIn extends java.rmi.Remote {

    public void displayMessage(String message) throws java.rmi.RemoteException;
    public void displayMessageFromServer(String message) throws java.rmi.RemoteException;
    public void getResultVote(Result result) throws java.rmi.RemoteException;

}
