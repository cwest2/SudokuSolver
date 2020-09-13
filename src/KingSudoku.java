import org.chocosolver.solver.variables.IntVar;

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
        this.base = base;
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
                IntVar left = getIfExists(rows, i + 1, j - 1);
                if (left != null) {
                    IntVar[] leftPair = new IntVar[2];
                    leftPair[0] = rows[i][j];
                    leftPair[1] = left;
                    model.allDifferent(leftPair, "AC").post();
                }
                IntVar right = getIfExists(rows, i + 1, j + 1);
                if (right != null) {
                    IntVar[] rightPair = new IntVar[2];
                    rightPair[0] = rows[i][j];
                    rightPair[1] = right;
                    model.allDifferent(rightPair, "AC").post();
                }
            }
        }
    }

    @Override
    protected void buildDokeFile(StringBuilder sb) {
        base.buildDokeFile(sb);
        sb.append("KING\n");
        sb.append("END KING\n");
    }
}
