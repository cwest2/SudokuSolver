import org.junit.jupiter.api.Test;

public class RossiniTest extends SudokuTest {
    String L = "L";
    String R = "R";
    String U = "U";
    String D = "D";
    String N = "N";

    @Test
    public void testRossini() {
        int[][] givens = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 5, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 3, 0, 0, 9, 0, 0},
                {0, 0, 0, 0, 0, 4, 0, 0, 0},
                {0, 0, 0, 6, 3, 7, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 7, 0, 0, 8, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 8, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0}
        };

        String[] leftRowDirs = {L, R, L, N, N, N, R, N, N};
        String[] topColDirs = {N, N, N, N, N, D, D, N, N};
        String[] rightRowDirs = {R, L, N, N, N, N, R, N, L};
        String[] bottomColDirs = {D, D, N, N, D, N, N, N, N};

        AbstractPuzzle puzzle = new RegularSudoku.RegularSudokuBuilder()
                .withGivens(givens)
                .build();
        puzzle = new RossiniSudoku.RossiniSudokuBuilder(puzzle)
                .withLeftRowDirs(leftRowDirs)
                .withTopColDirs(topColDirs)
                .withRightRowDirs(rightRowDirs)
                .withBottomColDirs(bottomColDirs)
                .withNegative(true)
                .build();

        int[][] solution = {
                {9, 6, 3, 7, 5, 1, 2, 4, 8},
                {1, 4, 5, 8, 9, 2, 7, 6, 3},
                {8, 7, 2, 3, 4, 6, 9, 1, 5},
                {3, 1, 9, 5, 2, 4, 6, 8, 7},
                {5, 8, 4, 6, 3, 7, 1, 9, 2},
                {7, 2, 6, 1, 8, 9, 5, 3, 4},
                {2, 3, 7, 9, 1, 8, 4, 5, 6},
                {4, 5, 1, 2, 6, 3, 8, 7, 9},
                {6, 9, 8, 4, 7, 5, 3, 2, 1}
        };

        compareSolutions(puzzle.solve(), solution);
    }
}
