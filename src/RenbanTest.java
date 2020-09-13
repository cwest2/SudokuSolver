import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class RenbanTest extends SudokuTest {

    @Test
    public void testRenban1() {
        int[][] givens = {
                {4, 0, 8, 0, 0, 3, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 9, 0, 0, 0, 0, 0},
                {0, 2, 0, 0, 0, 0, 1, 4, 0},
                {9, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 9},
                {0, 7, 1, 3, 0, 2, 4, 5, 0}
        };

        List<int[][]> regions = new ArrayList<>();

        regions.add(new int[][] {
                {1, 0},
                {2, 0},
                {3, 0},
                {2, 1},
                {2, 2},
                {1, 2},
                {3, 2}
        });

        regions.add(new int[][] {
                {2, 3},
                {1, 3},
                {0, 3},
                {1, 4},
                {2, 5},
                {1, 5},
                {0, 5}
        });

        regions.add(new int[][] {
                {1, 6},
                {2, 6},
                {2, 7},
                {2, 8},
                {1, 8},
                {3, 8},
                {3, 7},
                {3, 6}
        });

        regions.add(new int[][] {
                {5, 1},
                {4, 1},
                {4, 2},
                {5, 2},
                {6, 2},
                {6, 1},
                {7, 1},
                {7, 2},
                {7, 3}
        });

        regions.add(new int[][] {
                {4, 3},
                {5, 3},
                {6, 3},
                {6, 4},
                {5, 4},
                {4, 4}
        });

        regions.add(new int[][] {
                {5, 5},
                {4, 6},
                {5, 6},
                {6, 6},
                {7, 6},
                {7, 5},
                {7, 7}
        });

        regions.add(new int[][] {
                {4, 7},
                {5, 7},
                {6, 7},
                {6, 8},
                {5, 8},
                {4, 8}
        });

        AbstractPuzzle puzzle = new RegularSudoku.RegularSudokuBuilder()
                .withGivens(givens)
                .build();
        puzzle = new RenbanSudoku.RenbanSudokuBuilder(puzzle, regions)
                .build();

        puzzle.printSolution();
    }
}
