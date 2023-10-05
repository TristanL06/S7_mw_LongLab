package election.server;

import election.global.Result;
import election.global.objectInterface.ObjectDistant;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class Server {


    public static ObjectDistant objectDistant;
    private static int passwordToStopVotingPhase = 1234;
    private static long numberMinutesToVote = 5;
    private static double numberMinutesToVoteWarning = Math.min((double)numberMinutesToVote / 2, 5);
    private static double numberMinutesBeforeLaunchingWarning = numberMinutesToVote - numberMinutesToVoteWarning;

    private static void startServer() {
        try {
            objectDistant = new ObjectDistant(10001, passwordToStopVotingPhase);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        try {
            LocateRegistry.createRegistry(10001);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        try {
            Naming.rebind("rmi://localhost:10001/echo", objectDistant);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("example.Server is ready !");
    }



    public static void main(String[] args){

        startServer();

        long dureeTotaleMillisecondes = (60000 * numberMinutesToVote); // 60 secondes
        long timeBeforeWarningTimeVote = dureeTotaleMillisecondes - (long) (60000 * numberMinutesBeforeLaunchingWarning); // 30 secondes

        Timer timer = new Timer();

        TimerTask mainTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    objectDistant.broadcastMessage("Temps écoulé dans " + numberMinutesToVoteWarning + " minutes !\n");
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        TimerTask stopTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    objectDistant.broadcastMessage("Temps écoulé ! Fin du vote.\n");
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
                //TODO : create right result
                Result result = new Result(new HashMap<>());
                objectDistant.stopVoting(passwordToStopVotingPhase, result);
                spacing(3);
                System.out.println(result);
            }
        };
        timer.schedule(mainTask, timeBeforeWarningTimeVote); // 30 secondes
        timer.schedule(stopTask, dureeTotaleMillisecondes);

    }

    private static void spacing(int number) {
        for (int i = 0; i < number; i++) {
            System.out.println("");
        }
    }


}
