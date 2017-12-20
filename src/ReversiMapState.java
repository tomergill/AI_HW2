import java.util.Arrays;

public class ReversiMapState {
    private char[][] map;
    private char next_turn;

    public ReversiMapState(char[][] map , char next_turn) {
        this.map = map;
        this.next_turn = next_turn;
    }

    public char[][] getMap() {
        char[][] copy = new char[map.length][];
        for (int i = 0; i < map.length; ++i)
            copy[i] = Arrays.copyOf(map[i], map[i].length);
        return copy;
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
        StringBuilder builder = new StringBuilder("");
        for (int i = 0; i < map.length; i++) {
            builder.append(map[i]);
            builder.append('\n');
        }
        return "ReversiMapState{" +
                "map=\n" + builder.toString() +
                '}';
    }
}
