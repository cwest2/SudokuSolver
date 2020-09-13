import org.junit.jupiter.api.Test;

public class AdjacentCellTest extends SudokuTest {
    String W = "W";
    String B = "B";
    String X = "X";
    String V = "V";
    String N = "N";

    @Test
    public void testKropki() {
        int[][] givens = {
                {7, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 9}
        };

        String[][] rowGaps = {
                {N, N, W, N, N, N, N, N},
                {N, B, N, N, N, B, N, N},
                {N, N, W, N, N, N, N, W},
                {B, N, N, B, W, W, B, W},
                {N, N, B, N, N, N, N, N},
                {W, N, W, N, B, N, N, N},
                {N, B, W, N, N, N, N, W},
                {N, N, W, N, B, N, N, N},
                {B, N, N, N, N, W, W, N}
        };

        String[][] colGaps = {
                {N, N, N, W, N, N, N, N},
                {W, B, N, N, W, B, N, N},
                {W, W, N, N, B, N, W, N},
                {W, N, W, N, W, N, W, N},
                {W, W, W, N, W, N, W, W},
                {N, B, N, N, W, N, N, N},
                {B, N, W, N, W, N, W, N},
                {N, N, W, N, N, N, N, N},
                {N, N, W, W, W, N, W, N}
        };

        AbstractPuzzle puzzle = new RegularSudoku.RegularSudokuBuilder()
                .withGivens(givens)
                .build();
        puzzle = new AdjacentCellSudoku.AdjacentCellSudokuBuilder(puzzle)
                .withKropkiConstraints()
                .withNegativeConstraints(true)
                .withRowGaps(rowGaps)
                .withColGaps(colGaps)
                .build();

        int[][] solutions = {
                {7, 5, 3, 4, 9, 1, 6, 2, 8},
                {9, 4, 2, 5, 8, 6, 3, 7, 1},
                {6, 8, 1, 2, 7, 3, 9, 5, 4},
                {2, 1, 9, 3, 6, 7, 8, 4, 5},
                {3, 7, 4, 8, 1, 5, 2, 9, 6},
                {5, 6, 8, 9, 2, 4, 1, 3, 7},
                {8, 3, 6, 7, 5, 9, 4, 1, 2},
                {1, 9, 7, 6, 4, 2, 5, 8, 3},
                {4, 2, 5, 1, 3, 8, 7, 6, 9}
        };

        compareSolutions(puzzle.solve(), solutions);
    }

    @Test
    public void testXV() {
        int[][] givens = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {7, 0, 0, 0, 0, 0, 0, 0, 3},
                {0, 3, 0, 0, 0, 0, 0, 2, 0},
                {0, 0, 2, 0, 0, 0, 6, 0, 0},
                {0, 0, 0, 6, 0, 4, 0, 0, 0},
                {0, 0, 0, 0, 9, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {6, 0, 0, 0, 0, 0, 0, 0, 2},
                {0, 7, 0, 0, 0, 0, 0, 1, 0}
        };

        String[][] rowGaps = {
                {N, N, N, N, N, N, N, N},
                {N, N, X, N, N, X, N, N},
                {N, N, N, N, N, N, N, N},
                {N, N, N, N, N, N, N, N},
                {N, N, N, N, N, N, N, N},
                {N, N, N, N, N, N, N, N},
                {N, N, N, N, N, N, N, N},
                {N, N, N, N, N, N, N, N},
                {N, N, N, N, N, N, N, N}
        };

        String[][] colGaps = {
                {N, N, N, N, N, N, N, N},
                {N, N, N, N, N, N, N, N},
                {N, N, N, N, N, V, N, N},
                {N, N, X, N, N, N, N, N},
                {N, N, N, X, N, N, N, V},
                {N, N, X, N, N, N, N, N},
                {N, N, N, N, N, V, N, N},
                {N, N, N, N, N, N, N, N},
                {N, N, N, N, N, N, N, N}
        };

        int[][] solution = {
                {4, 2, 9, 8, 3, 1, 7, 6, 5},
                {7, 1, 6, 4, 5, 2, 8, 9, 3},
                {8, 3, 5, 9, 6, 7, 1, 2, 4},
                {9, 4, 2, 1, 8, 3, 6, 5, 7},
                {5, 8, 7, 6, 2, 4, 9, 3, 1},
                {3, 6, 1, 7, 9, 5, 2, 4, 8},
                {1, 5, 4, 2, 7, 6, 3, 8, 9},
                {6, 9, 3, 5, 1, 8, 4, 7, 2},
                {2, 7, 8, 3, 4, 9, 5, 1, 6}
        };

        AbstractPuzzle puzzle = new RegularSudoku.RegularSudokuBuilder()
                .withGivens(givens)
                .build();
        puzzle = new AdjacentCellSudoku.AdjacentCellSudokuBuilder(puzzle)
                .withXVConstraints()
                .withNegativeConstraints(true)
                .withRowGaps(rowGaps)
                .withColGaps(colGaps)
                .build();

        compareSolutions(puzzle.solve(), solution);
    }
}
