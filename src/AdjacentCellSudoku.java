import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.expression.discrete.arithmetic.ArExpression;
import org.chocosolver.solver.expression.discrete.relational.ReExpression;
import org.chocosolver.solver.variables.IntVar;

import java.util.HashMap;

public class AdjacentCellSudoku extends VariantPuzzle{
    public static interface AdjacentCellFunction {
        ReExpression adjCellConstraint(IntVar x, IntVar y);
    }
    HashMap<String, AdjacentCellFunction> functionMapping;
    String[][] rowGaps;
    String[][] colGaps;
    boolean negativeConstraints;
    boolean kropkiWhite;
    boolean kropkiBlack;
    boolean x;
    boolean v;

    public static class AdjacentCellSudokuBuilder {
        AbstractPuzzle base;
        HashMap<String, AdjacentCellFunction> functionMapping = new HashMap<>();
        String[][] rowGaps = null;
        String[][] colGaps = null;
        boolean negativeConstraints = false;
        boolean kropkiWhite = false;
        boolean kropkiBlack = false;
        boolean x = false;
        boolean v = false;

        public AdjacentCellSudokuBuilder(AbstractPuzzle base) {
            this.base = base;
        }

        public AdjacentCellSudokuBuilder withKropkiWhiteConstraint() {
            functionMapping.put("W", (x, y) -> x.sub(y).abs().eq(1));
            kropkiWhite = true;
            return this;
        }

        public AdjacentCellSudokuBuilder withKropkiBlackConstraint() {
            functionMapping.put("B", (x, y) -> x.mul(2).eq(y).or(y.mul(2).eq(x)));
            kropkiBlack = true;
            return this;
        }

        public AdjacentCellSudokuBuilder withKropkiConstraints() {
            withKropkiWhiteConstraint();
            withKropkiBlackConstraint();
            return this;
        }

        public AdjacentCellSudokuBuilder withXConstraint() {
            functionMapping.put("X", (x, y) -> x.add(y).eq(10));
            x = true;
            return this;
        }

        public AdjacentCellSudokuBuilder withVConstraint() {
            functionMapping.put("V", (x, y) -> x.add(y).eq(5));
            v = true;
            return this;
        }

        public AdjacentCellSudokuBuilder withXVConstraints() {
            withXConstraint();
            withVConstraint();
            return this;
        }

        public AdjacentCellSudokuBuilder withRowGaps(String[][] rowGaps) {
            this.rowGaps = rowGaps;
            return this;
        }

        public AdjacentCellSudokuBuilder withColGaps(String[][] colGaps) {
            this.colGaps = colGaps;
            return this;
        }

        public AdjacentCellSudokuBuilder withNegativeConstraints(boolean negativeConstraints) {
            this.negativeConstraints = negativeConstraints;
            return this;
        }

        public AdjacentCellSudoku build() {
            int n = base.getN();
            if (rowGaps == null) {
                rowGaps = new String[n][n - 1];
            }
            if (colGaps == null) {
                colGaps = new String[n][n - 1];
            }
            return new AdjacentCellSudoku(base, functionMapping, rowGaps, colGaps, negativeConstraints,
                    kropkiWhite, kropkiBlack, x, v);
        }
    }

    private AdjacentCellSudoku(AbstractPuzzle base, HashMap<String, AdjacentCellFunction> functionMapping,
                               String[][] rowGaps, String[][] colGaps, boolean negativeConstraints,
                               boolean kropkiWhite, boolean kropkiBlack, boolean x, boolean v) {
        this.base = base;
        this.functionMapping = functionMapping;
        this.rowGaps = rowGaps;
        this.colGaps = colGaps;
        this.negativeConstraints = negativeConstraints;
        this.kropkiWhite = kropkiWhite;
        this.kropkiBlack = kropkiBlack;
        this.x = x;
        this.v = v;
    }

    @Override
    public void buildModel() {
        model = base.getModel();
        int n = getN();

        IntVar[][] rows = getRows();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - 1; j++) {
                if (functionMapping.containsKey(rowGaps[i][j])) {
                    functionMapping.get(rowGaps[i][j]).adjCellConstraint(rows[i][j], rows[i][j + 1]).post();
                } else if (negativeConstraints) {
                    for (AdjacentCellFunction fun : functionMapping.values()) {
                        fun.adjCellConstraint(rows[i][j], rows[i][j + 1]).not().post();
                    }
                }
            }
        }

        IntVar[][] cols = getCols();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - 1; j++) {
                if (functionMapping.containsKey(colGaps[i][j])) {
                    functionMapping.get(colGaps[i][j]).adjCellConstraint(cols[i][j], cols[i][j + 1]).post();
                } else if (negativeConstraints) {
                    for (AdjacentCellFunction fun : functionMapping.values()) {
                        fun.adjCellConstraint(cols[i][j], cols[i][j + 1]).not().post();
                    }
                }
            }
        }
    }

    @Override
    protected void buildDokeFile(StringBuilder sb) {
        base.buildDokeFile(sb);
        sb.append("ADJACENT CELL\n");
        buildGrid(sb, rowGaps);
        buildGrid(sb, colGaps);
        if (negativeConstraints) {
            sb.append("NEGATIVE\n");
        }
        if (kropkiWhite) {
            sb.append("KROPKI WHITE\n");
        }
        if (kropkiBlack) {
            sb.append("KROPKI BLACK\n");
        }
        if (x) {
            sb.append("X CONSTRAINT\n");
        }
        if (v) {
            sb.append("V CONSTRAINT\n");
        }
        sb.append("END ADJACENT CELL\n");
    }
}
