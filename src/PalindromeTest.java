import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class PalindromeTest extends SudokuTest {

    @Test
    public void testPalindrome1() {
        int[][] givens = {
                {9, 0, 0, 4, 0, 0, 0, 0, 0},
                {0, 5, 0, 0, 8, 0, 0, 0, 4},
                {0, 0, 0, 0, 0, 0, 0, 0, 2},
                {6, 0, 0, 0, 0, 0, 0, 0, 3},
                {0, 3, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 8},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 8, 4, 5, 0, 0, 6, 0, 7}
        };

        List<int[][]> palindromes = new ArrayList<>();

        palindromes.add(new int[][]{
                {0, 1},
                {0, 2},
                {1, 3},
                {2, 4},
                {1, 5},
                {0, 6},
                {0, 7},
                {1, 6},
                {2, 5},
                {3, 5},
                {4, 6},
                {5, 7},
                {5, 8},
                {6, 7},
                {7, 8}
        });

        palindromes.add(new int[][]{
                {1, 0},
                {2, 0},
                {3, 1},
                {4, 2},
                {5, 1},
                {6, 0},
                {7, 0},
                {6, 1},
                {5, 2},
                {5, 3},
                {6, 4},
                {7, 5},
                {8, 5},
                {7, 6},
                {8, 7}
        });

        palindromes.add(new int[][]{
                {3, 1},
                {2, 2},
                {1, 3}
        });

        AbstractPuzzle puzzle = new RegularSudoku.RegularSudokuBuilder()
                .withGivens(givens)
                .build();
        puzzle = new PalindromeSudoku.PalindromeSudokuBuilder(puzzle, palindromes)
                .build();

        int[][] solution = {
                {9, 1, 3, 4, 2, 7, 8, 5, 6},
                {2, 5, 6, 9, 8, 1, 3, 7, 4},
                {4, 7, 8, 3, 6, 5, 9, 1, 2},
                {6, 9, 1, 2, 5, 8, 7, 4, 3},
                {7, 3, 2, 6, 9, 4, 1, 8, 5},
                {8, 4, 5, 1, 7, 3, 2, 6, 9},
                {1, 2, 9, 7, 4, 6, 5, 3, 8},
                {5, 6, 7, 8, 3, 2, 4, 9, 1},
                {3, 8, 4, 5, 1, 9, 6, 2, 7}
        };

        compareSolutions(puzzle.solve(), solution);
    }
}
