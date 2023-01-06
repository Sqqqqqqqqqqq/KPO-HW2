package handler;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import static handler.Runner.result;

/**
 * This class handles with files and their requires
 */
public class FileHandler {
    /**
     * Concatenate list of files and print it
     */
    public static void concatenateFiles() throws IOException {
        for (File file : result) {
            Reader reader = new FileReader(file);
            BufferedReader br = new BufferedReader(reader);
            while (br.ready()) {
                System.out.println(br.readLine());
            }
            reader.close();
            br.close();
        }
    }

    /**
     * Add all keys of HashMap to Vector
     *
     * @param in  - element of temp
     * @param lst - list of requires
     */
    public static void addToTemp(Vector<File> in, HashMap<File, List<String>> lst) {
        in.addAll(lst.keySet());
    }

    /**
     * Find all requires in file
     *
     * @param entry - file are working with
     * @return requires of file
     */
    public static HashMap<File, List<String>> fileRequires(File entry) throws IOException {
        HashMap<File, List<String>> reqList = new HashMap<>();
        List<String> req = new ArrayList<>();

        FileReader fr = new FileReader(entry);
        BufferedReader reader = new BufferedReader(fr);

        String line = reader.readLine();
        while (line != null) {
            //find requires in text
            if (line.contains("require ‘")) {
                req.add(line.substring(line.indexOf('‘') + 1, line.indexOf('’')));
            }
            line = reader.readLine();
        }
        reqList.put(entry, req);
        return reqList;
    }

    /**
     * Create list of files with their requires
     *
     * @return map of requires
     */
    public static HashMap<File, List<String>> processFilesFromFolder(File folder) throws IOException {
        HashMap<File, List<String>> miniMap = new HashMap<>();
        File[] folderEntries = folder.listFiles();
        assert folderEntries != null;
        //Recursively traversing all files in a folder and subfolders
        for (File entry : folderEntries) {
            if (entry.isDirectory()) {
                miniMap.putAll(processFilesFromFolder(entry));
            } else {
                //Merge maps
                miniMap.putAll(fileRequires(entry));
            }
        }
        return miniMap;
    }
}
