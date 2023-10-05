package election.global;

import java.util.HashMap;


public class Result implements java.io.Serializable {

    HashMap<Candidate, Integer> result;

    public Result() {
        this.result = new HashMap<>();
    }
    public Result(HashMap<Candidate, Integer> result) {
        this.result = result;
    }

    private Candidate getCandidateByRank(int rank) {
        csvWorker csv = new csvWorker();
        String[][] candidates = csv.readCSV("candidates.csv");
        for (String[] candidate : candidates) {
            System.out.println("ranks =>");
            System.out.println(candidate[0]);
            System.out.println(rank);
            System.out.println("compare =>");
            System.out.println(Integer.parseInt(candidate[0]) == rank);
            if (Integer.parseInt(candidate[0]) == rank) {
                return new Candidate(candidate[0], candidate[1], candidate[2], Boolean.parseBoolean(candidate[3]));
            }
        }
        return null;
    }

    public HashMap<String, Integer> evaluate() {
        System.out.println("Evaluating votes");
        csvWorker csv = new csvWorker();
        String[][] votes = csv.readCSV("../data/votes.csv");
        HashMap<String, HashMap<String, Integer>> globalVotes = new HashMap<>(); // Hashmap<UserId, Hashmap<CandidateRank, Note>>
        for (String[] vote : votes) { // pour tous les votes on attribue à la paire "votant", "candidat" la note. On réécrit si plusieurs votes
            System.out.println("vote =>" + vote[0] + " " + vote[1] + " " + vote[2]);
            if (!globalVotes.containsKey(vote[0])) { // Si le votant n'est pas enregistré dans les résultats ça l'ajoute
                System.out.println("new user" + vote[0]);
                globalVotes.put(vote[0], new HashMap<>());
            }
            globalVotes.get(vote[0]).put(vote[1], Integer.parseInt(vote[2]));
            System.out.println(globalVotes);
        }
        HashMap<String, Integer> result = new HashMap<>();
        for (String userId : globalVotes.keySet()) {
            for (String candidate : globalVotes.get(userId).keySet()) {
                if (!result.containsKey(candidate)) {
                    result.put(candidate, 0);
                }
                result.put(candidate, result.get(candidate) + globalVotes.get(userId).get(candidate));
            }
        }
        System.out.println(result);
        return result;
    }

    public String toString() {
        String resultString = "";
        for (Candidate candidate : result.keySet()) {
            resultString += candidate.toString() + " : " + result.get(candidate) + "\n";
        }
        return resultString;
    }


}
