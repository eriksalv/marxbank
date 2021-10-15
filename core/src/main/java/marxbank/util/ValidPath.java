package marxbank.util;

import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

public class ValidPath {
    /**
     * tests if a path is valid or not
     * @param path to test if its valid
     * @return true if path is valid, otherwise false
     */
    public static Boolean isValidPath(String path) {
        try {
            Paths.get(path);
        } catch (InvalidPathException e) {
            return false;
        }

        return true;
    }
}
