import org.junit.jupiter.api.Test;
public class SkyscraperTest extends SudokuTest {

    @Test
    public void testSkyscraper1() {
        int[][] givens = {
                {1, 0, 0, 0, 0, 2, 0, 0, 8},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {3, 0, 0, 6, 0, 0, 4, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {5, 0, 2, 0, 0, 3, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {7, 0, 0, 8, 0, 0, 2, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {9, 0, 0, 0, 0, 4, 0, 0, 6}
        };
        int[] leftRowSums = {0, 2, 0, 4, 0, 6, 0, 8, 0};
        int[] topColSums = {0, 0, 0, 0, 5, 0, 0, 0, 0};
        int[] rightRowSums = {0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] bottomColSums = {0, 0, 0, 0, 0, 0, 0, 0, 0};

        AbstractPuzzle regular = new RegularSudoku.RegularSudokuBuilder()
                .withGivens(givens)
                .build();
        AbstractPuzzle skyscraper = new SkyscraperSudoku.SkyscraperSudokuBuilder(regular)
                .withLeftRowSums(leftRowSums)
                .withTopColSums(topColSums)
                .build();

        long[][] solution = {
                {1, 4, 7, 9, 3, 2, 6, 5, 8},
                {8, 2, 6, 1, 4, 5, 9, 3, 7},
                {3, 5, 9, 6, 7, 8, 4, 1, 2},
                {6, 7, 8, 4, 1, 9, 3, 2, 5},
                {5, 9, 2, 7, 8, 3, 1, 6, 4},
                {4, 1, 3, 2, 5, 6, 7, 8, 9},
                {7, 6, 5, 8, 9, 1, 2, 4, 3},
                {2, 3, 4, 5, 6, 7, 8, 9, 1},
                {9, 8, 1, 3, 2, 4, 5, 7, 6}
        };

        compareSolutions(skyscraper.solve(), solution);

    }
}
