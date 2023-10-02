import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class VotingMaterials implements java.io.Serializable {

    ArrayList<Candidate> candidates;
    HashMap<Candidate,Integer> votes = new HashMap<>();

    public VotingMaterials(ArrayList<Candidate> candidates) {
        this.candidates = candidates;
    }

    public void vote() {
        Scanner scanner = new Scanner(System.in);
        for (Candidate candidate : candidates) {
            boolean isVoteValueFalse = true;
            while(isVoteValueFalse) {
                System.out.println(candidate.toString());
                String voteValue = scanner.nextLine();
                try {
                    int voteValueInteger = Integer.parseInt(voteValue);
                    isVoteValueFalse = false;
                    this.votes.put(candidate, voteValueInteger);
                } catch (NumberFormatException e) {
                    System.out.println("Veuillez entrer un nombre");
                }
            }
        }
    }




}
