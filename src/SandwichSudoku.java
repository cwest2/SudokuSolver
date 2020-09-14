import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.variables.BoolVar;
import org.chocosolver.solver.variables.IntVar;

import java.util.HashMap;

public class SandwichSudoku extends VariantPuzzle {
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
        DokeVarCarrier[] rowSumCarriers;
        DokeVarCarrier[] colSumCarriers;
        DokeVarCarrier topBun = null;
        DokeVarCarrier bottomBun = null;

        public DokeVarCarrier[] emptySums(int n) {
            DokeVarCarrier[] sums = new DokeVarCarrier[n];
            for (int i = 0; i < n; i++) {
                sums[i] = new DokeNull();
            }
            return sums;
        }

        public SandwichSudokuBuilder(AbstractPuzzle base) {
            this.base = base;
        }

        public SandwichSudokuBuilder withRowSumSets(int[][] rowSumSets) {
            int n = rowSumSets.length;
            DokeVarCarrier[] rowSumCarriers = new DokeVarCarrier[n];
            for (int i = 0; i < n; i++) {
                rowSumCarriers[i] = rowSumSets[i].length > 0 ? new DokeSetVar(rowSumSets[i]) : new DokeNull();
            }
            this.rowSumCarriers = rowSumCarriers;
            return this;
        }

        public SandwichSudokuBuilder withColSumSets(int[][] colSumSets) {
            int n = colSumSets.length;
            DokeVarCarrier[] colSumCarriers = new DokeVarCarrier[n];
            for (int i = 0; i < n; i++) {
                colSumCarriers[i] = colSumSets[i].length > 0 ? new DokeSetVar(colSumSets[i]) : new DokeNull();
            }
            this.colSumCarriers = colSumCarriers;
            return this;
        }

        public SandwichSudokuBuilder withRowSums(int[] rowSums) {
            int n = rowSums.length;
            DokeVarCarrier[] rowSumCarriers = new DokeVarCarrier[n];
            for (int i = 0; i < n; i++) {
                rowSumCarriers[i] = rowSums[i] >= 0 ? new DokeInt(rowSums[i]) : new DokeNull();
            }
            this.rowSumCarriers = rowSumCarriers;
            return this;
        }

        public SandwichSudokuBuilder withColSums(int[] colSums) {
            int n = colSums.length;
            DokeVarCarrier[] colSumCarriers = new DokeVarCarrier[n];
            for (int i = 0; i < n; i++) {
                colSumCarriers[i] = colSums[i] >= 0 ? new DokeInt(colSums[i]) : new DokeNull();
            }
            this.colSumCarriers = colSumCarriers;
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
            if (rowSumCarriers == null) {
                rowSumCarriers = emptySums(n);
            }
            if (colSumCarriers == null) {
                colSumCarriers = emptySums(n);
            }
            if (topBun == null) {
                topBun = new DokeInt(n);
            }
            if (bottomBun == null) {
                bottomBun = new DokeInt(1);
            }
            return new SandwichSudoku(base, rowSumCarriers, colSumCarriers, topBun, bottomBun);
        }
    }

    private SandwichSudoku(AbstractPuzzle base, DokeVarCarrier[] rowSumCarriers, DokeVarCarrier[] colSumCarriers, DokeVarCarrier topBun, DokeVarCarrier bottomBun) {
        this.base = base;
        this.rowSumCarriers = rowSumCarriers;
        this.colSumCarriers = colSumCarriers;
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

    private void minDistanceConstraint(Model model, IntVar sum, int n, IntVar distance) {
        String varName = "Min dist ";
        varName += sum;
        IntVar minDistance = model.intVar(varName, 0, n);

        maxDistVars.put(varName, minDistance);

        for (int i = 1; i <= n; i++) {
            maxTotals[i].ge(sum).and(maxTotals[i - 1].lt(sum)).imp(minDistance.eq(minDists[i])).post();
            maxTotals[i].ge(sum).and(maxTotals[i - 1].lt(sum)).imp(distance.ge(minDists[i].add(1))).post();
        }
    }

    private void maxDistanceConstraint(Model model, IntVar sum, int n, IntVar distance) {
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

    private Constraint getSandwichConstraint(Model model, IntVar[] row, IntVar sum, IntVar topBun, IntVar bottomBun) {
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

        maxDistanceConstraint(model, sum, n, distance);
        minDistanceConstraint(model, sum, n, distance);

        return model.sum(rowSumVars, "=", sum);
    }

    @Override
    public void buildModel() {
        model = base.getModel();
        IntVar[][] rows = base.getRows();
        IntVar[][] cols = base.getCols();
        int n = base.getN();

        IntVar[] rowSumVars = new IntVar[n];
        IntVar[] colSumVars = new IntVar[n];
        for (int i = 0; i < n; i++) {
            rowSumVars[i] = rowSumCarriers[i].getVar(model);
            colSumVars[i] = colSumCarriers[i].getVar(model);
        }

        topBunVar = topBun.getVar(model);
        bottomBunVar = bottomBun.getVar(model);

        populateMaxDists(model, topBunVar, bottomBunVar, n);
        populateMinDists(model, topBunVar, bottomBunVar, n);

        for (int i = 0; i < n; i++) {
            if (rowSumVars[i] != null) {
                getSandwichConstraint(model, rows[i], rowSumVars[i], topBunVar, bottomBunVar).post();
            }
            if (colSumVars[i] != null) {
                getSandwichConstraint(model, cols[i], colSumVars[i], topBunVar, bottomBunVar).post();
            }
        }

        IntVar maxSumVar = model.intVar(0);
        for (IntVar sum : rowSumVars) {
            maxSumVar = sum != null ? maxSumVar.max(sum).intVar() : maxSumVar;
        }
        for (IntVar sum : colSumVars) {
            maxSumVar = sum != null ? maxSumVar.max(sum).intVar() : maxSumVar;
        }

//        int maxSum = 0;
//        for (int sum : rowSums) {
//            maxSum = Math.max(sum, maxSum);
//        }
//        for (int sum : colSums) {
//            maxSum = Math.max(sum, maxSum);
//        }

        int fullRowSum = n * (n + 1) / 2;

        topBunVar.add(bottomBunVar).add(maxSumVar).le(fullRowSum).post();
    }

    @Override
    protected void buildDokeFile(StringBuilder sb) {
        throw new IllegalStateException("Not implemented");
    }
}
