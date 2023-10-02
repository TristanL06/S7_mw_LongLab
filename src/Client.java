import exception.globalException;
import exception.voteIsCloseException;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Client {

    private static Distant server;
    private static ArrayList<Candidate> candidate;
    private static ObjectClientStub objectClientStub;
    private static LogIn logIn;
    private static boolean keepLooping = true;
    private static boolean stillCanVote;

    private static ExecutorService executor;
    private static Scanner scanner;
    private static String input;

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

        System.out.println("Client is ready !\n\n");
        LogIn();
        createStub();
        registerInServer();



        loopInterfaceClient();

        System.exit(0);



    }

    private static void LogIn() {
        try {
            logIn = new ObjectLogIn();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    private static void createStub() {
        try {
            objectClientStub = new ObjectClientStub();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    private static void registerInServer() {
        try {
            server.registerUser(logIn);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
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
        int studentNumber;
        Optional<Integer> studentNumberOptional = getUserStudentNumber();
        if (studentNumberOptional.isPresent()) {
            studentNumber = studentNumberOptional.get();
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
        } catch (globalException e) {
            System.err.println("\n  -> An error has occurred : " + e.getErrorTitle() + "\n");
        }
        for (int i = 0; i < candidate.size(); i++) {
            System.out.println("Candidate: " + i);
            System.out.println(candidate.get(i).toString());
            System.out.println("");
        }
    }

    private static void spacing(int number) {
        for (int i = 0; i < number; i++) {
            System.out.println("");
        }
    }



    private static void loopInterfaceClient() {

        updateStillCanVote();

        while(keepLooping) {
            if (!stillCanVote) {
                loopInterfaceClientResultPhase();
            } else {
                updateStillCanVote();
                if (!stillCanVote) {
                    loopInterfaceClientResultPhase();
                } else {
                    loopInterfaceClientVotingPhase();
                }
            }

        }
    }

    private static void updateStillCanVote() {
        try {
            stillCanVote = server.isStillInVotingPhase();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    private static void scannerMethod() {
        scanner = new Scanner(System.in);
        input = scanner.nextLine();
        executor.shutdownNow();
    }

    private static void loopInterfaceClientVotingPhase() {
        System.out.println("1. View candidates");
        System.out.println("2. Vote");
        System.out.println("3. Quit");
        System.out.print("Enter your choice: ");
        int choice;
        executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> scannerMethod());
        boolean st = true;
        while (st && !executor.isTerminated()) {
            try {
                // Attendez un certain temps avant de vérifier à nouveau
                st = server.isStillInVotingPhase();
                stillCanVote = st;
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Vous ne pouvez plus voter");
        if (!stillCanVote) {
            executor.shutdownNow();
            choice = 4;
        } else {
            choice = Integer.parseInt(input);
        }
        spacing(1);
        switch (choice) {
            case 1:
                displayCandidates();
                spacing(2);
                break;
            case 2:
                displayVote();
                spacing(2);
                break;
            case 3 :
                keepLooping = false;
                break;
            case 4:
                break;
            default:
                System.out.println("Invalid choice");
                spacing(2);
        }
    }

    private static void loopInterfaceClientResultPhase() {
        System.out.println("1. View results");
        System.out.println("2. Quit");
        System.out.print("Enter your choice: ");


        int choice;
        if (executor.isTerminated()) {
            executor.shutdown();
            try {
                executor.awaitTermination(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            executor.shutdown();
            executor = Executors.newSingleThreadExecutor();
            executor.submit(() -> scannerMethod());
        }
        while (!executor.isTerminated()) {
            try {
                Thread.sleep(1); // Attendre 1 seconde (peut être ajusté)
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        choice = Integer.parseInt(input);

        spacing(1);
        switch (choice) {
            case 1:
                displayResult();
                spacing(2);
                break;
            case 2:
                keepLooping = false;
            default:
                System.out.println("Invalid choice");
                spacing(2);
        }
    }

    private static void displayResult() {
        try {
            server.getResultVote(logIn);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (voteIsCloseException e) {
            throw new RuntimeException(e);
        }
    }
}

