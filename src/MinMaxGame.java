/**
 * Class that can play turns in a game using the MinMax algorithm, up to the finish.
 *
 * @param <T> Type of states of a game.
 * @param <R> Type that represents the players.
 */
public class MinMaxGame<T, R> {
    private Game<T, R> problem;
    private State<T> current;
    private MinMaxAlgo.Heuristics heuristics;
    private int numberOfTurnsToDevelop;

    /**
     * Constructor.
     *
     * @param problem                Game to play in.
     * @param heuristics             Function to calculate a state's score.
     * @param numberOfTurnsToDevelop How much turns ahead to look in the MinMax algorithm. Starts from 1.
     */
    public MinMaxGame(Game<T, R> problem, MinMaxAlgo.Heuristics<T> heuristics, int numberOfTurnsToDevelop) {
        assert problem != null && heuristics != null;
        this.problem = problem;
        this.heuristics = heuristics;
        this.current = problem.getStart();
        this.numberOfTurnsToDevelop = numberOfTurnsToDevelop;
    }

    /**
     * Plays a single turn of the game using the MinMax algorithm.
     * A turn is one player's move. 2 calls to this function will play one player's turn, and then the other (both
     * using the MinMax algorithm).
     */
    public void playOnePlayersTurn() {
        current = MinMaxAlgo.chooseNextState(problem, current, heuristics, true, numberOfTurnsToDevelop);
    }

    /**
     * @return the current state of the game.
     */
    public State<T> getCurrent() {
        return current;
    }

    /**
     * @return The game played
     */
    public Game<T, R> getProblem() {
        return problem;
    }

    /**
     * Plays the game, turn by turn (for both players) until it is finished.
     *
     * @return The winning player
     */
    public R playAllGame() {
        while (!problem.isFinished(current))
            playOnePlayersTurn();
        return problem.whichPlayerWon(current);
    }
}
