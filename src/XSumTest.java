import org.junit.jupiter.api.Test;

public class XSumTest extends SudokuTest {

    @Test
    public void testXSum() {
        int[] leftRowSums = {-1, 8, -1, 17, -1, 30, -1, 28, -1};
        int[] topColSums = {-1, 27, -1, 11, -1, 21, 16, -1, -1};
        int[] rightRowSums = {-1, 8, -1, 17, -1, 30, -1, 28, -1};
        int[] bottomColSums = {-1, 27, -1, 11, -1, -1, 16, -1, -1};

        AbstractPuzzle regular = new RegularSudoku.RegularSudokuBuilder()
                .build();
        AbstractPuzzle xsums = new XSumSudoku.XSumSudokuBuilder(regular)
                .withLeftRowSums(leftRowSums)
                .withTopColSums(topColSums)
                .withRightRowSums(rightRowSums)
                .withBottomColSums(bottomColSums)
                .build();

        int[][] solution = {
                {8, 5, 6, 2, 1, 4, 3, 7, 9},
                {3, 4, 1, 9, 7, 5, 8, 6, 2},
                {7, 9, 2, 8, 6, 3, 5, 4, 1},
                {4, 1, 7, 5, 2, 9, 6, 8, 3},
                {9, 8, 5, 6, 3, 1, 7, 2, 4},
                {6, 2, 3, 4, 8, 7, 1, 9, 5},
                {2, 7, 4, 1, 5, 6, 9, 3, 8},
                {5, 3, 9, 7, 4, 8, 2, 1, 6},
                {1, 6, 8, 3, 9, 2, 4, 5, 7}
        };

        compareSolutions(xsums.solve(), solution);
    }
}
