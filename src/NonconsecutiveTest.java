import org.junit.jupiter.api.Test;

public class NonconsecutiveTest extends SudokuTest {

    @Test
    public void testNonconsecutive1() {
        int[][] givens = {
                {0, 0, 0, 0, 7, 0, 0, 0, 0},
                {0, 6, 0, 5, 0, 1, 0, 9, 0},
                {7, 0, 0, 0, 0, 0, 0, 0, 5},
                {9, 0, 7, 0, 0, 0, 8, 0, 3},
                {0, 0, 0, 1, 0, 3, 0, 0, 0},
                {3, 0, 1, 0, 0, 0, 2, 0, 6},
                {5, 0, 0, 0, 0, 0, 0, 0, 4},
                {0, 4, 0, 3, 0, 5, 0, 2, 0},
                {0, 0, 0, 0, 4, 0, 0, 0, 0}
        };

        AbstractPuzzle regular = new RegularSudoku.RegularSudokuBuilder()
                .withGivens(givens)
                .build();
        AbstractPuzzle nonconsecutive = new NonconsecutiveSudoku.NonconsecutiveSudokuBuilder(regular)
                .build();

        int[][] solution = {
                {1, 3, 5, 2, 7, 9, 4, 6, 8},
                {4, 6, 8, 5, 3, 1, 7, 9, 2},
                {7, 9, 2, 8, 6, 4, 1, 3, 5},
                {9, 5, 7, 4, 2, 6, 8, 1, 3},
                {6, 2, 4, 1, 8, 3, 5, 7, 9},
                {3, 8, 1, 9, 5, 7, 2, 4, 6},
                {5, 1, 3, 7, 9, 2, 6, 8, 4},
                {8, 4, 6, 3, 1, 5, 9, 2, 7},
                {2, 7, 9, 6, 4, 8, 3, 5, 1}
        };

        compareSolutions(nonconsecutive.solve(), solution);
    }
}
