import java.io.IOException;

public class RegularSudoku extends IrregularSudoku {

    int blockWidth;
    int blockHeight;
    int vertBlocks;
    int horzBlocks;

    public static class RegularSudokuBuilder {
        AbstractPuzzle base = null;
        int[][] givens = null;
        int size = 9;
        int blockHeight = 3;
        int blockWidth = 3;

        public RegularSudokuBuilder withGivens(int[][] givens) {
            this.givens = givens;
            return this;
        }

        public RegularSudokuBuilder withSizeAndBlockDims(int size, int blockHeight, int blockWidth) {
            this.size = size;
            this.blockHeight = blockHeight;
            this.blockWidth = blockWidth;
            return this;
        }

        public RegularSudokuBuilder withBase(AbstractPuzzle base) {
            this.base = base;
            return this;
        }

        public RegularSudoku build() {
            if (this.base == null && this.givens == null) {
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
            return new RegularSudoku(base, size, blockHeight, blockWidth);
        }
    }

    private RegularSudoku(AbstractPuzzle base, int size, int blockHeight, int blockWidth) {
        super(base, getRegions(size, size / blockHeight, size / blockWidth, blockHeight, blockWidth));
        if (size != blockHeight * blockWidth) {
            throw new IllegalArgumentException("Block size incompatible with grid size");
        }
        this.blockHeight = blockHeight;
        this.blockWidth = blockWidth;
        this.vertBlocks = size / blockHeight;
        this.horzBlocks = size / blockWidth;
    }

    private static int[][] getRegions(int n, int vertBlocks, int horzBlocks, int blockHeight, int blockWidth) {
        int[][] regions = new int[n][n];
        for (int bi = 0; bi < vertBlocks; bi++) {
            for (int bj = 0; bj < horzBlocks; bj++) {
                int bn = bi * horzBlocks + bj;
                for (int si = 0; si < blockHeight; si++) {
                    for (int sj = 0; sj < blockWidth; sj++) {
                        int i = bi * blockHeight + si;
                        int j = bj * blockWidth + sj;
                        regions[i][j] = bn;
                    }
                }
            }
        }
        return regions;
    }
}
