import com.google.ortools.sat.CpModel;
import com.google.ortools.sat.IntVar;
import com.google.ortools.util.Domain;

public class DigitLocationSudoku extends VariantPuzzle {
    int digit;
    boolean[] leftRowActive;
    boolean[] topColActive;
    boolean[] rightRowActive;
    boolean[] bottomColActive;

    public static class DigitLocationSudokuBuilder {
        AbstractPuzzle base;
        int digit = 1;
        boolean[] leftRowActive = null;
        boolean[] topColActive = null;
        boolean[] rightRowActive = null;
        boolean[] bottomColActive = null;

        public DigitLocationSudokuBuilder(AbstractPuzzle base) {
            this.base = base;
        }

        public DigitLocationSudokuBuilder withLeftRow(boolean[] leftRowActive) {
            this.leftRowActive = leftRowActive;
            return this;
        }

        public DigitLocationSudokuBuilder withTopCol(boolean[] topColActive) {
            this.topColActive = topColActive;
            return this;
        }

        public DigitLocationSudokuBuilder withRightRow(boolean[] rightRowActive) {
            this.rightRowActive = rightRowActive;
            return this;
        }

        public DigitLocationSudokuBuilder withBottomCol(boolean[] bottomColActive) {
            this.bottomColActive = bottomColActive;
            return this;
        }

        public DigitLocationSudokuBuilder withDigit(int digit) {
            this.digit = digit;
            return this;
        }

        public DigitLocationSudoku build() {
            int n = base.getN();
            if (leftRowActive == null) {
                leftRowActive = new boolean[n];
            }
            if (topColActive == null) {
                topColActive = new boolean[n];
            }
            if (rightRowActive == null) {
                rightRowActive = new boolean[n];
            }
            if (bottomColActive == null) {
                bottomColActive = new boolean[n];
            }
            return new DigitLocationSudoku(base, digit, leftRowActive, topColActive, rightRowActive, bottomColActive);
        }
    }

    private DigitLocationSudoku(AbstractPuzzle base, int digit, boolean[] leftRowActive, boolean[] topColActive, boolean[] rightRowActive, boolean[] bottomColActive) {
        super(base);
        this.digit = digit;
        this.leftRowActive = leftRowActive;
        this.topColActive = topColActive;
        this.rightRowActive = rightRowActive;
        this.bottomColActive = bottomColActive;
    }

    private void applyDigitLocationConstraint(int digit, CpModel model, IntVar[] row, int n) {
        IntVar digitLoc = model.newIntVar(0, n - 1, "digitLoc");
        model.addElement(digitLoc, row, model.newIntVarFromDomain(new Domain(digit), "" + digit));
        model.addEqualityWithOffset(digitLoc, row[0], 1);
    }

    private void applyDigitLocationConstraints(int digit, boolean[] actives, CpModel model, IntVar[][] grid, int n) {
        for (int i = 0; i < n; i++) {
            if (actives[i]) {
                applyDigitLocationConstraint(digit, model, grid[i], n);
            }
        }
    }

    @Override
    public void buildModel() {
        model = base.getModel();
        int n = getN();
        IntVar[][] rows = getRows();
        IntVar[][] cols = getCols();

        applyDigitLocationConstraints(digit, leftRowActive, model, rows, n);
        applyDigitLocationConstraints(digit, topColActive, model, cols, n);
        applyDigitLocationConstraints(digit, rightRowActive, model, makeRowReversedGrid(rows), n);
        applyDigitLocationConstraints(digit, bottomColActive, model, makeRowReversedGrid(cols), n);
    }
}
