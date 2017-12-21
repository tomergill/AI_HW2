import java.util.Arrays;

/**
 * Class that holds a function to deep-copy a char matrix
 */
public class CharMatrixCloner {
    /**
     * Deep-copies the given matrix (creating a new object with the same char values)
     *
     * @param matrix Char matrix to copy
     * @return The new char matrix after being copied matrix's contents.
     */
    public static char[][] clone(char[][] matrix) {
        assert matrix != null && matrix.length > 0;
        char[][] copy = new char[matrix.length][];
        for (int i = 0; i < matrix.length; ++i)
            copy[i] = Arrays.copyOf(matrix[i], matrix[i].length);
        return copy;
    }
}
