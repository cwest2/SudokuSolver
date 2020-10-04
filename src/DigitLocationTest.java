import org.junit.jupiter.api.Test;

public class DigitLocationTest extends SudokuTest {
    boolean T = true;
    boolean F = false;

    @Test
    public void testSurrounding() {
        int[][] givens = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 9, 0, 0, 0},
                {0, 0, 0, 0, 5, 0, 3, 0, 0},
                {0, 0, 0, 4, 0, 3, 7, 0, 0},
                {0, 0, 3, 0, 8, 0, 0, 0, 0},
                {0, 0, 0, 3, 6, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 5, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0}
        };

        boolean[] allActives = {F, T, T, T, T, T, T, T, F};

        AbstractPuzzle puzzle = new RegularSudoku.RegularSudokuBuilder()
                .withGivens(givens)
                .build();
        puzzle = new DigitLocationSudoku.DigitLocationSudokuBuilder(puzzle)
                .withLeftRow(allActives)
                .withTopCol(allActives)
                .withRightRow(allActives)
                .withBottomCol(allActives)
                .build();

        long[][] solution = {
                {7, 4, 2, 8, 1, 6, 9, 3, 5},
                {3, 9, 1, 5, 4, 2, 6, 8, 7},
                {8, 5, 6, 7, 3, 9, 4, 1, 2},
                {2, 1, 4, 6, 5, 7, 3, 9, 8},
                {9, 8, 5, 4, 2, 3, 7, 6, 1},
                {6, 7, 3, 9, 8, 1, 5, 2, 4},
                {1, 2, 7, 3, 6, 5, 8, 4, 9},
                {4, 3, 9, 1, 7, 8, 2, 5, 6},
                {5, 6, 8, 2, 9, 4, 1, 7, 3}
        };

        compareSolutions(puzzle.solve(), solution);

    }

    @Test
    public void testTopLeft() {
        int[][] givens = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 2, 0, 0, 0, 8, 0},
                {0, 0, 9, 0, 0, 0, 2, 0, 0},
                {0, 0, 0, 5, 0, 6, 0, 0, 0},
                {0, 0, 6, 0, 9, 0, 0, 0, 4},
                {0, 9, 0, 0, 0, 4, 0, 6, 0},
                {0, 0, 0, 0, 4, 0, 7, 0, 0},
                {0, 0, 0, 6, 0, 0, 0, 4, 0},
                {0, 0, 7, 0, 0, 0, 8, 0, 0}
        };

        boolean[] leftRowActive = {T, T, T, T, T, T, T, T, T};
        boolean[] topColActive = {T, T, T, T, T, T, T, T, T};

        AbstractPuzzle puzzle = new RegularSudoku.RegularSudokuBuilder()
                .withGivens(givens)
                .build();
        puzzle = new DigitLocationSudoku.DigitLocationSudokuBuilder(puzzle)
                .withLeftRow(leftRowActive)
                .withTopCol(topColActive)
                .build();

        long[][] solution = {
                {8, 5, 2, 9, 6, 3, 4, 1, 7},
                {3, 4, 1, 2, 5, 7, 6, 8, 9},
                {6, 7, 9, 4, 8, 1, 2, 3, 5},
                {7, 3, 4, 5, 2, 6, 1, 9, 8},
                {2, 1, 6, 3, 9, 8, 5, 7, 4},
                {5, 9, 8, 7, 1, 4, 3, 6, 2},
                {9, 6, 3, 8, 4, 5, 7, 2, 1},
                {1, 8, 5, 6, 7, 2, 9, 4, 3},
                {4, 2, 7, 1, 3, 9, 8, 5, 6}
        };

        compareSolutions(puzzle.solve(), solution);
    }
}
