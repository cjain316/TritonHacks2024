package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ZoneHandler {
    String savesLocation = "src/main/resources/dataresources/zonesaves/";
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
            f.write(z.boundary.PointsToString() + "\n");
            f.write(z.boundary.LinecoordsToString() + "\n");
            f.write(arrListToString(z.attributes));

            f.close();

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
        for (int i = 0; i < arr.length; i++) {
            output += arr[i] + ",";
        }
        output += "]";
        return output;
    }

    private String arrListToString(ArrayList<String> arr) {
        if (arr.size() > 0) {
            String output = "[";
            for (int i = 0; i < arr.size()-1; i++) {
                output += arr.get(i) + ",";
            }
            output += arr.get(arr.size()-1);
            output += "]";
            return output;
        }
        return "";
    }

    private ArrayList<Zone> parseZones() {
        ArrayList<Zone> output = new ArrayList<Zone>();
        for (String path: dirFiles) {
            try {
                Scanner s = new Scanner(new File(savesLocation + path));


            } catch (FileNotFoundException e) {
                System.out.println("Could not read file!");
                return null;
            }
        }
        return output;
    }
}
