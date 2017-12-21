import java.util.LinkedList;
import java.util.List;

/**
 * Class representing a Reversi game starting from a given char[][] map. Includes all the logic of the game:
 * placement of a stone in a place, getting all possible states from a given state, checking if the game ended and
 * checking which player won.
 */
public class Reversi extends Game<ReversiState, Character> {
    private char black, white, empty;
    private char[][] starting_game_map;
    private char starting_color;

    /**
     * Constructor.
     *
     * @param map            The starting map of the game, from this the start state of teh game will be made.
     * @param black          Char representing the black color.
     * @param white          Char representing the white color.
     * @param empty          Char representing the empty tile.
     * @param starting_color The first color to make a turn.
     */
    public Reversi(char[][] map, char black, char white, char empty, char starting_color) {
        assert starting_color == black || starting_color == white;
        this.starting_game_map = map;
        this.black = black;
        this.empty = empty;
        this.white = white;
        this.starting_color = starting_color;
    }

    /**
     * @return A starting state of this game.
     */
    @Override
    public State<ReversiState> getStart() {
        return new State<>(new ReversiState(CharMatrixCloner.clone(starting_game_map),
                starting_color));
    }

    /**
     * Creates and returns all the states of the game that can be made from this state (each state is made after the
     * current player makes a single, legal move).
     *
     * @param state The current state.
     * @return A list of all states that can be reached from state, each representing a different move made by the
     * player.
     */
    @Override
    public List<State<ReversiState>> getChildStates(State<ReversiState> state) {
        int len_i = state.getState().get_len_i(), len_j = state.getState().get_len_j();
        char[][] new_map;
        List<State<ReversiState>> children = new LinkedList<>();
        char turn = state.getState().getNext_turn();
        if (state.getChildren() == null) {
            for (int i = 0; i < len_i; i++) {
                for (int j = 0; j < len_j; j++) {
                    if ((new_map = placement(state.getState().getMap(), i, j, turn)) != null)
                        children.add(new State<>(new ReversiState(new_map, otherColor(turn))));
                }
            }
            state.setChildren(children);
        }
        return state.getChildren();
    }

    /**
     * @param i     i-index
     * @param j     j-index
     * @param len_i length of i axis
     * @param len_j length of j axis
     * @return true if point (i , j) is in the range of the map sized len_i x len_j
     */
    private boolean isPointInMap(int i, int j, int len_i, int len_j) {
        return (i >= 0) && (j >= 0) && (i < len_i) && (j < len_j);
    }

    /**
     * @param c Char representing either the black color or the white color.
     * @return The reversed color, or 0 if not black or white.
     */
    public char otherColor(char c) {
        if (c == black)
            return white;
        else if (c == white)
            return black;
        return 0;
    }

    /**
     * @param c   color to put in (i, j)
     * @param i   i-index
     * @param j   j-index
     * @param map The char matrix representing the current map
     * @return true if (i, j) has another stone near it, false otherwise
     */
    private boolean isNearAnotherStone(char c, int i, int j, char[][] map) {
        int len_i = map.length, len_j = map[0].length;
        if ((c != white && c != black) || !isPointInMap(i, j, len_i, len_j))
            return false;
        int[] ds = {-1, 0, 1};
        for (int di : ds) {
            for (int dj : ds) {
                if ((di == dj) && (dj == 0))
                    continue;
                int ni = i + di, nj = j + dj;
                if (isPointInMap(ni, nj, len_i, len_j) && map[ni][nj] != empty)
                    return true;
            }
        }
        return false;
    }

    /**
     * Checks if there are stones to flip in this direction (di, dj) after a stone was placed in (i, j), and if there
     * are flips them.
     *
     * @param c   color to put in (i, j)
     * @param i   i-index
     * @param j   j-index
     * @param map The char matrix representing the current map
     * @param di  i axis direction
     * @param dj  j axis direction
     * @return How many stones were flipped in this direction
     */
    private int flipInDirection(char[][] map, int i, int j, char c, int di, int dj) {
        if (di == 0 && dj == 0)
            return 0;
        int ni, nj, len_i = map.length, len_j = map[0].length, counter = 0;
        if ((c != white && c != black) || !isPointInMap(i, j, len_i, len_j))
            return -1;

        //find if there is another stone in the same color in the direction
        boolean found = false;
        ni = i;
        nj = j;
        while (isPointInMap((ni = ni + di), (nj = nj + dj), len_i, len_j)) { //while stone at i j is opposite color
            if (map[ni][nj] == c) {
                found = true;
                break;
            } else if (map[ni][nj] == empty)
                return 0;  // found empty space in this direction, no flip in there
        }
        if (!found)
            return 0;

        /* return in the same direction and flip the stones */
        // new coordinates
        ni -= di;
        nj -= dj;
        while (ni != i || nj != j) {
            map[ni][nj] = c;  // flip
            counter++;
            // new coordinates
            ni -= di;
            nj -= dj;
        }
        return counter;
    }

    /**
     * Checks if can place a stone (colored c) at (i, j) in map, and if can places it and flips the appropriate stones.
     *
     * @param c   color to put in (i, j)
     * @param i   i-index
     * @param j   j-index
     * @param map The char matrix representing the current map
     * @return The new map after the placement (changes are made to the given map itself), or null if placement is
     * illegal.
     */
    private char[][] placement(char[][] map, int i, int j, char c) {
        if (map == null || (c != white && c != black) || !isPointInMap(i, j, map.length, map[0].length))
            return null;
        if (map[i][j] != empty || !isNearAnotherStone(c, i, j, map))
            return null;
        map[i][j] = c;
        int[] ds = {-1, 0, 1};
        int sum = 0;
        for (int di : ds)
            for (int dj : ds)
                sum += flipInDirection(map, i, j, c, di, dj);
//        if (sum == 0)
//            return null;
        return map;
    }

    /**
     * @param currentState State of the game to check if finished
     * @return true if the game is finished (map is full), otherwise false.
     */
    @Override
    public boolean isFinished(State<ReversiState> currentState) {
        for (char[] row : currentState.getState().getMap())
            for (char tile : row)
                if (tile == empty)
                    return false;
        return true;
    }

    /**
     * @param current Current end-game state
     * @return The char representing the black color if the black has more stones (won), the char representing white
     * if white has more stones, null if there is a tile which isn't white or black or 0 if a tie is reached.
     */
    @Override
    public Character whichPlayerWon(State<ReversiState> current) {
        int sum_b = 0, sum_w = 0;
        for (char[] row : current.getState().getMap()) {
            for (char tile : row) {
                if (tile == white)
                    ++sum_w;
                else if (tile == black)
                    ++sum_b;
                else
                    return null;
            }
        }
        return sum_b > sum_w ? black : sum_w > sum_b ? white : 0;
    }
}
