import java.util.Arrays;

public class CharMatrixCloner {
    public static char[][] clone(char[][] matrix) {
        assert matrix != null && matrix.length > 0;
        char[][] copy = new char[matrix.length][];
        for (int i = 0; i < matrix.length; ++i)
            copy[i] = Arrays.copyOf(matrix[i], matrix[i].length);
        return copy;
    }
}
