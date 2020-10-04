import com.google.ortools.constraintsolver.IntVarLocalSearchOperatorTemplate;
import com.google.ortools.sat.*;
import com.google.ortools.util.Domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SandwichSudoku extends VariantPuzzle {
    IntVar[] leftRowSums;
    IntVar[] topColSums;
    IntVar[] rightRowSums;
    IntVar[] bottomColSums;

    Literal[] leftRowConds;
    Literal[] topColConds;
    Literal[] rightRowConds;
    Literal[] bottomColConds;

    IntVar topBun;
    IntVar bottomBun;

    IntVar topBunVar;
    IntVar bottomBunVar;

    IntVar[] minTotals;
    IntVar[] maxDists;

    IntVar[] maxTotals;
    IntVar[] minDists;

    HashMap<String, IntVar> maxDistVars = new HashMap<>();

    public static class SandwichSudokuBuilder {
        AbstractPuzzle base;
        IntVar[] leftRowSums;
        IntVar[] topColSums;
        IntVar[] rightRowSums;
        IntVar[] bottomColSums;

        IntVar topBun = null;
        IntVar bottomBun = null;

        Literal[] leftRowConds;
        Literal[] topColConds;
        Literal[] rightRowConds;
        Literal[] bottomColConds;

        public IntVar[] emptySums(int n) {
            IntVar[] sums = new IntVar[n];
            for (int i = 0; i < n; i++) {
                sums[i] = null;
            }
            return sums;
        }

        public SandwichSudokuBuilder(AbstractPuzzle base) {
            this.base = base;
        }

        public SandwichSudokuBuilder withRowSumSets(long[][] rowSumSets) {
            int n = rowSumSets.length;
            IntVar[] rowSumCarriers = new IntVar[n];
            for (int i = 0; i < n; i++) {
                rowSumCarriers[i] = rowSumSets[i].length > 0 ? base.makeSetVar(rowSumSets[i], "l_sum" + i): null;
            }
            this.leftRowSums = rowSumCarriers;
            return this;
        }

        public SandwichSudokuBuilder withColSumSets(long[][] colSumSets) {
            int n = colSumSets.length;
            IntVar[] colSumCarriers = new IntVar[n];
            for (int i = 0; i < n; i++) {
                colSumCarriers[i] = colSumSets[i].length > 0 ? base.makeSetVar(colSumSets[i], "t_sum" + i) : null;
            }
            this.topColSums = colSumCarriers;
            return this;
        }

        public SandwichSudokuBuilder withLeftRowSums(int[] rowSums) {
            int n = rowSums.length;
            IntVar[] rowSumCarriers = new IntVar[n];
            for (int i = 0; i < n; i++) {
                rowSumCarriers[i] = rowSums[i] >= 0 ? base.makeConstVar(rowSums[i], "l_sum" + i) : null;
            }
            this.leftRowSums = rowSumCarriers;
            return this;
        }

        public SandwichSudokuBuilder withRightRowSums(int[] rowSums) {
            int n = rowSums.length;
            IntVar[] rowSumCarriers = new IntVar[n];
            for (int i = 0; i < n; i++) {
                rowSumCarriers[i] = rowSums[i] >= 0 ? base.makeConstVar(rowSums[i], "r_sum" + i) : null;
            }
            this.rightRowSums = rowSumCarriers;
            return this;
        }

        public SandwichSudokuBuilder withTopColSums(int[] colSums) {
            int n = colSums.length;
            IntVar[] colSumCarriers = new IntVar[n];
            for (int i = 0; i < n; i++) {
                colSumCarriers[i] = colSums[i] >= 0 ? base.makeConstVar(colSums[i], "t_sum" + i) : null;
            }
            this.topColSums = colSumCarriers;
            return this;
        }

        public SandwichSudokuBuilder withBottomColSums(int[] colSums) {
            int n = colSums.length;
            IntVar[] colSumCarriers = new IntVar[n];
            for (int i = 0; i < n; i++) {
                colSumCarriers[i] = colSums[i] >= 0 ? base.makeConstVar(colSums[i], "b_sum" + i) : null;
            }
            this.bottomColSums = colSumCarriers;
            return this;
        }

        public SandwichSudokuBuilder withTopBun(int topBun) {
            this.topBun = base.makeConstVar(topBun, "top_bun");
            return this;
        }

        public SandwichSudokuBuilder withBottomBun(int bottomBun) {
            this.bottomBun = base.makeConstVar(bottomBun, "bottom_bun");
            return this;
        }

        public SandwichSudokuBuilder withTopBunRange(int lb, int ub) {
            this.topBun = base.makeRangeVar(lb, ub, "top_bun");
            return this;
        }

        public SandwichSudokuBuilder withBottomBunRange(int lb, int ub) {
            this.bottomBun = base.makeRangeVar(lb, ub, "bottom_bun");
            return this;
        }

        public SandwichSudokuBuilder withLeftRowConds(Literal[] rowConds) {
            this.leftRowConds = rowConds;
            return this;
        }

        public SandwichSudokuBuilder withRightRowConds(Literal[] rowConds) {
            this.rightRowConds = rowConds;
            return this;
        }

        public SandwichSudokuBuilder withTopColConds(Literal[] colConds) {
            this.topColConds = colConds;
            return this;
        }

        public SandwichSudokuBuilder withBottomColConds(Literal[] colConds) {
            this.bottomColConds = colConds;
            return this;
        }

        public SandwichSudoku build() {
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
            if (topBun == null) {
                topBun = base.makeConstVar(n, "top_bun");
            }
            if (bottomBun == null) {
                bottomBun = base.makeConstVar(1, "bottom_bun");
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
            return new SandwichSudoku(base, leftRowSums, topColSums, rightRowSums, bottomColSums,
                    topBun, bottomBun, leftRowConds, topColConds, rightRowConds, bottomColConds);
        }
    }

    private SandwichSudoku(AbstractPuzzle base, IntVar[] leftRowSums, IntVar[] topColSums, IntVar[] rightRowSums, IntVar[] bottomColSums,
                           IntVar topBun, IntVar bottomBun, Literal[] leftRowConds, Literal[] topColConds, Literal[] rightRowConds, Literal[] bottomColConds) {
        super(base);
        this.leftRowSums = leftRowSums;
        this.topColSums = topColSums;
        this.rightRowSums = rightRowSums;
        this.bottomColSums = bottomColSums;
        this.topBun = topBun;
        this.bottomBun = bottomBun;
        this.leftRowConds = leftRowConds;
        this.topColConds = topColConds;
        this.rightRowConds = rightRowConds;
        this.bottomColConds = bottomColConds;
    }

    private void populateMinDists(CpModel model, IntVar topBun, IntVar bottomBun, int n) {
        IntVar totalSum = model.newIntVarFromDomain(new Domain(0), "totalSum0");
        IntVar totalDist = model.newIntVarFromDomain(new Domain(0), "totalDist0");

        maxTotals = new IntVar[n + 1];
        minDists = new IntVar[n + 1];

        maxTotals[0] = totalSum;
        minDists[0] = totalDist;

        int maxSum = n * (n + 1) / 2;

        for (int i = 1; i <= n; i++) {
            int digit = n - i + 1;

            IntVar isTopBun = varEquals(topBun, digit);

            IntVar isBottomBun = varEquals(bottomBun, digit);

            IntVar isBun = varOr(isTopBun, isBottomBun);

            IntVar isNotBun = varNot(isBun);

            IntVar digitVar = model.newIntVarFromDomain(new Domain(digit), "digitVar" + i);

            IntVar scaledVar = model.newIntVar(0, n, "scaledVar" + i);
            model.addProductEquality(scaledVar, new IntVar[] {digitVar, isNotBun});

            IntVar newTotalSum = model.newIntVar(0, maxSum, "totalSum" + i);
            model.addEquality(newTotalSum, LinearExpr.sum(new IntVar[] {scaledVar, totalSum}));

            IntVar newTotalDist = model.newIntVar(0, n, "totalDist" + i);
            model.addEquality(newTotalDist, LinearExpr.sum(new IntVar[] {totalDist, isNotBun}));

            totalSum = newTotalSum;
            totalDist = newTotalDist;
            maxTotals[i] = totalSum;
            minDists[i] = totalDist;
        }
    }

    private void populateMaxDists(CpModel model, IntVar topBun, IntVar bottomBun, int n) {
        IntVar totalSum = model.newIntVarFromDomain(new Domain(0), "totalSum0");
        IntVar totalDist = model.newIntVarFromDomain(new Domain(0), "totalDist0");

        minTotals = new IntVar[n + 1];
        maxDists = new IntVar[n + 1];

        minTotals[0] = totalSum;
        maxDists[0] = totalDist;

        int maxSum = n * (n + 1) / 2;

        for (int i = 1; i <= n; i++) {
            int digit = i;

            IntVar isTopBun = varEquals(topBun, digit);

            IntVar isBottomBun = varEquals(bottomBun, digit);

            IntVar isBun = varOr(isTopBun, isBottomBun);

            IntVar isNotBun = varNot(isBun);

            IntVar digitVar = model.newIntVarFromDomain(new Domain(digit), "digitVar" + i);

            IntVar scaledVar = model.newIntVar(0, n, "scaledVar" + i);
            model.addProductEquality(scaledVar, new IntVar[] {digitVar, isNotBun});

            IntVar newTotalSum = model.newIntVar(0, maxSum, "totalSum" + i);
            model.addEquality(newTotalSum, LinearExpr.sum(new IntVar[] {scaledVar, totalSum}));

            IntVar newTotalDist = model.newIntVar(0, n, "totalDist" + i);
            model.addEquality(newTotalDist, LinearExpr.sum(new IntVar[] {totalDist, isNotBun}));

            totalSum = newTotalSum;
            totalDist = newTotalDist;
            minTotals[i] = totalSum;
            maxDists[i] = totalDist;
        }
    }

    private List<Constraint> minDistanceConstraint(CpModel model, IntVar sum, int n, IntVar distance) {
        List<Constraint> constraints = new ArrayList<>();

//        constraints.add(maxTotals[0].gt(sum).and(sum.ne(0)).not().decompose());

        for (int i = 1; i <= n; i++) {


            IntVar gtTotalI = varGe(maxTotals[i], sum);

            IntVar ltTotalIP1 = varLt(maxTotals[i - 1], sum);

            IntVar inBucketI = varAnd(gtTotalI, ltTotalIP1);

            IntVar shiftedDist = model.newIntVar(1, n, "shiftedDist" + i);
            model.addEqualityWithOffset(shiftedDist, minDists[i], 1);

            Constraint minDistConstraint = model.addGreaterOrEqual(distance, shiftedDist);

            minDistConstraint.onlyEnforceIf(inBucketI);

            constraints.add(minDistConstraint);
        }

        return constraints;
    }

    private List<Constraint> maxDistanceConstraint(CpModel model, IntVar sum, int n, IntVar distance) {
        List<Constraint> constraints = new ArrayList<>();

//        constraints.add(maxTotals[0].gt(sum).and(sum.ne(0)).not().decompose());

        for (int i = 0; i < n; i++) {
            IntVar leTotalI = varLe(minTotals[i], sum);
            IntVar gtTotalIp1 = varGt(minTotals[i + 1], sum);
            IntVar inBucket1 = varAnd(leTotalI, gtTotalIp1);

            IntVar shiftedDist = model.newIntVar(1, n, "shiftedDist" + i);
            model.addEqualityWithOffset(shiftedDist, maxDists[i], 1);

            Constraint maxDistanceConstraint = model.addLessOrEqual(distance, shiftedDist);
            maxDistanceConstraint.onlyEnforceIf(inBucket1);

            constraints.add(maxDistanceConstraint);
        }

        return constraints;
    }

    private List<Constraint> getSandwichConstraint(CpModel model, IntVar[] row, IntVar sum, IntVar topBun, IntVar bottomBun) {
        List<Constraint> constraints = new ArrayList<>();

        int n = row.length;
        IntVar topBunLoc = model.newIntVar(0, n - 1, sum.getName() + "_topBunLoc");
        IntVar bottomBunLoc = model.newIntVar(0, n - 1, sum.getName() + "_bottomBunLoc");

        model.addElement(topBunLoc, row, topBun);
        model.addElement(bottomBunLoc, row, bottomBun);

        IntVar start = model.newIntVar(0, n - 1, sum + "_start");
        model.addMinEquality(start, new IntVar[] {topBunLoc, bottomBunLoc});

        IntVar end = model.newIntVar(0, n - 1, sum + "_end");
        model.addMaxEquality(end, new IntVar[] {topBunLoc, bottomBunLoc});

        IntVar[] rowSumVars = new IntVar[n];
        for(int i = 0; i < n; i++) {
            IntVar iVar = model.newIntVarFromDomain(new Domain(i), "iVar" + i);

            IntVar startLtI = varLt(start, iVar);

            IntVar endGtI = varLt(iVar, end);

            IntVar inSandwich = varAnd(startLtI, endGtI);

            IntVar rowSumVar = model.newIntVar(0, n, sum.getName() + "_rowSumVar" + i);
            model.addProductEquality(rowSumVar, new IntVar[] {inSandwich, row[i]});

            rowSumVars[i] = rowSumVar;
        }

        constraints.add(model.addEquality(sum, LinearExpr.sum(rowSumVars)));

        IntVar negStart = model.newIntVar(-n + 1, -0, sum.getName() + "_negStart");
        model.addAbsEquality(start, negStart);

        IntVar distance = model.newIntVar(1, n - 1, sum.getName() + "_distance");
        model.addEquality(distance, LinearExpr.sum(new IntVar[] {end, negStart}));

//        constraints.addAll(maxDistanceConstraint(model, sum, n, distance));
//        constraints.addAll(minDistanceConstraint(model, sum, n, distance));

        return constraints;
    }

    @Override
    public void buildModel() {
        model = base.getModel();
        IntVar[][] rows = base.getRows();
        IntVar[][] cols = base.getCols();
        int n = base.getN();

        topBunVar = topBun;
        bottomBunVar = bottomBun;

        populateMaxDists(model, topBunVar, bottomBunVar, n);
        populateMinDists(model, topBunVar, bottomBunVar, n);

        for (int i = 0; i < n; i++) {
            if (leftRowSums[i] != null) {
                for (Constraint constraint : getSandwichConstraint(model, rows[i], leftRowSums[i], topBunVar, bottomBunVar)) {
                    if (leftRowConds[i] != null) {
                        constraint.onlyEnforceIf(leftRowConds[i]);
                    }
                }
            }

            if (rightRowSums[i] != null) {
                for (Constraint constraint : getSandwichConstraint(model, rows[i], rightRowSums[i], topBunVar, bottomBunVar)) {
                    if (rightRowConds[i] != null) {
                        constraint.onlyEnforceIf(rightRowConds[i]);
                    }
                }
            }

            if (topColSums[i] != null) {
                for (Constraint constraint : getSandwichConstraint(model, cols[i], topColSums[i], topBunVar, bottomBunVar)) {
                    if (topColConds[i] != null) {
                        constraint.onlyEnforceIf(topColConds[i]);
                    }
                }
            }

            if (bottomColSums[i] != null) {
                for (Constraint constraint : getSandwichConstraint(model, cols[i], bottomColSums[i], topBunVar, bottomBunVar)) {
                    if (bottomColConds[i] != null) {
                        constraint.onlyEnforceIf(bottomColConds[i]);
                    }
                }
            }
        }

        int fullRowSum = n * (n + 1) / 2;

        IntVar maxSumVar = model.newIntVar(0, fullRowSum, "maxSumVar");

        List<IntVar> allSums = new ArrayList<>();
        for (IntVar sum : leftRowSums) {
            if (sum != null) {
                allSums.add(sum);
            }
        }
        for (IntVar sum : topColSums) {
            if (sum != null) {
                allSums.add(sum);
            }
        }
        for (IntVar sum : rightRowSums) {
            if (sum != null) {
                allSums.add(sum);
            }
        }
        for (IntVar sum : bottomColSums) {
            if (sum != null) {
                allSums.add(sum);
            }
        }

        IntVar[] allSumsArray = new IntVar[allSums.size()];

        allSums.toArray(allSumsArray);

//        int maxSum = 0;
//        for (int sum : rowSums) {
//            maxSum = Math.max(sum, maxSum);
//        }
//        for (int sum : colSums) {
//            maxSum = Math.max(sum, maxSum);
//        }

        model.addMaxEquality(maxSumVar, allSumsArray);

        model.addLessOrEqual(LinearExpr.sum(new IntVar[] {topBunVar, bottomBunVar, maxSumVar}), fullRowSum);
    }
}
