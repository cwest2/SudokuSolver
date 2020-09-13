import org.chocosolver.solver.variables.IntVar;

public class NonconsecutiveSudoku extends VariantPuzzle {

    public static class NonconsecutiveSudokuBuilder {
        AbstractPuzzle base;

        public NonconsecutiveSudokuBuilder(AbstractPuzzle base) {
            this.base = base;
        }

        public NonconsecutiveSudoku build() {
            return new NonconsecutiveSudoku(base);
        }
    }

    private NonconsecutiveSudoku(AbstractPuzzle base) {
        this.base = base;
    }

    @Override
    public void buildModel() {
        model = base.getModel();
        IntVar[][] rows = getRows();
        IntVar[][] cols = getCols();
        int n = getN();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - 1; j++) {
                rows[i][j].sub(rows[i][j+1]).abs().gt(1).post();
                cols[i][j].sub(cols[i][j+1]).abs().gt(1).post();
            }
        }
    }

    @Override
    protected void buildDokeFile(StringBuilder sb) {
        base.buildDokeFile(sb);
        sb.append("NONCONSECUTIVE\n");
        sb.append("END NONCONSECUTIVE\n");
    }
}
