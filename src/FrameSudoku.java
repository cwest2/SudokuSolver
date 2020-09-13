import org.chocosolver.solver.constraints.nary.nvalue.amnv.mis.F;
import org.chocosolver.solver.variables.IntVar;

public class FrameSudoku extends VariantPuzzle {
    int[] leftRowSums;
    int[] topColSums;
    int[] rightRowSums;
    int[] bottomColSums;
    int numCells;

    public static class FrameSudokuBuilder {
        AbstractPuzzle base;
        int[] leftRowSums = null;
        int[] topColSums = null;
        int[] rightRowSums = null;
        int[] bottomColSums = null;
        int numCells = 3;

        public int[] emptySums(int n) {
            int[] sums = new int[n];
            for (int i = 0; i < n; i++) {
                sums[i] = 0;
            }
            return sums;
        }

        public FrameSudokuBuilder(AbstractPuzzle base) {
            this.base = base;
        }

        public FrameSudokuBuilder withLeftRowSums(int[] leftRowSums) {
            this.leftRowSums = leftRowSums;
            return this;
        }

        public FrameSudokuBuilder withTopColSums(int[] topColSums) {
            this.topColSums = topColSums;
            return this;
        }

        public FrameSudokuBuilder withRightRowSums(int[] rightRowSums) {
            this.rightRowSums = rightRowSums;
            return this;
        }

        public FrameSudokuBuilder withBottomColSums(int[] bottomColSums) {
            this.bottomColSums = bottomColSums;
            return this;
        }

        public FrameSudoku build() {
            int n = base.getN();
            if (leftRowSums == null) {
                leftRowSums = emptySums(n);
            }
            if (topColSums == null) {
                topColSums = emptySums(n);
            }
            if (rightRowSums == null) {
                rightRowSums = emptySums(n);
            }
            if (bottomColSums == null) {
                bottomColSums = emptySums(n);
            }
            return new FrameSudoku(base, leftRowSums, topColSums, rightRowSums, bottomColSums, numCells);
        }
    }

    private FrameSudoku(AbstractPuzzle base, int[] leftRowSums, int[] topColSums, int[] rightRowSums, int[] bottomColSums, int numCells) {
        this.base = base;
        this.leftRowSums = leftRowSums;
        this.topColSums = topColSums;
        this.rightRowSums = rightRowSums;
        this.bottomColSums = bottomColSums;
        this.numCells = numCells;
    }

    private void buildFrameConstraints(IntVar[][] rows, int[] sums, int numCells) {
        for (int i = 0; i < sums.length; i++) {
            IntVar[] row = rows[i];
            IntVar sum = row[0];
            for (int j = 1; j < numCells; j++) {
                sum = sum.add(row[j]).intVar();
            }
            sum.eq(sums[i]).post();
        }
    }

    @Override
    public void buildModel() {
        model = base.getModel();
        IntVar[][] rows = getRows();
        IntVar[][] cols = getCols();

        buildFrameConstraints(rows, leftRowSums, numCells);
        buildFrameConstraints(cols, topColSums, numCells);
        buildFrameConstraints(makeRowReversedGrid(rows), rightRowSums, numCells);
        buildFrameConstraints(makeRowReversedGrid(cols), bottomColSums, numCells);
    }

    @Override
    protected void buildDokeFile(StringBuilder sb) {

    }
}
