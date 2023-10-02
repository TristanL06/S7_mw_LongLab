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
        /**
         * Method use to create a new LogIn object
         */
        try {
            logIn = new ObjectLogIn();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    private static void createStub() {
        /**
         * Method use to create a new ObjectClientStub object
         */
        try {
            objectClientStub = new ObjectClientStub();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    private static void registerInServer() {
        /**
         * Method use to register the client in the server
         */
        try {
            server.registerUser(logIn);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }


    private static Optional<Integer> getUserStudentNumber() {
        /**
         * Method use to ask the user to enter his student number
         *
         * @return Optional<Integer> : the student number if the user entered a valid one, empty otherwise
         */
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
        /**
         * Method use to display the vote interface in order to allow the user to vote
         */
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
        /**
         * Method use to display the candidates
         */
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
        /**
         * Method use to add a certain number of line breaks for more lisibility in the console
         */
        for (int i = 0; i < number; i++) {
            System.out.println("");
        }
    }



    private static void loopInterfaceClient() {
        /**
         * Method use to loop the client interface until he wants to exit
         * Display either a vote interface or a result interface depending on the voting phase of the server
         */

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
        /**
         * Method use to update the stillCanVote variable : if the server is still in voting phase, the client can still vote
         */
        try {
            stillCanVote = server.isStillInVotingPhase();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    private static void scannerMethod() {
        /**
         * Method use to scan the user input. Used in a thread to bypass conflict between the end of the voting phase and the user input
         */
        scanner = new Scanner(System.in);
        input = scanner.nextLine();
        executor.shutdownNow();
    }

    private static int loopInterfaceClientVotingPhase_executor() {
        /**
         * Method use to manage the executor of the scannerMethod and the end of the voting phase for the voting interface
         */
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
        if (!stillCanVote) {
            executor.shutdownNow();
            choice = 4;
        } else {
            choice = Integer.parseInt(input);
        }
        return choice;
    }

    private static void loopInterfaceClientVotingPhase() {
        /**
         * Method use to display the voting interface
         * Ask the user to choose between 3 options : view candidates, vote or quit
         */
        System.out.println("1. View candidates");
        System.out.println("2. Vote");
        System.out.println("3. Quit");
        System.out.print("Enter your choice: ");
        int choice = loopInterfaceClientVotingPhase_executor();
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
                //DOing nothing : use when the voting phase is over
                break;
            default:
                System.out.println("Invalid choice");
                spacing(2);
        }
    }

    private static int loopInterfaceClientResultPhase_executor() {
        /**
         * Method use to manage the executor of the scannerMethod for the result interface
         */
        int choice;
        if (executor.isTerminated()) {
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
        return choice;
    }

    private static void loopInterfaceClientResultPhase() {
        /**
         * Method use to display the result interface
         * Ask the user to choose between 2 options : view results or quit
         */
        System.out.println("1. View results");
        System.out.println("2. Quit");
        System.out.print("Enter your choice: ");
        int choice = loopInterfaceClientResultPhase_executor();
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
        /**
         * Method use to display the result of the vote
         */
        try {
            server.getResultVote(logIn);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (voteIsCloseException e) {
            throw new RuntimeException(e);
        }
    }
}

