import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.BoolVar;
import org.chocosolver.solver.variables.IntVar;

public class BattlefieldSudoku extends VariantPuzzle{
    int[] rowSums;
    int[] colSums;

    public static class BattlefieldSudokuBuilder {
        AbstractPuzzle base;
        int[] rowSums;
        int[] colSums;

        public BattlefieldSudokuBuilder(AbstractPuzzle base) {
            this.base = base;
        }

        public BattlefieldSudokuBuilder withRowSums(int[] rowSums) {
            this.rowSums = rowSums;
            return this;
        }

        public BattlefieldSudokuBuilder withColSums(int[] colSums) {
            this.colSums = colSums;
            return this;
        }

        public BattlefieldSudoku build() {
            return new BattlefieldSudoku(base, rowSums, colSums);
        }
    }

    private BattlefieldSudoku(AbstractPuzzle base, int[] rowSums, int[] colSums) {
        this.base = base;
        this.rowSums = rowSums;
        this.colSums = colSums;
    }

    private void alternateAddBattlefieldConstraint(int sum, IntVar[] row, int n, Model model) {
        IntVar leftArmy = row[0];
        IntVar rightArmy = row[n - 1].intVar();
        if (sum == 0) {
            leftArmy.add(rightArmy).eq(n).post();
        } else {
            IntVar[] rowSumVars = new IntVar[n * 2];
            IntVar[] revRow = makeReversedArray(row);
            for (int i = 0; i < row.length; i++) {
                rowSumVars[i] = row[i].mul(leftArmy.gt(i).intVar()).intVar();
                rowSumVars[n + i] = revRow[i].mul(rightArmy.gt(i).intVar()).intVar();
            }
            IntVar fullSum = model.intVar(0, n * (n + 1));
            model.sum(rowSumVars, "=", fullSum).post();
            fullSum.sub((n * (n + 1)) / 2).abs().eq(sum).post();
        }

        IntVar regionSize = leftArmy.add(rightArmy).sub(n).abs().intVar();

        int minCells = 0;
        int maxSum = 0;
        int i = n;
        while (maxSum < sum) {
            maxSum += i;
            minCells++;
            i--;
        }
        regionSize.ge(minCells);

        int maxCells = 0;
        int minSum = 0;
        i = 1;
        while (minSum < sum) {
            minSum += i;
            i++;
            maxCells++;
        }
        regionSize.le(maxCells);
    }

    private void addBattlefieldConstraint(int sum, IntVar[] row, int n, Model model) {
        IntVar leftArmy = row[0];
        IntVar rightArmy = row[n - 1].intVar();
        if (sum == 0) {
            leftArmy.add(rightArmy).eq(n).post();
        } else {
            IntVar[] sumVars = new IntVar[n];
            for (int i = 0; i < n; i++) {
                BoolVar overlapCondition = leftArmy.gt(i).and(rightArmy.gt(n - i - 1)).boolVar();
                BoolVar betweenCondition = leftArmy.le(i).and(rightArmy.le(n - i - 1)).boolVar();
                sumVars[i] = row[i].mul(overlapCondition.or(betweenCondition).intVar()).intVar();
            }
            model.sum(sumVars, "=", sum).post();

            IntVar regionSize = leftArmy.add(rightArmy).sub(n).abs().intVar();

            int minCells = 0;
            int maxSum = 0;
            int i = n;
            while (maxSum < sum) {
                maxSum += i;
                minCells++;
                i--;
            }
            regionSize.ge(minCells);

            int maxCells = 0;
            int minSum = 0;
            i = 1;
            while (minSum < sum) {
                minSum += i;
                i++;
                maxCells++;
            }
            regionSize.le(maxCells);
        }

    }

    private void addBattlefieldConstraints(int[] sums, IntVar[][] grid, int n, Model model) {
        for (int i = 0; i < n; i++) {
            if (sums[i] > 0) {
                addBattlefieldConstraint(sums[i], grid[i], n, model);
            }
        }
    }

    @Override
    public void buildModel() {
        model = base.getModel();
        IntVar[][] rows = getRows();
        IntVar[][] cols = getCols();
        int n = getN();

        addBattlefieldConstraints(rowSums, rows, n, model);
        addBattlefieldConstraints(colSums, cols, n, model);
    }

    @Override
    protected void buildDokeFile(StringBuilder sb) {

    }
}
