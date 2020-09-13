import org.chocosolver.solver.variables.IntVar;

public class DiagonalSudoku extends VariantPuzzle {

    public static class DiagonalSudokuBuilder {
        AbstractPuzzle base;

        public DiagonalSudokuBuilder(AbstractPuzzle base) {
            this.base = base;
        }

        public DiagonalSudoku build() {
            return new DiagonalSudoku(base);
        }
    }

    public DiagonalSudoku(AbstractPuzzle base) {
        this.base = base;
    }

    @Override
    public void buildModel() {
        model = base.getModel();
        IntVar[][] rows = base.getRows();
        int n = base.getN();

        IntVar[] downDiagonal = new IntVar[n];
        IntVar[] upDiagonal = new IntVar[n];

        for (int i = 0; i < n; i++) {
            downDiagonal[i] = rows[i][i];
            upDiagonal[i] = rows[i][n - i - 1];
        }

        model.allDifferent(downDiagonal, "AC").post();
        model.allDifferent(upDiagonal, "AC").post();
    }

    @Override
    protected void buildDokeFile(StringBuilder sb) {
        base.buildDokeFile(sb);
        sb.append("DIAGONAL\n");
        sb.append("END DIAGONAL\n");
    }
}
