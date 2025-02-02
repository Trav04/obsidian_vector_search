package projects;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class ObsidianProcessor {

    public static void main(String[] args) {
        Path dir = Paths.get("C:/Users/tngra/Documents/Main/");

        List<Path> excludedDirs = new ArrayList<>();

        excludedDirs.add(Paths.get("C:/Users/tngra/Documents/Main/.obsidian"));

        

        // Map to store file paths and their content
        List<ObsidianNote> obsidianNotes = new ArrayList<>();

        try (Stream<Path> stream = Files.walk(dir)) {

            stream.filter(Files::isRegularFile) // Only process files
                    .filter(file -> excludedDirs.stream().noneMatch(file::startsWith))
                    .forEach(file -> {
                        try {
                            // Read the file content and name as String
                            String content = Files.readString(file);
                            String filename = file.getFileName().toString();

                            obsidianNotes.add(new ObsidianNote(filename, content));
                        } catch (IOException e) {
                            System.err.println("Error reading file: " + file);
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(obsidianNotes.size());
    }
}