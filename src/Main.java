import election.global.Result;
import election.global.csvWorker;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Result result = new Result();
        result.evaluate();
        System.out.println(result);
    }
}