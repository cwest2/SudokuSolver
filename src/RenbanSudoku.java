import org.chocosolver.solver.variables.IntVar;

import java.util.List;

public class RenbanSudoku extends VariantPuzzle{
    List<int[][]> regions;

    public static class RenbanSudokuBuilder {
        AbstractPuzzle base;
        List<int[][]> regions;

        public RenbanSudokuBuilder(AbstractPuzzle base, List<int[][]> regions) {
            this.base = base;
            this.regions = regions;
        }

        public RenbanSudoku build() {
            return new RenbanSudoku(base, regions);
        }
    }

    private RenbanSudoku(AbstractPuzzle base, List<int[][]> regions) {
        this.base = base;
        this.regions = regions;
    }

    @Override
    public void buildModel() {
        model = base.getModel();
        IntVar[][] rows = base.getRows();
        int n = base.getN();

        for (int[][] region : regions) {
            IntVar[] renban = new IntVar[region.length];
            for (int i = 0; i < region.length; i++) {
                int[] coord = region[i];
                renban[i] = rows[coord[0]][coord[1]];
            }
            model.allDifferent(renban).post();
            IntVar max = model.intVar(1, n);
            IntVar min = model.intVar(1, n);
            model.max(max, renban).post();
            model.min(min, renban).post();
            max.sub(min).eq(renban.length - 1).post();
        }
    }

    @Override
    protected void buildDokeFile(StringBuilder sb) {

    }
}
