package election.global.Interface;

import election.global.Candidate;
import election.global.exception.globalException;

import java.util.ArrayList;

public interface ServerCandidate extends java.rmi.Remote {

    public ArrayList<Candidate> getCandidate() throws java.rmi.RemoteException, globalException;


}
