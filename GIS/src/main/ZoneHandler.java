package main;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ZoneHandler {
    String savesLocation = "src/main/resources/dataresources/zonesaves";
    String[] dirFiles;
    public ZoneHandler() {
        dirFiles = getFileNames(savesLocation);
        System.out.println(arrToString(dirFiles));
    }

    public void save(Zone z) {
        int numFiles = dirFiles.length;
        String filePath = savesLocation+"zone"+numFiles+".zone";

        try {
            FileWriter f = new FileWriter(filePath);
            f.write(z.name + "\n");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String[] getFileNames(String directoryPath) {
        try {
            Path dirPath = FileSystems.getDefault().getPath(directoryPath);
            if (Files.isDirectory(dirPath)) {
                List<String> fileNames = new ArrayList<>();
                Files.list(dirPath)
                        .filter(Files::isRegularFile)
                        .forEach(file -> fileNames.add(file.getFileName().toString()));

                return fileNames.toArray(new String[0]);
            } else {
                return new String[]{"Error: Not a valid directory path."};
            }
        } catch (Exception e) {
            return new String[]{"An error occurred: " + e.getMessage()};
        }
    }

    private String arrToString(String[] arr) {
        String output = "[";
        for (int i = 0; i < arr.length-1; i++) {
            output += arr[i] + ", ";
        }
        output += arr[arr.length-1];
        output += "]";
        return output;
    }
}
