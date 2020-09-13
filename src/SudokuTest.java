import static org.junit.jupiter.api.Assertions.assertEquals;

public class SudokuTest {
    public void compareSolutions(int[][] solverSolution, int[][] solution) {
        for (int i = 0; i < solution.length; i++) {
            for (int j = 0; j < solution.length; j++) {
                assertEquals(solverSolution[i][j], solution[i][j]);
            }
        }
    }

    protected int[][] emptyGrid(int n) {
        int[][] grid = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = 0;
            }
        }
        return grid;
    }

    protected void printSolution(int[][] solution) {
        for (int[] row : solution) {
            for (int cell : row) {
                System.out.print(cell);
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
