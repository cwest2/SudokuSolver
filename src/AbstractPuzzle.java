import com.google.ortools.sat.*;
import com.google.ortools.util.Domain;

import javax.sound.sampled.Line;

public abstract class AbstractPuzzle {
    CpModel model;

    public static Domain dokeDomain = Domain.fromFlatIntervals(new long[] {-10000, 10000});

    public IntVar makeRangeVar(long lb, long ub, String s) {
        CpModel model = getModel();
        return model.newIntVar(lb, ub, s);
    }

    public IntVar makeSetVar(long[] vals, String s) {
        CpModel model = getModel();
        return model.newIntVarFromDomain(Domain.fromValues(vals), s);
    }

    public IntVar makeConstVar(long val, String s) {
        CpModel model = getModel();
        return model.newIntVarFromDomain(new Domain(val), s);
    }

    public IntVar makeBoolVar(String s) {
        CpModel model = getModel();
        return model.newBoolVar(s);
    }

    public IntVar[] makeBoolVarArray(boolean[] populatedSlots, String name) {
        IntVar[] boolArr = new IntVar[populatedSlots.length];
        for (int i = 0; i < populatedSlots.length; i++) {
            if (populatedSlots[i]) {
                boolArr[i] = makeBoolVar(name + i);
            }
        }
        return boolArr;
    }

    public IntVar[] assignVarsFromValues(long[] set) {
        CpModel model = getModel();
        int n = set.length;
        Domain setDomain = Domain.fromValues(set);
        IntVar[] vars = new IntVar[n];
        IntVar[] locs = new IntVar[n];
        for (int i = 0; i < n; i++) {
            locs[i] = model.newIntVar(0, n - 1, "locs" + i);
            vars[i] = model.newIntVarFromDomain(setDomain, "vars" + i);
            model.addElement(locs[i], set, vars[i]);
        }
        model.addAllDifferent(locs);
        return vars;
    }

    public abstract void buildModel();

    public CpModel getModel() {
        if (model == null) {
            buildModel();
        }
        return model;
    }

    public abstract int getN();

    public abstract IntVar[][] getRows();

    public abstract IntVar[][] getCols();

