/**
 * Class representing a turn-by-turn, 2 player game as a search problem, using a tree to represent the flow of the
 * game (the tree's nodes are the class State of type T)
 *
 * @param <T> Type of search problem states
 * @param <R> Object representing a player
 */
public abstract class Game<T, R> extends Searchable<T> {

    /**
     * Checks if the game is finished in the current state.
     *
     * @param currentState State of teh game to check if finished
     * @return true if the game is finished, false otherwise
     */
    public abstract boolean isFinished(State<T> currentState);

    /**
     * Checks which player won and return it. Should be used after checking if game finished.
     *
     * @param current Current end-game state
     * @return The player that won
     */
    public abstract R whichPlayerWon(State<T> current);
}
