import com.google.ortools.sat.IntVar;

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
        super(base);
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

    private IntVar incConstraint(IntVar[] row, int len) {
        IntVar con = varLt(row[0], row[1]);
        for (int j = 1; j < len - 1; j++) {
            con = varAnd(con, varLt(row[j], row[j + 1]));
        }
        return con;
    }

    private IntVar decConstraint(IntVar[] row, int len) {
        IntVar con = varGt(row[0], row[1]);
        for (int j = 1; j < len - 1; j++) {
            con = varAnd(con, varGt(row[j], row[j + 1]));
        }
        return con;
    }

    private void addRossiniConstraints(String[] dirs, String incDir, String decDir, IntVar[][] grid, int len, int n) {
        for (int i = 0; i < n; i++) {
            IntVar[] row = grid[i];
            if (dirs[i].equals(incDir)) {
                enforceBool(incConstraint(row, len));
            } else if (dirs[i].equals(decDir)) {
                enforceBool(decConstraint(row, len));
            } else if (negative) {
                enforceBool(incConstraint(row, len).not());
                enforceBool(decConstraint(row, len).not());
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
}
