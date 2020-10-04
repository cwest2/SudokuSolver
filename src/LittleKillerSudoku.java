import com.google.ortools.sat.Constraint;
import com.google.ortools.sat.CpModel;
import com.google.ortools.sat.IntVar;
import com.google.ortools.sat.LinearExpr;

public class LittleKillerSudoku extends VariantPuzzle{
    IntVar[] leftRowSums;
    IntVar[] topColSums;
    IntVar[] rightRowSums;
    IntVar[] bottomColSums;
    String[] leftRowDirs;
    String[] topColDirs;
    String[] rightRowDirs;
    String[] bottomColDirs;
    IntVar downDiagonalSum;
    IntVar upDiagonalSum;

    public static class LittleKillerSudokuBuilder {
        AbstractPuzzle base;
        IntVar[] leftRowSums = null;
        IntVar[] topColSums = null;
        IntVar[] rightRowSums = null;
        IntVar[] bottomColSums = null;
        String[] leftRowDirs = null;
        String[] topColDirs = null;
        String[] rightRowDirs = null;
        String[] bottomColDirs = null;
        IntVar downDiagonalSum = null;
        IntVar upDiagonalSum = null;

        public IntVar[] emptySums(int n) {
            IntVar[] sums = new IntVar[n];
            for (int i = 0; i < n; i++) {
                sums[i] = null;
            }
            return sums;
        }

        public String[] emptyDirs(int n) {
            String[] dirs = new String[n];
            for (int i = 0; i < n; i++) {
                dirs[i] = "";
            }
            return dirs;
        }

        public IntVar[] intsToVars(int[] ints) {
            int n = ints.length;
            IntVar[] vars = new IntVar[n];
            for (int i = 0; i < n; i++) {
                if (ints[i] > 0) {
                    vars[i] = base.makeConstVar(ints[i], "vars" + i);
                } else {
                    vars[i] = null;
                }
            }
            return vars;
        }

        public LittleKillerSudokuBuilder(AbstractPuzzle base) {
            this.base = base;
        }

        public LittleKillerSudokuBuilder withUpDiagonal(int upDiagonalSum) {
            this.upDiagonalSum = upDiagonalSum > 0 ? base.makeConstVar(upDiagonalSum, "upDiagonalSum") : null;
            return this;
        }

        public LittleKillerSudokuBuilder withUpDiagonal(IntVar upDiagonalSum) {
            this.upDiagonalSum = upDiagonalSum;
            return this;
        }

        public LittleKillerSudokuBuilder withDownDiagonal(int downDiagonalSum) {
            this.downDiagonalSum = downDiagonalSum > 0 ? base.makeConstVar(downDiagonalSum, "downDiagonalSum") : null;
            return this;
        }

        public LittleKillerSudokuBuilder withDownDiagonal(IntVar downDiagonalSum) {
            this.downDiagonalSum = downDiagonalSum;
            return this;
        }

        public LittleKillerSudokuBuilder withLeftRowSums(int[] leftRowSums, String[] leftRowDirs) {
            this.leftRowSums = intsToVars(leftRowSums);
            this.leftRowDirs = leftRowDirs;
            return this;
        }

        public LittleKillerSudokuBuilder withLeftRowSums(IntVar[] leftRowSums, String[] leftRowDirs) {
            this.leftRowSums = leftRowSums;
            this.leftRowDirs = leftRowDirs;
            return this;
        }

        public LittleKillerSudokuBuilder withTopColSums(int[] topColSums, String[] topColDirs) {
            this.topColSums = intsToVars(topColSums);
            this.topColDirs = topColDirs;
            return this;
        }

        public LittleKillerSudokuBuilder withTopColSums(IntVar[] topColSums, String[] topColDirs) {
            this.topColSums = topColSums;
            this.topColDirs = topColDirs;
            return this;
        }

        public LittleKillerSudokuBuilder withRightRowSums(int[] rightRowSums, String[] rightRowDirs) {
            this.rightRowSums = intsToVars(rightRowSums);
            this.rightRowDirs = rightRowDirs;
            return this;
        }

        public LittleKillerSudokuBuilder withRightRowSums(IntVar[] rightRowSums, String[] rightRowDirs) {
            this.rightRowSums = rightRowSums;
            this.rightRowDirs = rightRowDirs;
            return this;
        }

        public LittleKillerSudokuBuilder withBottomColSums(int[] bottomColSums, String[] bottomColDirs) {
            this.bottomColSums = intsToVars(bottomColSums);
            this.bottomColDirs = bottomColDirs;
            return this;
        }

