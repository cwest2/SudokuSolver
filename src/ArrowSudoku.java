import com.google.ortools.sat.IntVar;
import com.google.ortools.sat.LinearExpr;
import com.google.ortools.util.Domain;

import java.util.List;

public class ArrowSudoku extends VariantPuzzle {
    List<int[][]> arrowPaths;
    List<int[][]> arrowSums;

    public static class ArrowSudokuBuilder {
        AbstractPuzzle base;
        List<int[][]> arrowPaths;
        List<int[][]> arrowSums;

        public ArrowSudokuBuilder(AbstractPuzzle base, List<int[][]> arrowSums, List<int[][]> arrowPaths) {
            this.base = base;
            this.arrowSums = arrowSums;
            this.arrowPaths = arrowPaths;
        }

        public ArrowSudoku build() {
            return new ArrowSudoku(base, arrowPaths, arrowSums);
        }
    }

    private ArrowSudoku(AbstractPuzzle base, List<int[][]> arrowPaths, List<int[][]> arrowSums){
        super(base);
        if (arrowSums.size() != arrowPaths.size()) {
            throw new IllegalArgumentException("Mismatch in number of paths and number of sums");
        }
        this.arrowPaths = arrowPaths;
        this.arrowSums = arrowSums;
    }

    @Override
    public void buildModel() {
        model = base.getModel();
        IntVar[][] rows = base.getRows();

        for (int i = 0; i < arrowSums.size(); i++) {
            int[][] path = arrowPaths.get(i);
            int[][] sumCells = arrowSums.get(i);

            int sumSize = sumCells.length;
            IntVar sum = model.newIntVarFromDomain(new Domain(0), "0");
            int power = 1;
            for (int j = 0; j < sumSize; j++) {
                int[] coords = sumCells[sumSize - 1 - j];
                IntVar cell = rows[coords[0]][coords[1]];
                sum = varAdd(varMul(cell, power), sum);
                power *= 10;
            }

            int pathSize = path.length;
            IntVar[] pathVars = new IntVar[pathSize];
            for (int j = 0; j < pathSize; j++) {
                int[] coords = path[j];
                pathVars[j] = rows[coords[0]][coords[1]];
            }

            model.addEquality(LinearExpr.sum(pathVars), sum);
        }
    }
}
