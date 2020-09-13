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

        puzzle.printSolution();

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

        puzzle.printSolution();
    }
}
