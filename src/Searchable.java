import java.util.List;

/**
 * Abstract class for a search problem, using states.
 * Holds the start state and the goal state, estimation for each state (not mandatory) and also
 * manages what sates can be reached from each state.
 *
 * @param <T> Type of state, depends on the problem.
 */
public abstract class Searchable<T> {

    /**
     * @return The start state.
     */
    public abstract State<T> getStart();

    /**
     * Gets a list of all the states that can be reached from state.
     * The function creates all the new states and gives them the time of creation.
     *
     * @param state The father state.
     * @return A list of all the children states from state.
     */
    public abstract List<State<T>> getChildStates(State<T> state);
}
