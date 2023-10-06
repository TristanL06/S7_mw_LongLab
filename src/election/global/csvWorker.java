package election.global;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.stream.StreamSupport;

public class csvWorker {
    private String[] files;
    private String path = "data/";

    public csvWorker() {
        this.files = new String[]{"candidats.csv", "votants.csv"};
    }

    public csvWorker(String file) {
        this.files = new String[]{"candidats.csv", "votants.csv", file};
    }

    public ArrayList<String> getAvailableFiles() { // return the files defined in this.files, creating them if they don't exist
        ArrayList<String> files = new ArrayList<>();
        for (String file : this.files) {
            Path path = Path.of(this.path + file);
            if (!Files.exists(path)) { // if file doesn't exist, create it
                try {
                    Files.createFile(path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            files.add(file);
        }
        return files;
    }

    public String[][] readCSV(String file) { // return the content of the CSV file as a String[][]
        String[][] values;
        Path path = Path.of(this.path + file);
        try {
            byte[] bytes = Files.readAllBytes(path);
            String content = new String(bytes);
            String[] lines = content.split("\n");
            values = new String[lines.length - 1][];
            for (int i = 1; i < lines.length; i++) {
                String[] splitLine = lines[i].split(",");
                values[i - 1] = new String[splitLine.length];
                for (int j = 0; j < splitLine.length; j++) {
                    values[i - 1][j] = splitLine[j].trim(); // Trim whitespace
                }
            }
        } catch (IOException e) {
            values = new String[0][0];
        }
        return values;
    }


    public void writeCSV(String file, String[][] values) throws IOException {
        Path path = Path.of(this.path + file);
        String content = "";
        for (String[] value : values) {
            content += String.join(",", value) + "\n";
        }
        Files.write(path, content.getBytes());
    }

    public void appendCSV(String file, String[] values) {
        Path path = Path.of(this.path + file);
        try {
            String content = new String(Files.readAllBytes(path));
            content += String.join(",", values) + "\n";
            Files.write(path, content.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteCSV(String file) throws IOException {
        Path path = Path.of(this.path + file);
        Files.delete(path);
    }
}