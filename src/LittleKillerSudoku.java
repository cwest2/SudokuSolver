import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

public class LittleKillerSudoku extends VariantPuzzle{
    int[] leftRowSums;
    int[] topColSums;
    int[] rightRowSums;
    int[] bottomColSums;
    String[] leftRowDirs;
    String[] topColDirs;
    String[] rightRowDirs;
    String[] bottomColDirs;
    int downDiagonalSum;
    int upDiagonalSum;

    @Override
    protected void buildDokeFile(StringBuilder sb) {
        base.buildDokeFile(sb);
        sb.append("LITTLE KILLER\n");
        sb.append(downDiagonalSum);
        sb.append(" ");
        sb.append(upDiagonalSum);
        sb.append("\n");
        buildRow(sb, leftRowSums);
        buildRow(sb, leftRowDirs);
        buildRow(sb, topColSums);
        buildRow(sb, topColDirs);
        buildRow(sb, rightRowSums);
        buildRow(sb, rightRowDirs);
        buildRow(sb, bottomColSums);
        buildRow(sb, bottomColDirs);
        sb.append("END LITTLE KILLER\n");
    }

    public static class LittleKillerSudokuBuilder {
        AbstractPuzzle base;
        int[] leftRowSums = null;
        int[] topColSums = null;
        int[] rightRowSums = null;
        int[] bottomColSums = null;
        String[] leftRowDirs = null;
        String[] topColDirs = null;
        String[] rightRowDirs = null;
        String[] bottomColDirs = null;
        int downDiagonalSum = 0;
        int upDiagonalSum = 0;

        public int[] emptySums(int n) {
            int[] sums = new int[n];
            for (int i = 0; i < n; i++) {
                sums[i] = 0;
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

        public LittleKillerSudokuBuilder(AbstractPuzzle base) {
            this.base = base;
        }

        public LittleKillerSudokuBuilder withUpDiagonal(int upDiagonalSum) {
            this.upDiagonalSum = upDiagonalSum;
            return this;
        }

        public LittleKillerSudokuBuilder withDownDiagonal(int downDiagonalSum) {
            this.downDiagonalSum = downDiagonalSum;
            return this;
        }

        public LittleKillerSudokuBuilder withLeftRowSums(int[] leftRowSums, String[] leftRowDirs) {
            this.leftRowSums = leftRowSums;
            this.leftRowDirs = leftRowDirs;
            return this;
        }

        public LittleKillerSudokuBuilder withTopColSums(int[] topColSums, String[] topColDirs) {
            this.topColSums = topColSums;
            this.topColDirs = topColDirs;
            return this;
        }

        public LittleKillerSudokuBuilder withRightRowSums(int[] rightRowSums, String[] rightRowDirs) {
            this.rightRowSums = rightRowSums;
            this.rightRowDirs = rightRowDirs;
            return this;
        }

        public LittleKillerSudokuBuilder withBottomColSums(int[] bottomColSums, String[] bottomColDirs) {
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

    private LittleKillerSudoku(AbstractPuzzle base, int downDiagonalSum, int upDiagonalSum,
                              int[] leftRowSums, int[] topColSums, int[] rightRowSums, int[] bottomColSums,
                              String[] leftRowDirs, String[] topColDirs, String[] rightRowDirs, String[] bottomColDirs) {
        this.base = base;
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

    private void addUpwardsLittleKillerConstraint(int n, int sum, IntVar[][] grid, Model model) {
        IntVar diagonal = model.intVar(0);
        for (int i = 0; i <= n; i++) {
            diagonal = diagonal.add(grid[n - i][i]).intVar();
        }
        diagonal.eq(sum).post();
    }

    private void addKillerConstraints(String[] dirs, int[] sums, String upDir, String downDir, IntVar[][] grid, Model model) {
        int n = dirs.length;
        IntVar[][] revGrid = makeColReversedGrid(grid);
        for (int i = 0; i < n; i++) {
            if (sums [i] > 0) {
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
        if (upDiagonalSum > 0) {
            addUpwardsLittleKillerConstraint(n - 1, upDiagonalSum, rows, model);
        }
        addKillerConstraints(leftRowDirs, leftRowSums, "U", "D", rows, model);

        IntVar[][] revRows = makeRowReversedGrid(rows);
        if (downDiagonalSum > 0) {
            addUpwardsLittleKillerConstraint(n - 1, downDiagonalSum, revRows, model);
        }
        addKillerConstraints(rightRowDirs, rightRowSums, "U", "D", revRows, model);

        IntVar[][] cols = getCols();
        addKillerConstraints(topColDirs, topColSums, "L", "R", cols, model);

        IntVar[][] revCols = makeRowReversedGrid(cols);
        addKillerConstraints(bottomColDirs, bottomColSums, "L", "R", revCols, model);
    }
}
