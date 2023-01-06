package handler;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import static handler.Runner.Path;
import static handler.Runner.result;
import static handler.Runner.temp;

/**
 * This class read and write information by the console
 */
public class ConsoleHandler {
    /**
     * Get path to main folder by the console
     */
    public static String getPath() {
        System.out.print("Write path to files: ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    /**
     * Check the emptiness of path
     *
     * @param path - path to files
     */
    public static File pathIsEmpty(String path) {
        File mainDir = new File(path);
        if (!mainDir.exists()) {
            System.out.println("Incorrect Directory!");
            return null;
        }
        return mainDir;
    }

    /**
     * Cycle checker
     *
     * @param it  - current file
     * @param cnt - count of result changes
     */
    public static void printCycleMessage(File it, int cnt) throws Exception {
        if (temp.lastElement().equals(it) && cnt == 0) {
            throw new Exception("Cycles in the files");
        }
    }

    /**
     * Find path to file
     *
     * @param req - require in file
     * @return file by require
     */
    public static File findPath(String req) {
        File reqPath = new File(Path, req);
        if (reqPath.exists()) {
            return reqPath;
        } else {
            return null;
        }
    }

    /**
     * @param file - file that need to check
     * @param req  - list of requires
     * @return true or false
     */
    public static boolean isReady(File file, List<String> req) throws Exception {
        //If there is no require in file
        if (req.isEmpty() && !result.contains(file)) {
            return true;
        }
        for (String addReq : req) {
            File tmp = findPath(addReq);
            if (tmp == null) {
                throw new Exception("Non-existent path");
            }
            if (tmp.equals(file)) {
                throw new Exception("Cycles in the files");
            }
            if (!result.contains(tmp)) {
                return false;
            }
        }
        return true;
    }
}
