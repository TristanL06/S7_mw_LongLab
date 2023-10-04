package election.global.objectInterface;

import election.global.Candidate;
import election.global.Interface.ServerCandidate;
import election.global.Interface.ServerVote;
import election.global.Result;
import election.global.VotingMaterials;
import election.global.Interface.Distant;
import election.global.Interface.LogIn;
import election.global.csvWorker;
import election.global.exception.badCredentialsException;
import election.global.exception.globalException;
import election.global.exception.voteIsCloseException;

import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class ObjectDistant extends java.rmi.server.UnicastRemoteObject implements Distant {

    private static ArrayList<Candidate> candidate;
    private static ArrayList<LogIn> users = new ArrayList<>();
    private static int passwordStopVoting;
    private static boolean votingIsOpen = true;

    private static Result result;
    private static int lastOTP = 0;

    public ObjectDistant(int port, int passwordStopVoting) throws RemoteException {
        super(port);
        this.passwordStopVoting = passwordStopVoting;
    }

    public static synchronized ArrayList<Candidate> getInstanceCandidate() throws RemoteException {
        if (candidate == null) {
            //TODO : create candidate using csv file
            candidate = new ArrayList<>();
            csvWorker csv = new csvWorker();
            String[][] cs = csv.readCSV("src/election/global/candidate.csv");
            for (String[] c : cs) {
                candidate.add(new Candidate(c[0], c[1], c[2], Boolean.parseBoolean(c[3])));
            }
            candidate.add(new Candidate("Jean", "Dupont", "Hello", false));
            candidate.add(new Candidate("Jean", "Dupont", "Hello", true));
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
        List<LogIn> usersCopy = new ArrayList<>(users);
        for (LogIn logInUser : usersCopy) {
            try {
                logInUser.displayMessageFromServer(message);
            } catch (RemoteException e) {
                users.remove(logInUser);
            }
        }
    }

    public void registerUser(LogIn logIn) {
        users.add(logIn);
    }

    public ServerCandidate retrieveCandidate() throws RemoteException, globalException {
        if (votingIsOpen) {
            ServerCandidate serverCandidate = new ObjectServerCandidate(getInstanceCandidate());
            return serverCandidate;
        } else {
            throw new voteIsCloseException();
        }
    }

    private int getOTP() {
        return this.lastOTP + 1;
    }

    public ServerVote getVotingMaterials(String password) throws RemoteException, globalException {
        if (!votingIsOpen) {
            throw new voteIsCloseException();
        }
        boolean userWasAbleToLogIn = this.checkCredentials(password);
        if (userWasAbleToLogIn) {
            //TODO : create right votingMaterials
            VotingMaterials votingMaterials = new VotingMaterials(getInstanceCandidate());
            ServerVote serverVote = new ObjectServerVote(votingMaterials, this, this.getOTP());
            return serverVote;
        } else {
            throw new badCredentialsException(password);
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

    public void updateCandidate(VotingMaterials votingMaterials, int OTP, String userName, int userNumber) {
        this.updateUsers(userName);
        //TODO : update candidate using votingMaterials
        //TODO : update user with his vote in order to allow him to modify it later
    }

    private void updateUsers(String userName) {
        LocalDateTime dateOfVote = LocalDateTime.now();
        //TODO : update user with his vote, his name and the date of his vote
    }




}