import com.google.ortools.sat.IntVar;

public class KnightSudoku extends VariantPuzzle {

    public static class KnightSudokuBuilder {
        AbstractPuzzle base;

        public KnightSudokuBuilder(AbstractPuzzle base) {
            this.base = base;
        }

        public KnightSudoku build() {
            return new KnightSudoku(base);
        }
    }

    private KnightSudoku(AbstractPuzzle base) {
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
            for (int j = 0; j < n; j++) {
                IntVar cell = rows[i][j];
                IntVar downLeft = getIfExists(rows, i + 2, j - 1);
                if (downLeft != null) {
                    model.addDifferent(cell, downLeft);
                }
                IntVar downRight = getIfExists(rows, i + 2, j + 1);
                if (downRight != null) {
                    model.addDifferent(cell, downRight);
                }
                IntVar leftDown = getIfExists(rows, i + 1, j - 2);
                if (leftDown != null) {
                    model.addDifferent(cell, leftDown);
                }
                IntVar rightDown = getIfExists(rows, i + 1, j + 2);
                if (rightDown != null) {
                    model.addDifferent(cell, rightDown);
                }
            }
        }
    }
}
