import org.junit.jupiter.api.Test;

public class RegularTest extends SudokuTest{
    /**
     * Tests a sudoku that can be solved just through propagation, without branching.
     * Taken from the choco-solver Sudoku example
     */
    @Test
    public void testBasicSudoku1() {
        int[][] givens = new int[][]{
                {0, 0, 0, 2, 0, 0, 0, 0, 0},
                {0, 8, 0, 0, 3, 0, 0, 7, 0},
                {3, 0, 0, 5, 0, 4, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 2, 8},
                {8, 3, 0, 0, 1, 0, 0, 0, 0},
                {0, 4, 0, 7, 2, 0, 3, 5, 1},
                {0, 7, 0, 0, 5, 6, 0, 0, 4},
                {0, 0, 3, 0, 0, 0, 0, 0, 0},
                {2, 0, 5, 4, 0, 1, 6, 0, 3}
        };

        long[][] solution =
                {
                        {9, 1, 4, 2, 6, 7, 8, 3, 5},
                        {5, 8, 6, 1, 3, 9, 4, 7, 2},
                        {3, 2, 7, 5, 8, 4, 1, 6, 9},
                        {7, 5, 1, 6, 4, 3, 9, 2, 8},
                        {8, 3, 2, 9, 1, 5, 7, 4, 6},
                        {6, 4, 9, 7, 2, 8, 3, 5, 1},
                        {1, 7, 8, 3, 5, 6, 2, 9, 4},
                        {4, 6, 3, 8, 9, 2, 5, 1, 7},
                        {2, 9, 5, 4, 7, 1, 6, 8, 3}
                };

        AbstractPuzzle sudoku = new RegularSudoku.RegularSudokuBuilder()
                .withGivens(givens)
                .build();
        long[][] solverSolution = sudoku.solve();

        compareSolutions(solverSolution, solution);
    }

    @Test
    public void testSudoku1() {
        int[][] givens = new int[][]{
                {5, 0, 0, 2, 0, 0, 0, 4, 0},
                {0, 0, 0, 6, 0, 3, 0, 0, 0},
                {0, 3, 0, 0, 0, 9, 0, 0, 7},
                {0, 0, 3, 0, 0, 7, 0, 0, 0},
                {0, 0, 7, 0, 0, 8, 0, 0, 0},
                {6, 0, 0, 0, 0, 0, 0, 2, 0},
                {0, 8, 0, 0, 0, 0, 0, 0, 3},
                {0, 0, 0, 4, 0, 0, 6, 0, 0},
                {0, 0, 0, 1, 0, 0, 5, 0, 0}
        };

        long[][] solution =
                {
                        {5, 9, 8, 2, 7, 1, 3, 4, 6},
                        {7, 4, 2, 6, 5, 3, 8, 9, 1},
                        {1, 3, 6, 8, 4, 9, 2, 5, 7},
                        {8, 1, 3, 5, 2, 7, 9, 6, 4},
                        {4, 2, 7, 9, 6, 8, 1, 3, 5},
                        {6, 5, 9, 3, 1, 4, 7, 2, 8},
                        {2, 8, 5, 7, 9, 6, 4, 1, 3},
                        {9, 7, 1, 4, 3, 5, 6, 8, 2},
                        {3, 6, 4, 1, 8, 2, 5, 7, 9}
                };

        AbstractPuzzle sudoku = new RegularSudoku.RegularSudokuBuilder()
                .withGivens(givens)
                .build();
        long[][] solverSolution = sudoku.solve();

        compareSolutions(solverSolution, solution);
    }

    @Test
    public void testSixBySix() {
        int[][] givens = {
                {6, 0, 3, 4, 0, 1},
                {0, 4, 1, 0, 2, 6},
                {0, 0, 0, 0, 0, 0},
                {1, 0, 6, 2, 0, 4},
                {0, 1, 5, 0, 4, 3},
                {0, 0, 0, 0, 0, 0}
        };

        long[][] solution = {
                {6, 2, 3, 4, 5, 1},
                {5, 4, 1, 3, 2, 6},
                {4, 3, 2, 1, 6, 5},
                {1, 5, 6, 2, 3, 4},
                {2, 1, 5, 6, 4, 3},
                {3, 6, 4, 5, 1, 2}
        };

        AbstractPuzzle sudoku = new RegularSudoku.RegularSudokuBuilder()
                .withGivens(givens)
                .withSizeAndBlockDims(6, 2, 3)
                .build();

        compareSolutions(sudoku.solve(), solution);
    }

}
