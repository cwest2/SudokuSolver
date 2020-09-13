import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class SandwichTest extends SudokuTest{

    @Test
    public void testSandwich1(){
        int[][] givens = emptyGrid(9);
        givens[4][3] = 1;

        int[] rowSums = {16, 7, 3, 14, 11, 21, 19, 33, 2};
        int[] colSums = {8, 23, 16, 15, 23, 13, 30, 27, 3};

        AbstractPuzzle regular = new RegularSudoku.RegularSudokuBuilder()
                .withGivens(givens)
                .build();
        AbstractPuzzle sandwich = new SandwichSudoku.SandwichSudokuBuilder(regular)
                .withRowSums(rowSums)
                .withColSums(colSums)
                .build();

        int[][] solution = {
                {8, 5, 9, 2, 7, 4, 3, 1, 6},
                {3, 1, 7, 9, 5, 6, 2, 4, 8},
                {4, 6, 2, 8, 1, 3, 9, 5, 7},
                {5, 2, 3, 7, 4, 9, 8, 6, 1},
                {9, 7, 4, 1, 6, 8, 5, 2, 3},
                {6, 8, 1, 3, 2, 5, 4, 7, 9},
                {2, 9, 5, 6, 8, 1, 7, 3, 4},
                {1, 4, 8, 5, 3, 7, 6, 9, 2},
                {7, 3, 6, 4, 9, 2, 1, 8, 5}
        };

        System.out.println("solving");
        compareSolutions(sandwich.solve(), solution);
    }

    @Test
    public void testSandwichWith0sAnd35s() {
        int[][] givens = {
                {0, 0, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0}
        };

        int[] rowSums = {5, 13, 20, 9, 12, 0, 4, 14, 5};
        int[] colSums = {19, 7, 15, 19, 4, 0, 6, 9, 35};

        AbstractPuzzle puzzle = new RegularSudoku.RegularSudokuBuilder()
                .withGivens(givens)
                .build();
        puzzle = new SandwichSudoku.SandwichSudokuBuilder(puzzle)
                .withRowSums(rowSums)
                .withColSums(colSums)
                .build();

        int[][] solution = {
                {4, 8, 5, 6, 7, 9, 3, 2, 1},
                {3, 2, 9, 5, 8, 1, 6, 4, 7},
                {7, 1, 6, 2, 3, 4, 5, 9, 8},
                {1, 7, 2, 9, 5, 3, 8, 6, 4},
                {6, 9, 4, 8, 1, 2, 7, 3, 5},
                {8, 5, 3, 7, 4, 6, 9, 1, 2},
                {5, 3, 1, 4, 9, 7, 2, 8, 6},
                {9, 6, 8, 1, 2, 5, 4, 7, 3},
                {2, 4, 7, 3, 6, 8, 1, 5, 9}
        };

        compareSolutions(puzzle.solve(), solution);
    }

    @Test
    public void testSandwichWith0s() {
        int[][] givens = {
                {8, 0, 0, 0, 0, 0, 0, 0, 6},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 9, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 6, 0, 0, 0, 8, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {4, 0, 0, 0, 0, 0, 0, 0, 3}
        };

        int[] rowSums = {7, 10, 21, 5, 21, 0, 12, 23, 16};
        int[] colSums = {7, 20, 23, 12, 24, 14, 25, 20, 0};

        AbstractPuzzle puzzle = new RegularSudoku.RegularSudokuBuilder()
                .withGivens(givens)
                .build();
        puzzle = new SandwichSudoku.SandwichSudokuBuilder(puzzle)
                .withRowSums(rowSums)
                .withColSums(colSums)
                .build();

        int[][] solution = {
                {8, 9, 7, 1, 4, 5, 3, 2, 6},
                {6, 4, 5, 2, 9, 3, 7, 1, 8},
                {2, 3, 1, 6, 8, 7, 9, 5, 4},
                {5, 6, 8, 4, 7, 1, 2, 3, 9},
                {3, 7, 4, 9, 6, 2, 5, 8, 1},
                {9, 1, 2, 5, 3, 8, 6, 4, 7},
                {7, 2, 6, 3, 1, 4, 8, 9, 5},
                {1, 8, 3, 7, 5, 9, 4, 6, 2},
                {4, 5, 9, 8, 2, 6, 1, 7, 3}
        };

        compareSolutions(puzzle.solve(), solution);
    }

    @Test
    public void testSandwichBetween4And6() {
        int[][] givens = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 4, 0, 0, 0}
        };

        int[] rowSums = {35, 16, 10, 14, 6, 2, 1, 25, 7};
        int[] colSums = {26, 22, 11, 3, 2, 8, 18, 6, 12};

        AbstractPuzzle puzzle = new RegularSudoku.RegularSudokuBuilder()
                .withGivens(givens)
                .build();
        puzzle = new SandwichSudoku.SandwichSudokuBuilder(puzzle)
                .withRowSums(rowSums)
                .withColSums(colSums)
                .withTopBun(6)
                .withBottomBun(4)
                .build();

        int[][] solution = {
                {4, 1, 3, 5, 8, 2, 9, 7, 6},
                {2, 5, 8, 6, 9, 7, 4, 3, 1},
                {9, 6, 7, 3, 4, 1, 5, 8, 2},
                {8, 3, 1, 4, 2, 5, 7, 6, 9},
                {7, 9, 5, 8, 6, 3, 2, 1, 4},
                {6, 2, 4, 7, 1, 9, 3, 5, 8},
                {5, 8, 2, 9, 3, 6, 1, 4, 7},
                {3, 4, 9, 1, 7, 8, 6, 2, 5},
                {1, 7, 6, 2, 5, 4, 8, 9, 3}
        };

        compareSolutions(puzzle.solve(), solution);
    }

    @Test
    public void testVariableBunWithKillerAndKnight() {
        int[] rowSums = {-1, 5, -1, -1, -1, -1, 40, -1, -1};
        int[] colSums = {9, -1, -1, 9, -1, -1, 8, 0, -1};

        int[][] cages = {
                {0, 0, 0, 0, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 1, 0, 2, 0},
                {0, 0, 0, 0, 0, 0, 0, 2, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 3, 3},
                {4, 0, 0, 0, 0, 0, 5, 0, 0},
                {4, 6, 6, 0, 0, 0, 5, 0, 7},
                {0, 0, 0, 0, 8, 8, 0, 0, 7},
                {0, 0, 0, 0, 0, 0, 0, 0, 7}
        };

        HashMap<Integer, Integer> cageSums = new HashMap<>();
        cageSums.put(1, 3);
        cageSums.put(2, 5);
        cageSums.put(3, 4);
        cageSums.put(4, 3);
        cageSums.put(5, 9);
        cageSums.put(6, 7);
        cageSums.put(7, 7);
        cageSums.put(8, 4);

        AbstractPuzzle puzzle = new RegularSudoku.RegularSudokuBuilder()
                .build();
        puzzle = new SandwichSudoku.SandwichSudokuBuilder(puzzle)
                .withRowSums(rowSums)
                .withColSums(colSums)
                .withTopBunRange(1, 9)
                .withBottomBunRange(1, 9)
                .build();
        puzzle = new KillerSudoku.KillerSudokuBuilder(puzzle, cages, cageSums)
                .build();
        puzzle = new KnightSudoku.KnightSudokuBuilder(puzzle)
                .build();

        int[][] solution = {
                {5, 8, 4, 3, 6, 2, 1, 7, 9},
                {9, 7, 2, 4, 5, 1, 8, 3, 6},
                {3, 6, 1, 9, 7, 8, 4, 2, 5},
                {4, 5, 6, 1, 3, 7, 2, 9, 8},
                {7, 9, 8, 2, 4, 6, 5, 1, 3},
                {2, 1, 3, 8, 9, 5, 6, 4, 7},
                {1, 2, 5, 7, 8, 9, 3, 6, 4},
                {6, 4, 7, 5, 1, 3, 9, 8, 2},
                {8, 3, 9, 6, 2, 4, 7, 5, 1}
        };

        compareSolutions(puzzle.solve(), solution);
    }
}