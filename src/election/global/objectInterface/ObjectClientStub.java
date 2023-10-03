package election.global.objectInterface;

import election.global.VotingMaterials;
import election.global.Interface.clientStub;
import election.global.exception.badCredentialsException;
import election.global.exception.globalException;

import java.rmi.RemoteException;
import java.util.Scanner;

public class ObjectClientStub extends java.rmi.server.UnicastRemoteObject implements clientStub {




    private Scanner scanner = new Scanner(System.in);


    public ObjectClientStub() throws RemoteException {
        super();
    }

    @Override
    public String getCredentials() throws RemoteException {
        System.out.print("Veuillez entrer votre mot de passe : ");
        String password = scanner.nextLine();
        System.out.println("\n\n\n");
        return password;
    }


    public void badCredentials(String password) throws globalException {
        throw new badCredentialsException(password);
    }

    public VotingMaterials goodCredentials(VotingMaterials votingMaterials) {
        System.out.println("Mot de passe correct\n\n\n");
        votingMaterials.vote();
        return votingMaterials;
    }

    public String getUserName() throws RemoteException {
        System.out.print("Veuillez entrer votre nom d'utilisateur : ");
        String userName = scanner.nextLine();
        System.out.println("\n\n\n");
        return userName;
    }

}
