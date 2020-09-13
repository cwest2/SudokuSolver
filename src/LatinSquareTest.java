import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class LatinSquareTest {

    @Test
    public void testLatinSquare() {
        int[][] givens = new int[9][9];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                givens[i][j] = (i + j) % 9 + 1;
            }
        }
        for (int i = 0; i < 9; i++) {
            givens[i][8] = 0;
            givens[8][i] = 0;
        }

        AbstractPuzzle latinSquare = new LatinSquare.LatinSquareBuilder().withGivens(givens).build();

        int[][] solution = latinSquare.solve();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertEquals(solution[i][j], (i + j) % 9 + 1);
            }
        }
    }

    @Test
    public void testLatinSquareDimFive() {
        int[][] givens = new int[5][5];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                givens[i][j] = (i + j) % 5 + 1;
            }
        }
        for (int i = 0; i < 5; i++) {
            givens[i][4] = 0;
            givens[4][i] = 0;
        }

        AbstractPuzzle latinSquare = new LatinSquare.LatinSquareBuilder()
                .withGivens(givens)
                .withSize(5)
                .build();

        int[][] solution = latinSquare.solve();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                assertEquals(solution[i][j], (i + j) % 5 + 1);
            }
        }
    }
}
