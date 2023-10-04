import election.global.csvWorker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class csvWorkerTest {
    @BeforeEach
    void init() throws IOException {
        csvWorker csvWorker = new csvWorker("test.csv");

    }

    @AfterEach
    void clear() throws IOException {
        csvWorker csvWorker = new csvWorker();
        csvWorker.deleteCSV("test.csv");
    }

    @org.junit.jupiter.api.Test
    void getAvailableFiles() {
        csvWorker csvWorker = new csvWorker();
        ArrayList<String> files = csvWorker.getAvailableFiles();
        assertEquals(3, files.size());
        assertEquals("candidats.csv", files.get(0));
        assertEquals("votants.csv", files.get(1));
        assertEquals("test.csv", files.get(2));
    }

    @org.junit.jupiter.api.Test
    void readCSV() throws IOException {
        csvWorker csvWorker = new csvWorker();
        csvWorker.writeCSV("test.csv", new String[][]{{"1", "2", "3"}, {"4", "5", "6"}, {"7", "8", "9"}});
        String[][] result = csvWorker.readCSV("test.csv");
        assertEquals(result.length, 3);
        assertEquals(result[0][0], "1");
        assertEquals(result[0][1], "2");
        assertEquals(result[0][2], "3");
        assertEquals(result[1][0], "4");
        assertEquals(result[1][1], "5");
        assertEquals(result[1][2], "6");
        assertEquals(result[2][0], "7");
        assertEquals(result[2][1], "8");
        assertEquals(result[2][2], "9");
    }

    @org.junit.jupiter.api.Test
    void writeCSV() throws IOException {
        csvWorker csvWorker = new csvWorker();
        csvWorker.writeCSV("test.csv", new String[][]{{"1", "2", "3"}, {"4", "5", "6"}});
        assertEquals(3, csvWorker.getAvailableFiles().size());
        assertEquals("test.csv", csvWorker.getAvailableFiles().get(2));
    }

    @org.junit.jupiter.api.Test
    void appendCSV() throws IOException {
        csvWorker csvWorker = new csvWorker();
        csvWorker.appendCSV("test.csv", new String[]{"7", "8", "9"});
        csvWorker.appendCSV("test.csv", new String[]{"10", "11", "12"});
        String[][] result = csvWorker.readCSV("test.csv");
        assertEquals(result.length, 4);
        assertEquals("1", result[0][0]);
        assertEquals("12", result[3][3]);
    }

    @Test
    void deleteCSV() throws IOException {
        csvWorker csvWorker = new csvWorker();
        csvWorker.deleteCSV("test.csv");
        assertEquals(csvWorker.getAvailableFiles().size(), 2);
    }
}