import com.google.ortools.sat.Constraint;
import com.google.ortools.sat.CpModel;
import com.google.ortools.sat.IntVar;
import com.google.ortools.sat.LinearExpr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KillerSudoku extends VariantPuzzle {

    int[][] cages;
    HashMap<Integer, IntVar> cageSums;

    public static class KillerSudokuBuilder {
        AbstractPuzzle base;
        int[][] cages;
        HashMap<Integer, IntVar> varCageSums;

        public KillerSudokuBuilder(AbstractPuzzle base) {
            this.base = base;
        }

        public KillerSudokuBuilder withCagesGrid(int[][] cages) {
            this.cages = cages;
            return this;
        }

        public KillerSudokuBuilder withCageSumInts(HashMap<Integer, Integer> cageSums) {
            varCageSums = new HashMap<>();
            for (int cage : cageSums.keySet()) {
                varCageSums.put(cage, base.makeConstVar(cageSums.get(cage), "killerSum" + cage));
            }
            return this;
        }

        public KillerSudokuBuilder withCageSumVars(HashMap<Integer, IntVar> cageSums) {
            this.varCageSums = cageSums;
            return this;
        }

        public KillerSudoku build() {
            return new KillerSudoku(base, cages, varCageSums);
        }
    }

    private KillerSudoku(AbstractPuzzle base, int[][] cages, HashMap<Integer, IntVar> cageSums) {
        super(base);
        this.cages = cages;
        this.cageSums = cageSums;
    }

    List<Constraint> getKillerCageConstraints(CpModel model, List<IntVar> cage, IntVar sum, int n) {
        List<Constraint> constraints = new ArrayList<>();

        IntVar[] vars = new IntVar[cage.size()];
        cage.toArray(vars);
        constraints.add(model.addAllDifferent(vars));
        if (sum != null) {
            constraints.add(model.addEquality(LinearExpr.sum(vars), sum));

//            int maxSumMinusOneCell = 0;
//            for (int i = 0; i < vars.length - 1; i++) {
//                maxSumMinusOneCell += n - i;
//            }
//            IntVar minCell = sum.sub(maxSumMinusOneCell).intVar();
//            for (IntVar var : vars) {
//                var.ge(minCell).post();
//            }
//
//            int minSumMinusOneCell = 0;
//            for (int i = 0; i < vars.length - 1; i++) {
//                minSumMinusOneCell += 1 + i;
//            }
//            IntVar maxCell = sum.sub(minSumMinusOneCell).intVar();
//            for (IntVar var : vars) {
//                var.le(maxCell).post();
//            }
        }

        return constraints;
    }

    @Override
    public void buildModel() {
        model = base.getModel();
        IntVar[][] rows = getRows();
        int n = getN();

        HashMap<Integer, ArrayList<IntVar>> cageVars = new HashMap<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (cages[i][j] > 0) {
                    if (!cageVars.containsKey(cages[i][j])) {
                        cageVars.put(cages[i][j], new ArrayList<>());
                    }
                    cageVars.get(cages[i][j]).add(rows[i][j]);
                }
            }
        }

        for (Integer cage : cageVars.keySet()) {
            List<Constraint> constraints = getKillerCageConstraints(model, cageVars.get(cage), cageSums.get(cage), n);
        }
    }
}
