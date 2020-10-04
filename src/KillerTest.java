import com.google.ortools.sat.IntVar;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class KillerTest extends SudokuTest {

    @Test
    public void testKiller1() {
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

        AbstractPuzzle regular = new RegularSudoku.RegularSudokuBuilder()
                .withGivens(givens)
                .build();
        AbstractPuzzle killer = new KillerSudoku.KillerSudokuBuilder(regular)
                .withCagesGrid(cages)
                .withCageSumInts(cageSums)
                .build();

        long[][] solverSolution = killer.solve();

        long[][] solution = {
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

        compareSolutions(solverSolution, solution);
    }

    @Test
    public void testOneVariableSums() {
        int[][] givens = emptyGrid(9);
        givens[3][7] = 8;

        AbstractPuzzle puzzle = new RegularSudoku.RegularSudokuBuilder()
                .withGivens(givens)
                .build();

        IntVar x = puzzle.makeRangeVar(1, 72, "x");

        int[][] cages = {
                {0, 1, 0, 0, 0, 2, 0, 0, 0},
                {1, 1, 0, 0, 0, 2, 0, 0, 0},
                {0, 0, 0, 0, 0, 2, 3, 0, 0},
                {4, 4, 0, 5, 5, 0, 3, 0, 0},
                {0, 0, 6, 0, 0, 7, 0, 0, 0},
                {0, 0, 6, 0, 0, 7, 0, 0, 0},
                {8, 8, 6, 9, 9, 0, 0, 0, 0},
                {10, 10, 6, 0, 0, 0, 0, 0, 0},
                {6, 6, 6, 6, 0, 0, 0, 0, 0}
        };

        HashMap<Integer, IntVar> cageSums = new HashMap<>();

        cageSums.put(1, x);
        cageSums.put(2, puzzle.makeConstVar(8, "sum2"));
        cageSums.put(3, x);
        cageSums.put(4, x);
        cageSums.put(5, x);
        cageSums.put(6, puzzle.makeConstVar(43, "sum6"));
        cageSums.put(7, puzzle.makeConstVar(8, "sum7"));
        cageSums.put(8, x);
        cageSums.put(9, x);
        cageSums.put(10, puzzle.varAdd(x, 1));

        puzzle = new KillerSudoku.KillerSudokuBuilder(puzzle)
                .withCagesGrid(cages)
                .withCageSumVars(cageSums)
                .build();

        IntVar o = null;

        String U = "U";
        String D = "D";
        String N = "";

        IntVar[] leftRowSums = {o, o, o, x, o, o, o, o, o};
        String[] leftRowDirs = {N, N, N, U, N, N, N, N, N};

        IntVar xMul3Div2 = puzzle.varDivExact(puzzle.varMul(x, 3), 2);

        IntVar[] rightRowSums = {o, o, o, o, o, xMul3Div2, x, puzzle.varDivExact(x, 2), o};
        String[] rightRowDirs = {N, N, N, N, N, D, D, D, N};

        IntVar xMul5Div2 = puzzle.varDivExact(puzzle.varMul(x, 5), 2);

        puzzle = new LittleKillerSudoku.LittleKillerSudokuBuilder(puzzle)
                .withLeftRowSums(leftRowSums, leftRowDirs)
                .withRightRowSums(rightRowSums, rightRowDirs)
                .withUpDiagonal(xMul5Div2)
                .build();

        long[][] solution = {
                {8, 5, 2, 9, 7, 1, 6, 3, 4},
                {4, 1, 6, 5, 8, 3, 7, 2, 9},
                {7, 9, 3, 2, 6, 4, 1, 5, 8},
                {6, 4, 1, 7, 3, 5, 9, 8, 2},
                {2, 3, 9, 8, 1, 6, 5, 4, 7},
                {5, 8, 7, 4, 9, 2, 3, 1, 6},
                {3, 7, 5, 6, 4, 8, 2, 9, 1},
                {9, 2, 8, 1, 5, 7, 4, 6, 3},
                {1, 6, 4, 3, 2, 9, 8, 7, 5}
        };

//        puzzle.printSolution();
        compareSolutions(puzzle.solve(), solution);

    }

    @Test
    public void testVariableSums() {
        int[][] givens = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {2, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 6, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 2, 0, 0, 0}
        };

        AbstractPuzzle puzzle = new RegularSudoku.RegularSudokuBuilder()
                .withGivens(givens)
                .build();

        IntVar m = puzzle.makeRangeVar(1, 45, "m");
        IntVar n = puzzle.makeRangeVar(1, 45, "n");

        int[][] cages = {
                {0, 0, 0, 0, 0, 0, 1, 2, 0},
                {0, 0, 0, 0, 0, 0, 1, 2, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 3},
                {0, 0, 0, 0, 0, 4, 4, 0, 0},
                {0, 0, 5, 5, 5, 0, 4, 0, 0},
                {0, 0, 0, 6, 6, 0, 0, 0, 0},
                {7, 0, 8, 8, 0, 0, 0, 9, 0},
                {7, 0, 8, 0, 0, 0, 0, 9, 9},
                {7, 0, 0, 0, 0, 0, 0, 0, 0}
        };

        HashMap<Integer, IntVar> cageSums = new HashMap<>();
        cageSums.put(1, n);
        cageSums.put(2, n);
        cageSums.put(3, m);
        cageSums.put(4, m);
        cageSums.put(5, n);
        cageSums.put(6, m);
        cageSums.put(7, n);
        cageSums.put(8, m);
        cageSums.put(9, m);

        puzzle = new KillerSudoku.KillerSudokuBuilder(puzzle)
                .withCagesGrid(cages)
                .withCageSumVars(cageSums)
                .build();

        IntVar o = null;

        String U = "U";
        String D = "D";
        String N = "";

        IntVar[] leftRowSums = {o, m, n, m, n, o, m, o, o};
        String[] leftRowDirs = {N, U, U, U, U, N, D, N, N};

        IntVar[] rightRowSums = {o, o, o, m, o, n, o, o, o};
        String[] rightRowDirs = {N, N, N, D, N, D, N, N, N};

        puzzle = new LittleKillerSudoku.LittleKillerSudokuBuilder(puzzle)
                .withLeftRowSums(leftRowSums, leftRowDirs)
                .withRightRowSums(rightRowSums, rightRowDirs)
                .build();

        long[][] solution = {
                {9, 7, 4, 3, 1, 8, 6, 5, 2},
                {6, 3, 5, 2, 4, 9, 7, 8, 1},
                {2, 1, 8, 5, 6, 7, 3, 4, 9},
                {4, 5, 1, 9, 8, 3, 2, 6, 7},
                {8, 2, 7, 1, 5, 6, 4, 9, 3},
                {3, 9, 6, 7, 2, 4, 8, 1, 5},
                {7, 8, 2, 4, 9, 5, 1, 3, 6},
                {5, 6, 3, 8, 7, 1, 9, 2, 4},
                {1, 4, 9, 6, 3, 2, 5, 7, 8}
        };

        compareSolutions(puzzle.solve(), solution);
    }

    private IntVar getChoiceVar(AbstractPuzzle puzzle, int lb, int ub, IntVar m, IntVar n) {
        IntVar var = puzzle.makeRangeVar(lb, ub, "" + lb + "_" + ub);
        puzzle.enforceBool(puzzle.varOr(puzzle.varEquals(var, m), puzzle.varEquals(var, n)));
        return var;
    }

    @Test
    public void testVariablesSumsDecisionLogic() {
        int[][] givens = emptyGrid(9);
        givens[6][4] = 9;

        AbstractPuzzle puzzle = new RegularSudoku.RegularSudokuBuilder()
                .withGivens(givens)
                .build();

        IntVar M = puzzle.makeRangeVar(1, 72, "M");
        IntVar N = puzzle.makeRangeVar(1, 72, "N");

        int[][] cages = {
                {00, 00, 00, 21, 21, 21, 31, 00, 00},
                {11, 00, 12, 22, 23, 23, 31, 32, 00},
                {11, 00, 12, 22, 22, 23, 00, 32, 00},
                {11, 00, 41, 51, 51, 52, 00, 61, 00},
                {00, 00, 41, 53, 54, 52, 00, 61, 00},
                {00, 42, 53, 53, 54, 54, 00, 00, 00},
                {71, 42, 00, 81, 00, 00, 91, 91, 92},
                {71, 71, 00, 81, 00, 00, 93, 93, 92},
                {00, 00, 72, 72, 00, 00, 94, 94, 92}
        };

        Set<Integer> cageSet = new HashSet<>();
        for (int[] row : cages) {
            for (int cage : row) {
                cageSet.add(cage);
            }
        }

        HashMap<Integer, IntVar> cageSums = new HashMap<>();

        for (int cage : cageSet) {
            cageSums.put(cage, getChoiceVar(puzzle, 1, 72, M, N));
        }

        puzzle = new KillerSudoku.KillerSudokuBuilder(puzzle)
                .withCagesGrid(cages)
                .withCageSumVars(cageSums)
                .build();

        IntVar o = null;

        String L = "L";
        String R = "R";
        String U = "U";
        String D = "D";
        String E = "";

        IntVar[] leftRowSums = {o, o, o, o, getChoiceVar(puzzle, 1, 72, M, N), o, o, o, o};
        String[] leftRowDirs = {E, E, E, E, U, E, E, E, E};

        IntVar[] rightRowSums = {o, o, o, getChoiceVar(puzzle, 1, 72, M, N), o, o, getChoiceVar(puzzle, 1, 72, M, N), o, o};
        String[] rightRowDirs = {E, E, E, U, E, E, U, E, E};

        IntVar[] bottomColSums = {o, o, getChoiceVar(puzzle, 1, 72, M, N), getChoiceVar(puzzle, 1, 72, M, N), getChoiceVar(puzzle, 1, 72, M, N), o, o, o, o};
        String[] bottomColDirs = {E, E, L, L, R, E, E, E, E};

        puzzle = new LittleKillerSudoku.LittleKillerSudokuBuilder(puzzle)
                .withLeftRowSums(leftRowSums, leftRowDirs)
                .withRightRowSums(rightRowSums, rightRowDirs)
                .withBottomColSums(bottomColSums, bottomColDirs)
                .build();

        long[][] solution = {
                {9, 7, 3, 1, 8, 6, 2, 5, 4},
                {1, 5, 2, 7, 4, 9, 8, 6, 3},
                {6, 4, 8, 5, 3, 2, 1, 9, 7},
                {3, 2, 4, 9, 1, 7, 5, 8, 6},
                {7, 1, 6, 4, 5, 8, 3, 2, 9},
                {8, 9, 5, 6, 2, 3, 4, 7, 1},
                {5, 6, 1, 2, 9, 4, 7, 3, 8},
                {2, 3, 9, 8, 7, 1, 6, 4, 5},
                {4, 8, 7, 3, 6, 5, 9, 1, 2}
        };
        
        compareSolutions(puzzle.solve(), solution);
    }
}
