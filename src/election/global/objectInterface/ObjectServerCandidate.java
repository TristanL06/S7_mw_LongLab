package election.global.objectInterface;

import election.global.Candidate;
import election.global.Interface.LogIn;
import election.global.Interface.ServerCandidate;
import election.global.Result;
import election.global.csvWorker;
import election.global.exception.globalException;
import election.global.exception.voteIsCloseException;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class ObjectServerCandidate extends java.rmi.server.UnicastRemoteObject implements ServerCandidate {


    private ArrayList<Candidate> candidate;

    public ObjectServerCandidate(ArrayList<Candidate> candidate) throws RemoteException {
        super();
        this.candidate = candidate;
    }

    public ArrayList<Candidate> getCandidate() throws RemoteException, globalException {
        return candidate;
    }


}