        public LittleKillerSudokuBuilder withBottomColSums(IntVar[] bottomColSums, String[] bottomColDirs) {
            this.bottomColSums = bottomColSums;
            this.bottomColDirs = bottomColDirs;
            return this;
        }

        public LittleKillerSudoku build() {
            if (base == null) {
                throw new IllegalArgumentException("LittleKillerSudoku instantiated without provided base");
            }

            leftRowSums = leftRowSums == null ? emptySums(base.getN()) : leftRowSums;
            topColSums = topColSums == null ? emptySums(base.getN()) : topColSums;
            rightRowSums = rightRowSums == null ? emptySums(base.getN()) : rightRowSums;
            bottomColSums = bottomColSums == null ? emptySums(base.getN()) : bottomColSums;

            leftRowDirs = leftRowDirs == null ? emptyDirs(base.getN()) : leftRowDirs;
            topColDirs = topColDirs == null ? emptyDirs(base.getN()) : topColDirs;
            rightRowDirs = rightRowDirs == null ? emptyDirs(base.getN()) : rightRowDirs;
            bottomColDirs = bottomColDirs == null ? emptyDirs(base.getN()) : bottomColDirs;

            return new LittleKillerSudoku(base, downDiagonalSum, upDiagonalSum,
                    leftRowSums, topColSums, rightRowSums, bottomColSums,
                    leftRowDirs, topColDirs, rightRowDirs, bottomColDirs);
        }
    }

    private LittleKillerSudoku(AbstractPuzzle base, IntVar downDiagonalSum, IntVar upDiagonalSum,
                               IntVar[] leftRowSums, IntVar[] topColSums, IntVar[] rightRowSums, IntVar[] bottomColSums,
                               String[] leftRowDirs, String[] topColDirs, String[] rightRowDirs, String[] bottomColDirs) {
        super(base);
        this.downDiagonalSum = downDiagonalSum;
        this.upDiagonalSum = upDiagonalSum;
        this.leftRowSums = leftRowSums;
        this.topColSums = topColSums;
        this.rightRowSums = rightRowSums;
        this.bottomColSums = bottomColSums;
        this.leftRowDirs = leftRowDirs;
        this.topColDirs = topColDirs;
        this.rightRowDirs = rightRowDirs;
        this.bottomColDirs = bottomColDirs;
    }

    private Constraint addUpwardsLittleKillerConstraint(int n, IntVar sum, IntVar[][] grid, CpModel model) {
        IntVar[] diagonal = new IntVar[n + 1];
        for (int i = 0; i <= n; i++) {
            diagonal[i] = grid[n - i][i];
        }
        return model.addEquality(LinearExpr.sum(diagonal), sum);
    }

    private void addKillerConstraints(String[] dirs, IntVar[] sums, String upDir, String downDir, IntVar[][] grid, CpModel model) {
        int n = dirs.length;
        IntVar[][] revGrid = makeColReversedGrid(grid);
        for (int i = 0; i < n; i++) {
            if (sums[i] != null) {
                if (dirs[i].equals(upDir)) {
                    addUpwardsLittleKillerConstraint(i - 1, sums[i], grid, model);
                } else if (dirs[i].equals(downDir)) {
                    addUpwardsLittleKillerConstraint(n - i - 2, sums[i], revGrid, model);
                } else {
                    throw new IllegalArgumentException("Nonexistant direction in dirs");
                }
            }
        }
    }

    @Override
    public void buildModel() {
        model = base.getModel();
        int n = getN();

        IntVar[][] rows = getRows();
        if (upDiagonalSum != null) {
            addUpwardsLittleKillerConstraint(n - 1, upDiagonalSum, rows, model);
        }
        addKillerConstraints(leftRowDirs, leftRowSums, "U", "D", rows, model);

        IntVar[][] revRows = makeRowReversedGrid(rows);
        if (downDiagonalSum != null) {
            addUpwardsLittleKillerConstraint(n - 1, downDiagonalSum, revRows, model);
        }
        addKillerConstraints(rightRowDirs, rightRowSums, "U", "D", revRows, model);

        IntVar[][] cols = getCols();
        addKillerConstraints(topColDirs, topColSums, "L", "R", cols, model);

        IntVar[][] revCols = makeRowReversedGrid(cols);
        addKillerConstraints(bottomColDirs, bottomColSums, "L", "R", revCols, model);
    }
}
