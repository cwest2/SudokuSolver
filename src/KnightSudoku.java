import org.chocosolver.solver.variables.IntVar;

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
            for (int j = 0; j < n; j++) {
                IntVar downLeft = getIfExists(rows, i + 2, j - 1);
                if (downLeft != null) {
                    IntVar[] downLeftPair = new IntVar[2];
                    downLeftPair[0] = rows[i][j];
                    downLeftPair[1] = downLeft;
                    model.allDifferent(downLeftPair, "AC").post();
                }
                IntVar downRight = getIfExists(rows, i + 2, j + 1);
                if (downRight != null) {
                    IntVar[] downRightPair = new IntVar[2];
                    downRightPair[0] = rows[i][j];
                    downRightPair[1] = downRight;
                    model.allDifferent(downRightPair, "AC").post();
                }
                IntVar leftDown = getIfExists(rows, i + 1, j - 2);
                if (leftDown != null) {
                    IntVar[] leftDownPair = new IntVar[2];
                    leftDownPair[0] = rows[i][j];
                    leftDownPair[1] = leftDown;
                    model.allDifferent(leftDownPair, "AC").post();
                }
                IntVar rightDown = getIfExists(rows, i + 1, j + 2);
                if (rightDown != null) {
                    IntVar[] rightDownPair = new IntVar[2];
                    rightDownPair[0] = rows[i][j];
                    rightDownPair[1] = rightDown;
                    model.allDifferent(rightDownPair, "AC").post();
                }
            }
        }
    }

    @Override
    protected void buildDokeFile(StringBuilder sb) {
        base.buildDokeFile(sb);
        sb.append("KNIGHT\n");
        sb.append("END KNIGHT\n");
    }
}
