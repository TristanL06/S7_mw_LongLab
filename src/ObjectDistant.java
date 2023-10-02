import exception.globalException;
import exception.voteIsCloseException;

import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;


public class ObjectDistant extends java.rmi.server.UnicastRemoteObject implements Distant {

    private static ArrayList<Candidate> candidate;
    private static ArrayList<LogIn> users = new ArrayList<>();
    private static int passwordStopVoting;
    private static boolean votingIsOpen = true;

    private static Result result;

    public ObjectDistant(int port, int passwordStopVoting) throws RemoteException {
        super(port);
        this.passwordStopVoting = passwordStopVoting;
    }

    public static synchronized ArrayList<Candidate> getInstanceCandidate() throws RemoteException {
        if (candidate == null) {
            //TODO : create candidate using csv file
            candidate = new ArrayList<>();
            candidate.add(new Candidate("Jean", "Dupont", ""));
        }
        return candidate;
    }

    public static void stopVoting(int password, Result resultGiven) {
        if (password == passwordStopVoting) {
            System.out.println("Voting is now closed");
            result = resultGiven;
            votingIsOpen = false;
        } else {
            System.out.println("Wrong password");
        }
    }

    public void broadcastMessage(String message) throws RemoteException {
        for (LogIn logInUser : users) {
            logInUser.displayMessage(message);
        }
    }

    public void registerUser(LogIn logIn) {
        users.add(logIn);
    }

    public ArrayList<Candidate> retrieveCandidate() throws RemoteException, globalException {
        if (votingIsOpen) {
            return getInstanceCandidate();
        } else {
            throw new voteIsCloseException();
        }
    }

    public void getVotingMaterials(clientStub clientStubElement, int studentNumber) throws RemoteException, globalException {
        if (!votingIsOpen) {
            throw new voteIsCloseException();
        }
        String password = clientStubElement.getCredentials();
        boolean userWasAbleToLogIn = this.checkCredentials(password);
        if (userWasAbleToLogIn) {
            //TODO : create right votingMaterials
            VotingMaterials votingMaterials = new VotingMaterials(getInstanceCandidate());
            VotingMaterials votingMaterialsModified = clientStubElement.goodCredentials(votingMaterials);
            this.updateCandidate(votingMaterialsModified);

            String userName = clientStubElement.getUserName();

            this.updateUsers(userName);
        } else {
            clientStubElement.badCredentials(password);
        }
    }


    public void getResultVote(LogIn login) throws RemoteException {
        login.getResultVote(result);
    }

    public boolean isStillInVotingPhase() throws RemoteException {
        return votingIsOpen;
    }


    private boolean checkCredentials(String password) {
        //TODO : check credentials
        return true;
    }

    private void updateCandidate(VotingMaterials votingMaterials) {
        //TODO : update candidate using votingMaterials
        //TODO : update user with his vote in order to allow him to modify it later
    }

    private void updateUsers(String userName) {
        LocalDateTime dateOfVote = LocalDateTime.now();
        //TODO : update user with his vote, his name and the date of his vote
    }




}
