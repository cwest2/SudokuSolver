import org.junit.jupiter.api.Test;

public class FrameTest extends SudokuTest {

    @Test
    public void testFrame() {
        int[] leftRowSums = {8, 15, 22, 11, 13, 21, 18, 19, 8};
        int[] topColSums = {15, 18, 12, 11, 21, 13, 15, 17, 13};
        int[] rightRowSums = {22, 8, 15, 22, 12, 11, 15, 13, 17};
        int[] bottomColSums = {15, 9, 21, 10, 16, 19, 13, 15, 17};

        AbstractPuzzle puzzle = new RegularSudoku.RegularSudokuBuilder()
                .build();
        puzzle = new FrameSudoku.FrameSudokuBuilder(puzzle)
                .withLeftRowSums(leftRowSums)
                .withTopColSums(topColSums)
                .withRightRowSums(rightRowSums)
                .withBottomColSums(bottomColSums)
                .build();

        long[][] solution = {
                {1, 4, 3, 2, 8, 5, 9, 6, 7},
                {8, 5, 2, 6, 9, 7, 4, 3, 1},
                {6, 9, 7, 3, 4, 1, 2, 8, 5},
                {2, 3, 6, 7, 1, 4, 8, 5, 9},
                {4, 8, 1, 9, 5, 6, 3, 7, 2},
                {9, 7, 5, 8, 2, 3, 6, 1, 4},
                {7, 2, 9, 1, 3, 8, 5, 4, 6},
                {5, 6, 8, 4, 7, 2, 1, 9, 3},
                {3, 1, 4, 5, 6, 9, 7, 2, 8}
        };

        compareSolutions(puzzle.solve(), solution);
    }
}
