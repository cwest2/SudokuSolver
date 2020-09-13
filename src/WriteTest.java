import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

public class WriteTest extends SudokuTest {

    String directory = "C:\\Users\\donut\\OneDrive\\Documents\\Dokes\\";

    // TEST METHOD

    private void writeTest(AbstractPuzzle write_sudoku, String fileName, int[][] solution) {
        write_sudoku.printDokeFile();
        write_sudoku.writeDokeFile(fileName);
        AbstractPuzzle read_sudoku = null;
        try {
            read_sudoku = SudokuParser.parseFile(fileName);
        } catch (IOException e) {
            fail("Exception while reading in writeRegularTest");
        }

        compareSolutions(read_sudoku.solve(), solution);
    }

    // STANDARD VARIANTS

    @Test
    public void writeRegularTest() {
        String fileName = directory + "regular.txt";
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
        int[][] solution =
                {
                        {9, 1, 4, 2, 6, 7, 8, 3, 5},
                        {5, 8, 6, 1, 3, 9, 4, 7, 2},
                        {3, 2, 7, 5, 8, 4, 1, 6, 9},
                        {7, 5, 1, 6, 4, 3, 9, 2, 8},
                        {8, 3, 2, 9, 1, 5, 7, 4, 6},
                        {6, 4, 9, 7, 2, 8, 3, 5, 1},
                        {1, 7, 8, 3, 5, 6, 2, 9, 4},
                        {4, 6, 3, 8, 9, 2, 5, 1, 7},
                        {2, 9, 5, 4, 7, 1, 6, 8, 3}
                };
        writeTest(write_sudoku, fileName, solution);
    }

    @Test
    public void writeIrregularTest() {
        String fileName = directory + "irregular.txt";
        int[][] givens = {
                {3, 0, 0, 0, 0, 0, 0, 0, 1},
                {0, 9, 0, 1, 7, 2, 0, 0, 0},
                {0, 0, 3, 0, 0, 0, 9, 0, 0},
                {0, 7, 0, 0, 0, 0, 0, 4, 0},
                {0, 4, 0, 0, 3, 0, 0, 6, 0},
                {0, 5, 0, 0, 0, 0, 0, 9, 0},
                {0, 0, 6, 0, 0, 0, 5, 0, 0},
                {0, 0, 0, 8, 5, 6, 0, 2, 0},
                {7, 0, 0, 0, 0, 0, 0, 0, 8}
        };
        int[][] regions = {
                {1, 1, 1, 1, 1, 2, 2, 2, 2},
                {1, 3, 3, 3, 4, 4, 2, 2, 2},
                {1, 3, 3, 3, 4, 5, 5, 5, 2},
                {1, 3, 3, 3, 4, 5, 5, 5, 2},
                {1, 4, 4, 4, 4, 5, 5, 5, 6},
                {7, 4, 8, 8, 8, 6, 6, 6, 6},
                {7, 7, 8, 8, 8, 6, 9, 9, 9},
                {7, 7, 8, 8, 8, 6, 9, 9, 9},
                {7, 7, 7, 7, 6, 6, 9, 9, 9}
        };


        AbstractPuzzle write_sudoku = new IrregularSudoku.IrregularSudokuBuilder(regions)
                .withGivens(givens)
                .build();

        int[][] solution = {
                {3, 6, 2, 7, 4, 9, 8, 5, 1},
                {5, 9, 8, 1, 7, 2, 4, 3, 6},
                {1, 2, 3, 4, 6, 5, 9, 8, 7},
                {9, 7, 5, 6, 8, 3, 1, 4, 2},
                {8, 4, 1, 9, 3, 7, 2, 6, 5},
                {6, 5, 4, 2, 1, 8, 7, 9, 3},
                {2, 8, 6, 3, 9, 1, 5, 7, 4},
                {4, 1, 7, 8, 5, 6, 3, 2, 9},
                {7, 3, 9, 5, 2, 4, 6, 1, 8}
        };
        writeTest(write_sudoku, fileName, solution);
    }

    @Test
    public void writeSurplusDeficitTest() {
        String fileName = directory + "deficit.txt";
        int[][] givens = {
                {0, 0, 0, 0, 0, 2, 0, 0, 5},
                {0, 0, 0, 6, 0, 8, 0, 0, 0},
                {0, 0, 0, 8, 0, 6, 0, 0, 0},
                {6, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 5, 0, 0, 0, 0, 9, 0, 3},
                {3, 0, 0, 0, 0, 0, 0, 8, 0},
                {0, 6, 0, 1, 0, 0, 8, 0, 0},
                {0, 3, 2, 0, 0, 0, 0, 5, 0},
                {5, 0, 0, 0, 0, 0, 0, 4, 0}
        };
        int[][] regions = {
                {1, 1, 1, 2, 2, 3, 4, 4, 4},
                {1, 1, 1, 2, 2, 3, 4, 4, 4},
                {1, 1, 2, 2, 3, 3, 3, 4, 4},
                {5, 5, 2, 2, 3, 3, 3, 6, 6},
                {5, 5, 5, 5, 11, 6, 6, 6, 6},
                {5, 5, 7, 7, 7, 8, 8, 6, 6},
                {9, 9, 7, 7, 7, 8, 8, 10, 10},
                {9, 9, 9, 7, 8, 8, 10, 10, 10},
                {9, 9, 9, 7, 8, 8, 10, 10, 10}
        };
        AbstractPuzzle write_sudoku = new IrregularSudoku.IrregularSudokuBuilder(regions)
                .withGivens(givens)
                .build();

        int[][] solution = {
                {8, 7, 3, 4, 1, 2, 6, 9, 5},
                {9, 4, 5, 6, 3, 8, 2, 1, 7},
                {1, 2, 7, 8, 9, 6, 5, 3, 4},
                {6, 8, 9, 5, 4, 1, 3, 7, 2},
                {2, 5, 1, 7, 8, 4, 9, 6, 3},
                {3, 9, 6, 2, 7, 5, 4, 8, 1},
                {7, 6, 4, 1, 5, 3, 8, 2, 9},
                {4, 3, 2, 9, 6, 7, 1, 5, 8},
                {5, 1, 8, 3, 2, 9, 7, 4, 6}
        };

        writeTest(write_sudoku, fileName, solution);
    }

