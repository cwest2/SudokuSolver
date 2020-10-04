import com.google.ortools.sat.Constraint;
import com.google.ortools.sat.IntVar;
import com.google.ortools.sat.LinearExpr;
import com.google.ortools.sat.Literal;

public class FrameSudoku extends VariantPuzzle {
    int[] leftRowSums;
    int[] topColSums;
    int[] rightRowSums;
    int[] bottomColSums;
    int numCells;
    Literal[] leftRowConds;
    Literal[] topColConds;
    Literal[] rightRowConds;
    Literal[] bottomColConds;

    public static class FrameSudokuBuilder {
        AbstractPuzzle base;
        int[] leftRowSums = null;
        int[] topColSums = null;
        int[] rightRowSums = null;
        int[] bottomColSums = null;
        int numCells = 3;
        Literal[] leftRowConds = null;
        Literal[] topColConds = null;
        Literal[] rightRowConds = null;
        Literal[] bottomColConds = null;

        public int[] emptySums(int n) {
            int[] sums = new int[n];
            for (int i = 0; i < n; i++) {
                sums[i] = 0;
            }
            return sums;
        }

        public FrameSudokuBuilder(AbstractPuzzle base) {
            this.base = base;
        }

        public FrameSudokuBuilder withLeftRowSums(int[] leftRowSums) {
            this.leftRowSums = leftRowSums;
            return this;
        }

        public FrameSudokuBuilder withTopColSums(int[] topColSums) {
            this.topColSums = topColSums;
            return this;
        }

        public FrameSudokuBuilder withRightRowSums(int[] rightRowSums) {
            this.rightRowSums = rightRowSums;
            return this;
        }

        public FrameSudokuBuilder withBottomColSums(int[] bottomColSums) {
            this.bottomColSums = bottomColSums;
            return this;
        }

        public FrameSudokuBuilder withLeftRowConds(Literal[] leftRowConds) {
            this.leftRowConds = leftRowConds;
            return this;
        }

        public FrameSudokuBuilder withTopColConds(Literal[] topColConds) {
            this.topColConds = topColConds;
            return this;
        }

        public FrameSudokuBuilder withRightRowConds(Literal[] rightRowConds) {
            this.rightRowConds = rightRowConds;
            return this;
        }

        public FrameSudokuBuilder withBottomColConds(Literal[] bottomColConds) {
            this.bottomColConds = bottomColConds;
            return this;
        }

        public FrameSudoku build() {
            int n = base.getN();
            if (leftRowSums == null) {
                leftRowSums = emptySums(n);
            }
            if (topColSums == null) {
                topColSums = emptySums(n);
            }
            if (rightRowSums == null) {
                rightRowSums = emptySums(n);
            }
            if (bottomColSums == null) {
                bottomColSums = emptySums(n);
            }
            if (leftRowConds == null) {
                leftRowConds = new IntVar[n];
            }
            if (topColConds == null) {
                topColConds = new IntVar[n];
            }
            if (rightRowConds == null) {
                rightRowConds = new IntVar[n];
            }
            if (bottomColConds == null) {
                bottomColConds = new IntVar[n];
            }
            return new FrameSudoku(base, leftRowSums, topColSums, rightRowSums, bottomColSums, numCells,
                    leftRowConds, topColConds, rightRowConds, bottomColConds);
        }
    }

    private FrameSudoku(AbstractPuzzle base, int[] leftRowSums, int[] topColSums, int[] rightRowSums, int[] bottomColSums, int numCells,
                        Literal[] leftRowConds, Literal[] topColConds, Literal[] rightRowConds, Literal[] bottomColConds) {
        super(base);
        this.leftRowSums = leftRowSums;
        this.topColSums = topColSums;
        this.rightRowSums = rightRowSums;
        this.bottomColSums = bottomColSums;
        this.numCells = numCells;
        this.leftRowConds = leftRowConds;
        this.topColConds = topColConds;
        this.rightRowConds = rightRowConds;
        this.bottomColConds = bottomColConds;
    }

    private void buildFrameConstraints(IntVar[][] rows, int[] sums, int numCells, Literal[] conds) {
        for (int i = 0; i < sums.length; i++) {
            if (sums[i] > 0) {
                IntVar[] row = rows[i];
                IntVar[] frame = new IntVar[numCells];
                if (numCells >= 0) System.arraycopy(row, 0, frame, 0, numCells);
                Constraint constraint = model.addEquality(LinearExpr.sum(frame), sums[i]);
                if (conds[i] != null) {
                    constraint.onlyEnforceIf(conds[i]);
                }
            }
        }
    }

    @Override
    public void buildModel() {
        model = base.getModel();
        IntVar[][] rows = getRows();
        IntVar[][] cols = getCols();

        buildFrameConstraints(rows, leftRowSums, numCells, leftRowConds);
        buildFrameConstraints(cols, topColSums, numCells, topColConds);
        buildFrameConstraints(makeRowReversedGrid(rows), rightRowSums, numCells, rightRowConds);
        buildFrameConstraints(makeRowReversedGrid(cols), bottomColSums, numCells, bottomColConds);
    }
}
