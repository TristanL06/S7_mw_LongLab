import exception.globalException;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;

public class Client {

    private static Distant server;
    private static ArrayList<Candidate> candidate;

    public static void main(String[] args) {


        try {
            server = (Distant) Naming.lookup("rmi://localhost:10001/echo");
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Client is ready !");
        loopInterfaceClient();




    }


    private static Optional<Integer> getUserStudentNumber() {
        Scanner scanner = new Scanner(System.in);
        int studentNumber;
        try {
            System.out.println("Entrez votre numéro d'étudiant : ");
            studentNumber = scanner.nextInt();
            return Optional.of(studentNumber);
        } catch (InputMismatchException e) {
            System.out.println("Entrée invalide. Veuillez saisir un nombre entier.");
            scanner.nextLine(); // Efface la ligne incorrecte du scanner
            return Optional.empty();
        }
    }

    private static void displayVote() {
        System.out.println(candidate.toString());
        ObjectClientStub objectClientStub;
        try {
            objectClientStub = new ObjectClientStub();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        int studentNumber;
        if (getUserStudentNumber().isPresent()) {
            studentNumber = getUserStudentNumber().get();
            System.out.println("\n\n\n");
        } else {
            return;
        }

        try {
            server.getVotingMaterials(objectClientStub, studentNumber);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (globalException e) {
            System.err.println("\n  -> An error has occurred : " + e.getErrorTitle() + "\n");
        }
    }


    private static void displayCandidates() {
        try {
            candidate = server.retrieveCandidate();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < candidate.size(); i++) {
            System.out.println("Candidate: " + i);
            System.out.println(candidate.get(i).toString());
            System.out.println("");
        }
    }

    private static void spacing() {
        System.out.println("");
        System.out.println("");
        System.out.println("");
    }



    private static void loopInterfaceClient() {
        boolean keepLooping = true;
        while(keepLooping) {
            System.out.println("1. View candidates");
            System.out.println("2. Vote");
            System.out.println("3. Quit");
            System.out.print("Enter your choice: ");
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    displayCandidates();
                    spacing();
                    break;
                case 2:
                    displayVote();
                    spacing();
                    break;
                case 3 :
                    keepLooping = false;
                default:
                    System.out.println("Invalid choice");
                    spacing();
            }
        }
    }


}
