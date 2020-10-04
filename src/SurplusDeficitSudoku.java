

import com.google.ortools.sat.IntVar;
import com.google.ortools.util.Domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SurplusDeficitSudoku extends VariantPuzzle {

    int[][] regions;

    public static class SurplusDeficitSudokuBuilder {
        AbstractPuzzle base = null;
        int[][] regions;
        int[][] givens = null;
        int size = 9;

        public SurplusDeficitSudokuBuilder(int[][] regions) {
            this.regions = regions;
        }

        public SurplusDeficitSudokuBuilder withBase(AbstractPuzzle base) {
            this.base = base;
            return this;
        }

        public SurplusDeficitSudokuBuilder withGivens(int[][] givens) {
            this.givens = givens;
            return this;
        }

        public SurplusDeficitSudokuBuilder withSize(int size) {
            this.size = size;
            return this;
        }

        public SurplusDeficitSudoku build() {
            if (base == null && givens == null) {
                this.givens = new int[size][size];
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        givens[i][j] = 0;
                    }
                }
            }
            if (this.base == null) {
                this.base = new LatinSquare.LatinSquareBuilder()
                        .withGivens(givens)
                        .withSize(size)
                        .build();
            }
            return new SurplusDeficitSudoku(base, regions);
        }
    }

    protected SurplusDeficitSudoku(AbstractPuzzle base, int[][] regions) {
        super(base);
        this.regions = regions;
    }

    @Override
    public void buildModel() {
        model = base.getModel();
        int n = getN();
        IntVar[][] rows = getRows();

        HashMap<Integer, List<IntVar>> regionsVars = new HashMap<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int region = regions[i][j];
                if (!regionsVars.containsKey(region)) {
                    regionsVars.put(region, new ArrayList<>());
                }
                regionsVars.get(region).add(rows[i][j]);
            }
        }

        for (Integer key : regionsVars.keySet()) {
            List<IntVar> regionVars = regionsVars.get(key);
            IntVar[] row = new IntVar[regionVars.size()];
            regionsVars.get(key).toArray(row);
            if (regionVars.size() <= n) {
                model.addAllDifferent(row);
            }
            if (regionVars.size() >= n) {
                for (int i = 1; i <= n; i++) {
                    IntVar loc = model.newIntVar(0, n - 1, "");
                    IntVar val = model.newIntVarFromDomain(new Domain(i), "");
                    model.addElement(loc, row, val);
                }
            }
        }
    }
}
