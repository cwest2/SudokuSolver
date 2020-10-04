public class IrregularSudoku extends SurplusDeficitSudoku {
    int[][] regions;

    public static class IrregularSudokuBuilder {
        AbstractPuzzle base = null;
        int[][] givens = null;
        int[][] regions;
        int size = 9;

        public IrregularSudokuBuilder(int[][] regions) {
            this.regions = regions;
        }

        public IrregularSudokuBuilder withBase(AbstractPuzzle base) {
            this.base = base;
            return this;
        }

        public IrregularSudokuBuilder withGivens(int[][] givens) {
            this.givens = givens;
            return this;
        }

        public IrregularSudokuBuilder withSize(int size) {
            this.size = size;
            return this;
        }

        public IrregularSudoku build() {
            if (base == null && givens == null) {
                this.givens = new int[size][size];
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        givens[i][j] = 0;
                    }
                }
            }
            if (this.base == null) {
                this.base = new LatinSquare.LatinSquareBuilder()
                        .withGivens(givens)
                        .withSize(size)
                        .build();
            }
            return new IrregularSudoku(base, regions);
        }
    }

    protected IrregularSudoku(AbstractPuzzle base, int[][] regions) {
        super(base, regions);
        this.regions = regions;
    }
}
