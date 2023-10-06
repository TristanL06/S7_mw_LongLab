package election.global;

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
                    if (voteValueInteger < 0 || voteValueInteger > 3) {
                        System.out.println("Veuillez entrer un nombre entre 0 et 3");
                    } else {
                        isVoteValueFalse = false;
                        this.votes.put(candidate, voteValueInteger);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Veuillez entrer un nombre");
                }
                System.out.println(" a voté !" + "\n\n\n");
            }
        }
    }

    public String toString() {
        String result = "";
        for (Candidate candidate : candidates) {
            result += candidate + " : " + votes.get(candidate) + "\n";
        }
        return result;
    }




}
