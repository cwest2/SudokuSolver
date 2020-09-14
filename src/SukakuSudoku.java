import org.chocosolver.solver.variables.IntVar;

public class SukakuSudoku extends VariantPuzzle{
    int[][][] options;

    public static class SukakuSudokuBuilder {
        AbstractPuzzle base;
        int[][][] options = null;

        public SukakuSudokuBuilder(AbstractPuzzle base) {
            this.base = base;
        }

        public SukakuSudokuBuilder withOptionsGrid(int[][][] options) {
            this.options = options;
            return this;
        }

        public SukakuSudoku build() {
            if (options == null) {
                throw new IllegalStateException("No options provided");
            }
            return new SukakuSudoku(base, options);
        }
    }

    private SukakuSudoku(AbstractPuzzle base, int[][][] options) {
        this.base = base;
        this.options = options;
    }

    @Override
    public void buildModel() {
        model = base.getModel();
        int n = getN();
        IntVar[][] rows = getRows();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (options[i][j].length > 0) {
                    rows[i][j].eq(model.intVar(options[i][j])).post();
                }
            }
        }
    }

    @Override
    protected void buildDokeFile(StringBuilder sb) {

    }

}
