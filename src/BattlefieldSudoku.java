import com.google.ortools.sat.*;

import java.util.ArrayList;
import java.util.List;

public class BattlefieldSudoku extends VariantPuzzle{
    int[] leftRowSums;
    int[] topColSums;
    int[] rightRowSums;
    int[] bottomColSums;

    Literal[] leftRowConds;
    Literal[] topColConds;
    Literal[] rightRowConds;
    Literal[] bottomColConds;

    public static class BattlefieldSudokuBuilder {
        AbstractPuzzle base;
        int[] leftRowSums;
        int[] topColSums;
        int[] rightRowSums;
        int[] bottomColSums;

        Literal[] leftRowConds;
        Literal[] topColConds;
        Literal[] rightRowConds;
        Literal[] bottomColConds;

        public BattlefieldSudokuBuilder(AbstractPuzzle base) {
            this.base = base;
        }

        public BattlefieldSudokuBuilder withLeftRowSums(int[] rowSums) {
            this.leftRowSums = rowSums;
            return this;
        }

        public BattlefieldSudokuBuilder withTopColSums(int[] colSums) {
            this.topColSums = colSums;
            return this;
        }

        public BattlefieldSudokuBuilder withRightRowSums(int[] rowSums) {
            this.rightRowSums = rowSums;
            return this;
        }

        public BattlefieldSudokuBuilder withBottomColSums(int[] colSums) {
            this.bottomColSums = colSums;
            return this;
        }

        public BattlefieldSudokuBuilder withLeftRowConds(Literal[] conds) {
            this.leftRowConds = conds;
            return this;
        }

        public BattlefieldSudokuBuilder withTopColConds(Literal[] conds) {
            this.topColConds = conds;
            return this;
        }

        public BattlefieldSudokuBuilder withRightRowConds(Literal[] conds) {
            this.rightRowConds = conds;
            return this;
        }

        public BattlefieldSudokuBuilder withBottomColConds(Literal[] conds) {
            this.bottomColConds = conds;
            return this;
        }

        public BattlefieldSudoku build() {
            int n = base.getN();
            if (leftRowSums == null) {
                leftRowSums = new int[n];
            }
            if (topColSums == null) {
                topColSums = new int[n];
            }
            if (rightRowSums == null) {
                rightRowSums = new int[n];
            }
            if (bottomColSums == null) {
                bottomColSums = new int[n];
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
            return new BattlefieldSudoku(base, leftRowSums, topColSums, rightRowSums, bottomColSums,
                    leftRowConds, topColConds, rightRowConds, bottomColConds);
        }
    }

    private BattlefieldSudoku(AbstractPuzzle base, int[] leftRowSums, int[] topColSums, int[] rightRowSums, int[] bottomColSums,
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

//    private void alternateAddBattlefieldConstraint(int sum, IntVar[] row, int n, CpModel model) {
//        IntVar leftArmy = row[0];
//        IntVar rightArmy = row[n - 1];
//        if (sum == 0) {
//            leftArmy.add(rightArmy).eq(n).post();
//        } else {
//            IntVar[] rowSumVars = new IntVar[n * 2];
//            IntVar[] revRow = makeReversedArray(row);
//            for (int i = 0; i < row.length; i++) {
//                rowSumVars[i] = row[i].mul(leftArmy.gt(i).intVar()).intVar();
//                rowSumVars[n + i] = revRow[i].mul(rightArmy.gt(i).intVar()).intVar();
//            }
//            IntVar fullSum = model.intVar(0, n * (n + 1));
//            model.sum(rowSumVars, "=", fullSum).post();
//            fullSum.sub((n * (n + 1)) / 2).abs().eq(sum).post();
//        }
//
//        IntVar regionSize = leftArmy.add(rightArmy).sub(n).abs().intVar();
//
//        int minCells = 0;
//        int maxSum = 0;
//        int i = n;
//        while (maxSum < sum) {
//            maxSum += i;
//            minCells++;
//            i--;
//        }
//        regionSize.ge(minCells);
//
//        int maxCells = 0;
//        int minSum = 0;
//        i = 1;
//        while (minSum < sum) {
//            minSum += i;
//            i++;
//            maxCells++;
//        }
//        regionSize.le(maxCells);
//    }

    private List<Constraint> addBattlefieldConstraint(int sum, IntVar[] row, int n, CpModel model) {
        List<Constraint> constraints = new ArrayList<>();

        IntVar leftArmy = row[0];
        IntVar rightArmy = row[n - 1];
        if (sum == 0) {
            constraints.add(enforceBool(varEquals(varAdd(leftArmy, rightArmy), n)));
        } else {
            IntVar[] sumVars = new IntVar[n];
            for (int i = 0; i < n; i++) {
                IntVar overlapCondition = varAnd(varGt(leftArmy, i), varGt(rightArmy, n - i - 1));
                IntVar betweenCondition = varAnd(varLe(leftArmy, i), varLe(rightArmy, n - i - 1));
                sumVars[i] = varMul(row[i], varOr(overlapCondition, betweenCondition));
            }
            constraints.add(model.addEquality(LinearExpr.sum(sumVars), sum));

//            IntVar regionSize = leftArmy.add(rightArmy).sub(n).abs().intVar();
//
//            int minCells = 0;
//            int maxSum = 0;
//            int i = n;
//            while (maxSum < sum) {
//                maxSum += i;
//                minCells++;
//                i--;
//            }
//            regionSize.ge(minCells).post();
//
//            int maxCells = 0;
//            int minSum = 0;
//            i = 1;
//            while (minSum < sum) {
//                minSum += i;
//                i++;
//                maxCells++;
//            }
//            regionSize.le(maxCells).post();
        }
        return constraints;
    }

    private void addBattlefieldConstraints(int[] sums, Literal[] conds, IntVar[][] grid, int n, CpModel model) {
        for (int i = 0; i < n; i++) {
            if (sums[i] > 0) {
                for (Constraint constraint : addBattlefieldConstraint(sums[i], grid[i], n, model)) {
                    if (conds[i] != null) {
                        constraint.onlyEnforceIf(conds[i]);
                    }
                }
            }
        }
    }

    @Override
    public void buildModel() {
        model = base.getModel();
        IntVar[][] rows = getRows();
        IntVar[][] cols = getCols();
        int n = getN();

        addBattlefieldConstraints(leftRowSums, leftRowConds, rows, n, model);
        addBattlefieldConstraints(topColSums, topColConds, cols, n, model);
        addBattlefieldConstraints(rightRowSums, rightRowConds, rows, n, model);
        addBattlefieldConstraints(bottomColSums, bottomColConds, cols, n, model);
    }
}
