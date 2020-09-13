import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.variables.IntVar;

public class SkyscraperSudoku extends VariantPuzzle {

    int[] leftRowSums;
    int[] topColSums;
    int[] rightRowSums;
    int[] bottomColSums;

    private Constraint getSkyscraperConstraint(Model model, IntVar[] row, int seen) {
        IntVar tallest = model.intVar(0);
        IntVar[] rowSeenVars = new IntVar[row.length];
        for (int i = 0; i < row.length; i++) {
            rowSeenVars[i] = tallest.lt(row[i]).intVar();
            tallest = tallest.max(row[i]).intVar();
        }
        return model.sum(rowSeenVars, "=", seen);
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
        this.base = base;
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
                getSkyscraperConstraint(model, rows[i], leftRowSums[i]).post();
            }
            if (topColSums[i] > 0) {
                getSkyscraperConstraint(model, cols[i], topColSums[i]).post();
            }
            if (rightRowSums[i] > 0) {
                getSkyscraperConstraint(model, makeReversedArray(rows[i]), rightRowSums[i]).post();
            }
            if (bottomColSums[i] > 0) {
                getSkyscraperConstraint(model, makeReversedArray(cols[i]), bottomColSums[i]).post();
            }
        }
    }

    @Override
    protected void buildDokeFile(StringBuilder sb) {
        base.buildDokeFile(sb);
        sb.append("SKYSCRAPER\n");
        buildRow(sb, leftRowSums);
        buildRow(sb, topColSums);
        buildRow(sb, rightRowSums);
        buildRow(sb, bottomColSums);
        sb.append("END SKYSCRAPER\n");
    }
}
