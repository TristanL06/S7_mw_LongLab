package election.global.Interface;

import election.global.Candidate;
import election.global.User;
import election.global.VotingMaterials;
import election.global.exception.globalException;

import java.util.AbstractMap;
import java.util.ArrayList;

public interface ServerVote extends java.rmi.Remote {

    public void vote(User user, ClientPrompt clientPrompt) throws java.rmi.RemoteException, globalException;

}
