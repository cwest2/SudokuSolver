import org.chocosolver.solver.variables.IntVar;

import java.util.ArrayList;
import java.util.HashMap;

public class KillerSudoku extends VariantPuzzle {

    int[][] cages;
    HashMap<Integer, Integer> cageSums;

    public static class KillerSudokuBuilder {
        AbstractPuzzle base;
        int[][] cages;
        HashMap<Integer, Integer> cageSums;

        public KillerSudokuBuilder(AbstractPuzzle base, int[][] cages, HashMap<Integer, Integer> cageSums) {
            this.base = base;
            this.cages = cages;
            this.cageSums = cageSums;
        }

        public KillerSudoku build() {
            return new KillerSudoku(base, cages, cageSums);
        }
    }

    private KillerSudoku(AbstractPuzzle base, int[][] cages, HashMap<Integer, Integer> cageSums) {
        this.base = base;
        this.cages = cages;
        this.cageSums = cageSums;
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
//            for (IntVar var : cageVars.get(cage)) {
//                System.out.println("var " + var.toString());
//            }

            IntVar[] vars = new IntVar[cageVars.get(cage).size()];
            cageVars.get(cage).toArray(vars);
            model.allDifferent(vars).post();
            if (cageSums.containsKey(cage)) {
                int sum = cageSums.get(cage);
                model.sum(vars, "=", sum).post();

                int maxSumMinusOneCell = 0;
                for (int i = 0; i < vars.length - 1; i++) {
                    maxSumMinusOneCell += n - i;
                }
                int minCell = sum - maxSumMinusOneCell;
                if (minCell > 1) {
                    for (IntVar var : vars) {
                        var.ge(minCell).post();
                    }
                }

                int minSumMinusOneCell = 0;
                for (int i = 0; i < vars.length - 1; i++) {
                    minSumMinusOneCell += 1 + i;
                }
                int maxCell = sum - minSumMinusOneCell;
                if (maxCell < n) {
                    for (IntVar var : vars) {
                        var.le(maxCell).post();
                    }
                }
            }
        }
    }

    @Override
    protected void buildDokeFile(StringBuilder sb) {
        base.buildDokeFile(sb);
        sb.append("KILLER\n");
        buildGrid(sb, cages);
        for (int cage : cageSums.keySet()) {
            sb.append(cage);
            sb.append(" ");
            sb.append(cageSums.get(cage));
            sb.append("\n");
        }
        sb.append("\n");
        sb.append("END KILLER\n");
    }
}