    // OTHER VARIANTS

    @Test
    public void writeAdjacentXV() {
        String fileName = directory + "xv.txt";
        String W = "W";
        String B = "B";
        String X = "X";
        String V = "V";
        String N = "N";

        int[][] givens = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {7, 0, 0, 0, 0, 0, 0, 0, 3},
                {0, 3, 0, 0, 0, 0, 0, 2, 0},
                {0, 0, 2, 0, 0, 0, 6, 0, 0},
                {0, 0, 0, 6, 0, 4, 0, 0, 0},
                {0, 0, 0, 0, 9, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {6, 0, 0, 0, 0, 0, 0, 0, 2},
                {0, 7, 0, 0, 0, 0, 0, 1, 0}
        };

        String[][] rowGaps = {
                {N, N, N, N, N, N, N, N},
                {N, N, X, N, N, X, N, N},
                {N, N, N, N, N, N, N, N},
                {N, N, N, N, N, N, N, N},
                {N, N, N, N, N, N, N, N},
                {N, N, N, N, N, N, N, N},
                {N, N, N, N, N, N, N, N},
                {N, N, N, N, N, N, N, N},
                {N, N, N, N, N, N, N, N}
        };

        String[][] colGaps = {
                {N, N, N, N, N, N, N, N},
                {N, N, N, N, N, N, N, N},
                {N, N, N, N, N, V, N, N},
                {N, N, X, N, N, N, N, N},
                {N, N, N, X, N, N, N, V},
                {N, N, X, N, N, N, N, N},
                {N, N, N, N, N, V, N, N},
                {N, N, N, N, N, N, N, N},
                {N, N, N, N, N, N, N, N}
        };

        int[][] solution = {
                {4, 2, 9, 8, 3, 1, 7, 6, 5},
                {7, 1, 6, 4, 5, 2, 8, 9, 3},
                {8, 3, 5, 9, 6, 7, 1, 2, 4},
                {9, 4, 2, 1, 8, 3, 6, 5, 7},
                {5, 8, 7, 6, 2, 4, 9, 3, 1},
                {3, 6, 1, 7, 9, 5, 2, 4, 8},
                {1, 5, 4, 2, 7, 6, 3, 8, 9},
                {6, 9, 3, 5, 1, 8, 4, 7, 2},
                {2, 7, 8, 3, 4, 9, 5, 1, 6}
        };

        AbstractPuzzle puzzle = new RegularSudoku.RegularSudokuBuilder()
                .withGivens(givens)
                .build();
        puzzle = new AdjacentCellSudoku.AdjacentCellSudokuBuilder(puzzle)
                .withXVConstraints()
                .withNegativeConstraints(true)
                .withRowGaps(rowGaps)
                .withColGaps(colGaps)
                .build();

        writeTest(puzzle, fileName, solution);
    }

    @Test
    public void writeAdjacentKropki() {
        String fileName = directory + "kropki.txt";
        String W = "W";
        String B = "B";
        String X = "X";
        String V = "V";
        String N = "N";

        int[][] givens = {
                {7, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 9}
        };

        String[][] rowGaps = {
                {N, N, W, N, N, N, N, N},
                {N, B, N, N, N, B, N, N},
                {N, N, W, N, N, N, N, W},
                {B, N, N, B, W, W, B, W},
                {N, N, B, N, N, N, N, N},
                {W, N, W, N, B, N, N, N},
                {N, B, W, N, N, N, N, W},
                {N, N, W, N, B, N, N, N},
                {B, N, N, N, N, W, W, N}
        };

        String[][] colGaps = {
                {N, N, N, W, N, N, N, N},
                {W, B, N, N, W, B, N, N},
                {W, W, N, N, B, N, W, N},
                {W, N, W, N, W, N, W, N},
                {W, W, W, N, W, N, W, W},
                {N, B, N, N, W, N, N, N},
                {B, N, W, N, W, N, W, N},
                {N, N, W, N, N, N, N, N},
                {N, N, W, W, W, N, W, N}
        };

        AbstractPuzzle puzzle = new RegularSudoku.RegularSudokuBuilder()
                .withGivens(givens)
                .build();
        puzzle = new AdjacentCellSudoku.AdjacentCellSudokuBuilder(puzzle)
                .withKropkiConstraints()
                .withNegativeConstraints(true)
                .withRowGaps(rowGaps)
                .withColGaps(colGaps)
                .build();

        int[][] solutions = {
                {7, 5, 3, 4, 9, 1, 6, 2, 8},
                {9, 4, 2, 5, 8, 6, 3, 7, 1},
                {6, 8, 1, 2, 7, 3, 9, 5, 4},
                {2, 1, 9, 3, 6, 7, 8, 4, 5},
                {3, 7, 4, 8, 1, 5, 2, 9, 6},
                {5, 6, 8, 9, 2, 4, 1, 3, 7},
                {8, 3, 6, 7, 5, 9, 4, 1, 2},
                {1, 9, 7, 6, 4, 2, 5, 8, 3},
                {4, 2, 5, 1, 3, 8, 7, 6, 9}
        };

        writeTest(puzzle, fileName, solutions);
    }

