import org.junit.jupiter.api.Test;

public class IrregularTest extends SudokuTest {

    @Test
    public void testIrregular1() {
        int[][] givens = {
                {3, 0, 0, 0, 0, 0, 0, 0, 1},
                {0, 9, 0, 1, 7, 2, 0, 0, 0},
                {0, 0, 3, 0, 0, 0, 9, 0, 0},
                {0, 7, 0, 0, 0, 0, 0, 4, 0},
                {0, 4, 0, 0, 3, 0, 0, 6, 0},
                {0, 5, 0, 0, 0, 0, 0, 9, 0},
                {0, 0, 6, 0, 0, 0, 5, 0, 0},
                {0, 0, 0, 8, 5, 6, 0, 2, 0},
                {7, 0, 0, 0, 0, 0, 0, 0, 8}
        };
        int[][] regions = {
                {1, 1, 1, 1, 1, 2, 2, 2, 2},
                {1, 3, 3, 3, 4, 4, 2, 2, 2},
                {1, 3, 3, 3, 4, 5, 5, 5, 2},
                {1, 3, 3, 3, 4, 5, 5, 5, 2},
                {1, 4, 4, 4, 4, 5, 5, 5, 6},
                {7, 4, 8, 8, 8, 6, 6, 6, 6},
                {7, 7, 8, 8, 8, 6, 9, 9, 9},
                {7, 7, 8, 8, 8, 6, 9, 9, 9},
                {7, 7, 7, 7, 6, 6, 9, 9, 9}
        };

        int[][] solution = {
                {3, 6, 2, 7, 4, 9, 8, 5, 1},
                {5, 9, 8, 1, 7, 2, 4, 3, 6},
                {1, 2, 3, 4, 6, 5, 9, 8, 7},
                {9, 7, 5, 6, 8, 3, 1, 4, 2},
                {8, 4, 1, 9, 3, 7, 2, 6, 5},
                {6, 5, 4, 2, 1, 8, 7, 9, 3},
                {2, 8, 6, 3, 9, 1, 5, 7, 4},
                {4, 1, 7, 8, 5, 6, 3, 2, 9},
                {7, 3, 9, 5, 2, 4, 6, 1, 8}
        };

        AbstractPuzzle irregular = new IrregularSudoku.IrregularSudokuBuilder(regions)
                .withGivens(givens)
                .build();

        int[][] solverSolution = irregular.solve();

        compareSolutions(solverSolution, solution);
    }

    @Test
    public void testIrregularSixBySix() {
        int[][] givens = {
                {3, 0, 0, 0, 0, 4},
                {0, 5, 3, 0, 0, 0},
                {5, 1, 2, 0, 0, 0},
                {0, 0, 0, 2, 1, 5},
                {0, 0, 0, 3, 5, 0},
                {2, 0, 0, 0, 0, 3}
        };
        int[][] regions = {
                {1, 1, 1, 2, 2, 2},
                {1, 3, 3, 3, 2, 2},
                {1, 3, 3, 3, 2, 4},
                {1, 5, 6, 6, 6, 4},
                {5, 5, 6, 6, 6, 4},
                {5, 5, 5, 4, 4, 4}
        };

        int[][] solution = {
                {3, 2, 1, 5, 6, 4},
                {4, 5, 3, 6, 2, 1},
                {5, 1, 2, 4, 3, 6},
                {6, 3, 4, 2, 1, 5},
                {1, 4, 6, 3, 5, 2},
                {2, 6, 5, 1, 4, 3}
        };

        AbstractPuzzle irregular = new IrregularSudoku.IrregularSudokuBuilder(regions)
                .withGivens(givens)
                .withSize(6)
                .build();

        compareSolutions(irregular.solve(), solution);
    }
}
