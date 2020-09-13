import org.junit.jupiter.api.Test;

public class ExtraRegionTest extends SudokuTest{

    @Test
    public void testWindoku() {
        int[][] givens = {
                {5, 0, 0, 0, 0, 0, 2, 0, 0},
                {0, 0, 0, 4, 0, 0, 0, 3, 0},
                {0, 0, 0, 0, 5, 6, 0, 0, 1},
                {0, 2, 0, 0, 0, 0, 7, 0, 0},
                {0, 0, 3, 0, 0, 0, 8, 0, 0},
                {0, 0, 4, 0, 0, 0, 0, 9, 0},
                {1, 0, 0, 5, 6, 0, 0, 0, 0},
                {0, 8, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 9, 0, 0, 0, 0, 0, 6}
        };

        int[][] windows = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 1, 0, 2, 2, 2, 0},
                {0, 1, 1, 1, 0, 2, 2, 2, 0},
                {0, 1, 1, 1, 0, 2, 2, 2, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 3, 3, 3, 0, 4, 4, 4, 0},
                {0, 3, 3, 3, 0, 4, 4, 4, 0},
                {0, 3, 3, 3, 0, 4, 4, 4, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0}
        };

        AbstractPuzzle puzzle = new RegularSudoku.RegularSudokuBuilder()
                .withGivens(givens)
                .build();
        puzzle = new ExtraRegionSudoku.ExtraRegionSudokuBuilder(puzzle)
                .withRegionsGrid(windows)
                .build();

        int[][] solution = {
                {5, 4, 7, 1, 9, 3, 2, 6, 8},
                {6, 9, 1, 4, 8, 2, 5, 3, 7},
                {2, 3, 8, 7, 5, 6, 9, 4, 1},
                {9, 2, 5, 6, 4, 8, 7, 1, 3},
                {7, 6, 3, 2, 1, 9, 8, 5, 4},
                {8, 1, 4, 3, 7, 5, 6, 9, 2},
                {1, 7, 2, 5, 6, 4, 3, 8, 9},
                {4, 8, 6, 9, 3, 7, 1, 2, 5},
                {3, 5, 9, 8, 2, 1, 4, 7, 6}
        };

        compareSolutions(puzzle.solve(), solution);
    }

    @Test
    public void testOffset() {
        int[][] givens = {
                {0, 0, 7, 0, 0, 0, 8, 0, 0},
                {0, 2, 0, 0, 0, 0, 0, 4, 0},
                {8, 0, 4, 0, 2, 0, 5, 0, 1},
                {0, 0, 0, 0, 7, 0, 0, 0, 0},
                {0, 0, 8, 3, 6, 4, 2, 0, 0},
                {0, 0, 0, 0, 9, 0, 0, 0, 0},
                {3, 0, 2, 0, 8, 0, 7, 0, 4},
                {0, 7, 0, 0, 0, 0, 0, 8, 0},
                {0, 0, 6, 0, 0, 0, 9, 0, 0}
        };

        int[][] regions = {
                {1, 2, 3, 1, 2, 3, 1, 2, 3},
                {4, 5, 6, 4, 5, 6, 4, 5, 6},
                {7, 8, 9, 7, 8, 9, 7, 8, 9},
                {1, 2, 3, 1, 2, 3, 1, 2, 3},
                {4, 5, 6, 4, 5, 6, 4, 5, 6},
                {7, 8, 9, 7, 8, 9, 7, 8, 9},
                {1, 2, 3, 1, 2, 3, 1, 2, 3},
                {4, 5, 6, 4, 5, 6, 4, 5, 6},
                {7, 8, 9, 7, 8, 9, 7, 8, 9},
        };

        AbstractPuzzle puzzle = new RegularSudoku.RegularSudokuBuilder()
                .withGivens(givens)
                .build();
        puzzle = new ExtraRegionSudoku.ExtraRegionSudokuBuilder(puzzle)
                .withRegionsGrid(regions)
                .build();

        int[][] solution = {
                {1, 5, 7, 6, 4, 3, 8, 2, 9},
                {9, 2, 3, 8, 5, 1, 6, 4, 7},
                {8, 6, 4, 7, 2, 9, 5, 3, 1},
                {2, 3, 1, 5, 7, 8, 4, 9, 6},
                {7, 9, 8, 3, 6, 4, 2, 1, 5},
                {6, 4, 5, 1, 9, 2, 3, 7, 8},
                {3, 1, 2, 9, 8, 5, 7, 6, 4},
                {5, 7, 9, 4, 3, 6, 1, 8, 2},
                {4, 8, 6, 2, 1, 7, 9, 5, 3}
        };

        compareSolutions(puzzle.solve(), solution);
    }
}
