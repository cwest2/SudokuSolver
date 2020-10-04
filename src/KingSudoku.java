import com.google.ortools.sat.IntVar;

public class KingSudoku extends VariantPuzzle {
    public static class KingSudokuBuilder {
        AbstractPuzzle base;

        public KingSudokuBuilder(AbstractPuzzle base) {
            this.base = base;
        }

        public KingSudoku build() {
            return new KingSudoku(base);
        }
    }

    private KingSudoku(AbstractPuzzle base) {
        super(base);
    }

    private IntVar getIfExists(IntVar[][] rows, int i, int j) {
        int n = getN();
        if (i < 0 || j < 0 || i >= n || j >= n) {
            return null;
        } else {
            return rows[i][j];
        }
    }

    @Override
    public void buildModel() {
        model = base.getModel();
        int n = getN();
        IntVar[][] rows = getRows();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - 1; j++) {
                IntVar cell = rows[i][j];
                IntVar left = getIfExists(rows, i + 1, j - 1);
                if (left != null) {
                    model.addDifferent(cell, left);
                }
                IntVar right = getIfExists(rows, i + 1, j + 1);
                if (right != null) {
                    model.addDifferent(cell, right);
                }
            }
        }
    }
}
