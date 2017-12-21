import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.DoubleStream;

/**
 * Class that holds functions to choose the next state from a given state using the MinMax algorithm.
 */
public class MinMaxAlgo {
    /**
     * Interface for heuristic for a game, that by getting a state can calculate it's score
     *
     * @param <T> Type of state of the game
     */
    public interface Heuristics<T> {
        /**
         * Heuristic function for a game state, calculate it's score for the MinMax algorithm.
         *
         * @param current State to calculate it's score
         * @return The score of current
         */
        double calculate(State<T> current);
    }

    /**
     * Chooses the next state from this state's (current) children, using the MinMax algorithm - chooses the best
     * state based on it's score.
     *
     * @param problem    The game the state is part of.
     * @param current    The state to get it's next.
     * @param score_calc Heuristic function to calculate the score of a given state.
     * @param isAFirst   If true chooses the child state with the maximal score, otherwise chooses the minimal.
     * @param max_depth  How many layers of the game states tree to go down when choosing the next state. Starts from 1.
     * @param <T>        Type of search problem to choose next state from
     * @return The best next state from this state's children, or the current state if it has no children (finish
     * state).
     */
    public static <T> State<T> chooseNextState(Searchable<T> problem, State<T> current, Heuristics<T> score_calc,
                                               boolean isAFirst, int max_depth) {
        List<State<T>> children = problem.getChildStates(current);
        if (children == null)  // finish state
            return current;

        /* calculate each child state's score and chooses the best state according to the algorithm */
        double best = getScore(problem, children.get(0), score_calc, !isAFirst, max_depth - 2);
        int best_index = 0;
        for (int i = 1; i < children.size(); ++i) {
            /*
             * calculates the calculate of each chile with the given calculation function, if A is first then chooses
             * the minimal state from the the child's children. depth is max_depth - 2 because it's one level down
             * and also -1 to change to representation that starts with 0 (so max_depth = 3 goes 2 more levels than
             * this.
             */
            double score = getScore(problem, children.get(i), score_calc, !isAFirst, max_depth - 2);
            if (isAFirst) {  // choose max
                if (score > best) {
                    best = score;
                    best_index = i;
                }
            } else {  // choose min
                if (score < best) {
                    best = score;
                    best_index = i;
                }
            }
        }
        return children.get(best_index);
    }

    /**
     * Calculate the score of a state using it's children by the MinMax algorithm:
     * If state is leaf, return it's score.
     * Otherwise, return the best score of it's children (maximum if max is true, minimum else).
     * The children score will be calculated recursively using this function, with !max as max.
     *
     * @param problem    Searching problem that searching in
     * @param current    Current state to get it's score
     * @param score_calc Function to calculate a state's score (activated if leaf)
     * @param max        If true return the maximal score of the children, otherwise the minimal.
     * @param depth      How much more layers to develop in the game states tree
     * @param <T>        Type of search problem ans states
     * @return The score of this state, using the MinMax algorithm.
     */
    private static <T> double getScore(Searchable<T> problem, State<T> current, Heuristics<T> score_calc,
                                       boolean max, int depth) {
        if (depth == 0)  // max depth, make this state a leaf and return it's heuristic
            return score_calc.calculate(current);

        List<State<T>> children = problem.getChildStates(current);
        if (children == null)  // finish state
            return score_calc.calculate(current);

        // calculate score for each child as stream
        DoubleStream stream = children.stream().mapToDouble(
                state -> getScore(problem, state, score_calc, !max, depth - 1)
        );
        OptionalDouble optionalDouble;
        if (max)  // choose the max value
            optionalDouble = stream.max();
        else  // choose the min value
            optionalDouble = stream.min();

        if (optionalDouble.isPresent())
            return optionalDouble.getAsDouble();
        return Double.NEGATIVE_INFINITY;
    }

}
