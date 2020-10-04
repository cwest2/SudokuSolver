import com.google.ortools.sat.*;

public class XSumSudoku extends VariantPuzzle {

    int[] leftRowSums;
    int[] topColSums;
    int[] rightRowSums;
    int[] bottomColSums;
    Literal[] leftRowConds;
    Literal[] topColConds;
    Literal[] rightRowConds;
    Literal[] bottomColConds;

    public static class XSumSudokuBuilder {
        AbstractPuzzle base;
        int[] leftRowSums = null;
        int[] topColSums = null;
        int[] rightRowSums = null;
        int[] bottomColSums = null;
        Literal[] leftRowConds = null;
        Literal[] topColConds = null;
        Literal[] rightRowConds = null;
        Literal[] bottomColConds = null;

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

        public XSumSudokuBuilder withLeftRowConds(Literal[] leftRowConds) {
            this.leftRowConds = leftRowConds;
            return this;
        }

        public XSumSudokuBuilder withTopColConds(Literal[] topColConds) {
            this.topColConds = topColConds;
            return this;
        }

        public XSumSudokuBuilder withRightRowConds(Literal[] rightRowConds) {
            this.rightRowConds = rightRowConds;
            return this;
        }

        public XSumSudokuBuilder withBottomColConds(Literal[] bottomColConds) {
            this.bottomColConds = bottomColConds;
            return this;
        }

        public XSumSudoku build() {
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
            if (leftRowConds == null) {
                leftRowConds = new IntVar[n];
            }
            if (topColConds == null) {
                topColConds = new IntVar[n];
            }
            if (rightRowConds == null) {
                rightRowConds = new IntVar[n];
            }
            if (bottomColConds == null) {
                bottomColConds = new IntVar[n];
            }
            return new XSumSudoku(base, leftRowSums, topColSums, rightRowSums, bottomColSums,
                    leftRowConds, topColConds, rightRowConds, bottomColConds);
        }
    }

    public XSumSudoku(AbstractPuzzle base, int[] leftRowSums, int[] topColSums, int[] rightRowSums, int[] bottomColSums,
                      Literal[] leftRowConds, Literal[] topColConds, Literal[] rightRowConds, Literal[] bottomColConds) {
        super(base);
        this.leftRowSums = leftRowSums;
        this.topColSums = topColSums;
        this.rightRowSums = rightRowSums;
        this.bottomColSums = bottomColSums;
        this.leftRowConds = leftRowConds;
        this.topColConds = topColConds;
        this.rightRowConds = rightRowConds;
        this.bottomColConds = bottomColConds;
    }

    private Constraint getXSumConstraint(CpModel model, IntVar[] row, int sum, Literal cond) {
        IntVar rowStart = row[0];
        IntVar[] rowSumVars = new IntVar[row.length];
        for (int i = 0; i < row.length; i++) {
            rowSumVars[i] = varMul(row[i], varGt(rowStart, i));
        }
        Constraint constraint = model.addEquality(LinearExpr.sum(rowSumVars), sum);
        if (cond != null) {
            constraint.onlyEnforceIf(cond);
        }
        return constraint;
    }

    @Override
    public void buildModel() {
        model = base.getModel();
        IntVar[][] rows = getRows();
        IntVar[][] cols = getCols();
        int n = getN();

        for (int i = 0; i < n; i++) {
            if (leftRowSums[i] > 0) {
                getXSumConstraint(model, rows[i], leftRowSums[i], leftRowConds[i]);
            }
            if (topColSums[i] > 0) {
                getXSumConstraint(model, cols[i], topColSums[i], topColConds[i]);
            }
            if (rightRowSums[i] > 0) {
                getXSumConstraint(model, makeReversedArray(rows[i]), rightRowSums[i], rightRowConds[i]);
            }
            if (bottomColSums[i] > 0) {
                getXSumConstraint(model, makeReversedArray(cols[i]), bottomColSums[i], bottomColConds[i]);
            }
        }
    }
}
