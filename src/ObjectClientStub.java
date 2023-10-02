import java.rmi.RemoteException;
import java.util.Scanner;

public class ObjectClientStub extends java.rmi.server.UnicastRemoteObject implements clientStub {




    private Scanner scanner = new Scanner(System.in);


    protected ObjectClientStub() throws RemoteException {
        super();
    }

    @Override
    public String getCredentials() throws RemoteException {
        System.out.print("Veuillez entrer votre mot de passe : ");
        String password = scanner.nextLine();
        return password;
    }


    public void badCredentials() {
        System.out.println("Mauvais mot de passe");
    }

    public VotingMaterials goodCredentials(VotingMaterials votingMaterials) {
        System.out.println("Bon mot de passe");
        votingMaterials.vote();
        return votingMaterials;
    }



}
