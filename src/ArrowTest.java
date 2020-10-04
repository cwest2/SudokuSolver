import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ArrowTest extends SudokuTest{

    @Test
    public void testArrowOneCellBubbles1() {
        int[][] givens = {
                {0, 0, 0, 0, 1, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 0, 3, 0},
                {0, 0, 0, 0, 0, 2, 0, 0, 5},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 2, 1, 0, 0, 0, 8, 0, 0},
                {0, 0, 0, 1, 3, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 5, 0, 0},
                {0, 0, 0, 0, 0, 5, 0, 0, 0},
                {3, 4, 0, 0, 0, 0, 0, 8, 9}
        };

        List<int[][]> arrowSums = new ArrayList<>();
        List<int[][]> arrowPaths = new ArrayList<>();

        arrowSums.add(new int[][] {{0, 3}});
        arrowPaths.add(new int[][]{
                {0, 4},
                {0, 5}
        });

        arrowSums.add(new int[][] {{1, 5}});
        arrowPaths.add(new int[][]{
                {1, 6},
                {1, 7}
        });

        arrowSums.add(new int[][] {{2, 0}});
        arrowPaths.add(new int[][]{
                {2, 1},
                {2, 2}
        });

        arrowSums.add(new int[][] {{2, 3}});
        arrowPaths.add(new int[][] {
                {2, 4},
                {2, 5}
        });

        arrowSums.add(new int[][] {{3, 6}});
        arrowPaths.add(new int[][] {
                {3, 5},
                {3, 4}
        });

        arrowSums.add(new int[][] {{5, 0}});
        arrowPaths.add(new int[][] {
                {4, 0},
                {3, 0}
        });

        arrowSums.add(new int[][] {{5, 1}});
        arrowPaths.add(new int[][] {
                {5, 2},
                {5, 3}
        });

        arrowSums.add(new int[][] {{6, 2}});
        arrowPaths.add(new int[][] {
                {7, 2},
                {8, 2}
        });

        arrowSums.add(new int[][] {{7, 8}});
        arrowPaths.add(new int[][] {
                {7, 7},
                {7, 6}
        });

        arrowSums.add(new int[][] {{8, 4}});
        arrowPaths.add(new int[][] {
                {8, 3},
                {8, 2}
        });

        arrowSums.add(new int[][] {{8, 5}});
        arrowPaths.add(new int[][] {
                {7, 5},
                {6, 5}
        });

        long[][] solution = {
                {2, 5, 9, 4, 1, 3, 7, 6, 8},
                {1, 6, 8, 5, 9, 7, 4, 3, 2},
                {7, 3, 4, 8, 6, 2, 9, 1, 5},
                {5, 8, 3, 7, 2, 4, 6, 9, 1},
                {4, 2, 1, 6, 5, 9, 8, 7, 3},
                {9, 7, 6, 1, 3, 8, 2, 5, 4},
                {8, 9, 7, 3, 4, 1, 5, 2, 6},
                {6, 1, 2, 9, 8, 5, 3, 4, 7},
                {3, 4, 5, 2, 7, 6, 1, 8, 9}
        };

        AbstractPuzzle puzzle = new RegularSudoku.RegularSudokuBuilder()
                .withGivens(givens)
                .build();
        puzzle = new ArrowSudoku.ArrowSudokuBuilder(puzzle, arrowSums, arrowPaths)
                .build();

        compareSolutions(puzzle.solve(), solution);
    }

    @Test
    public void testArrowTwoCellBubbles() {
        List<int[][]> arrowSums = new ArrayList<>();
        List<int[][]> arrowPaths = new ArrayList<>();

        arrowSums.add(new int[][] {{1, 6}});
        arrowPaths.add(new int[][] {
                {1, 5},
                {1, 4},
                {1, 3}
        });

        arrowSums.add(new int[][] {{1, 6}});
        arrowPaths.add(new int[][] {
                {0, 6},
                {0, 5}
        });

        arrowSums.add(new int[][] {
                {2, 0},
                {2, 1}
        });
        arrowPaths.add(new int[][] {
                {3, 0},
                {3, 1},
                {3, 2},
                {4, 3}
        });

        arrowSums.add(new int[][] {
                {2, 3},
                {2, 4}
        });
        arrowPaths.add(new int[][] {
                {3, 4},
                {3, 3},
                {4, 2},
                {5, 2}
        });

        arrowSums.add(new int[][] {{2, 5}});
        arrowPaths.add(new int[][] {
                {3, 5},
                {4, 5},
                {5, 5}
        });

        arrowSums.add(new int[][] {
                {2, 6},
                {2, 7}
        });
        arrowPaths.add(new int[][] {
                {2, 8},
                {1, 8}
        });

        arrowSums.add(new int[][] {{5, 6}});
        arrowPaths.add(new int[][] {
                {6, 5},
                {7, 5}
        });

        arrowSums.add(new int[][] {
                {5, 7},
                {5, 8}
        });
        arrowPaths.add(new int[][] {
                {6, 7},
                {7, 8},
                {8, 8},
                {8, 7},
                {8, 6},
                {8, 5},
                {8, 4},
                {8, 3},
                {8, 2},
                {8, 1},
                {8, 0},
                {7, 0}
        });

        arrowSums.add(new int[][] {{6, 0}});
        arrowPaths.add(new int[][] {
                {5, 1},
                {4, 1}
        });

        arrowSums.add(new int[][] {{7, 1}});
        arrowPaths.add(new int[][] {
                {6, 2},
                {6, 3},
                {6, 4}
        });

        arrowSums.add(new int[][] {{7, 4}});
        arrowPaths.add(new int[][] {
                {7, 3},
                {7, 2}
        });

        long[][] solution = {
                {8, 1, 4, 9, 7, 6, 2, 5, 3},
                {3, 9, 6, 2, 1, 5, 8, 4, 7},
                {2, 5, 7, 3, 4, 8, 1, 6, 9},
                {6, 7, 5, 8, 9, 1, 3, 2, 4},
                {1, 3, 9, 7, 2, 4, 6, 8, 5},
                {4, 2, 8, 5, 6, 3, 9, 7, 1},
                {5, 8, 2, 1, 3, 7, 4, 9, 6},
                {9, 6, 1, 4, 5, 2, 7, 3, 8},
                {7, 4, 3, 6, 8, 9, 5, 1, 2}
        };

        AbstractPuzzle puzzle = new RegularSudoku.RegularSudokuBuilder()
                .build();
        puzzle = new ArrowSudoku.ArrowSudokuBuilder(puzzle, arrowSums, arrowPaths)
                .build();

        compareSolutions(puzzle.solve(), solution);
    }
}
