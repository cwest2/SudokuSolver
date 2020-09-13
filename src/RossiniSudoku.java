import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.expression.discrete.relational.ReExpression;
import org.chocosolver.solver.variables.IntVar;

public class RossiniSudoku extends VariantPuzzle {
    String[] leftRowDirs;
    String[] topColDirs;
    String[] rightRowDirs;
    String[] bottomColDirs;
    String left;
    String right;
    String up;
    String down;
    int lrLen;
    int udLen;
    boolean negative;

    public static class RossiniSudokuBuilder {
        AbstractPuzzle base;
        String[] leftRowDirs;
        String[] topColDirs;
        String[] rightRowDirs;
        String[] bottomColDirs;
        String left = "L";
        String right = "R";
        String up = "U";
        String down = "D";
        int lrLen = 3;
        int udLen = 3;
        boolean negative = false;

        public RossiniSudokuBuilder(AbstractPuzzle base) {
            this.base = base;
        }

        public RossiniSudokuBuilder withLeftRowDirs(String[] leftRowDirs) {
            this.leftRowDirs = leftRowDirs;
            return this;
        }

        public RossiniSudokuBuilder withTopColDirs(String[] topColDirs) {
            this.topColDirs = topColDirs;
            return this;
        }

        public RossiniSudokuBuilder withRightRowDirs(String[] rightRowDirs) {
            this.rightRowDirs = rightRowDirs;
            return this;
        }

        public RossiniSudokuBuilder withBottomColDirs(String[] bottomColDirs) {
            this.bottomColDirs = bottomColDirs;
            return this;
        }

        public RossiniSudokuBuilder withDirs(String left, String right, String up, String down) {
            this.left = left;
            this.right = right;
            this.up = up;
            this.down = down;
            return this;
        }

        public RossiniSudokuBuilder withLen(int len) {
            this.lrLen = len;
            this.udLen = len;
            return this;
        }

        public RossiniSudokuBuilder withNegative(boolean negative) {
            this.negative = negative;
            return this;
        }

        public RossiniSudoku build() {
            return new RossiniSudoku(base, leftRowDirs, topColDirs, rightRowDirs, bottomColDirs,
                    left, right, up, down, lrLen, udLen, negative);
        }
    }

    private RossiniSudoku(AbstractPuzzle base,
                          String[] leftRowDirs, String[] topColDirs, String[] rightRowDirs, String[] bottomColDirs,
                          String left, String right, String up, String down, int lrLen, int udLen, boolean negative) {
        this.base = base;
        this.leftRowDirs = leftRowDirs;
        this.topColDirs = topColDirs;
        this.rightRowDirs = rightRowDirs;
        this.bottomColDirs = bottomColDirs;
        this.left = left;
        this.right = right;
        this.up = up;
        this.down = down;
        this.lrLen = lrLen;
        this.udLen = udLen;
        this.negative = negative;
    }

    private ReExpression incConstraint(IntVar[] row, int len) {
        ReExpression con = row[0].lt(row[1]);
        for (int j = 1; j < len - 1; j++) {
            con = con.and(row[j].lt(row[j + 1]));
        }
        return con;
    }

    private ReExpression decConstraint(IntVar[] row, int len) {
        ReExpression con = row[0].gt(row[1]);
        for (int j = 1; j < len - 1; j++) {
            con = con.and(row[j].gt(row[j + 1]));
        }
        return con;
    }

    private void addRossiniConstraints(String[] dirs, String incDir, String decDir, IntVar[][] grid, int len, int n) {
        for (int i = 0; i < n; i++) {
            IntVar[] row = grid[i];
            if (dirs[i].equals(incDir)) {
                incConstraint(row, len).post();
            } else if (dirs[i].equals(decDir)) {
                decConstraint(row, len).post();
            } else if (negative) {
                incConstraint(row, len).not().post();
                decConstraint(row, len).not().post();
            }
        }
    }

    @Override
    public void buildModel() {
        model = base.getModel();
        int n = getN();
        IntVar[][] rows = getRows();
        IntVar[][] cols = getCols();
        addRossiniConstraints(leftRowDirs, right, left, rows, lrLen, n);
        addRossiniConstraints(rightRowDirs, left, right, makeRowReversedGrid(rows), lrLen, n);
        addRossiniConstraints(topColDirs, down, up, cols, udLen, n);
        addRossiniConstraints(bottomColDirs, up, down, makeRowReversedGrid(cols), udLen, n);
    }

    @Override
    protected void buildDokeFile(StringBuilder sb) {

    }
}
