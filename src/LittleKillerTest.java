import com.google.ortools.sat.CpModel;
import com.google.ortools.sat.CpSolver;
import com.google.ortools.sat.IntVar;
import com.google.ortools.sat.LinearExpr;
import com.google.ortools.util.Domain;
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

        long[][] solution = {
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

    @Test
    public void testAssignVarsFromValues() {
        int[][] givens = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 3, 8, 7, 0, 0, 0},
                {0, 0, 5, 0, 0, 0, 0, 0, 0},
                {0, 0, 9, 0, 0, 0, 6, 0, 0},
                {0, 0, 1, 0, 6, 2, 0, 4, 0},
                {0, 0, 4, 0, 0, 0, 7, 0, 0},
                {0, 0, 6, 0, 0, 0, 2, 0, 0},
                {0, 0, 0, 4, 2, 6, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0}
        };

        AbstractPuzzle puzzle = new RegularSudoku.RegularSudokuBuilder()
                .withGivens(givens)
                .build();

        IntVar[] sums = puzzle.assignVarsFromValues(new long[] {31, 10, 19, 35, 6, 7, 20, 20});
        IntVar E = null;

        IntVar[] topColSums = {E, E, E, E, sums[0], E, sums[1], sums[2], E};
        String[] topColDirs = {N, N, N, N, R, N, R, R, N};
        IntVar[] rightRowSums = {sums[3], E, E, E, E, sums[4], E, sums[5], E};
        String[] rightRowDirs = {D, N, N, N, N, D, N, D, N};
        IntVar[] bottomColSums = {E, E, E, sums[6], E, E, E, E, E};
        String[] bottomColDirs = {N, N, N, L, N, N, N, N, N};
        IntVar[] leftRowSums = {E, E, E, E, sums[7], E, E, E, E};
        String[] leftRowDirs = {N, N, N, N, U, N, N, N, N};

        puzzle = new LittleKillerSudoku.LittleKillerSudokuBuilder(puzzle)
                .withTopColSums(topColSums, topColDirs)
                .withRightRowSums(rightRowSums, rightRowDirs)
                .withBottomColSums(bottomColSums, bottomColDirs)
                .withLeftRowSums(leftRowSums, leftRowDirs)
                .build();

        long[][] solution = {
                {3, 4, 8, 2, 5, 9, 1, 6, 7},
                {6, 1, 2, 3, 8, 7, 9, 5, 4},
                {9, 7, 5, 6, 4, 1, 3, 8, 2},
                {8, 3, 9, 1, 7, 4, 6, 2, 5},
                {7, 5, 1, 9, 6, 2, 8, 4, 3},
                {2, 6, 4, 5, 3, 8, 7, 9, 1},
                {4, 8, 6, 7, 1, 5, 2, 3, 9},
                {1, 9, 3, 4, 2, 6, 5, 7, 8},
                {5, 2, 7, 8, 9, 3, 4, 1, 6}
        };

        compareSolutions(puzzle.solve(), solution);
    }
}