    public void printSolution() {
        buildModel();
        CpSolver solver = new CpSolver();
        CpSolverStatus status = solver.solve(model);
        if (status != CpSolverStatus.FEASIBLE && status != CpSolverStatus.OPTIMAL) {
            System.out.println("No solution found!");
            return;
        }

        StringBuilder st = new StringBuilder();
        st.append("\t");

        int n = getN();
        IntVar[][] rows = getRows();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                st.append(solver.value(rows[i][j])).append("\t");
            }
            st.append("\n\t");
        }

        System.out.println(st.toString());
    }


    public long[][] solve() {
        buildModel();
        CpSolver solver = new CpSolver();
        CpSolverStatus status = solver.solve(model);
        if (status != CpSolverStatus.FEASIBLE && status != CpSolverStatus.OPTIMAL) {
            throw new RuntimeException("No solution found!");
        }

        int n = getN();
        IntVar[][] rows = getRows();
        long[][] solution = new long[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                solution[i][j] = solver.value(rows[i][j]);
            }
        }

        return solution;
    }

    protected IntVar[] makeReversedArray(IntVar[] row) {
        int n = row.length;
        IntVar[] rev = new IntVar[n];
        for (int i = 0; i < n; i++) {
            rev[n - i - 1] = row[i];
        }
        return rev;
    }

    protected IntVar[][] makeRowReversedGrid(IntVar[][] grid) {
        int n = grid.length;
        IntVar[][] rev = new IntVar[n][];
        for (int i = 0; i < n; i++) {
            rev[i] = makeReversedArray(grid[i]);
        }
        return rev;
    }

    protected IntVar[][] makeColReversedGrid(IntVar[][] grid) {
        int n = grid.length;
        IntVar[][] rev = new IntVar[n][];
        for (int i = 0; i < n; i++) {
            rev[n - i - 1] = grid[i];
        }
        return rev;
    }

    public IntVar varEquals(IntVar var1, IntVar var2) {
        CpModel model = getModel();
        IntVar equal = model.newBoolVar("eq");
        model.addEquality(var1, var2).onlyEnforceIf(equal);
        model.addDifferent(var1, var2).onlyEnforceIf(equal.not());
        return equal;
    }

    public IntVar varEquals(IntVar var, int val) {
        CpModel model = getModel();
        IntVar equal = model.newBoolVar("eq");
        model.addEquality(var, val).onlyEnforceIf(equal);
        model.addDifferent(var, val).onlyEnforceIf(equal.not());
        return equal;
    }

    public IntVar varOr(IntVar var1, IntVar var2) {
        CpModel model = getModel();
        IntVar or = model.newBoolVar("or");
        model.addBoolOr(new IntVar[] {var1, var2}).onlyEnforceIf(or);
        model.addBoolAnd(new Literal[] {var1.not(), var2.not()}).onlyEnforceIf(or.not());
        return or;
    }

    public IntVar varAnd(IntVar var1, IntVar var2) {
        CpModel model = getModel();
        IntVar and = model.newBoolVar("and");
        model.addBoolAnd(new IntVar[] {var1, var2}).onlyEnforceIf(and);
        model.addBoolOr(new Literal[] {var1.not(), var2.not()}).onlyEnforceIf(and.not());
        return and;
    }

    public IntVar varNot(IntVar var) {
        CpModel model = getModel();
        IntVar not = model.newBoolVar("not");
        model.addDifferent(var, not);
        return not;
    }

    public IntVar varGt(IntVar var1, IntVar var2) {
        CpModel model = getModel();
        IntVar gt = model.newBoolVar("gt");
        model.addGreaterThan(var1, var2).onlyEnforceIf(gt);
        model.addLessOrEqual(var1, var2).onlyEnforceIf(gt.not());
        return gt;
    }

    public IntVar varGt(IntVar var, int val) {
        return varGt(var, model.newIntVarFromDomain(new Domain(val), "" + val));
    }

    public IntVar varGe(IntVar var1, IntVar var2) {
        CpModel model = getModel();
        IntVar ge = model.newBoolVar("ge");
        model.addGreaterOrEqual(var1, var2).onlyEnforceIf(ge);
        model.addLessThan(var1, var2).onlyEnforceIf(ge.not());
        return ge;
    }

    public IntVar varLt(IntVar var1, IntVar var2) {
        CpModel model = getModel();
        IntVar lt = model.newBoolVar("lt");
        model.addLessThan(var1, var2).onlyEnforceIf(lt);
        model.addGreaterOrEqual(var1, var2).onlyEnforceIf(lt.not());
        return lt;
    }

    public IntVar varLe(IntVar var1, IntVar var2) {
        CpModel model = getModel();
        IntVar le = model.newBoolVar("le");
        model.addLessOrEqual(var1, var2).onlyEnforceIf(le);
        model.addGreaterThan(var1, var2).onlyEnforceIf(le.not());
        return le;
    }

    public IntVar varLe(IntVar var, int val) {
        return varLe(var, model.newIntVarFromDomain(new Domain(val), "" + val));
    }

    public IntVar varAdd(IntVar var1, IntVar var2) {
        CpModel model = getModel();
        IntVar sum = model.newIntVarFromDomain(var1.getDomain().additionWith(var2.getDomain()),
                var1.getName() + "+" + var2.getName());
        model.addEquality(LinearExpr.sum(new IntVar[] {var1, var2}), sum);
        return sum;
    }

    public IntVar varAdd(IntVar var, int val) {
        CpModel model = getModel();
        IntVar sum = model.newIntVarFromDomain(dokeDomain,
                var.getName() + "+" + val);
        model.addEqualityWithOffset(var, sum, val);
        return sum;
    }

    public IntVar varNegate(IntVar var) {
        CpModel model = getModel();
        IntVar neg = model.newIntVarFromDomain(dokeDomain, "-" + var.getName());
        model.addEquality(LinearExpr.sum(new IntVar[] {var, neg}), 0);
        return neg;
    }

    public IntVar varAbs(IntVar var) {
        CpModel model = getModel();
        IntVar abs = model.newIntVarFromDomain(dokeDomain, "|" + var.getName() + "|");
        model.addAbsEquality(abs, var);
        return abs;
    }

    public IntVar varSub(IntVar var, int val) {
        CpModel model = getModel();
        IntVar diff = model.newIntVarFromDomain(dokeDomain, var.getName() + "-" + val);
        model.addEqualityWithOffset(var, diff, -val);
        return diff;
    }

    public IntVar varSub(IntVar var1, IntVar var2) {
        CpModel model = getModel();
        IntVar diff = model.newIntVarFromDomain(dokeDomain, var1.getName() + "-" + var2.getName());
        model.addEquality(LinearExpr.sum(new IntVar[] {diff, var2}), var1);
        return diff;
    }

    public IntVar varMul(IntVar var, int val) {
        CpModel model = getModel();
        IntVar prod = model.newIntVarFromDomain(dokeDomain, var.getName() + "*" + val);

        model.addEquality(LinearExpr.term(var, val), prod);

        return prod;
    }

    public IntVar varMul(IntVar var1, IntVar var2) {
        CpModel model = getModel();
        IntVar prod = model.newIntVarFromDomain(dokeDomain, var1.getName() + "*" + var2.getName());
        model.addProductEquality(prod, new IntVar[] {var1, var2});
        return prod;
    }

    public IntVar varDivExact(IntVar var, int val) {
        CpModel model = getModel();
        IntVar quo = model.newIntVarFromDomain(dokeDomain, var.getName() + "/" + val);
        IntVar valV = model.newIntVarFromDomain(new Domain(val), "" + val);
        model.addDivisionEquality(quo, var, valV);
        model.addModuloEquality(model.newIntVarFromDomain(new Domain(0), "0"), var, valV);
        return quo;
    }

    public IntVar varDivExact(IntVar var1, IntVar var2) {
        CpModel model = getModel();
        IntVar quo = model.newIntVarFromDomain(dokeDomain, var1.getName() + "/" + var2.getName());
        model.addDivisionEquality(quo, var1, var2);
        model.addModuloEquality(model.newIntVarFromDomain(new Domain(0), "0"), var1, var2);
        return quo;
    }

    public Constraint enforceBool(Literal bool) {
        CpModel model = getModel();
        return model.addBoolAnd(new Literal[] {bool});
    }

    public IntVar varMax(IntVar var1, IntVar var2) {
        IntVar max = model.newIntVarFromDomain(dokeDomain, "max(" + var1.getName() + ", " + var2.getName() + ")");
        model.addMaxEquality(max, new IntVar[] {var1, var2});
        return max;
    }

    public IntVar varMin(IntVar var1, IntVar var2) {
        IntVar min = model.newIntVarFromDomain(dokeDomain, "min(" + var1.getName() + ", " + var2.getName() + ")");
        model.addMinEquality(min, new IntVar[] {var1, var2});
        return min;
    }
}
