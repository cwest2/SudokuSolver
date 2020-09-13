import java.io.IOException;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;
import static org.chocosolver.util.tools.ArrayUtils.append;

public class LatinSquare extends AbstractPuzzle {

    int[][] givens;
    int n;
    IntVar[][] rows;
    IntVar[][] cols;

    public static class LatinSquareBuilder {
        int[][] givens = null;
        int size = 9;

        public LatinSquareBuilder withGivens(int[][] givens) {
            this.givens = givens;
            return this;
        }

        public LatinSquareBuilder withSize(int size) {
            this.size = size;
            return this;
        }

        public LatinSquare build() {
            if (givens == null) {
                this.givens = new int[size][size];
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        this.givens[i][j] = 0;
                    }
                }
            }
            return new LatinSquare(givens, size);
        }
    }

    private LatinSquare(int[][] givens, int size) {
        if (givens.length != size || givens[0].length != size) {
            throw new IllegalArgumentException("Length of givens does not match size");
        }
        this.givens = givens;
        this.n = size;
    }

    @Override
    protected void buildDokeFile(StringBuilder sb) {
        sb.append(n);
        sb.append("\n");
        sb.append("GIVENS\n");
        buildGrid(sb, givens);
    }

    @Override
    public void buildModel() {
        model = new Model();

        rows = new IntVar[n][n];
        cols = new IntVar[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (givens[i][j] == 0) {
                    rows[i][j] = model.intVar("c_" + i + "_" + j, 1, n, false);
                } else {
                    rows[i][j] = model.intVar(givens[i][j]);
                }
                cols[j][i] = rows[i][j];
            }
        }

        for (int i = 0; i < n; i++) {
            model.allDifferent(rows[i], "AC").post();
            model.allDifferent(cols[i], "AC").post();
        }
    }

    @Override
    public int getN() {
        return n;
    }

    @Override
    public IntVar[][] getRows() {
        return rows;
    }

    @Override
    public IntVar[][] getCols() {
        return cols;
    }
}
