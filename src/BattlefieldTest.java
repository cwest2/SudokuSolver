import org.junit.jupiter.api.Test;

public class BattlefieldTest extends SudokuTest {
    @Test
    public void testBattlefield() {
        int[] rowSums = {12, 0, 21, 39, 39, 3, 5, 0, 5};
        int[] colSums = {2, 0, 28, 6, 16, 12, 25, 0, 9};

        AbstractPuzzle puzzle = new RegularSudoku.RegularSudokuBuilder()
                .build();
        puzzle = new BattlefieldSudoku.BattlefieldSudokuBuilder(puzzle)
                .withRowSums(rowSums)
                .withColSums(colSums)
                .build();

        puzzle.printSolution();
    }
}
