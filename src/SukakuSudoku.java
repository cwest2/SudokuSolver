import com.google.ortools.sat.IntVar;
import com.google.ortools.util.Domain;

public class SukakuSudoku extends VariantPuzzle{
    long[][][] options;

    public static class SukakuSudokuBuilder {
        AbstractPuzzle base;
        long[][][] options = null;

        public SukakuSudokuBuilder(AbstractPuzzle base) {
            this.base = base;
        }

        public SukakuSudokuBuilder withOptionsGrid(long[][][] options) {
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

    private SukakuSudoku(AbstractPuzzle base, long[][][] options) {
        super(base);
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
                    model.addEquality(rows[i][j], model.newIntVarFromDomain(Domain.fromValues(options[i][j]), "options[" + i + "," + j + "]"));
                }
            }
        }
    }
}
