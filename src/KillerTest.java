import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class KillerTest extends SudokuTest {

    @Test
    public void testKiller1() {
        int[][] givens = {
                {0, 0, 0, 0, 5, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {8, 0, 0, 0, 0, 0, 0, 0, 9},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 6, 0, 0, 0, 0}
                };
        int[][] cages = {
                {1, 1, 2, 2, 0, 3, 3, 4, 4},
                {1, 5, 6, 6, 6, 6, 6, 7, 4},
                {1, 5, 8, 8, 9, 11, 11, 7, 4},
                {1, 12, 12, 8, 9, 11, 15, 15, 4},
                {0, 12, 13, 13, 0, 14, 14, 15, 0},
                {16, 12, 12, 17, 18, 19, 15, 15, 21},
                {16, 23, 17, 17, 18, 19, 19, 25, 21},
                {16, 23, 24, 24, 24, 24, 24, 25, 21},
                {16, 16, 27, 27, 0, 28, 28, 21, 21}
        };
        HashMap<Integer, Integer> cageSums = new HashMap<>();
        cageSums.put(1, 20);
        cageSums.put(2, 14);
        cageSums.put(3, 10);
        cageSums.put(4, 20);
        cageSums.put(5, 11);
        cageSums.put(6, 21);
        cageSums.put(7, 13);
        cageSums.put(8, 20);
        cageSums.put(9, 12);
        cageSums.put(11, 12);
        cageSums.put(12, 27);
        cageSums.put(15, 27);
        cageSums.put(13, 9);
        cageSums.put(14, 10);
        cageSums.put(16, 20);
        cageSums.put(17, 8);
        cageSums.put(18, 16);
        cageSums.put(19, 14);
        cageSums.put(21, 19);
        cageSums.put(23, 11);
        cageSums.put(24, 23);
        cageSums.put(25, 13);
        cageSums.put(27, 13);
        cageSums.put(28, 13);

        AbstractPuzzle regular = new RegularSudoku.RegularSudokuBuilder()
                .withGivens(givens)
                .build();
        AbstractPuzzle killer = new KillerSudoku.KillerSudokuBuilder(regular, cages, cageSums)
                .build();

        int[][] solverSolution = killer.solve();

        int[][] solution = {
                {9, 2, 6, 8, 5, 7, 3, 1, 4},
                {5, 4, 1, 6, 3, 2, 9, 7, 8},
                {3, 7, 8, 9, 4, 1, 2, 6, 5},
                {1, 6, 4, 3, 8, 9, 7, 5, 2},
                {8, 5, 7, 2, 1, 4, 6, 3, 9},
                {2, 9, 3, 5, 7, 6, 4, 8, 1},
                {6, 8, 2, 1, 9, 3, 5, 4, 7},
                {4, 3, 5, 7, 2, 8, 1, 9, 6},
                {7, 1, 9, 4, 6, 5, 8, 2, 3}
        };

        compareSolutions(solverSolution, solution);
    }
}
