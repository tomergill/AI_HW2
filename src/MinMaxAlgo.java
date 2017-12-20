import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.DoubleStream;

public class MinMaxAlgo {
    public interface Heuristics<T> {double calculate(State<T> current);}

    public static <T> State<T> chooseNextState(Searchable<T> problem, State<T> current, Heuristics<T> score_calc,
                                               boolean isAFirst, int max_depth) {
        List<State<T>> children = problem.getChildStates(current);
        if (children == null)  // leaf state
            return current;
        double best = Double.NEGATIVE_INFINITY;
        int best_index = -1;
        for (int i = 0; i < children.size(); ++i) {
            /*
             * calculates the calculate of each chile with the given calculation function, if A is first then chooses
             * the minimal state from the the child's children. depth is max_depth - 2 because it's one level down
             * and also -1 to change to representation that starts with 0 (so max_depth = 3 goes 2 more levels than
             * this.
             */
            double score = getScore(problem, children.get(i), score_calc, !isAFirst, max_depth - 2);
            if (isAFirst) {
                if (score > best) {
                    best = score;
                    best_index = i;
                }
            } else {
                if (score < best) {
                    best = score;
                    best_index = i;
                }
            }
        }
        return children.get(best_index);
    }

    private static <T> double getScore(Searchable<T> problem, State<T> current, Heuristics<T> score_calc,
                                       boolean max, int depth) {
        if (depth == 0)
            return score_calc.calculate(current);
        List<State<T>> children = problem.getChildStates(current);
        if (children == null)  // leaf state
            return score_calc.calculate(current);
        DoubleStream stream = children.stream().mapToDouble(
                state -> getScore(problem, state, score_calc, !max, depth - 1)
        );
        OptionalDouble optionalDouble;
        if (max)
            optionalDouble = stream.max();
        else
            optionalDouble = stream.min();
        if (optionalDouble.isPresent())
            return optionalDouble.getAsDouble();
        return Double.NEGATIVE_INFINITY;
    }

}
