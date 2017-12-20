import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReversiMapReader {
    public static char[][] readMap(String path, int rows) throws IOException {
        char[][] map = new char[rows][];
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String line;
        int i = -1;
        while ((line = reader.readLine()) != null)
            map[++i] = line.toCharArray();
        return i < 0 ? null : map;
    }
}
