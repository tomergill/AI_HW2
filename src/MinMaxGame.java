public class MinMaxGame<T, R> {
    private Game<T, R> problem;
    private State<T> current;
    private MinMaxAlgo.Heuristics heuristics;
    private int numberOfTurnsToDevelop;


    public MinMaxGame(Game<T, R> problem, MinMaxAlgo.Heuristics<T> heuristics, int numberOfTurnsToDevelop) {
        assert problem != null && heuristics != null;
        this.problem = problem;
        this.heuristics = heuristics;
        this.current = problem.getStart();
        this.numberOfTurnsToDevelop = numberOfTurnsToDevelop;
    }

    public void playOnePlayersTurn() {
        current = MinMaxAlgo.chooseNextState(problem, current, heuristics, true, numberOfTurnsToDevelop);
    }

    public State<T> getCurrent() {
        return current;
    }

    public Game<T, R> getProblem() {
        return problem;
    }

    public R playAllGame() {
        while (!problem.isFinished(current))
            playOnePlayersTurn();
        return problem.whichPlayerWon(current);
    }
}
