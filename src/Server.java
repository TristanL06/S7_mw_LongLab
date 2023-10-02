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
        System.out.println("Server is ready !");
    }



    public static void main(String[] args){

        startServer();

        long dureeTotaleMillisecondes = (60000 * 1/2); // 60 secondes
        long timeBeforeWarningTimeVote = dureeTotaleMillisecondes - (60000 * 1/4); // 30 secondes

        Timer timer = new Timer();

        TimerTask mainTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    objectDistant.broadcastMessage("Temps écoulé dans 5 minutes !\n\n");
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        TimerTask stopTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    objectDistant.broadcastMessage("Temps écoulé ! Fin du vote.\n\n");
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
