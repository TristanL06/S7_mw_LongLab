package election.global.Interface;

import election.global.VotingMaterials;

public interface ClientPrompt extends java.rmi.Remote {

    public VotingMaterials vote(VotingMaterials votingMaterials) throws java.rmi.RemoteException;

}
