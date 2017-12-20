import java.io.IOException;
import java.util.Arrays;

public class java_ex2 {

    public static void main(String[] args) {
        assert args.length < 4;

        String input_path = "input.txt", output_path = "output.txt";
        int rows = 5;
        if (args.length >= 1)
            rows = Integer.parseInt(args[0]);
        if (args.length >= 2)
            input_path = args[1];
        if (args.length == 3)
            output_path = args[2];

        char[][] char_map;
        try {
            if ((char_map = ReversiMapReader.readMap(input_path, rows)) == null) {
                System.out.println("Error Reading map");
                return;
            }
        } catch (IOException e) {
            System.out.println("Error reading file:");
            e.printStackTrace();
            return;
        }

        ReversiMap map = new ReversiMap(char_map, 'B', 'W', 'E', 'B');
        System.out.println("start children:");
        for (State<ReversiMapState> s : map.getChildStates(map.getStart()))
            System.out.println("##\n" + s.toString());
    }

    public static String printMap(char[][] map) {
        StringBuilder builder = new StringBuilder("");
        for (int i = 0; i < map.length; i++) {
            builder.append(map[i]);
            builder.append('\n');
        }
        System.out.println("###################");
        System.out.println(builder.toString());
        System.out.println("###################");
        return builder.toString();
    }
}
