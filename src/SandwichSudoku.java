import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.variables.BoolVar;
import org.chocosolver.solver.variables.IntVar;

import java.util.HashMap;

public class SandwichSudoku extends VariantPuzzle {
    int[] rowSums;
    int[] colSums;

    DokeVarCarrier[] rowSumCarriers;
    DokeVarCarrier[] colSumCarriers;

    DokeVarCarrier topBun;
    DokeVarCarrier bottomBun;

    IntVar topBunVar;
    IntVar bottomBunVar;

    IntVar[] minTotals;
    IntVar[] maxDists;

    IntVar[] maxTotals;
    IntVar[] minDists;

    HashMap<String, IntVar> maxDistVars = new HashMap<>();

    public static class SandwichSudokuBuilder {
        AbstractPuzzle base;
        int[] rowSums;
        int[] colSums;
        DokeVarCarrier topBun = null;
        DokeVarCarrier bottomBun = null;

        public int[] emptySums(int n) {
            int[] sums = new int[n];
            for (int i = 0; i < n; i++) {
                sums[i] = 0;
            }
            return sums;
        }

        public SandwichSudokuBuilder(AbstractPuzzle base) {
            this.base = base;
        }

        public SandwichSudokuBuilder withRowSums(int[] rowSums) {
            this.rowSums = rowSums;
            return this;
        }

        public SandwichSudokuBuilder withColSums(int[] colSums) {
            this.colSums = colSums;
            return this;
        }

        public SandwichSudokuBuilder withTopBun(int topBun) {
            this.topBun = new DokeInt(topBun);
            return this;
        }

        public SandwichSudokuBuilder withBottomBun(int bottomBun) {
            this.bottomBun = new DokeInt(bottomBun);
            return this;
        }

        public SandwichSudokuBuilder withTopBunRange(int lb, int ub) {
            this.topBun = new DokeRangeVar(lb, ub);
            return this;
        }

        public SandwichSudokuBuilder withBottomBunRange(int lb, int ub) {
            this.bottomBun = new DokeRangeVar(lb, ub);
            return this;
        }

        public SandwichSudoku build() {
            int n = base.getN();
            if (rowSums == null) {
                rowSums = emptySums(n);
            }
            if (colSums == null) {
                colSums = emptySums(n);
            }
            if (topBun == null) {
                topBun = new DokeInt(n);
            }
            if (bottomBun == null) {
                bottomBun = new DokeInt(1);
            }
            return new SandwichSudoku(base, rowSums, colSums, topBun, bottomBun);
        }
    }

    private SandwichSudoku(AbstractPuzzle base, int[] rowSums, int[] colSums, DokeVarCarrier topBun, DokeVarCarrier bottomBun) {
        this.base = base;
        this.rowSums = rowSums;
        this.colSums = colSums;
        this.topBun = topBun;
        this.bottomBun = bottomBun;
    }

    private void populateMinDists(Model model, IntVar topBun, IntVar bottomBun, int n) {
        IntVar totalSum = model.intVar(0);
        IntVar totalDist = model.intVar(0);

        maxTotals = new IntVar[n + 1];
        minDists = new IntVar[n + 1];

        maxTotals[0] = totalSum;
        minDists[0] = totalDist;

        for (int i = 1; i <= n; i++) {
            int digit = n - i + 1;
            BoolVar isNotBun = topBun.eq(digit).or(bottomBun.eq(digit)).not().boolVar();
            totalSum = totalSum.add(isNotBun.intVar().mul(digit)).intVar();
            totalDist = totalDist.add(isNotBun.intVar()).intVar();
            maxTotals[i] = totalSum;
            minDists[i] = totalDist;
        }
    }

    private void populateMaxDists(Model model, IntVar topBun, IntVar bottomBun, int n) {
        IntVar totalSum = model.intVar(0);
        IntVar totalDist = model.intVar(0);

        minTotals = new IntVar[n + 1];
        maxDists = new IntVar[n + 1];

        minTotals[0] = totalSum;
        maxDists[0] = totalDist;

        for (int i = 1; i <= n; i++) {
            BoolVar isNotBun = topBun.eq(i).or(bottomBun.eq(i)).not().boolVar();
            totalSum = totalSum.add(isNotBun.intVar().mul(i)).intVar();
            totalDist = totalDist.add(isNotBun.intVar()).intVar();
            minTotals[i] = totalSum;
            maxDists[i] = totalDist;
        }
    }

