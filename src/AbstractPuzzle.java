import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public abstract class AbstractPuzzle implements IPuzzle<Model> {
    Model model;

    @Override
    public Model getModel() {
        if (model == null) {
            buildModel();
        }
        return model;
    }

    public abstract int getN();

    public abstract IntVar[][] getRows();

    public abstract IntVar[][] getCols();

    @Override
    public void printSolution() {
        buildModel();
        if (!model.getSolver().solve()) {
            System.out.println("No solution found!");
            return;
        }

        StringBuilder st = new StringBuilder();
        st.append("\t");

        int n = getN();
        IntVar[][] rows = getRows();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                st.append(rows[i][j].getValue()).append("\t");
            }
            st.append("\n\t");
        }

        System.out.println(st.toString());
    }

    @Override
    public int[][] solve() {
        buildModel();
        if (!model.getSolver().solve()) {
            throw new RuntimeException("No solution found!");
        }

        int n = getN();
        IntVar[][] rows = getRows();
        int[][] solution = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                solution[i][j] = rows[i][j].getValue();
            }
        }

        return solution;
    }

    protected abstract void buildDokeFile(StringBuilder sb);

    public void printDokeFile() {
        StringBuilder sb = new StringBuilder();
        buildDokeFile(sb);
        System.out.println(sb.toString());
    }

    public void writeDokeFile(String fileName) {
        StringBuilder sb = new StringBuilder();
        try {
            buildDokeFile(sb);
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(sb.toString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void buildRow(StringBuilder sb, int[] row) {
        int n = getN();
        for (int j = 0; j < n; j++) {
            sb.append(row[j]);
            if (j < n - 1) {
                sb.append(" ");
            }
        }
        sb.append("\n");
    }

    protected void buildRow(StringBuilder sb, String[] row) {
        int n = row.length;
        for (int j = 0; j < n; j++) {
            sb.append(row[j]);
            if (j < n - 1) {
                sb.append(" ");
            }
        }
        sb.append("\n");
    }

    protected void buildGrid(StringBuilder sb, int[][] grid) {
        int n = grid.length;
        for (int i = 0; i < n; i++) {
            buildRow(sb, grid[i]);
        }
    }

    protected void buildGrid(StringBuilder sb, String[][] grid) {
        int n = grid.length;
        for (int i = 0; i < n; i++) {
            buildRow(sb, grid[i]);
        }
    }

    protected IntVar[] makeReversedArray(IntVar[] row) {
        int n = row.length;
        IntVar[] rev = new IntVar[n];
        for (int i = 0; i < n; i++) {
            rev[n - i - 1] = row[i];
        }
        return rev;
    }

    protected IntVar[][] makeRowReversedGrid(IntVar[][] grid) {
        int n = grid.length;
        IntVar[][] rev = new IntVar[n][];
        for (int i = 0; i < n; i++) {
            rev[i] = makeReversedArray(grid[i]);
        }
        return rev;
    }

    protected IntVar[][] makeColReversedGrid(IntVar[][] grid) {
        int n = grid.length;
        IntVar[][] rev = new IntVar[n][];
        for (int i = 0; i < n; i++) {
            rev[n - i - 1] = grid[i];
        }
        return rev;
    }
}
