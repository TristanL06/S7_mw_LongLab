package election.global;

import java.util.HashMap;


public class Result implements java.io.Serializable {

    HashMap<String, Integer> result;
    int voters = 0;

    public Result() {
        this.result = new HashMap<>();
    }
    public Result(HashMap<String, Integer> result) {
        this.result = result;
    }

    private Candidate getCandidateByRank(int rank) {
        csvWorker csv = new csvWorker();
        String[][] candidates = csv.readCSV("../data/candidats.csv");
        for (String[] candidate : candidates) {
            if (Integer.parseInt(candidate[0]) == rank) {
                return new Candidate(candidate[0], candidate[1], candidate[2], Boolean.parseBoolean(candidate[3]));
            }
        }
        return null;
    }

    public HashMap<String, Integer> evaluate() {
        csvWorker csv = new csvWorker();
        String[][] votes = csv.readCSV("../data/votes.csv");
        HashMap<String, HashMap<String, Integer>> globalVotes = new HashMap<>(); // Hashmap<UserId, Hashmap<CandidateRank, Note>>
        for (String[] vote : votes) { // pour tous les votes on attribue à la paire "votant", "candidat" la note. On réécrit si plusieurs votes
            if (!globalVotes.containsKey(vote[0])) { // Si le votant n'est pas enregistré dans les résultats ça l'ajoute
                voters++;
                globalVotes.put(vote[0], new HashMap<>());
            }
            globalVotes.get(vote[0]).put(vote[1], Integer.parseInt(vote[2]));
        }
        HashMap<String, Integer> result = new HashMap<>();
        for (String userId : globalVotes.keySet()) {
            for (String candidate : globalVotes.get(userId).keySet()) {
                if (!this.result.containsKey(candidate)) {
                    this.result.put(candidate, 0);
                }
                this.result.put(candidate, this.result.get(candidate) + globalVotes.get(userId).get(candidate));
            }
        }
        return this.result;
    }

    private String[] sortResults(HashMap<String, Integer> hashmap) {
        String[] sorted = new String[hashmap.size()];
        HashMap<String, Integer> entryCopy = new HashMap<>(hashmap);
        int size = entryCopy.size();
        for (int i = 0; i < size; i++) {
            int max = 0;
            String maxKey = "";
            for (String key : entryCopy.keySet()) {
                if (entryCopy.get(key) > max) {
                    max = entryCopy.get(key);
                    maxKey = key;
                }
            }
            sorted[i] = maxKey;
            entryCopy.remove(maxKey);
        }
        return sorted;
    }

    public String toString() {
        String resultString = "";
        csvWorker csv = new csvWorker();
        String[][] candidates = csv.readCSV("../data/candidats.csv");
        String[] results = sortResults(this.result);
        for (String i : results) {
            Candidate candidate = getCandidateByRank(Integer.parseInt(i));
            resultString += i + " : " + candidate.getName() + " (" + this.result.get(i)/this.voters + "/3)\n";
        }
        return resultString;
    }


}
