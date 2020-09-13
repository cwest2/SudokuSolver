import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CombinationTest extends SudokuTest {

    @Test
    public void testDiagonalThermo() {
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

        AbstractPuzzle regular = new RegularSudoku.RegularSudokuBuilder()
                .withGivens(givens)
                .build();
        AbstractPuzzle diagonal = new ExtraRegionSudoku.ExtraRegionSudokuBuilder(regular)
                .withDiagonals()
                .build();
        AbstractPuzzle thermo = new ThermoSudoku.ThermoSudokuBuilder(diagonal, thermos)
                .build();

        compareSolutions(thermo.solve(), solution);

        AbstractPuzzle regular2 = new RegularSudoku.RegularSudokuBuilder()
                .withGivens(givens)
                .build();
        AbstractPuzzle thermo2 = new ThermoSudoku.ThermoSudokuBuilder(regular2, thermos)
                .build();
        AbstractPuzzle diagonal2 = new ExtraRegionSudoku.ExtraRegionSudokuBuilder(thermo2)
                .withDiagonals()
                .build();

        compareSolutions(diagonal2.solve(), solution);
    }

    @Test
    public void testSandwichNonconsecutive() {
        int[][] givens = emptyGrid(9);
        givens[4][4] = 4;

        int[] rowSums = {18, 0, 21, -1, 15, -1, 26, 13, 15};
        int[] colSums = {0, 0, 0, -1, 0, -1, 10, 23, 22};

        int[][] solution = {
                {3, 6, 9, 7, 5, 2, 4, 1, 8},
                {8, 4, 1, 9, 3, 6, 2, 7, 5},
                {5, 2, 7, 1, 8, 4, 6, 3, 9},
                {2, 7, 5, 3, 6, 9, 1, 8, 4},
                {6, 9, 3, 8, 4, 1, 7, 5, 2},
                {4, 1, 8, 5, 2, 7, 3, 9, 6},
                {1, 8, 4, 2, 7, 5, 9, 6, 3},
                {9, 3, 6, 4, 1, 8, 5, 2, 7},
                {7, 5, 2, 6, 9, 3, 8, 4, 1}
        };

        AbstractPuzzle regular = new RegularSudoku.RegularSudokuBuilder()
                .withGivens(givens)
                .build();
        AbstractPuzzle nonconsecutive = new NonconsecutiveSudoku.NonconsecutiveSudokuBuilder(regular)
                .build();
        AbstractPuzzle sandwich = new SandwichSudoku.SandwichSudokuBuilder(nonconsecutive)
                .withRowSums(rowSums)
                .withColSums(colSums)
                .build();

        compareSolutions(sandwich.solve(), solution);

        AbstractPuzzle regular2 = new RegularSudoku.RegularSudokuBuilder()
                .withGivens(givens)
                .build();
        AbstractPuzzle sandwich2 = new SandwichSudoku.SandwichSudokuBuilder(regular2)
                .withRowSums(rowSums)
                .withColSums(colSums)
                .build();
        AbstractPuzzle nonconsecutive2 = new NonconsecutiveSudoku.NonconsecutiveSudokuBuilder(sandwich2)
                .build();

        compareSolutions(nonconsecutive2.solve(), solution);
    }

    @Test
    public void testKillerThermoKnight() {
        int[][] givens = emptyGrid(9);
        int[][] cages = {
                {1, 2, 0, 0, 0, 0, 0, 0, 0},
                {1, 2, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {3, 3, 0, 0, 0, 4, 0, 5, 6},
                {7, 7, 0, 0, 0, 4, 0, 5, 6},
                {0, 0, 0, 0, 8, 8, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 9},
                {0, 0, 10, 11, 11, 0, 0, 0, 9},
                {0, 0, 10, 0, 0, 0, 0, 0, 0}
        };
        HashMap<Integer, Integer> cageSums = new HashMap<>();
        for (int i = 1; i <= 11; i++) {
            cageSums.put(i, 10);
        }
        List<int[][]> thermos = new ArrayList<>();
        thermos.add(new int[][]{
                {0, 0},
                {1, 0}
        });
        thermos.add(new int[][]{
                {0, 1},
                {1, 1}
        });
        thermos.add(new int[][]{
                {3, 0},
                {3, 1}
        });
        thermos.add(new int[][]{
                {4, 0},
                {4, 1}
        });
        thermos.add(new int[][]{
                {1, 4},
                {0, 5},
                {1, 6},
                {1, 7}
        });

        int[][] solution = {
                {1, 3, 5, 9, 4, 2, 7, 8, 6},
                {9, 7, 8, 6, 1, 5, 3, 4, 2},
                {6, 4, 2, 3, 7, 8, 9, 1, 5},
                {4, 6, 3, 5, 8, 9, 2, 7, 1},
                {2, 8, 7, 4, 6, 1, 5, 3, 9},
                {5, 1, 9, 2, 3, 7, 4, 6, 8},
                {3, 9, 1, 8, 5, 4, 6, 2, 7},
                {7, 2, 4, 1, 9, 6, 8, 5, 3},
                {8, 5, 6, 7, 2, 3, 1, 9, 4}
        };

        AbstractPuzzle regular = new RegularSudoku.RegularSudokuBuilder()
                .withGivens(givens)
                .build();
        AbstractPuzzle thermo = new ThermoSudoku.ThermoSudokuBuilder(regular, thermos)
                .build();
        AbstractPuzzle knight = new KnightSudoku.KnightSudokuBuilder(thermo)
                .build();
        AbstractPuzzle killer = new KillerSudoku.KillerSudokuBuilder(knight, cages, cageSums)
                .build();

        int[][] solverSolution = killer.solve();

        compareSolutions(solverSolution, solution);

        AbstractPuzzle regular2 = new RegularSudoku.RegularSudokuBuilder()
                .withGivens(givens)
                .build();
        AbstractPuzzle killer2 = new KillerSudoku.KillerSudokuBuilder(regular2, cages, cageSums)
                .build();
        AbstractPuzzle knight2 = new KnightSudoku.KnightSudokuBuilder(killer2)
                .build();
        AbstractPuzzle thermo2 = new ThermoSudoku.ThermoSudokuBuilder(knight2, thermos)
                .build();

        compareSolutions(thermo2.solve(), solution);
    }

    @Test
    public void testKingKnightNonconsecutive() {
        int[][] givens = emptyGrid(9);
        givens[4][2] = 1;
        givens[5][6] = 2;

        int[][] solution = {
                {4, 8, 3, 7, 2, 6, 1, 5, 9},
                {7, 2, 6, 1, 5, 9, 4, 8, 3},
                {1, 5, 9, 4, 8, 3, 7, 2, 6},
                {8, 3, 7, 2, 6, 1, 5, 9, 4},
                {2, 6, 1, 5, 9, 4, 8, 3, 7},
                {5, 9, 4, 8, 3, 7, 2, 6, 1},
                {3, 7, 2, 6, 1, 5, 9, 4, 8},
                {6, 1, 5, 9, 4, 8, 3, 7, 2},
                {9, 4, 8, 3, 7, 2, 6, 1, 5}
        };

        AbstractPuzzle regular = new RegularSudoku.RegularSudokuBuilder()
                .withGivens(givens)
                .build();
        AbstractPuzzle king = new KingSudoku.KingSudokuBuilder(regular)
                .build();
        AbstractPuzzle knight = new KnightSudoku.KnightSudokuBuilder(king)
                .build();
        AbstractPuzzle nonconsecutive = new NonconsecutiveSudoku.NonconsecutiveSudokuBuilder(knight)
                .build();

        compareSolutions(nonconsecutive.solve(), solution);

        AbstractPuzzle regular2 = new RegularSudoku.RegularSudokuBuilder()
                .withGivens(givens)
                .build();
        AbstractPuzzle nonconsecutive2 = new NonconsecutiveSudoku.NonconsecutiveSudokuBuilder(regular2)
                .build();
        AbstractPuzzle knight2 = new KnightSudoku.KnightSudokuBuilder(nonconsecutive2)
                .build();
        AbstractPuzzle king2 = new KingSudoku.KingSudokuBuilder(knight2)
                .build();

        compareSolutions(king2.solve(), solution);
    }

    @Test
    public void killerLittleKiller() {
        int[][] givens = emptyGrid(9);
        int[][] cages = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 0, 0, 0, 0, 0, 0, 0},
                {2, 1, 3, 0, 0, 0, 0, 0, 0},
                {2, 2, 3, 3, 0, 0, 0, 0, 0},
                {4, 2, 5, 3, 6, 0, 0, 0, 0},
                {4, 4, 5, 5, 6, 6, 0, 0, 0},
                {4, 7, 7, 7, 7, 6, 6, 0, 0}
        };
        HashMap<Integer, Integer> cageSums = new HashMap<>();
        cageSums.put(1, 25);
        cageSums.put(2, 24);
        cageSums.put(3, 24);
        cageSums.put(4, 21);
        cageSums.put(5, 15);
        cageSums.put(6, 27);
        cageSums.put(7, 23);

        String N = "";
        String R = "R";
        String D = "D";

        int[] leftRowSums = {36, 0, 0, 0, 0, 0, 0, 0, 0};
        String[] leftRowDirs = {D, N, N, N, N, N, N, N, N};
        int[] topColSums = {46, 0, 0, 0, 0, 0, 0, 0, 0};
        String[] topColDirs = {R, N, N, N, N, N, N, N, N};
        int[] rightRowSums = {0, 0, 0, 0, 0, 0, 0, 0, 0};
        String[] rightRowDirs = {N, N, N, N, N, N, N, N, N};
        int[] bottomColSums = {0, 0, 0, 0, 0, 0, 0, 0, 0};
        String[] bottomColDirs = {N, N, N, N, N, N, N, N, N};

        AbstractPuzzle regular = new RegularSudoku.RegularSudokuBuilder()
                .withGivens(givens)
                .build();
        AbstractPuzzle killer = new KillerSudoku.KillerSudokuBuilder(regular, cages, cageSums)
                .build();
        AbstractPuzzle littleKiller = new LittleKillerSudoku.LittleKillerSudokuBuilder(killer)
                .withDownDiagonal(34)
                .withLeftRowSums(leftRowSums, leftRowDirs)
                .withTopColSums(topColSums, topColDirs)
                .build();

        int[][] solution = {
                {2, 4, 3, 1, 9, 7, 5, 8, 6},
                {6, 1, 8, 3, 4, 5, 2, 7, 9},
                {9, 5, 7, 6, 2, 8, 1, 3, 4},
                {3, 7, 1, 5, 6, 2, 4, 9, 8},
                {8, 6, 5, 4, 1, 9, 3, 2, 7},
                {4, 9, 2, 8, 7, 3, 6, 1, 5},
                {7, 3, 4, 9, 5, 1, 8, 6, 2},
                {5, 8, 9, 2, 3, 6, 7, 4, 1},
                {1, 2, 6, 7, 8, 4, 9, 5, 3}
        };

        compareSolutions(littleKiller.solve(), solution);
    }
}