    @Test
    public void writeArrowOneCellBubbleTest() {
        String fileName = directory + "oneArrow.txt";
        int[][] givens = {
                {0, 0, 0, 0, 1, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 0, 3, 0},
                {0, 0, 0, 0, 0, 2, 0, 0, 5},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 2, 1, 0, 0, 0, 8, 0, 0},
                {0, 0, 0, 1, 3, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 5, 0, 0},
                {0, 0, 0, 0, 0, 5, 0, 0, 0},
                {3, 4, 0, 0, 0, 0, 0, 8, 9}
        };

        List<int[][]> arrowSums = new ArrayList<>();
        List<int[][]> arrowPaths = new ArrayList<>();

        arrowSums.add(new int[][] {{0, 3}});
        arrowPaths.add(new int[][]{
                {0, 4},
                {0, 5}
        });

        arrowSums.add(new int[][] {{1, 5}});
        arrowPaths.add(new int[][]{
                {1, 6},
                {1, 7}
        });

        arrowSums.add(new int[][] {{2, 0}});
        arrowPaths.add(new int[][]{
                {2, 1},
                {2, 2}
        });

        arrowSums.add(new int[][] {{2, 3}});
        arrowPaths.add(new int[][] {
                {2, 4},
                {2, 5}
        });

        arrowSums.add(new int[][] {{3, 6}});
        arrowPaths.add(new int[][] {
                {3, 5},
                {3, 4}
        });

        arrowSums.add(new int[][] {{5, 0}});
        arrowPaths.add(new int[][] {
                {4, 0},
                {3, 0}
        });

        arrowSums.add(new int[][] {{5, 1}});
        arrowPaths.add(new int[][] {
                {5, 2},
                {5, 3}
        });

        arrowSums.add(new int[][] {{6, 2}});
        arrowPaths.add(new int[][] {
                {7, 2},
                {8, 2}
        });

        arrowSums.add(new int[][] {{7, 8}});
        arrowPaths.add(new int[][] {
                {7, 7},
                {7, 6}
        });

        arrowSums.add(new int[][] {{8, 4}});
        arrowPaths.add(new int[][] {
                {8, 3},
                {8, 2}
        });

        arrowSums.add(new int[][] {{8, 5}});
        arrowPaths.add(new int[][] {
                {7, 5},
                {6, 5}
        });

        int[][] solution = {
                {2, 5, 9, 4, 1, 3, 7, 6, 8},
                {1, 6, 8, 5, 9, 7, 4, 3, 2},
                {7, 3, 4, 8, 6, 2, 9, 1, 5},
                {5, 8, 3, 7, 2, 4, 6, 9, 1},
                {4, 2, 1, 6, 5, 9, 8, 7, 3},
                {9, 7, 6, 1, 3, 8, 2, 5, 4},
                {8, 9, 7, 3, 4, 1, 5, 2, 6},
                {6, 1, 2, 9, 8, 5, 3, 4, 7},
                {3, 4, 5, 2, 7, 6, 1, 8, 9}
        };

        AbstractPuzzle puzzle = new RegularSudoku.RegularSudokuBuilder()
                .withGivens(givens)
                .build();
        puzzle = new ArrowSudoku.ArrowSudokuBuilder(puzzle, arrowSums, arrowPaths)
                .build();

