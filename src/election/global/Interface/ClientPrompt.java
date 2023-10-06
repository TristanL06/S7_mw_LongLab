package election.global.Interface;

import election.global.VotingMaterials;

public interface ClientPrompt extends java.rmi.Remote {

    public String ask() throws java.rmi.RemoteException;

    public void print(String message) throws java.rmi.RemoteException;

    public VotingMaterials vote(VotingMaterials votingMaterials) throws java.rmi.RemoteException;

}
