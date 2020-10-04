import com.google.ortools.sat.Constraint;
import com.google.ortools.sat.CpModel;
import com.google.ortools.sat.IntVar;
import com.google.ortools.sat.Literal;
import com.google.ortools.util.Domain;

import java.util.HashMap;

public class AdjacentCellSudoku extends VariantPuzzle{
    public static interface AdjacentCellFunction {
        Literal adjCellConstraint(IntVar x, IntVar y, AbstractPuzzle p);
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

        public AdjacentCellSudokuBuilder withNegativeConstraintOf(String name, String negName) {
            AdjacentCellFunction pos = functionMapping.get(name);
            functionMapping.put(negName, (x, y, p) -> pos.adjCellConstraint(x, y, p).not());
            return this;
        }

        public AdjacentCellSudokuBuilder withDifferenceConstraint(IntVar diff, String name) {
            functionMapping.put(name, (x, y, p) -> p.varEquals(p.varAbs(p.varSub(x, y)), diff));
            kropkiWhite = true;
            return this;
        }

        public AdjacentCellSudokuBuilder withDifferenceConstraints() {
            int n = base.getN();
            for (int i = 1; i < n; i++) {
                withDifferenceConstraint(i, "" + i);
            }
            return this;
        }

        public AdjacentCellSudokuBuilder withDifferenceConstraint(int diff, String name) {
            return withDifferenceConstraint(base.getModel().newIntVarFromDomain(new Domain(diff), "diff"), name);
        }

        public AdjacentCellSudokuBuilder withKropkiWhiteConstraint() {
            return withDifferenceConstraint(1, "W");
        }

        public AdjacentCellSudokuBuilder withKropkiBlackConstraint() {
            functionMapping.put("B", (x, y, p) -> p.varOr(p.varEquals(p.varMul(x, 2), y), p.varEquals(p.varMul(y, 2), x)));
            kropkiBlack = true;
            return this;
        }

        public AdjacentCellSudokuBuilder withKropkiConstraints() {
            withKropkiWhiteConstraint();
            withKropkiBlackConstraint();
            return this;
        }

        public AdjacentCellSudokuBuilder withXConstraint() {
            functionMapping.put("X", (x, y, p) -> p.varEquals(p.varAdd(x, y), 10));
            x = true;
            return this;
        }

        public AdjacentCellSudokuBuilder withVConstraint() {
            functionMapping.put("V", (x, y, p) -> p.varEquals(p.varAdd(x, y), 5));
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
        super(base);
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
                    Literal conVar = functionMapping.get(rowGaps[i][j]).adjCellConstraint(rows[i][j], rows[i][j + 1], this);
                    enforceBool(conVar);
                } else if (negativeConstraints) {
                    for (AdjacentCellFunction fun : functionMapping.values()) {
                        enforceBool(fun.adjCellConstraint(rows[i][j], rows[i][j + 1], this).not());
                    }
                }
            }
        }

        IntVar[][] cols = getCols();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - 1; j++) {
                if (functionMapping.containsKey(colGaps[i][j])) {
                    enforceBool(functionMapping.get(colGaps[i][j]).adjCellConstraint(cols[i][j], cols[i][j + 1], this));
                } else if (negativeConstraints) {
                    for (AdjacentCellFunction fun : functionMapping.values()) {
                        enforceBool(fun.adjCellConstraint(cols[i][j], cols[i][j + 1], this).not());
                    }
                }
            }
        }
    }
}
