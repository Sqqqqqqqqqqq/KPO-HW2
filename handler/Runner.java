package handler;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import static handler.ConsoleHandler.isReady;
import static handler.FileHandler.*;

/**
 * This class use other classes to perform a task
 */
public class Runner {
    static File mainDir;
    static Vector<File> result = new Vector<>();
    static Vector<File> temp = new Vector<>();
    //Path to direction with files
    static String Path;

    public static void run() throws Exception {
        do {
            Path = ConsoleHandler.getPath();
            mainDir = ConsoleHandler.pathIsEmpty(Path);
        } while (mainDir == null);

        HashMap<File, List<String>> reqList = processFilesFromFolder(mainDir);
        if (reqList.isEmpty()) {
            System.out.println("Directory is empty");
            return;
        }

        addToTemp(temp, reqList);

        while (true) {
            int cnt = 0;
            boolean end = false;
            for (File it : temp) {
                //if it necessary to add element to list
                if (isReady(it, reqList.get(it)) && !result.contains(it)) {
                    result.add(it);
                    ++cnt;
                }
                ConsoleHandler.printCycleMessage(it, cnt);
                //if all files in list
                if (result.size() == reqList.size()) {
                    end = true;
                    break;
                }
            }
            if (end) {
                break;
            }
        }

        //Concatenating files
        concatenateFiles();
    }
}
