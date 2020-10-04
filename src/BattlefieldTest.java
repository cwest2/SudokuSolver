import com.google.ortools.sat.IntVar;
import com.google.ortools.sat.Literal;
import org.junit.jupiter.api.Test;

public class BattlefieldTest extends SudokuTest {
    @Test
    public void testBattlefield() {
        int[] rowSums = {12, 0, 21, 39, 39, 3, 5, 0, 5};
        int[] colSums = {2, 0, 28, 6, 16, 12, 25, 0, 9};

        AbstractPuzzle puzzle = new RegularSudoku.RegularSudokuBuilder()
                .build();
        puzzle = new BattlefieldSudoku.BattlefieldSudokuBuilder(puzzle)
                .withLeftRowSums(rowSums)
                .withTopColSums(colSums)
                .build();

        long[][] solution = {
                {4, 5, 3, 9, 8, 2, 6, 1, 7},
                {9, 2, 6, 5, 1, 7, 3, 4, 8},
                {8, 1, 7, 3, 6, 4, 2, 9, 5},
                {2, 3, 9, 6, 4, 8, 7, 5, 1},
                {1, 6, 8, 7, 5, 9, 4, 3, 2},
                {5, 7, 4, 1, 2, 3, 9, 8, 6},
                {7, 4, 2, 8, 9, 1, 5, 6, 3},
                {3, 8, 5, 4, 7, 6, 1, 2, 9},
                {6, 9, 1, 2, 3, 5, 8, 7, 4}
        };

        compareSolutions(puzzle.solve(), solution);
    }

    @Test
    public void testSandwichBattlefield() {
        AbstractPuzzle puzzle = new RegularSudoku.RegularSudokuBuilder()
                .build();

        int[] leftRowSums = {-1, 9, 0, -1, 6, 21, 1, 5, -1};
        int[] topColSums = {5, -1, 31, 20, 26, -1, 33, -1, 11};
        int[] rightRowSums = {-1, 6, 2, -1, 13, 6, 24, 0, -1};
        int[] bottomColSums = {18, -1, 5, 21, 0, -1, 33, -1, 20};

        Literal[] rowConds = puzzle.makeBoolVarArray(new boolean[] {false, true, true, false, true, true, true, true, false}, "r");
        Literal[] colConds = puzzle.makeBoolVarArray(new boolean[] {true, false, true, true, true, false, true, false, true}, "c");
        Literal[] nRowConds = negateConds(rowConds);
        Literal[] nColConds = negateConds(colConds);

        puzzle = new SandwichSudoku.SandwichSudokuBuilder(puzzle)
                .withLeftRowSums(leftRowSums)
                .withRightRowSums(rightRowSums)
                .withTopColSums(topColSums)
                .withBottomColSums(bottomColSums)
                .withLeftRowConds(rowConds)
                .withRightRowConds(nRowConds)
                .withTopColConds(colConds)
                .withBottomColConds(nColConds)
                .build();

        puzzle = new BattlefieldSudoku.BattlefieldSudokuBuilder(puzzle)
                .withLeftRowSums(leftRowSums)
                .withRightRowSums(rightRowSums)
                .withTopColSums(topColSums)
                .withBottomColSums(bottomColSums)
                .withLeftRowConds(nRowConds)
                .withRightRowConds(rowConds)
                .withTopColConds(nColConds)
                .withBottomColConds(colConds)
                .build();

        long[][] solution = {
                {8, 9, 7, 5, 3, 4, 2, 6, 1},
                {3, 5, 4, 2, 1, 6, 9, 7, 8},
                {6, 1, 2, 9, 7, 8, 4, 5, 3},
                {1, 2, 6, 8, 4, 7, 5, 3, 9},
                {5, 4, 8, 3, 2, 9, 6, 1, 7},
                {9, 7, 3, 6, 5, 1, 8, 4, 2},
                {2, 3, 1, 4, 8, 5, 7, 9, 6},
                {7, 6, 5, 1, 9, 2, 3, 8, 4},
                {4, 8, 9, 7, 6, 3, 1, 2, 5}
        };

        compareSolutions(puzzle.solve(), solution);
    }
}
