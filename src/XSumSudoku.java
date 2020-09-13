import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.variables.IntVar;

public class XSumSudoku extends VariantPuzzle {

    int[] leftRowSums;
    int[] topColSums;
    int[] rightRowSums;
    int[] bottomColSums;

    public static class XSumSudokuBuilder {
        AbstractPuzzle base;
        int[] leftRowSums = null;
        int[] topColSums = null;
        int[] rightRowSums = null;
        int[] bottomColSums = null;

        public int[] emptySums(int n) {
            int[] sums = new int[n];
            for (int i = 0; i < n; i++) {
                sums[i] = 0;
            }
            return sums;
        }

        public XSumSudokuBuilder(AbstractPuzzle base) {
            this.base = base;
        }

        public XSumSudokuBuilder withLeftRowSums(int[] leftRowSums) {
            this.leftRowSums = leftRowSums;
            return this;
        }

        public XSumSudokuBuilder withTopColSums(int[] topColSums) {
            this.topColSums = topColSums;
            return this;
        }

        public XSumSudokuBuilder withRightRowSums(int[] rightRowSums) {
            this.rightRowSums = rightRowSums;
            return this;
        }

        public XSumSudokuBuilder withBottomColSums(int[] bottomColSums) {
            this.bottomColSums = bottomColSums;
            return this;
        }

        public XSumSudoku build() {
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
            return new XSumSudoku(base, leftRowSums, topColSums, rightRowSums, bottomColSums);
        }
    }

    public XSumSudoku(AbstractPuzzle base, int[] leftRowSums, int[] topColSums, int[] rightRowSums, int[] bottomColSums) {
        this.base = base;
        this.leftRowSums = leftRowSums;
        this.topColSums = topColSums;
        this.rightRowSums = rightRowSums;
        this.bottomColSums = bottomColSums;
    }

    private Constraint getXSumConstraint(Model model, IntVar[] row, int sum) {
        IntVar rowStart = row[0];
        IntVar[] rowSumVars = new IntVar[row.length];
        for (int i = 0; i < row.length; i++) {
            rowSumVars[i] = row[i].mul(rowStart.gt(i).intVar()).intVar();
        }
        return model.sum(rowSumVars, "=", sum);
    }

    @Override
    public void buildModel() {
        model = base.getModel();
        IntVar[][] rows = getRows();
        IntVar[][] cols = getCols();
        int n = getN();

        for (int i = 0; i < n; i++) {
            if (leftRowSums[i] > 0) {
                getXSumConstraint(model, rows[i], leftRowSums[i]).post();
            }
            if (topColSums[i] > 0) {
                getXSumConstraint(model, cols[i], topColSums[i]).post();
            }
            if (rightRowSums[i] > 0) {
                getXSumConstraint(model, makeReversedArray(rows[i]), rightRowSums[i]).post();
            }
            if (bottomColSums[i] > 0) {
                getXSumConstraint(model, makeReversedArray(cols[i]), bottomColSums[i]).post();
            }
        }
    }

    @Override
    protected void buildDokeFile(StringBuilder sb) {
        base.buildDokeFile(sb);
        sb.append("XSUMS\n");
        buildRow(sb, leftRowSums);
        buildRow(sb, topColSums);
        buildRow(sb, rightRowSums);
        buildRow(sb, bottomColSums);
        sb.append("END XSUMS\n");
    }
}
