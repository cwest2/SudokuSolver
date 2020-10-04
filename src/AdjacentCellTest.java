import com.google.ortools.sat.IntVar;
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

        long[][] solutions = {
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

        long[][] solution = {
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

    @Test
    public void testXVKropki() {
        int[][] givens = {
                {0, 0, 0, 0, 0, 5, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {5, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
        };

        AbstractPuzzle puzzle = new RegularSudoku.RegularSudokuBuilder()
                .withGivens(givens)
                .build();

        String[][] rowGaps = {
                {W, N, X, N, N, N, B, N},
                {W, N, V, N, N, X, N, W},
                {V, N, N, N, N, N, N, N},
                {X, N, N, N, N, N, N, N},
                {X, N, N, N, N, N, N, W},
                {N, N, X, N, N, X, N, N},
                {N, N, N, N, N, N, N, N},
                {N, N, X, N, N, W, N, W},
                {W, N, N, N, N, N, N, W}
        };

        String[][] colGaps = {
                {N, N, N, N, N, N, W, W},
                {N, N, N, N, N, X, N, W},
                {N, N, N, N, N, N, N, N},
                {N, N, N, N, N, N, N, N},
                {B, N, N, N, N, N, N, W},
                {N, N, N, N, N, N, N, N},
                {N, N, N, N, N, N, N, N},
                {N, N, W, N, N, X, N, N},
                {W, N, W, N, N, W, N, N}
        };

        puzzle = new AdjacentCellSudoku.AdjacentCellSudokuBuilder(puzzle)
                .withKropkiConstraints()
                .withXVConstraints()
                .withRowGaps(rowGaps)
                .withColGaps(colGaps)
                .build();

        long[][] solution = {
                {8, 9, 3, 7, 4, 5, 2, 1, 6},
                {6, 7, 2, 3, 8, 1, 9, 4, 5},
                {1, 4, 5, 6, 9, 2, 7, 8, 3},
                {7, 3, 4, 8, 1, 6, 5, 9, 2},
                {9, 1, 8, 2, 5, 3, 4, 6, 7},
                {5, 2, 6, 4, 7, 9, 1, 3, 8},
                {2, 8, 1, 5, 6, 4, 3, 7, 9},
                {3, 6, 9, 1, 2, 7, 8, 5, 4},
                {4, 5, 7, 9, 3, 8, 6, 2, 1}
        };

        compareSolutions(puzzle.solve(), solution);
    }

    @Test
    public void testDifference() {
        int[][] givens = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 9, 2, 1, 0, 7, 3, 8, 0},
                {0, 0, 0, 3, 0, 9, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0}
        };

        AbstractPuzzle puzzle = new RegularSudoku.RegularSudokuBuilder()
                .withGivens(givens)
                .build();

        String E = "";

        String[][] rowGaps = {
                {E, E, E, E, E, E, E, E},
                {E, E, "1", E, E, "1", E, E},
                {E, E, "7", E, E, "7", E, E},
                {E, E, "5", E, E, "5", E, E},
                {E, E, E, E, E, E, E, E},
                {E, "1", E, E, E, E, "2", E},
                {E, E, E, E, E, E, E, E},
                {E, E, E, E, E, E, E, E},
                {E, E, E, E, E, E, E, E}
        };

        String[][] colGaps = {
                {E, "4", E, E, E, E, "1", E},
                {E, E, "6", E, E, E, E, "1"},
                {E, E, E, E, E, E, E, E},
                {E, E, E, E, E, E, E, E},
                {"8", E, E, "3", E, E, E, E},
                {E, E, E, E, E, E, E, E},
                {E, E, E, E, E, E, E, E},
                {E, E, "6", E, E, E, E, "2"},
                {E, "4", E, E, E, E, "2", E}
        };

        puzzle = new AdjacentCellSudoku.AdjacentCellSudokuBuilder(puzzle)
                .withDifferenceConstraints()
                .withRowGaps(rowGaps)
                .withColGaps(colGaps)
                .build();

        long[][] solution = {
                {1, 2, 5, 4, 9, 3, 8, 6, 7},
                {8, 3, 6, 7, 1, 5, 4, 2, 9},
                {4, 7, 9, 2, 6, 8, 1, 3, 5},
                {5, 1, 3, 8, 4, 2, 7, 9, 6},
                {2, 8, 4, 9, 7, 6, 5, 1, 3},
                {9, 6, 7, 5, 3, 1, 2, 4, 8},
                {6, 9, 2, 1, 5, 7, 3, 8, 4},
                {7, 4, 1, 3, 8, 9, 6, 5, 2},
                {3, 5, 8, 6, 2, 4, 9, 7, 1}
        };

        compareSolutions(puzzle.solve(), solution);
    }

    @Test
    public void testDifferentDifferences() {
        int[][] givens = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {8, 0, 0, 2, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 3, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 4, 0, 0, 5},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0}
        };

        AbstractPuzzle puzzle = new RegularSudoku.RegularSudokuBuilder()
                .withGivens(givens)
                .build();

        AdjacentCellSudoku.AdjacentCellSudokuBuilder builder = new AdjacentCellSudoku.AdjacentCellSudokuBuilder(puzzle);

        long[] diffOptions = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        IntVar[] diffs = puzzle.assignVarsFromValues(diffOptions);
        for (int i = 0; i < 9; i++) {
            IntVar diff = diffs[i];
            builder.withDifferenceConstraint(diff, "D" + i);
            builder.withNegativeConstraintOf("D" + i, "N" + i);
        }

        String E = null;
        String D0 = "D0";
        String D1 = "D1";
        String D2 = "D2";
        String D3 = "D3";
        String D4 = "D4";
        String D5 = "D5";
        String D6 = "D6";
        String D7 = "D7";
        String D8 = "D8";
        String N0 = "N0";
        String N1 = "N1";
        String N2 = "N2";
        String N3 = "N3";
        String N4 = "N4";
        String N5 = "N5";
        String N6 = "N6";
        String N7 = "N7";
        String N8 = "N8";

        String[][] rowGaps = {
                {D0, D0, E, N1, N1, E, N2, N2},
                {N0, N0, E, D1, D1, E, D2, N2},
                {N0, N0, E, N1, D1, E, N2, D2},
                {N3, N3, E, N4, N4, E, N5, N5},
                {N3, N3, E, N4, N4, E, N5, N5},
                {N3, N3, E, D4, N4, E, N5, N5},
                {D6, N6, E, N7, N7, E, N8, N8},
                {D6, N6, E, N7, N7, E, N8, N8},
                {N6, N6, E, D7, N7, E, N8, N8}
        };
        String[][] colGaps = {
                {D0, D0, E, N3, N3, E, N6, N6},
                {N0, N0, E, D3, D3, E, N6, N6},
                {N0, N0, E, N3, N3, E, N6, D6},
                {N1, N1, E, N4, N4, E, N7, N7},
                {N1, N1, E, N4, N4, E, N7, N7},
                {N1, N1, E, D4, N4, E, N7, D7},
                {N2, N2, E, N5, N5, E, N8, N8},
                {N2, N2, E, N5, N5, E, D8, N8},
                {N2, N2, E, N5, N5, E, N8, N8}
        };

        builder.withRowGaps(rowGaps);
        builder.withColGaps(colGaps);

        puzzle = builder.build();

        long[][] solution = {
                {5, 6, 7, 8, 2, 1, 4, 3, 9},
                {4, 2, 9, 3, 5, 7, 1, 6, 8},
                {3, 8, 1, 9, 4, 6, 5, 2, 7},
                {8, 9, 4, 2, 1, 5, 3, 7, 6},
                {2, 5, 6, 7, 3, 8, 9, 4, 1},
                {7, 1, 3, 6, 9, 4, 2, 8, 5},
                {1, 7, 5, 4, 6, 3, 8, 9, 2},
                {9, 3, 8, 5, 7, 2, 6, 1, 4},
                {6, 4, 2, 1, 8, 9, 7, 5, 3}
        };

        compareSolutions(puzzle.solve(), solution);
    }
}
