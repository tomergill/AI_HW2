import java.util.List;

/**
 * Class of a general state in a Searchable problem, with cost.
 *
 * @param <T> Type of state.
 */
public class State<T> {
    private T state;
    private List<State<T>> children;

    /**
     * Constructor.
     *
     * @param state The state this object represents.
     */
    public State(T state) {
        this.state = state;
        this.children = null;
    }

    /**
     * Gets the state.
     *
     * @return the state.
     */
    public T getState() {
        return state;
    }

    /**
     * @return The children of this state
     */
    public List<State<T>> getChildren() {
        return children;
    }

    /**
     * Sets the states that can be reached from this state
     *
     * @param children List of states that can be reached from this state.
     */
    public void setChildren(List<State<T>> children) {
        this.children = children;
    }

    /**
     * Checks if object is equal to this state.
     *
     * @param o Object to check.
     * @return True if equals, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        State<?> state1 = (State<?>) o;

        return state.equals(state1.state);
    }

    /**
     * @return the hashcode of this state.
     */
    @Override
    public int hashCode() {
        return state.hashCode();
    }

    /**
     * @return String representation of the state and it's children
     */
    @Override
    public String toString() {
        return "State{" +
                "state=" + state +
                ", children=" + children +
                '}';
    }
}
