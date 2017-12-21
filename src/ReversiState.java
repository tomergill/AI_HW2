import java.util.Arrays;
import java.util.Objects;

/**
 * State of a Reversi game, holding the current game map and who should make the next turn (color).
 * Also may hold the states that can be reached from mthis state.
 */
public class ReversiState {
    private char[][] map;
    private char next_turn;

    /**
     * Ctor.
     *
     * @param map       Game map.
     * @param next_turn char representing a color in the Reversi game.
     */
    public ReversiState(char[][] map, char next_turn) {
        this.map = map;
        this.next_turn = next_turn;
    }

    /**
     * @return A deep-copy of the map of this state.
     */
    public char[][] getMap() {
        return CharMatrixCloner.clone(map);
    }

    /**
     * @return The length of the i axis of the map
     */
    public int get_len_i() {
        return map == null ? -1 : map.length;
    }

    /**
     * @return The length of the j axis of the map
     */
    public int get_len_j() {
        return map == null ? -1 : map[0].length;
    }

    /**
     * Sets a new map.
     *
     * @param map new map to be setted.
     */
    private void setMap(char[][] map) {
        this.map = map;
    }

    /**
     * @return char representing the color of the next player to play.
     */
    public char getNext_turn() {
        return next_turn;
    }

    /**
     * @param o Other object to check
     * @return true if the 2 states are equal (map is equal and next player is same), otherwise false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReversiState that = (ReversiState) o;
        return Arrays.equals(map, that.map) && next_turn == that.next_turn;
    }

    /**
     * @return IntelliJ generated hash code
     */
    @Override
    public int hashCode() {
        int result = Objects.hash(next_turn);
        result = 31 * result + Arrays.hashCode(map);
        return result;
    }

    /**
     * @return String representation of the object.
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("");
        for (char[] aMap : map) {
            builder.append(aMap);
            builder.append('\n');
        }
        return "ReversiState{" +
                "map=\n" + builder.toString() +
                '}';
    }
}
