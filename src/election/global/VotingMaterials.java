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
        System.out.println("Mot de passe correct\n\n\n");
        Scanner scanner = new Scanner(System.in);
        for (Candidate candidate : candidates) {
            boolean isVoteValueFalse = true;
            System.out.println(candidate.toString());
            while(isVoteValueFalse) {

                String voteValue = scanner.nextLine();
                try {
                    int voteValueInteger = Integer.parseInt(voteValue);
                    if (voteValueInteger < 0 || voteValueInteger > 3) {
                        System.out.println("Veuillez entrer un nombre entre 0 et 3");
                    } else {
                        isVoteValueFalse = false;
                        this.votes.put(candidate, voteValueInteger);
                        System.out.println("A voté !" + "\n\n\n");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Veuillez entrer un nombre");
                }

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
