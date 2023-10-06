package election.global.objectInterface;

import election.global.Candidate;
import election.global.Interface.ClientPrompt;
import election.global.Interface.LogIn;
import election.global.Interface.ServerVote;
import election.global.Result;
import election.global.User;
import election.global.VotingMaterials;
import election.global.exception.globalException;
import election.global.exception.voteIsCloseException;

import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.ArrayList;


public class ObjectServerVote extends java.rmi.server.UnicastRemoteObject implements ServerVote {

    private final VotingMaterials votingMaterials;
    private final ObjectDistant objectDistant;
    private final int OTP;

    public ObjectServerVote(VotingMaterials votingMaterials, ObjectDistant objectDistant, int OTP) throws RemoteException {
        super();
        this.votingMaterials = votingMaterials;
        this.objectDistant = objectDistant;
        this.OTP = OTP;
    }


    public void vote(User user, ClientPrompt clientPrompt) throws globalException {

        try {
            clientPrompt.vote(votingMaterials);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        this.objectDistant.updateCandidate(votingMaterials, OTP, user);
    }



}
