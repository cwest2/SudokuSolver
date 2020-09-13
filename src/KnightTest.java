import org.junit.jupiter.api.Test;

public class KnightTest extends SudokuTest{

    @Test
    public void testKnight1() {
        int[][] givens = {
                {0, 0, 0, 0, 1, 0, 0, 0, 8},
                {0, 0, 0, 0, 2, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 3, 0, 0, 0},
                {9, 0, 0, 0, 0, 0, 4, 0, 0},
                {0, 8, 0, 0, 0, 0, 0, 5, 0},
                {0, 0, 7, 0, 0, 0, 0, 0, 6},
                {0, 0, 0, 6, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 5, 0, 0, 0, 0},
                {3, 0, 0, 0, 4, 0, 0, 0, 0}
        };

        AbstractPuzzle regular = new RegularSudoku.RegularSudokuBuilder()
                .withGivens(givens)
                .build();
        AbstractPuzzle knight = new KnightSudoku.KnightSudokuBuilder(regular)
                .build();

        int[][] solution = {
                {2, 3, 9, 7, 1, 4, 5, 6, 8},
                {8, 6, 4, 9, 2, 5, 3, 7, 1},
                {7, 1, 5, 8, 6, 3, 9, 4, 2},
                {9, 2, 1, 5, 7, 6, 4, 8, 3},
                {6, 8, 3, 4, 9, 1, 2, 5, 7},
                {4, 5, 7, 3, 8, 2, 1, 9, 6},
                {5, 4, 2, 6, 3, 8, 7, 1, 9},
                {1, 9, 6, 2, 5, 7, 8, 3, 4},
                {3, 7, 8, 1, 4, 9, 6, 2, 5}
        };

        compareSolutions(knight.solve(), solution);
    }
}
