public class MinMaxGame<T, R> {
    private Game<T, R> problem;
    private State<T> current;
    private MinMaxAlgo.StateScore score_calculator;
    private int numberOfTurnsToDevelop;


    public MinMaxGame(Game<T, R> problem, MinMaxAlgo.StateScore score_calculator, int numberOfTurnsToDevelop) {
        assert problem != null && score_calculator != null;
        this.problem = problem;
        this.score_calculator = score_calculator;
        this.current = problem.getStart();
        this.numberOfTurnsToDevelop = numberOfTurnsToDevelop;
    }

    public void playOnePlayersTurn() {
        current = MinMaxAlgo.chooseNextState(problem, current, score_calculator, true, numberOfTurnsToDevelop);
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
