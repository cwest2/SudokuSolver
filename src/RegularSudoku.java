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

    @Override
    protected void buildDokeFile(StringBuilder sb) {
        base.buildDokeFile(sb);
        sb.append("REGULAR\n");
        sb.append(blockHeight);
        sb.append(" ");
        sb.append(blockWidth);
        sb.append("\n");
        sb.append("END REGULAR\n");
    }

//    @Override
//    public void buildModel() {
//        model = base.getModel();
//        IntVar[][] rows = base.getRows();
//        int n = base.getN();
//
//        IntVar[][] blocks = new IntVar[n][n];
//
//        for (int bi = 0; bi < vertBlocks; bi++) {
//            for (int bj = 0; bj < horzBlocks; bj++) {
//                int bn = bi * horzBlocks + bj;
//                for (int si = 0; si < blockHeight; si++) {
//                    for (int sj = 0; sj < blockWidth; sj++) {
//                        int sn = si * blockWidth + sj;
//                        int i = bi * blockHeight + si;
//                        int j = bj * blockWidth + sj;
//                        blocks[bn][sn] = rows[i][j];
//                    }
//                }
//            }
//        }
//
//        for (int i = 0; i < n; i++) {
//            model.allDifferent(blocks[i], "AC").post();
//        }
//    }

    public static void main(String[] args) throws IOException {
        int[][] givens = new int[][]{
                {0, 0, 0, 2, 0, 0, 0, 0, 0},
                {0, 8, 0, 0, 3, 0, 0, 7, 0},
                {3, 0, 0, 5, 0, 4, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 2, 8},
                {8, 3, 0, 0, 1, 0, 0, 0, 0},
                {0, 4, 0, 7, 2, 0, 3, 5, 1},
                {0, 7, 0, 0, 5, 6, 0, 0, 4},
                {0, 0, 3, 0, 0, 0, 0, 0, 0},
                {2, 0, 5, 4, 0, 1, 6, 0, 3}
        };
        AbstractPuzzle write_sudoku = new RegularSudoku.RegularSudokuBuilder()
                .withGivens(givens)
                .build();
        write_sudoku.printDokeFile();
        String fileName = "C:\\Users\\donut\\OneDrive\\Documents\\Dokes\\written.txt";
        write_sudoku.writeDokeFile(fileName);
        AbstractPuzzle read_sudoku = SudokuParser.parseFile(fileName);
        read_sudoku.printSolution();
    }
}
