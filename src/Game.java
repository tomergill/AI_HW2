public abstract class Game<T, R> extends Searchable<T> {
    public abstract boolean isFinished(State<T> currentState);

    public abstract R whichPlayerWon(State<T> current);
}
