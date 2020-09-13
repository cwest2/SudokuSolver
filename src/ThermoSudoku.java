import org.chocosolver.solver.variables.IntVar;

import java.util.List;

public class ThermoSudoku extends VariantPuzzle {

    List<int[][]> thermos;

    public static class ThermoSudokuBuilder {
        AbstractPuzzle base;
        List<int[][]> thermos;

        public ThermoSudokuBuilder(AbstractPuzzle base, List<int[][]> thermos) {
            this.base = base;
            this.thermos = thermos;
        }

        public ThermoSudoku build() {
            return new ThermoSudoku(base, thermos);
        }
    }

    private ThermoSudoku(AbstractPuzzle base, List<int[][]> thermos) {
        this.base = base;
        this.thermos = thermos;
    }

    @Override
    public void buildModel() {
        model = base.getModel();

        IntVar[][] rows = getRows();

        for(int[][] thermo : thermos) {
            for (int i = 0; i < thermo.length - 1; i++) {
                int[] c1 = thermo[i];
                int[] c2 = thermo[i + 1];
                rows[c1[0]][c1[1]].lt(rows[c2[0]][c2[1]]).post();
            }
        }
    }

    @Override
    protected void buildDokeFile(StringBuilder sb) {
        base.buildDokeFile(sb);
        sb.append("THERMO\n");
        for (int[][] thermo : thermos) {
            for (int[] coord : thermo) {
                sb.append(coord[0]);
                sb.append(" ");
                sb.append(coord[1]);
                sb.append("\n");
            }
            sb.append("\n");
        }
        sb.append("\n");
        sb.append("END THERMO\n");
    }
}
