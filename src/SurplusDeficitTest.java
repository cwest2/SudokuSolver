import org.junit.jupiter.api.Test;

public class SurplusDeficitTest extends SudokuTest {

    @Test
    public void testSmallRegionSudoku() {
        int[][] givens = {
                {0, 0, 0, 0, 0, 2, 0, 0, 5},
                {0, 0, 0, 6, 0, 8, 0, 0, 0},
                {0, 0, 0, 8, 0, 6, 0, 0, 0},
                {6, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 5, 0, 0, 0, 0, 9, 0, 3},
                {3, 0, 0, 0, 0, 0, 0, 8, 0},
                {0, 6, 0, 1, 0, 0, 8, 0, 0},
                {0, 3, 2, 0, 0, 0, 0, 5, 0},
                {5, 0, 0, 0, 0, 0, 0, 4, 0}
        };
        int[][] regions = {
                {1, 1, 1, 2, 2, 3, 4, 4, 4},
                {1, 1, 1, 2, 2, 3, 4, 4, 4},
                {1, 1, 2, 2, 3, 3, 3, 4, 4},
                {5, 5, 2, 2, 3, 3, 3, 6, 6},
                {5, 5, 5, 5, 11, 6, 6, 6, 6},
                {5, 5, 7, 7, 7, 8, 8, 6, 6},
                {9, 9, 7, 7, 7, 8, 8, 10, 10},
                {9, 9, 9, 7, 8, 8, 10, 10, 10},
                {9, 9, 9, 7, 8, 8, 10, 10, 10}
        };

        int[][] solution = {
                {8, 7, 3, 4, 1, 2, 6, 9, 5},
                {9, 4, 5, 6, 3, 8, 2, 1, 7},
                {1, 2, 7, 8, 9, 6, 5, 3, 4},
                {6, 8, 9, 5, 4, 1, 3, 7, 2},
                {2, 5, 1, 7, 8, 4, 9, 6, 3},
                {3, 9, 6, 2, 7, 5, 4, 8, 1},
                {7, 6, 4, 1, 5, 3, 8, 2, 9},
                {4, 3, 2, 9, 6, 7, 1, 5, 8},
                {5, 1, 8, 3, 2, 9, 7, 4, 6}
        };

        AbstractPuzzle smallRegion = new SurplusDeficitSudoku.SurplusDeficitSudokuBuilder(regions)
                .withGivens(givens)
                .build();

        compareSolutions(smallRegion.solve(), solution);
    }
}
