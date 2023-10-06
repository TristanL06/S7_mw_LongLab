package election.global.objectInterface;


import election.global.Interface.ClientPrompt;
import election.global.VotingMaterials;

import java.util.Scanner;

public class ObjectClientPrompt extends java.rmi.server.UnicastRemoteObject implements ClientPrompt {


    public ObjectClientPrompt() throws java.rmi.RemoteException {
        super();
    }

    public String ask() throws java.rmi.RemoteException {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public void print(String message) throws java.rmi.RemoteException {
        System.out.println(message);
    }

    public VotingMaterials vote(VotingMaterials votingMaterials) throws java.rmi.RemoteException {
        votingMaterials.vote();
        return votingMaterials;
    }
}
