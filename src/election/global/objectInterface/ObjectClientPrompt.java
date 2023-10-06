package election.global.objectInterface;


import election.global.Interface.ClientPrompt;
import election.global.VotingMaterials;

import java.util.Scanner;

public class ObjectClientPrompt extends java.rmi.server.UnicastRemoteObject implements ClientPrompt {


    public ObjectClientPrompt() throws java.rmi.RemoteException {
        super();
    }

    public VotingMaterials vote(VotingMaterials votingMaterials) throws java.rmi.RemoteException {
        votingMaterials.vote();
        return votingMaterials;
    }
}
