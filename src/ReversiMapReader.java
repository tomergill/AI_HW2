import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class for reading a char matrix from a file.
 */
public class ReversiMapReader {
    /**
     * Reads a char matrix from file.
     *
     * @param path Path of file to read from.
     * @param rows How many lines (rows) should be read from the file
     * @return The char matrix
     * @throws IOException If read failed.
     */
    public static char[][] readMap(String path, int rows) throws IOException {
        char[][] map = new char[rows][];
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String line;
        int i = -1;
        while (i < rows && (line = reader.readLine()) != null)
            map[++i] = line.toCharArray();
        reader.close();
        return i < 0 ? null : map;
    }
}
