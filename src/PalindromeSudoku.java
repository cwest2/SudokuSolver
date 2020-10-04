import com.google.ortools.sat.IntVar;

import java.util.List;

public class PalindromeSudoku extends VariantPuzzle {
    List<int[][]> palindromes;

    public static class PalindromeSudokuBuilder {
        AbstractPuzzle base;
        List<int[][]> palindromes;

        public PalindromeSudokuBuilder(AbstractPuzzle base, List<int[][]> palindromes) {
            this.base = base;
            this.palindromes = palindromes;
        }

        public PalindromeSudoku build() {
            return new PalindromeSudoku(base, palindromes);
        }
    }

    private PalindromeSudoku(AbstractPuzzle base, List<int[][]> palindromes) {
        super(base);
        this.palindromes = palindromes;
    }

    @Override
    public void buildModel() {
        model = base.getModel();
        IntVar[][] rows = base.getRows();

        for (int[][] palindrome : palindromes) {
            int len = palindrome.length;
            for (int k = 0; k < len / 2; k++) {
                int[] first = palindrome[k];
                int[] second = palindrome[len - k - 1];
                model.addEquality(rows[first[0]][first[1]], rows[second[0]][second[1]]);
            }
        }
    }
}
