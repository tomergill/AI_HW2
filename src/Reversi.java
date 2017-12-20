import java.util.LinkedList;
import java.util.List;

public class Reversi extends Game<ReversiMapState, Character> {
    private char black, white, empty;
    private char[][] starting_game_map;
    private char starting_color;

    public Reversi(char[][] map, char black, char white, char empty, char starting_color) {
        this.starting_game_map = map;
        this.black = black;
        this.empty = empty;
        this.white = white;
        this.starting_color = starting_color;
    }

    @Override
    public State<ReversiMapState> getStart() {
        return new State<ReversiMapState>(new ReversiMapState(CharMatrixCloner.clone(starting_game_map),
                starting_color));
    }

    @Override
    public boolean isGoal(State<ReversiMapState> state) {
        return false;
    }

    @Override
    public List<State<ReversiMapState>> getChildStates(State<ReversiMapState> state) {
        int len_i = state.getState().get_len_i(), len_j = state.getState().get_len_j();
        char[][] new_map = null;
        List<State<ReversiMapState>> children = new LinkedList<>();
        char turn = state.getState().getNext_turn();
        if (state.getChildren() == null) {
            for (int i = 0; i < len_i; i++) {
                for (int j = 0; j < len_j; j++) {
                    if ((new_map = placement(state.getState().getMap(), i ,j, turn)) != null)
                        children.add(new State<>(new ReversiMapState(new_map, otherColor(turn))));
                }
            }
            state.setChildren(children);
        }
        return state.getChildren();
    }

    @Override
    public double getEstimationForState(State<ReversiMapState> state) {
        return 0;
    }

    private boolean isPointInMap(int i, int j, int len_i, int len_j) {
        return (i >= 0) && (j >= 0) && (i < len_i) && (j < len_j);
    }

    public char otherColor(char c) {
        if (c == black)
            return white;
        else if (c == white)
            return black;
        return 0;
    }

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

    private int flipInDirection(char[][] map, int i, int j, char c, int di, int dj) {
        if (di == 0 && dj == 0)
            return 0;
        char otherColor = otherColor(c);
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
            }
            else if (map[ni][nj] == empty)
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

    @Override
    public boolean isFinished(State<ReversiMapState> currentState) {
        for (char[] row : currentState.getState().getMap())
            for (char tile : row)
                if (tile == empty)
                    return false;
        return true;
    }

    @Override
    public Character whichPlayerWon(State<ReversiMapState> current) {
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
