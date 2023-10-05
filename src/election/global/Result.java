package election.global;

import java.util.HashMap;


public class Result implements java.io.Serializable {

    HashMap<Candidate, Integer> result;

    public Result(HashMap<Candidate, Integer> result) {
        this.result = result;
    }

    private Candidate getCandidateByRank(int rank) {
        csvWorker csv = new csvWorker();
        String[][] candidates = csv.readCSV("candidates.csv");
        for (String[] candidate : candidates) {
            if (Integer.parseInt(candidate[0]) == rank) {
                return new Candidate(candidate[0], candidate[1], candidate[2], Boolean.parseBoolean(candidate[3]));
            }
        }
        return null;
    }

    public HashMap<Candidate, Integer> evaluate() {
        csvWorker csv = new csvWorker();
        String[][] votes = csv.readCSV("votes.csv");
        HashMap<Users, HashMap<Candidate, Integer>> globaVotes = new HashMap<>();
        for (String[] vote : votes) {
            HashMap<Candidate, Integer> userVote = new HashMap<>();
            for (int i = 1; i < vote.length; i++) {
                userVote.put(this.getCandidateByRank(i), Integer.parseInt(vote[i]));
            }
        }
        for (Users user : globaVotes.keySet()) {
            for (Candidate candidate : globaVotes.get(user).keySet()) {
                if (result.containsKey(candidate)) {
                    result.put(candidate, result.get(candidate) + globaVotes.get(user).get(candidate));
                } else {
                    result.put(candidate, globaVotes.get(user).get(candidate));
                }
            }
        }
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
