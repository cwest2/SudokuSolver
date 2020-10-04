import com.google.ortools.sat.IntVar;
import com.google.ortools.sat.Literal;
import org.junit.jupiter.api.Test;

public class XSumTest extends SudokuTest {

    @Test
    public void testXSum() {
        int[] leftRowSums = {-1, 8, -1, 17, -1, 30, -1, 28, -1};
        int[] topColSums = {-1, 27, -1, 11, -1, 21, 16, -1, -1};
        int[] rightRowSums = {-1, 8, -1, 17, -1, 30, -1, 28, -1};
        int[] bottomColSums = {-1, 27, -1, 11, -1, -1, 16, -1, -1};

        AbstractPuzzle regular = new RegularSudoku.RegularSudokuBuilder()
                .build();
        AbstractPuzzle xsums = new XSumSudoku.XSumSudokuBuilder(regular)
                .withLeftRowSums(leftRowSums)
                .withTopColSums(topColSums)
                .withRightRowSums(rightRowSums)
                .withBottomColSums(bottomColSums)
                .build();

        long[][] solution = {
                {8, 5, 6, 2, 1, 4, 3, 7, 9},
                {3, 4, 1, 9, 7, 5, 8, 6, 2},
                {7, 9, 2, 8, 6, 3, 5, 4, 1},
                {4, 1, 7, 5, 2, 9, 6, 8, 3},
                {9, 8, 5, 6, 3, 1, 7, 2, 4},
                {6, 2, 3, 4, 8, 7, 1, 9, 5},
                {2, 7, 4, 1, 5, 6, 9, 3, 8},
                {5, 3, 9, 7, 4, 8, 2, 1, 6},
                {1, 6, 8, 3, 9, 2, 4, 5, 7}
        };

        compareSolutions(xsums.solve(), solution);
    }

    @Test
    public void testXSumVsFrame() {
        AbstractPuzzle puzzle = new RegularSudoku.RegularSudokuBuilder()
                .build();

        int[] leftRowSums = {23, -1, 21, -1, 21, -1, 24, -1, 21};
        int[] topColSums = {23, -1, 21, -1, 20, -1, 23, -1, 21};
        int[] rightRowSums = {22, -1, 24, -1, 20, -1, 20, -1, 22};
        int[] bottomColSums = {22, -1, 24, -1, 22, -1, 20, -1, 23};

        IntVar N = null;

        IntVar[] rowConds = {puzzle.makeBoolVar("r0"), N, puzzle.makeBoolVar("r2"), N, puzzle.makeBoolVar("r4"), N, puzzle.makeBoolVar("r6"), N, puzzle.makeBoolVar("r8")};
        Literal[] nRowConds = negateConds(rowConds);

        IntVar[] colConds = {puzzle.makeBoolVar("c0"), N, puzzle.makeBoolVar("c2"), N, puzzle.makeBoolVar("c4"), N, puzzle.makeBoolVar("c6"), N, puzzle.makeBoolVar("c8")};
        Literal[] nColConds = negateConds(colConds);

        puzzle = new XSumSudoku.XSumSudokuBuilder(puzzle)
                .withLeftRowSums(leftRowSums)
                .withTopColSums(topColSums)
                .withRightRowSums(rightRowSums)
                .withBottomColSums(bottomColSums)
                .withLeftRowConds(rowConds)
                .withTopColConds(colConds)
                .withRightRowConds(nRowConds)
                .withBottomColConds(nColConds)
                .build();

        puzzle = new FrameSudoku.FrameSudokuBuilder(puzzle)
                .withLeftRowSums(leftRowSums)
                .withTopColSums(topColSums)
                .withRightRowSums(rightRowSums)
                .withBottomColSums(bottomColSums)
                .withLeftRowConds(nRowConds)
                .withTopColConds(nColConds)
                .withRightRowConds(rowConds)
                .withBottomColConds(colConds)
                .build();

        long[][] solution = {
                {6, 3, 4, 1, 7, 2, 5, 9, 8},
                {2, 1, 8, 9, 5, 6, 4, 3, 7},
                {7, 5, 9, 3, 8, 4, 1, 2, 6},
                {3, 9, 5, 6, 2, 8, 7, 4, 1},
                {4, 8, 2, 7, 1, 3, 6, 5, 9},
                {1, 6, 7, 4, 9, 5, 2, 8, 3},
                {5, 2, 3, 8, 6, 1, 9, 7, 4},
                {9, 4, 1, 5, 3, 7, 8, 6, 2},
                {8, 7, 6, 2, 4, 9, 3, 1, 5}
        };

        compareSolutions(puzzle.solve(), solution);
    }
}
