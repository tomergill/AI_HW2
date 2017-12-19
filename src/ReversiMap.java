import java.util.LinkedList;
import java.util.List;

public class ReversiMap extends Searchable<ReversiMapState> {
    private char black, white, empty;

    public ReversiMap(char[][] map, char black, char white, char empty, char starting_color) {
        this.start = new State<ReversiMapState>(new ReversiMapState(map.clone(), starting_color));
        this.black = black;
        this.empty = empty;
        this.white = white;
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

    private char otherColor(char c) {
        if (c == black)
            return white;
        else if (c == white)
            return black;
        return 0;
    }

    private boolean isNearColor(char c, int i, int j, char[][] map) {
        int len_i = map.length, len_j = map[0].length;
        if ((c != white && c != black) || !isPointInMap(i, j, len_i, len_j))
            return false;
        int[] ds = {-1, 0, 1};
        for (int di : ds) {
            for (int dj : ds) {
                if ((di == dj) && (dj == 0))
                    continue;
                int ni = i + di, nj = j + dj;
                if (isPointInMap(ni, nj, len_i, len_j) && map[ni][nj] == otherColor(c))
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
        while (isPointInMap((ni = i + di), (nj = j + dj), len_i, len_j) && map[ni][nj] == otherColor) {
            map[ni][nj] = c;
            counter++;
        }
        return counter;
    }

    private char[][] placement(char[][] map, int i, int j, char c) {
        if (map == null || (c != white && c != black) || !isPointInMap(i, j, map.length, map[0].length))
            return null;
        if (map[i][j] != empty || !isNearColor(c, i, j, map))
            return null;
        map[i][j] = c;
        int[] ds = {-1, 0, 1};
        int sum = 0;
        for (int di : ds)
            for (int dj : ds)
                sum += flipInDirection(map, i, j, c, di, dj);
        if (sum == 0)
            return null;
        return map;
    }
}
