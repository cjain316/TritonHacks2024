package main;

import java.awt.*;
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

import static java.lang.Integer.parseInt;

public class ZoneHandler {
    String savesLocation = "src/main/resources/dataresources/zonesaves/";
    String[] dirFiles;
    public ZoneHandler() {
        dirFiles = getFileNames(savesLocation);
    }

    public void save(Zone zoneInit) {
        Zone z = zoneInit;
        z.name = ""+System.currentTimeMillis();
        int numFiles = dirFiles.length;
        String filePath = savesLocation+System.currentTimeMillis()+numFiles+".zone";

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
        String output = "[.";
        for (int i = 0; i < arr.length; i++) {
            output += arr[i] + ",";
        }
        output += ".]";
        return output;
    }

    private String arrListToString(ArrayList<String> arr) {
        if (arr.size() > 0) {
            String output = "[.";
            for (int i = 0; i < arr.size()-1; i++) {
                output += arr.get(i) + ",";
            }
            output += arr.get(arr.size()-1);
            output += ".]";
            return output;
        }
        return "";
    }

    public ArrayList<Zone> parseZones() {
        ArrayList<Zone> output = new ArrayList<Zone>();
        for (String path: dirFiles) {
            try {
                Scanner s = new Scanner(new File(savesLocation + path));
                String name = s.next();

                String boundaryPointsRaw = s.next();
                Boundary boundary = new Boundary(getPointsFromRaw(boundaryPointsRaw));

                String lineCoordsRaw = s.next();
                ArrayList<int[]> lineCoords = getLineCoordsFromRaw(lineCoordsRaw);

                String attributesRaw = s.next();
                ArrayList<String> attributes = getAttributesFromRaw(attributesRaw);

                Zone add = new Zone(name,boundary,new Color(
                        randInt(170,250),
                        randInt(170,250),
                        randInt(170,250)
                ),attributes);
                add.boundary.lineCords = lineCoords;

                output.add(add);

            } catch (FileNotFoundException e) {
                System.out.println("Could not read file!");
                return null;
            }
        }
        return output;
    }

    private ArrayList<String> getAttributesFromRaw(String raw) {
        String raw1 = (raw.split("\\.")[1]);
        String[] data = raw1.split(",");

        ArrayList<String> output = new ArrayList<String>();
        for (int i = 0; i < data.length; i++) {
            output.add(data[i]);
        }
        return output;
    }

    private ArrayList<int[]> getLineCoordsFromRaw(String raw) {
        ArrayList<int[]> output = new ArrayList<int[]>();


        String[] data = raw.split("\\.");
        for (int i = 1; i < data.length-1; i++) {
            output.add(getLineCoordSet(data[i]));
        }

        return output;
    }

    private int[] getLineCoordSet(String raw) {
        String[] data = raw.substring(1,raw.length()-1).split(",");
        ArrayList<Integer> outdata = new ArrayList<Integer>();
        for (String i : data) {
            outdata.add(parseInt(i));
        }
        return arrListToArr(outdata);
    }

    private int[] arrListToArr(ArrayList<Integer> arr) {
        int[] output = new int[arr.size()];
        for (int i = 0; i < arr.size(); i++) {
            output[i] = arr.get(i);
        }
        return output;
    }

    private ArrayList<Point> getPointsFromRaw(String raw) {
        ArrayList<Point> output = new ArrayList<Point>();

        String[] data = raw.split("\\.");

        for (int i = 1; i < data.length-1; i++) {
            output.add(getPointFromRaw(data[i]));
        }

        return output;
    }

    private Point getPointFromRaw(String raw) {
        String raw1 = raw.substring(1,raw.length()-1);
        String[] data = raw1.split(",");
        return new Point(parseInt(data[0]),parseInt(data[1]));
    }

    private int randInt(int high, int low) {
        return ((int)(Math.random()*(high-low)) + low);
    }
}
