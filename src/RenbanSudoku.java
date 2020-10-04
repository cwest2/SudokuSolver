import com.google.ortools.sat.IntVar;

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
        super(base);
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
            model.addAllDifferent(renban);
            IntVar max = model.newIntVar(1, n, "renbanMax");
            IntVar min = model.newIntVar(1, n, "renbanMin");
            model.addMaxEquality(max, renban);
            model.addMinEquality(min, renban);
            model.addEqualityWithOffset(min, max, renban.length - 1);
        }
    }
}
