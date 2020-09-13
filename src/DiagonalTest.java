import org.junit.jupiter.api.Test;

public class DiagonalTest extends SudokuTest {

    @Test
    public void testDiagonal1() {
        int[][] givens = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 7, 9, 6, 0, 0},
                {2, 0, 0, 0, 0, 0, 0, 3, 0},
                {3, 0, 0, 0, 0, 0, 0, 1, 0},
                {0, 4, 0, 0, 0, 0, 0, 2, 0},
                {0, 5, 0, 0, 0, 0, 0, 0, 7},
                {0, 6, 0, 0, 0, 0, 0, 0, 2},
                {0, 0, 7, 8, 9, 0, 0, 0, 5},
                {0, 0, 0, 0, 0, 0, 0, 0, 0}
        };

        AbstractPuzzle regular = new RegularSudoku.RegularSudokuBuilder()
                .withGivens(givens)
                .build();
        AbstractPuzzle diagonal = new ExtraRegionSudoku.ExtraRegionSudokuBuilder(regular)
                .withDiagonals()
                .build();

        int[][] solution = {
                {7, 8, 6, 3, 5, 1, 2, 4, 9},
                {1, 3, 4, 2, 7, 9, 6, 5, 8},
                {2, 9, 5, 4, 6, 8, 7, 3, 1},
                {3, 7, 2, 9, 8, 4, 5, 1, 6},
                {6, 4, 8, 7, 1, 5, 9, 2, 3},
                {9, 5, 1, 6, 3, 2, 4, 8, 7},
                {5, 6, 3, 1, 4, 7, 8, 9, 2},
                {4, 2, 7, 8, 9, 3, 1, 6, 5},
                {8, 1, 9, 5, 2, 6, 3, 7, 4}
        };

        compareSolutions(diagonal.solve(), solution);
    }
}
