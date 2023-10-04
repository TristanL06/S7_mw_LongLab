package election.global.Interface;

import election.global.Candidate;
import election.global.exception.globalException;
import election.global.exception.voteIsCloseException;

import java.util.ArrayList;

public interface Distant extends java.rmi.Remote {

    public ServerCandidate retrieveCandidate() throws java.rmi.RemoteException, globalException;

    public ServerVote getVotingMaterials(String password) throws java.rmi.RemoteException, globalException;

    public void registerUser(LogIn login) throws java.rmi.RemoteException;

    public void getResultVote(LogIn login) throws java.rmi.RemoteException, voteIsCloseException;

    public boolean isStillInVotingPhase() throws java.rmi.RemoteException;

}
