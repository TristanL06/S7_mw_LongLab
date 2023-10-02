import java.util.HashMap;


public class Result implements java.io.Serializable {

    HashMap<Candidate, Integer> result;

    public Result(HashMap<Candidate, Integer> result) {
        this.result = result;
    }

    public String toString() {
        String resultString = "";
        for (Candidate candidate : result.keySet()) {
            resultString += candidate.toString() + " : " + result.get(candidate) + "\n";
        }
        return resultString;
    }


}
