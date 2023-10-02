import exception.globalException;

import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;


public class ObjectDistant extends java.rmi.server.UnicastRemoteObject implements Distant {

    private static ArrayList<Candidate> candidate;


    public ObjectDistant(int port) throws RemoteException {
        super(port);
    }


    public static synchronized ArrayList<Candidate> getInstanceCandidate() throws RemoteException {
        if (candidate == null) {
            //TODO : create candidate using csv file
            candidate = new ArrayList<>();
            candidate.add(new Candidate("Jean", "Dupont", ""));
        }
        return candidate;
    }

    public ArrayList<Candidate> retrieveCandidate() throws RemoteException {
        return getInstanceCandidate();
    }

    public void getVotingMaterials(clientStub clientStubElement, int studentNumber) throws RemoteException, globalException {
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
