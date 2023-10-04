import election.global.csvWorker;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        csvWorker csvWorker = new csvWorker();
        System.out.println(csvWorker.getAvailableFiles());
        String[][] result = csvWorker.readCSV("candidats.csv");
        for (String[] strings : result) {
            for (String string : strings) {
                System.out.print(string + " ");
            }
            System.out.println();
        }

        csvWorker.writeCSV("votants.csv", new String[][]{{"1", "2", "3"}, {"4", "5", "6"}});
        csvWorker.appendCSV("votants.csv", new String[]{"7", "8", "9"});
        csvWorker.appendCSV("votants.csv", new String[]{"10", "11", "12"});
        result = csvWorker.readCSV("votants.csv");
        for (String[] strings : result) {
            for (String string : strings) {
                System.out.print(string + " ");
            }
            System.out.println();
        }
    }
}