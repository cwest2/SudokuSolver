import org.junit.jupiter.api.Test;

public class LittleKillerTest extends SudokuTest {
    String N = "N";
    String U = "U";
    String D = "D";
    String L = "L";
    String R = "R";

    @Test
    public void testLittleKiller1() {
        int[] leftRowSums = {0, 0, 30, 11, 17, 15, 12, 2, 0};
        String[] leftRowDirs = {N, N, D, D, D, D, D, D, N};
        int[] topColSums = {0, 5, 14, 17, 20, 23, 0, 0, 0};
        String[] topColDirs = {N, L, L, L, L, L, N, N, N};
        int[] rightRowSums = {0, 3, 6, 13, 25, 23, 28, 0, 0};
        String[] rightRowDirs = {N, U, U, U, U, U, U, N, N};
        int[] bottomColSums = {0, 0, 0, 28, 20, 19, 12, 8, 0};
        String[] bottomColDirs = {N, N, N, R, R, R, R, R, N};

        AbstractPuzzle regular = new RegularSudoku.RegularSudokuBuilder()
                .build();
        AbstractPuzzle littleKiller = new LittleKillerSudoku.LittleKillerSudokuBuilder(regular)
                .withLeftRowSums(leftRowSums, leftRowDirs)
                .withTopColSums(topColSums, topColDirs)
                .withRightRowSums(rightRowSums, rightRowDirs)
                .withBottomColSums(bottomColSums, bottomColDirs)
                .build();

        int[][] solution = {
                {5, 8, 1, 9, 6, 2, 7, 4, 3},
                {6, 9, 4, 7, 1, 3, 8, 5, 2},
                {7, 3, 2, 8, 5, 4, 6, 9, 1},
                {4, 7, 8, 3, 9, 5, 2, 1, 6},
                {1, 5, 9, 6, 2, 8, 4, 3, 7},
                {3, 2, 6, 4, 7, 1, 5, 8, 9},
                {9, 6, 3, 5, 8, 7, 1, 2, 4},
                {8, 1, 7, 2, 4, 9, 3, 6, 5},
                {2, 4, 5, 1, 3, 6, 9, 7, 8}
        };

        compareSolutions(littleKiller.solve(), solution);
    }
}