        writeTest(puzzle, fileName, solution);
    }

    @Test
    public void writeArrowTwoCellBubbleTest() {
        String fileName = directory + "twoArrow.txt";

        List<int[][]> arrowSums = new ArrayList<>();
        List<int[][]> arrowPaths = new ArrayList<>();

        arrowSums.add(new int[][] {{1, 6}});
        arrowPaths.add(new int[][] {
                {1, 5},
                {1, 4},
                {1, 3}
        });

        arrowSums.add(new int[][] {{1, 6}});
        arrowPaths.add(new int[][] {
                {0, 6},
                {0, 5}
        });

        arrowSums.add(new int[][] {
                {2, 0},
                {2, 1}
        });
        arrowPaths.add(new int[][] {
                {3, 0},
                {3, 1},
                {3, 2},
                {4, 3}
        });

        arrowSums.add(new int[][] {
                {2, 3},
                {2, 4}
        });
        arrowPaths.add(new int[][] {
                {3, 4},
                {3, 3},
                {4, 2},
                {5, 2}
        });

        arrowSums.add(new int[][] {{2, 5}});
        arrowPaths.add(new int[][] {
                {3, 5},
                {4, 5},
                {5, 5}
        });

        arrowSums.add(new int[][] {
                {2, 6},
                {2, 7}
        });
        arrowPaths.add(new int[][] {
                {2, 8},
                {1, 8}
        });

        arrowSums.add(new int[][] {{5, 6}});
        arrowPaths.add(new int[][] {
                {6, 5},
                {7, 5}
        });

        arrowSums.add(new int[][] {
                {5, 7},
                {5, 8}
        });
        arrowPaths.add(new int[][] {
                {6, 7},
                {7, 8},
                {8, 8},
                {8, 7},
                {8, 6},
                {8, 5},
                {8, 4},
                {8, 3},
                {8, 2},
                {8, 1},
                {8, 0},
                {7, 0}
        });

        arrowSums.add(new int[][] {{6, 0}});
        arrowPaths.add(new int[][] {
                {5, 1},
                {4, 1}
        });

        arrowSums.add(new int[][] {{7, 1}});
        arrowPaths.add(new int[][] {
                {6, 2},
                {6, 3},
                {6, 4}
        });

        arrowSums.add(new int[][] {{7, 4}});
        arrowPaths.add(new int[][] {
                {7, 3},
                {7, 2}
        });

        int[][] solution = {
                {8, 1, 4, 9, 7, 6, 2, 5, 3},
                {3, 9, 6, 2, 1, 5, 8, 4, 7},
                {2, 5, 7, 3, 4, 8, 1, 6, 9},
                {6, 7, 5, 8, 9, 1, 3, 2, 4},
                {1, 3, 9, 7, 2, 4, 6, 8, 5},
                {4, 2, 8, 5, 6, 3, 9, 7, 1},
                {5, 8, 2, 1, 3, 7, 4, 9, 6},
                {9, 6, 1, 4, 5, 2, 7, 3, 8},
                {7, 4, 3, 6, 8, 9, 5, 1, 2}
        };

        AbstractPuzzle puzzle = new RegularSudoku.RegularSudokuBuilder()
                .build();
        puzzle = new ArrowSudoku.ArrowSudokuBuilder(puzzle, arrowSums, arrowPaths)
                .build();

        writeTest(puzzle, fileName, solution);
    }

    @Test
    public void writeDiagonalTest() {
        String fileName = directory + "diagonal.txt";
        int[][] givens = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 7, 9, 6, 0, 0},
                {2, 0, 0, 0, 0, 0, 0, 3, 0},
                {3, 0, 0, 0, 0, 0, 0, 1, 0},
                {0, 4, 0, 0, 0, 0, 0, 2, 0},
                {0, 5, 0, 0, 0, 0, 0, 0, 7},
                {0, 6, 0, 0, 0, 0, 0, 0, 2},
                {0, 0, 7, 8, 9, 0, 0, 0, 5},
                {0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
        AbstractPuzzle write_sudoku = new RegularSudoku.RegularSudokuBuilder()
                .withGivens(givens)
                .build();
        write_sudoku = new ExtraRegionSudoku.ExtraRegionSudokuBuilder(write_sudoku)
                .withDiagonals()
                .build();

        int[][] solution = {
                {7, 8, 6, 3, 5, 1, 2, 4, 9},
                {1, 3, 4, 2, 7, 9, 6, 5, 8},
                {2, 9, 5, 4, 6, 8, 7, 3, 1},
                {3, 7, 2, 9, 8, 4, 5, 1, 6},
                {6, 4, 8, 7, 1, 5, 9, 2, 3},
                {9, 5, 1, 6, 3, 2, 4, 8, 7},
                {5, 6, 3, 1, 4, 7, 8, 9, 2},
                {4, 2, 7, 8, 9, 3, 1, 6, 5},
                {8, 1, 9, 5, 2, 6, 3, 7, 4}
        };

        writeTest(write_sudoku, fileName, solution);
    }

    @Test
    public void writeKillerTest() {
        String fileName = directory + "killer.txt";
        int[][] givens = {
                {0, 0, 0, 0, 5, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {8, 0, 0, 0, 0, 0, 0, 0, 9},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 6, 0, 0, 0, 0}
        };
        int[][] cages = {
                {1, 1, 2, 2, 0, 3, 3, 4, 4},
                {1, 5, 6, 6, 6, 6, 6, 7, 4},
                {1, 5, 8, 8, 9, 11, 11, 7, 4},
                {1, 12, 12, 8, 9, 11, 15, 15, 4},
                {0, 12, 13, 13, 0, 14, 14, 15, 0},
                {16, 12, 12, 17, 18, 19, 15, 15, 21},
                {16, 23, 17, 17, 18, 19, 19, 25, 21},
                {16, 23, 24, 24, 24, 24, 24, 25, 21},
                {16, 16, 27, 27, 0, 28, 28, 21, 21}
        };
        HashMap<Integer, Integer> cageSums = new HashMap<>();
        cageSums.put(1, 20);
        cageSums.put(2, 14);
        cageSums.put(3, 10);
        cageSums.put(4, 20);
        cageSums.put(5, 11);
        cageSums.put(6, 21);
        cageSums.put(7, 13);
        cageSums.put(8, 20);
        cageSums.put(9, 12);
        cageSums.put(11, 12);
        cageSums.put(12, 27);
        cageSums.put(15, 27);
        cageSums.put(13, 9);
        cageSums.put(14, 10);
        cageSums.put(16, 20);
        cageSums.put(17, 8);
        cageSums.put(18, 16);
        cageSums.put(19, 14);
        cageSums.put(21, 19);
        cageSums.put(23, 11);
        cageSums.put(24, 23);
        cageSums.put(25, 13);
        cageSums.put(27, 13);
        cageSums.put(28, 13);

        AbstractPuzzle write_sudoku = new RegularSudoku.RegularSudokuBuilder()
                .withGivens(givens)
                .build();
        write_sudoku = new KillerSudoku.KillerSudokuBuilder(write_sudoku, cages, cageSums)
                .build();

        int[][] solution = {
                {9, 2, 6, 8, 5, 7, 3, 1, 4},
                {5, 4, 1, 6, 3, 2, 9, 7, 8},
                {3, 7, 8, 9, 4, 1, 2, 6, 5},
                {1, 6, 4, 3, 8, 9, 7, 5, 2},
                {8, 5, 7, 2, 1, 4, 6, 3, 9},
                {2, 9, 3, 5, 7, 6, 4, 8, 1},
                {6, 8, 2, 1, 9, 3, 5, 4, 7},
                {4, 3, 5, 7, 2, 8, 1, 9, 6},
                {7, 1, 9, 4, 6, 5, 8, 2, 3}
        };

        writeTest(write_sudoku, fileName, solution);
    }

    @Test
    public void writeKingTest() {
        String fileName = directory + "king.txt";
        int[][] givens = {
                {0, 0, 0, 4, 3, 1, 0, 0, 0},
                {0, 0, 8, 0, 0, 0, 4, 0, 0},
                {0, 3, 0, 0, 0, 0, 0, 1, 0},
                {2, 0, 0, 0, 0, 0, 0, 0, 5},
                {3, 0, 0, 0, 6, 0, 0, 0, 9},
                {9, 0, 0, 0, 0, 0, 0, 0, 2},
                {0, 7, 0, 0, 0, 0, 0, 6, 0},
                {0, 0, 9, 0, 0, 0, 5, 0, 0},
                {0, 0, 0, 8, 5, 3, 0, 0, 0}
        };
        AbstractPuzzle write_sudoku = new RegularSudoku.RegularSudokuBuilder()
                .withGivens(givens)
                .build();
        write_sudoku = new KingSudoku.KingSudokuBuilder(write_sudoku)
                .build();

        int[][] solution = {
                {7, 6, 2, 4, 3, 1, 9, 5, 8},
                {1, 9, 8, 6, 7, 5, 4, 2, 3},
                {4, 3, 5, 9, 2, 8, 7, 1, 6},
                {2, 8, 7, 3, 1, 9, 6, 4, 5},
                {3, 5, 4, 2, 6, 7, 1, 8, 9},
                {9, 1, 6, 5, 8, 4, 3, 7, 2},
                {5, 7, 3, 1, 9, 2, 8, 6, 4},
                {8, 2, 9, 7, 4, 6, 5, 3, 1},
                {6, 4, 1, 8, 5, 3, 2, 9, 7}
        };

        writeTest(write_sudoku, fileName, solution);
    }

    @Test
    public void writeKnightTest() {
        String fileName = directory + "knight.txt";
        int[][] givens = {
                {0, 0, 0, 0, 1, 0, 0, 0, 8},
                {0, 0, 0, 0, 2, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 3, 0, 0, 0},
                {9, 0, 0, 0, 0, 0, 4, 0, 0},
                {0, 8, 0, 0, 0, 0, 0, 5, 0},
                {0, 0, 7, 0, 0, 0, 0, 0, 6},
                {0, 0, 0, 6, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 5, 0, 0, 0, 0},
                {3, 0, 0, 0, 4, 0, 0, 0, 0}
        };
        AbstractPuzzle write_sudoku = new RegularSudoku.RegularSudokuBuilder()
                .withGivens(givens)
                .build();
        write_sudoku = new KnightSudoku.KnightSudokuBuilder(write_sudoku)
                .build();

        int[][] solution = {
                {2, 3, 9, 7, 1, 4, 5, 6, 8},
                {8, 6, 4, 9, 2, 5, 3, 7, 1},
                {7, 1, 5, 8, 6, 3, 9, 4, 2},
                {9, 2, 1, 5, 7, 6, 4, 8, 3},
                {6, 8, 3, 4, 9, 1, 2, 5, 7},
                {4, 5, 7, 3, 8, 2, 1, 9, 6},
                {5, 4, 2, 6, 3, 8, 7, 1, 9},
                {1, 9, 6, 2, 5, 7, 8, 3, 4},
                {3, 7, 8, 1, 4, 9, 6, 2, 5}
        };

        writeTest(write_sudoku, fileName, solution);
    }

    @Test
    public void writeLittleKillerTest() {
        String fileName = directory + "littleKiller.txt";

        String U = "U";
        String N = "N";
        String D = "D";
        String L = "L";
        String R = "R";

        int[] leftRowSums = {0, 0, 30, 11, 17, 15, 12, 2, 0};
        String[] leftRowDirs = {N, N, D, D, D, D, D, D, N};
        int[] topColSums = {0, 5, 14, 17, 20, 23, 0, 0, 0};
        String[] topColDirs = {N, L, L, L, L, L, N, N, N};
        int[] rightRowSums = {0, 3, 6, 13, 25, 23, 28, 0, 0};
        String[] rightRowDirs = {N, U, U, U, U, U, U, N, N};
        int[] bottomColSums = {0, 0, 0, 28, 20, 19, 12, 8, 0};
        String[] bottomColDirs = {N, N, N, R, R, R, R, R, N};

        AbstractPuzzle regular = new RegularSudoku.RegularSudokuBuilder()
                .build();
        AbstractPuzzle littleKiller = new LittleKillerSudoku.LittleKillerSudokuBuilder(regular)
                .withLeftRowSums(leftRowSums, leftRowDirs)
                .withTopColSums(topColSums, topColDirs)
                .withRightRowSums(rightRowSums, rightRowDirs)
                .withBottomColSums(bottomColSums, bottomColDirs)
                .build();

        int[][] solution = {
                {5, 8, 1, 9, 6, 2, 7, 4, 3},
                {6, 9, 4, 7, 1, 3, 8, 5, 2},
                {7, 3, 2, 8, 5, 4, 6, 9, 1},
                {4, 7, 8, 3, 9, 5, 2, 1, 6},
                {1, 5, 9, 6, 2, 8, 4, 3, 7},
                {3, 2, 6, 4, 7, 1, 5, 8, 9},
                {9, 6, 3, 5, 8, 7, 1, 2, 4},
                {8, 1, 7, 2, 4, 9, 3, 6, 5},
                {2, 4, 5, 1, 3, 6, 9, 7, 8}
        };

        writeTest(littleKiller, fileName, solution);
    }

    @Test
    public void writeNonconsecutiveTest() {
        String fileName = directory + "nonconsecutive.txt";
        int[][] givens = {
                {0, 0, 0, 0, 7, 0, 0, 0, 0},
                {0, 6, 0, 5, 0, 1, 0, 9, 0},
                {7, 0, 0, 0, 0, 0, 0, 0, 5},
                {9, 0, 7, 0, 0, 0, 8, 0, 3},
                {0, 0, 0, 1, 0, 3, 0, 0, 0},
                {3, 0, 1, 0, 0, 0, 2, 0, 6},
                {5, 0, 0, 0, 0, 0, 0, 0, 4},
                {0, 4, 0, 3, 0, 5, 0, 2, 0},
                {0, 0, 0, 0, 4, 0, 0, 0, 0}
        };
        AbstractPuzzle write_sudoku = new RegularSudoku.RegularSudokuBuilder()
                .withGivens(givens)
                .build();
        write_sudoku = new NonconsecutiveSudoku.NonconsecutiveSudokuBuilder(write_sudoku)
                .build();

        int[][] solution = {
                {1, 3, 5, 2, 7, 9, 4, 6, 8},
                {4, 6, 8, 5, 3, 1, 7, 9, 2},
                {7, 9, 2, 8, 6, 4, 1, 3, 5},
                {9, 5, 7, 4, 2, 6, 8, 1, 3},
                {6, 2, 4, 1, 8, 3, 5, 7, 9},
                {3, 8, 1, 9, 5, 7, 2, 4, 6},
                {5, 1, 3, 7, 9, 2, 6, 8, 4},
                {8, 4, 6, 3, 1, 5, 9, 2, 7},
                {2, 7, 9, 6, 4, 8, 3, 5, 1}
        };

        writeTest(write_sudoku, fileName, solution);
    }

    @Test
    public void writePalindromeTest() {
        String fileName = directory + "palindrome.txt";

        int[][] givens = {
                {9, 0, 0, 4, 0, 0, 0, 0, 0},
                {0, 5, 0, 0, 8, 0, 0, 0, 4},
                {0, 0, 0, 0, 0, 0, 0, 0, 2},
                {6, 0, 0, 0, 0, 0, 0, 0, 3},
                {0, 3, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 8},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 8, 4, 5, 0, 0, 6, 0, 7}
        };

        List<int[][]> palindromes = new ArrayList<>();

        palindromes.add(new int[][]{
                {0, 1},
                {0, 2},
                {1, 3},
                {2, 4},
                {1, 5},
                {0, 6},
                {0, 7},
                {1, 6},
                {2, 5},
                {3, 5},
                {4, 6},
                {5, 7},
                {5, 8},
                {6, 7},
                {7, 8}
        });

        palindromes.add(new int[][]{
                {1, 0},
                {2, 0},
                {3, 1},
                {4, 2},
                {5, 1},
                {6, 0},
                {7, 0},
                {6, 1},
                {5, 2},
                {5, 3},
                {6, 4},
                {7, 5},
                {8, 5},
                {7, 6},
                {8, 7}
        });

        palindromes.add(new int[][]{
                {3, 1},
                {2, 2},
                {1, 3}
        });

        AbstractPuzzle puzzle = new RegularSudoku.RegularSudokuBuilder()
                .withGivens(givens)
                .build();
        puzzle = new PalindromeSudoku.PalindromeSudokuBuilder(puzzle, palindromes)
                .build();

        int[][] solution = {
                {9, 1, 3, 4, 2, 7, 8, 5, 6},
                {2, 5, 6, 9, 8, 1, 3, 7, 4},
                {4, 7, 8, 3, 6, 5, 9, 1, 2},
                {6, 9, 1, 2, 5, 8, 7, 4, 3},
                {7, 3, 2, 6, 9, 4, 1, 8, 5},
                {8, 4, 5, 1, 7, 3, 2, 6, 9},
                {1, 2, 9, 7, 4, 6, 5, 3, 8},
                {5, 6, 7, 8, 3, 2, 4, 9, 1},
                {3, 8, 4, 5, 1, 9, 6, 2, 7}
        };

        writeTest(puzzle, fileName, solution);
    }

    @Test
    public void writeSandwichTest() {
        String fileName = directory + "sandwich.txt";
        int[][] givens = emptyGrid(9);
        givens[4][3] = 1;

        int[] rowSums = {16, 7, 3, 14, 11, 21, 19, 33, 2};
        int[] colSums = {8, 23, 16, 15, 23, 13, 30, 27, 3};
        AbstractPuzzle write_sudoku = new RegularSudoku.RegularSudokuBuilder()
                .withGivens(givens)
                .build();
        write_sudoku = new SandwichSudoku.SandwichSudokuBuilder(write_sudoku)
                .withRowSums(rowSums)
                .withColSums(colSums)
                .build();

        int[][] solution = {
                {8, 5, 9, 2, 7, 4, 3, 1, 6},
                {3, 1, 7, 9, 5, 6, 2, 4, 8},
                {4, 6, 2, 8, 1, 3, 9, 5, 7},
                {5, 2, 3, 7, 4, 9, 8, 6, 1},
                {9, 7, 4, 1, 6, 8, 5, 2, 3},
                {6, 8, 1, 3, 2, 5, 4, 7, 9},
                {2, 9, 5, 6, 8, 1, 7, 3, 4},
                {1, 4, 8, 5, 3, 7, 6, 9, 2},
                {7, 3, 6, 4, 9, 2, 1, 8, 5}
        };

        writeTest(write_sudoku, fileName, solution);
    }

    @Test
    public void writeSkyscraperTest() {
        String fileName = directory + "skyscraper.txt";
        int[][] givens = {
                {1, 0, 0, 0, 0, 2, 0, 0, 8},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {3, 0, 0, 6, 0, 0, 4, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {5, 0, 2, 0, 0, 3, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {7, 0, 0, 8, 0, 0, 2, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {9, 0, 0, 0, 0, 4, 0, 0, 6}
        };
        int[] leftRowSums = {0, 2, 0, 4, 0, 6, 0, 8, 0};
        int[] topColSums = {0, 0, 0, 0, 5, 0, 0, 0, 0};

        AbstractPuzzle write_sudoku = new RegularSudoku.RegularSudokuBuilder()
                .withGivens(givens)
                .build();
        write_sudoku = new SkyscraperSudoku.SkyscraperSudokuBuilder(write_sudoku)
                .withLeftRowSums(leftRowSums)
                .withTopColSums(topColSums)
                .build();

        int[][] solution = {
                {1, 4, 7, 9, 3, 2, 6, 5, 8},
                {8, 2, 6, 1, 4, 5, 9, 3, 7},
                {3, 5, 9, 6, 7, 8, 4, 1, 2},
                {6, 7, 8, 4, 1, 9, 3, 2, 5},
                {5, 9, 2, 7, 8, 3, 1, 6, 4},
                {4, 1, 3, 2, 5, 6, 7, 8, 9},
                {7, 6, 5, 8, 9, 1, 2, 4, 3},
                {2, 3, 4, 5, 6, 7, 8, 9, 1},
                {9, 8, 1, 3, 2, 4, 5, 7, 6}
        };

        writeTest(write_sudoku, fileName, solution);
    }

    @Test
    public void writeThermoTest() {
        String fileName = directory + "thermo.txt";
        int[][] givens = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                givens[i][j] = 0;
            }
        }
        List<int[][]> thermos = new ArrayList<>();
        thermos.add(new int[][]{
                {3, 3},
                {3, 2},
                {3, 1},
                {3, 0},
                {2, 0},
                {1, 0},
                {0, 0}
        });
        thermos.add(new int[][]{
                {3, 3},
                {2, 3},
                {1, 3},
                {0, 3},
                {0, 2},
                {0, 1}
        });
        thermos.add(new int[][]{
                {0, 5},
                {1, 5}
        });
        thermos.add(new int[][]{
                {0, 6},
                {0, 7},
                {1, 7},
                {2, 7},
                {2, 6},
                {2, 5}
        });
        thermos.add(new int[][]{
                {7, 0},
                {6, 0},
                {5, 0},
                {5, 1}
        });
        thermos.add(new int[][]{
                {7, 1},
                {7, 2}
        });
        thermos.add(new int[][]{
                {6, 2},
                {5, 2}
        });
        thermos.add(new int[][]{
                {4, 4},
                {4, 5},
                {4, 6},
                {4, 7},
                {4, 8}
        });
        thermos.add(new int[][]{
                {4, 4},
                {5, 4},
                {6, 4},
                {7, 4}
        });
        thermos.add(new int[][]{
                {7, 8},
                {6, 8},
                {5, 8}
        });
        thermos.add(new int[][]{
                {7, 8},
                {8, 8},
                {8, 7},
                {8, 6},
                {8, 5},
                {8, 4}
        });
        AbstractPuzzle write_sudoku = new RegularSudoku.RegularSudokuBuilder()
                .withGivens(givens)
                .build();
        write_sudoku = new ThermoSudoku.ThermoSudokuBuilder(write_sudoku, thermos)
                .build();

        int[][] solution = {
                {9, 8, 7, 6, 3, 4, 1, 2, 5},
                {6, 2, 1, 5, 7, 8, 3, 4, 9},
                {5, 4, 3, 2, 1, 9, 8, 7, 6},
                {4, 3, 2, 1, 8, 6, 5, 9, 7},
                {1, 7, 5, 9, 2, 3, 4, 6, 8},
                {8, 9, 6, 7, 4, 5, 2, 1, 3},
                {7, 6, 4, 8, 5, 1, 9, 3, 2},
                {3, 5, 9, 4, 6, 2, 7, 8, 1},
                {2, 1, 8, 3, 9, 7, 6, 5, 4}
        };

        writeTest(write_sudoku, fileName, solution);
    }

    @Test
    public void writeXSumTest() {
        String fileName = directory + "xsum.txt";
        int[] leftRowSums = {-1, 8, -1, 17, -1, 30, -1, 28, -1};
        int[] topColSums = {-1, 27, -1, 11, -1, 21, 16, -1, -1};
        int[] rightRowSums = {-1, 8, -1, 17, -1, 30, -1, 28, -1};
        int[] bottomColSums = {-1, 27, -1, 11, -1, -1, 16, -1, -1};

        AbstractPuzzle write_sudoku = new RegularSudoku.RegularSudokuBuilder()
                .build();
        write_sudoku = new XSumSudoku.XSumSudokuBuilder(write_sudoku)
                .withLeftRowSums(leftRowSums)
                .withTopColSums(topColSums)
                .withRightRowSums(rightRowSums)
                .withBottomColSums(bottomColSums)
                .build();

        int[][] solution = {
                {8, 5, 6, 2, 1, 4, 3, 7, 9},
                {3, 4, 1, 9, 7, 5, 8, 6, 2},
                {7, 9, 2, 8, 6, 3, 5, 4, 1},
                {4, 1, 7, 5, 2, 9, 6, 8, 3},
                {9, 8, 5, 6, 3, 1, 7, 2, 4},
                {6, 2, 3, 4, 8, 7, 1, 9, 5},
                {2, 7, 4, 1, 5, 6, 9, 3, 8},
                {5, 3, 9, 7, 4, 8, 2, 1, 6},
                {1, 6, 8, 3, 9, 2, 4, 5, 7}
        };

        writeTest(write_sudoku, fileName, solution);
    }





    @Test
    public void writeThermoDiagonalTest() {
        int[][] givens = emptyGrid(9);
        List<int[][]> thermos = new ArrayList<>();
        thermos.add(new int[][]{
                {0, 0},
                {1, 0},
                {2, 0},
                {3, 0}
        });
        thermos.add(new int[][]{
                {8, 0},
                {7, 0},
                {6, 0},
                {5, 0}
        });
        thermos.add(new int[][]{
                {0, 3},
                {0, 4},
                {0, 5},
                {0, 6},
                {0, 7},
                {0, 8},
                {1, 8}
        });
        thermos.add(new int[][]{
                {1, 3},
                {1, 4},
                {1, 5},
                {1, 6}
        });
        thermos.add(new int[][]{
                {4, 4},
                {4, 5},
                {4, 6},
                {4, 7},
                {4, 8}
        });
        thermos.add(new int[][]{
                {5, 4},
                {6, 3}
        });
        thermos.add(new int[][]{
                {8, 3},
                {8, 4},
                {8, 5},
                {8, 6},
                {8, 7},
                {8, 8}
        });

        AbstractPuzzle regular = new RegularSudoku.RegularSudokuBuilder()
                .withGivens(givens)
                .build();
        AbstractPuzzle diagonal = new ExtraRegionSudoku.ExtraRegionSudokuBuilder(regular)
                .withDiagonals()
                .build();
        AbstractPuzzle thermo = new ThermoSudoku.ThermoSudokuBuilder(diagonal, thermos)
                .build();

        int[][] solution = {
                {1, 8, 9, 2, 3, 4, 5, 6, 7},
                {2, 5, 4, 1, 6, 7, 9, 3, 8},
                {3, 7, 6, 5, 8, 9, 1, 2, 4},
                {6, 3, 2, 7, 4, 5, 8, 9, 1},
                {8, 1, 7, 9, 2, 3, 4, 5, 6},
                {9, 4, 5, 6, 1, 8, 2, 7, 3},
                {7, 6, 8, 4, 9, 2, 3, 1, 5},
                {5, 9, 3, 8, 7, 1, 6, 4, 2},
                {4, 2, 1, 3, 5, 6, 7, 8, 9}
        };

        String fileName1 = directory + "diagonalThermo.txt";

        writeTest(thermo, fileName1, solution);

        AbstractPuzzle regular2 = new RegularSudoku.RegularSudokuBuilder()
                .withGivens(givens)
                .build();
        AbstractPuzzle thermo2 = new ThermoSudoku.ThermoSudokuBuilder(regular2, thermos)
                .build();
        AbstractPuzzle diagonal2 = new ExtraRegionSudoku.ExtraRegionSudokuBuilder(thermo2)
                .withDiagonals()
                .build();

        String fileName2 = directory + "thermoDiagonal.txt";

        writeTest(diagonal2, fileName2, solution);
    }
}
