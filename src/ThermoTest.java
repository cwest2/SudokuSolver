import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ThermoTest extends SudokuTest {

    @Test
    public void testSpookyThermo() {
        AbstractPuzzle puzzle = new RegularSudoku.RegularSudokuBuilder()
                .build();
        List<int[][]> thermos = new ArrayList<>();
        thermos.add(new int[][]{
                {1, 0},
                {0, 1}
        });

        thermos.add(new int[][]{
                {1, 1},
                {0, 2}
        });

        thermos.add(new int[][] {
                {2, 1},
                {1, 2}
        });

        thermos.add(new int[][] {
                {1, 3},
                {2, 2}
        });

        thermos.add(new int[][] {
                {1, 3},
                {0, 4}
        });

        thermos.add(new int[][] {
                {0, 5},
                {1, 4},
                {2, 3}
        });

        thermos.add(new int[][] {
                {2, 4},
                {1, 5},
                {0, 6}
        });

        thermos.add(new int[][] {
                {2, 4},
                {3, 3},
                {4, 2},
                {1, 5}
        });

        thermos.add(new int[][] {
                {1, 6},
                {2, 5},
                {3, 4},
                {4, 3}
        });

        thermos.add(new int[][] {
                {2, 6},
                {1, 7}
        });

        thermos.add(new int[][] {
                {2, 7},
                {1, 8}
        });

        thermos.add(new int[][] {
                {3, 7},
                {2, 8}
        });

        thermos.add(new int[][] {
                {4, 7},
                {3, 8}
        });

        thermos.add(new int[][] {
                {5, 0},
                {4, 1},
                {3, 2}
        });

        thermos.add(new int[][] {
                {4, 6},
                {5, 5},
                {6, 4}
        });

        thermos.add(new int[][] {
                {6, 1},
                {7, 0}
        });

        thermos.add(new int[][] {
                {5, 3},
                {4, 4},
                {3, 5}
        });

        thermos.add(new int[][] {
                {5, 3},
                {6, 2},
                {7, 1},
                {8, 0}
        });

        thermos.add(new int[][] {
                {7, 2},
                {6, 3},
                {5, 4},
                {4, 5},
                {3, 6}
        });

        thermos.add(new int[][] {
                {6, 6},
                {7, 5}
        });

        thermos.add(new int[][] {
                {6, 7},
                {5, 8}
        });

        thermos.add(new int[][] {
                {6, 7},
                {7, 6},
                {8, 5}
        });

        thermos.add(new int[][] {
                {7, 7},
                {8, 6}
        });

        puzzle = new ThermoSudoku.ThermoSudokuBuilder(puzzle, thermos)
                .build();

        puzzle.printSolution();
    }

    @Test
    public void testThermo1() {
        int[][] givens = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                givens[i][j] = 0;
            }
        }
        List<int[][]> thermos = new ArrayList<>();
        thermos.add(new int[][]{
                {3, 3},
                {3, 2},
                {3, 1},
                {3, 0},
                {2, 0},
                {1, 0},
                {0, 0}
        });
        thermos.add(new int[][]{
                {3, 3},
                {2, 3},
                {1, 3},
                {0, 3},
                {0, 2},
                {0, 1}
        });
        thermos.add(new int[][]{
                {0, 5},
                {1, 5}
        });
        thermos.add(new int[][]{
                {0, 6},
                {0, 7},
                {1, 7},
                {2, 7},
                {2, 6},
                {2, 5}
        });
        thermos.add(new int[][]{
                {7, 0},
                {6, 0},
                {5, 0},
                {5, 1}
        });
        thermos.add(new int[][]{
                {7, 1},
                {7, 2}
        });
        thermos.add(new int[][]{
                {6, 2},
                {5, 2}
        });
        thermos.add(new int[][]{
                {4, 4},
                {4, 5},
                {4, 6},
                {4, 7},
                {4, 8}
        });
        thermos.add(new int[][]{
                {4, 4},
                {5, 4},
                {6, 4},
                {7, 4}
        });
        thermos.add(new int[][]{
                {7, 8},
                {6, 8},
                {5, 8}
        });
        thermos.add(new int[][]{
                {7, 8},
                {8, 8},
                {8, 7},
                {8, 6},
                {8, 5},
                {8, 4}
        });
        AbstractPuzzle regular = new RegularSudoku.RegularSudokuBuilder()
                .withGivens(givens)
                .build();
        AbstractPuzzle thermo = new ThermoSudoku.ThermoSudokuBuilder(regular, thermos)
                .build();

        long[][] solution = {
                {9, 8, 7, 6, 3, 4, 1, 2, 5},
                {6, 2, 1, 5, 7, 8, 3, 4, 9},
                {5, 4, 3, 2, 1, 9, 8, 7, 6},
                {4, 3, 2, 1, 8, 6, 5, 9, 7},
                {1, 7, 5, 9, 2, 3, 4, 6, 8},
                {8, 9, 6, 7, 4, 5, 2, 1, 3},
                {7, 6, 4, 8, 5, 1, 9, 3, 2},
                {3, 5, 9, 4, 6, 2, 7, 8, 1},
                {2, 1, 8, 3, 9, 7, 6, 5, 4}
        };

        compareSolutions(thermo.solve(), solution);
    }
}
