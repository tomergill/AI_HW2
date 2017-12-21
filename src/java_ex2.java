import java.io.FileWriter;
import java.io.IOException;

/**
 * Main class - reads the map from the file, creates a Reversi object and plays it turn by turn using the MinMax
 * Algorithm, then write the result to an output file.
 */
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

        /* Reading the map */
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

        /* Create the Reversi game, and the MinMaxGame that will play it's turns */
        Reversi game = new Reversi(char_map, 'B', 'W', 'E', 'B');
        MinMaxGame gamePlayer = new MinMaxGame<>(game, (State<ReversiState> state) -> {
            // Function for calculating a score of a game state
            char[][] map = state.getState().getMap();
            char otherPlayer = state.getState().getNext_turn(), player = game.otherColor(otherPlayer);
            int sum_p = 0, sum_o = 0, sum_e = 0, sum_wall_p = 0, sum_wall_o = 0;
            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[0].length; j++) {
                    if (map[i][j] == player) {
                        sum_p++;
                        if (i == 0 || i == map.length - 1 || j == 0 || j == map[0].length - 1)
                            sum_wall_p++;
                    } else if (map[i][j] == otherPlayer) {
                        sum_o++;
                        if (i == 0 || i == map.length - 1 || j == 0 || j == map[0].length - 1)
                            sum_wall_o++;
                    } else
                        sum_e++;
                }
            }
            if (sum_e == 0) { //game finished
                if (sum_p > sum_o)  // win
                    return Double.POSITIVE_INFINITY;
                if (sum_p < sum_o)  // lose
                    return Double.NEGATIVE_INFINITY;
                return 0.0;  // tie
            }

            // game not finished
            return (sum_p - sum_o) + (sum_wall_p - sum_wall_o);

        }, 3);

        /* Play the game, get the winner and write to file */
        Object winningPlayer = gamePlayer.playAllGame();
        System.out.println("WINNER:" + winningPlayer);
        try {
            FileWriter writer = new FileWriter(output_path);
            writer.write(winningPlayer.toString());
            writer.close();
        } catch (IOException e) {
            System.out.println("Error opening file to write");
            e.printStackTrace();
        }
    }

//    public static String printMap(char[][] map) {
//        StringBuilder builder = new StringBuilder("");
//        for (int i = 0; i < map.length; i++) {
//            builder.append(map[i]);
//            builder.append('\n');
//        }
//        System.out.println("###################");
//        System.out.println(builder.toString());
//        System.out.println("###################");
//        return builder.toString();
//    }
}
