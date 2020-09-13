import org.chocosolver.solver.variables.IntVar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExtraRegionSudoku extends VariantPuzzle {
    List<int[][]> regions;

    public static class ExtraRegionSudokuBuilder {
        AbstractPuzzle base;
        List<int[][]> regions = new ArrayList<>();

        public ExtraRegionSudokuBuilder(AbstractPuzzle base) {
            this.base = base;
        }

        public ExtraRegionSudokuBuilder withRegionsList(List<int[][]> regions) {
            this.regions.addAll(regions);
            return this;
        }

        public ExtraRegionSudokuBuilder withRegionsGrid(int[][] grid) {
            HashMap<Integer, List<int[]>> regionMap = new HashMap<>();
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid.length; j++) {
                    int gridVal = grid[i][j];
                    if (gridVal > 0) {
                        if (!regionMap.containsKey(gridVal)) {
                            regionMap.put(gridVal, new ArrayList<>());
                        }
                        regionMap.get(gridVal).add(new int[] {i, j});
                    }
                }
            }

            List<int[][]> regions = new ArrayList<>();
            for (List<int[]> regionList : regionMap.values()) {
                int[][] region = new int[regionList.size()][];
                regionList.toArray(region);
                regions.add(region);
            }

            this.regions.addAll(regions);
            return this;
        }

        public ExtraRegionSudokuBuilder withDiagonals() {
            int n = base.getN();

            int[][] downDiagonal = new int[n][];
            for (int i = 0; i < n; i++) {
                downDiagonal[i] = new int[] {i, i};
            }
            this.regions.add(downDiagonal);

            int[][] upDiagonal = new int[n][];
            for (int i = 0; i < n; i++) {
                upDiagonal[i] = new int[] {n - i - 1, i};
            }
            this.regions.add(upDiagonal);

            return this;
        }

        public ExtraRegionSudoku build() {
            return new ExtraRegionSudoku(base, regions);
        }
    }

    private ExtraRegionSudoku(AbstractPuzzle base, List<int[][]> regions) {
        this.base = base;
        this.regions = regions;
    }

    @Override
    public void buildModel() {
        model = base.getModel();
        IntVar[][] rows = getRows();

        for (int[][] region : regions) {
            IntVar[] regionVars = new IntVar[region.length];
            for (int i = 0; i < region.length; i++) {
                int[] coord = region[i];
                regionVars[i] = rows[coord[0]][coord[1]];
            }
            model.allDifferent(regionVars).post();
        }
    }

    @Override
    protected void buildDokeFile(StringBuilder sb) {
        base.buildDokeFile(sb);
        sb.append("EXTRA REGION\n");
        for (int[][] region : regions) {
            for (int[] coord : region) {
                sb.append(coord[0]);
                sb.append(" ");
                sb.append(coord[1]);
                sb.append("\n");
            }
            sb.append("\n");
        }
        sb.append("\n");
        sb.append("END EXTRA REGION\n");
    }
}
