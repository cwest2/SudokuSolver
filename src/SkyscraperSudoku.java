import com.google.ortools.sat.Constraint;
import com.google.ortools.sat.CpModel;
import com.google.ortools.sat.IntVar;
import com.google.ortools.sat.LinearExpr;
import com.google.ortools.util.Domain;

public class SkyscraperSudoku extends VariantPuzzle {

    int[] leftRowSums;
    int[] topColSums;
    int[] rightRowSums;
    int[] bottomColSums;

    private Constraint getSkyscraperConstraint(CpModel model, IntVar[] row, int seen) {
        IntVar tallest = model.newIntVarFromDomain(new Domain(0), "tallest");
        IntVar[] rowSeenVars = new IntVar[row.length];
        for (int i = 0; i < row.length; i++) {
            rowSeenVars[i] = varLt(tallest, row[i]);
            tallest = varMax(tallest, row[i]);
        }
        return model.addEquality(LinearExpr.sum(rowSeenVars), seen);
    }

    public static class SkyscraperSudokuBuilder {
        AbstractPuzzle base;
        int[] leftRowSums;
        int[] topColSums;
        int[] rightRowSums;
        int[] bottomColSums;

        public int[] emptySums(int n) {
            int[] sums = new int[n];
            for (int i = 0; i < n; i++) {
                sums[i] = 0;
            }
            return sums;
        }

        public SkyscraperSudokuBuilder(AbstractPuzzle base) {
            this.base = base;
        }

        public SkyscraperSudokuBuilder withLeftRowSums(int[] leftRowSums) {
            this.leftRowSums = leftRowSums;
            return this;
        }

        public SkyscraperSudokuBuilder withTopColSums(int[] topColSums) {
            this.topColSums = topColSums;
            return this;
        }

        public SkyscraperSudokuBuilder withRightRowSums(int[] rightRowSums) {
            this.rightRowSums = rightRowSums;
            return this;
        }

        public SkyscraperSudokuBuilder withBottomColSums(int[] bottomColSums) {
            this.bottomColSums = bottomColSums;
            return this;
        }

        public SkyscraperSudoku build() {
            if (leftRowSums == null) {
                leftRowSums = emptySums(base.getN());
            }
            if (topColSums == null) {
                topColSums = emptySums(base.getN());
            }
            if (rightRowSums == null) {
                rightRowSums = emptySums(base.getN());
            }
            if (bottomColSums == null) {
                bottomColSums = emptySums(base.getN());
            }
            return new SkyscraperSudoku(base, leftRowSums, topColSums, rightRowSums, bottomColSums);
        }
    }

    public SkyscraperSudoku(AbstractPuzzle base, int[] leftRowSums, int[] topColSums, int[] rightRowSums, int[] bottomColSums) {
        super(base);
        this.leftRowSums = leftRowSums;
        this.topColSums = topColSums;
        this.rightRowSums = rightRowSums;
        this.bottomColSums = bottomColSums;
    }

    @Override
    public void buildModel() {
        model = base.getModel();
        IntVar[][] rows = getRows();
        IntVar[][] cols = getCols();
        int n = getN();

        for (int i = 0; i < n; i++) {
            if (leftRowSums[i] > 0) {
                getSkyscraperConstraint(model, rows[i], leftRowSums[i]);
            }
            if (topColSums[i] > 0) {
                getSkyscraperConstraint(model, cols[i], topColSums[i]);
            }
            if (rightRowSums[i] > 0) {
                getSkyscraperConstraint(model, makeReversedArray(rows[i]), rightRowSums[i]);
            }
            if (bottomColSums[i] > 0) {
                getSkyscraperConstraint(model, makeReversedArray(cols[i]), bottomColSums[i]);
            }
        }
    }
}
