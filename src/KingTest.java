import org.junit.jupiter.api.Test;

public class KingTest extends SudokuTest{

    @Test
    public void testKing1() {
        int[][] givens = {
                {0, 0, 0, 4, 3, 1, 0, 0, 0},
                {0, 0, 8, 0, 0, 0, 4, 0, 0},
                {0, 3, 0, 0, 0, 0, 0, 1, 0},
                {2, 0, 0, 0, 0, 0, 0, 0, 5},
                {3, 0, 0, 0, 6, 0, 0, 0, 9},
                {9, 0, 0, 0, 0, 0, 0, 0, 2},
                {0, 7, 0, 0, 0, 0, 0, 6, 0},
                {0, 0, 9, 0, 0, 0, 5, 0, 0},
                {0, 0, 0, 8, 5, 3, 0, 0, 0}
        };

        AbstractPuzzle regular = new RegularSudoku.RegularSudokuBuilder()
                .withGivens(givens)
                .build();
        AbstractPuzzle king = new KingSudoku.KingSudokuBuilder(regular)
                .build();

        int[][] solution = {
                {7, 6, 2, 4, 3, 1, 9, 5, 8},
                {1, 9, 8, 6, 7, 5, 4, 2, 3},
                {4, 3, 5, 9, 2, 8, 7, 1, 6},
                {2, 8, 7, 3, 1, 9, 6, 4, 5},
                {3, 5, 4, 2, 6, 7, 1, 8, 9},
                {9, 1, 6, 5, 8, 4, 3, 7, 2},
                {5, 7, 3, 1, 9, 2, 8, 6, 4},
                {8, 2, 9, 7, 4, 6, 5, 3, 1},
                {6, 4, 1, 8, 5, 3, 2, 9, 7}
        };

        compareSolutions(king.solve(), solution);
    }
}
