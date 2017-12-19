import java.util.Arrays;

public class ReversiMapState {
    private char[][] map;
    private char next_turn;

    public ReversiMapState(char[][] map, char next_turn) {
        this.map = map;
        this.next_turn = next_turn;
    }

    public char[][] getMap() {
        return map.clone();
    }

    public int get_len_i() {return map == null ? -1 : map.length;}
    public int get_len_j() {return map == null ? -1 : map[0].length;}

    private void setMap(char[][] map) {
        this.map = map;
    }

    public char getNext_turn() {
        return next_turn;
    }

    public void putCharAt(int i, int j, char c) {map[i][j] = c; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReversiMapState that = (ReversiMapState) o;
        return Arrays.equals(map, that.map) && next_turn == that.next_turn;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(map);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        ReversiMapState clone = (ReversiMapState) super.clone();
        clone.setMap(map.clone());
        return clone;
    }

    @Override
    public String toString() {
        return "ReversiMapState{" +
                "map=" + Arrays.toString(map) +
                '}';
    }
}