    private void minDistanceConstraint(Model model, IntVar topBun, IntVar bottomBun, int sum, int n, IntVar distance) {
        String varName = "Min dist ";
        varName += sum;
        IntVar minDistance = model.intVar(varName, 0, n);

        maxDistVars.put(varName, minDistance);

        for (int i = 1; i <= n; i++) {
            maxTotals[i].ge(sum).and(maxTotals[i - 1].lt(sum)).imp(minDistance.eq(minDists[i])).post();
            maxTotals[i].ge(sum).and(maxTotals[i - 1].lt(sum)).imp(distance.ge(minDists[i].add(1))).post();
        }
    }

    private void maxDistanceConstraint(Model model, IntVar topBun, IntVar bottomBun, int sum, int n, IntVar distance) {
        String varName = "Max dist ";
        varName += sum;
        IntVar maxDistance = model.intVar(varName, 0, n);

        maxDistVars.put(varName, maxDistance);

        for (int i = 0; i < n; i++) {
            minTotals[i].le(sum).and(minTotals[i+1].gt(sum)).imp(maxDistance.eq(maxDists[i])).post();
            minTotals[i].le(sum).and(minTotals[i+1].gt(sum)).imp(distance.le(maxDists[i].add(1))).post();
        }

        minTotals[n].le(sum).imp(distance.le(maxDists[n].add(1))).post();
    }

    private Constraint getSandwichConstraint(Model model, IntVar[] row, int sum, IntVar topBun, IntVar bottomBun) {
        int n = row.length;
        IntVar topBunLoc = model.intVar(0, n - 1);
        IntVar bottomBunLoc = model.intVar(0, n - 1);
        for(int i = 0; i < n; i++) {
            model.ifThen(row[i].eq(topBun).decompose(),
                    topBunLoc.eq(i).decompose());
            model.ifThen(row[i].eq(bottomBun).decompose(),
                    bottomBunLoc.eq(i).decompose());
        }
        IntVar start = topBunLoc.min(bottomBunLoc).intVar();
        IntVar end = topBunLoc.max(bottomBunLoc).intVar();
        IntVar[] rowSumVars = new IntVar[n];
        for(int i = 0; i < n; i++) {
            rowSumVars[i] = row[i].mul(start.lt(i).and(end.gt(i)).intVar()).intVar();
        }

        IntVar distance = end.sub(start).intVar();

        {
//            int minSum = 0;
//            int maxNumCells = 0;
//
////            System.out.print("Sum: ");
////            System.out.println(sum);
//            for (int i = 1; i <= n; i++) {
//                if (i == topBun || i == bottomBun) {
//                    continue;
//                }
//                minSum += i;
//                if (minSum > sum) {
//                    distance.le(maxNumCells + 1).post();
//                    break;
//                }
//                maxNumCells++;
//            }

            maxDistanceConstraint(model, topBunVar, bottomBunVar, sum, n, distance);
//            System.out.print("max cells: ");
//            System.out.println(maxNumCells);
//            int maxSum = 0;
//            int minNumCells = 0;
//            for (int i = n; i >= 0; i--) {
//                if (maxSum >= sum) {
//                    distance.ge(minNumCells + 1).post();
//                    break;
//                }
//                if (i == topBun || i == bottomBun) {
//                    continue;
//                }
//                maxSum += i;
//                minNumCells++;
//            }
//            System.out.print("min cells: ");
//            System.out.println(minNumCells);

            minDistanceConstraint(model, topBunVar, bottomBunVar, sum, n, distance);
        }


        return model.sum(rowSumVars, "=", sum);
    }

    @Override
    public void buildModel() {
        model = base.getModel();
        IntVar[][] rows = base.getRows();
        IntVar[][] cols = base.getCols();
        int n = base.getN();

        topBunVar = topBun.getVar(model);
        bottomBunVar = bottomBun.getVar(model);

        populateMaxDists(model, topBunVar, bottomBunVar, n);
        populateMinDists(model, topBunVar, bottomBunVar, n);

        for (int i = 0; i < n; i++) {
            if (rowSums[i] >= 0) {
                getSandwichConstraint(model, rows[i], rowSums[i], topBunVar, bottomBunVar).post();
            }
            if (colSums[i] >= 0) {
                getSandwichConstraint(model, cols[i], colSums[i], topBunVar, bottomBunVar).post();
            }
        }

        int maxSum = 0;
        for (int sum : rowSums) {
            maxSum = Math.max(sum, maxSum);
        }
        for (int sum : colSums) {
            maxSum = Math.max(sum, maxSum);
        }

        int fullRowSum = n * (n + 1) / 2;

        topBunVar.add(bottomBunVar).add(maxSum).le(fullRowSum).post();
    }

    @Override
    protected void buildDokeFile(StringBuilder sb) {
        base.buildDokeFile(sb);
        sb.append("SANDWICH\n");
        buildRow(sb, rowSums);
        buildRow(sb, colSums);
        sb.append("END SANDWICH\n");
    }
}
