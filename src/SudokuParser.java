import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SudokuParser {

    public static AbstractPuzzle parseFile(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));

        // Getting grid size
        String line = reader.readLine();
        int n = Integer.parseInt(line);

        line = reader.readLine();

        int[][] givens;
        if (line.equals("GIVENS")) {
            // Get givens if present
            givens = parseGrid(reader, n);
            line = reader.readLine();
        } else {
            // Set givens to 0 if not given
            givens = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    givens[i][j] = 0;
                }
            }
        }

        AbstractPuzzle puzzle = new LatinSquare.LatinSquareBuilder()
                .withGivens(givens)
                .withSize(n)
                .build();

        // Variant stacking loop
        while (line != null) {
            switch(line) {
                case "REGULAR":
                    line = reader.readLine();
                    String[] blockDims = line.split(" ");
                    if (blockDims.length != 2) {
                        throw new IllegalArgumentException("Incorrect number of block dimensions given");
                    }
                    puzzle = new RegularSudoku.RegularSudokuBuilder()
                            .withBase(puzzle)
                            .withSizeAndBlockDims(n, Integer.parseInt(blockDims[0]), Integer.parseInt(blockDims[1]))
                            .build();
                    line = reader.readLine();
                    while (!line.equals("END REGULAR")) {
                        switch (line) {
                            default -> throw new IllegalArgumentException("Unrecognized argument in variant REGULAR: " + line);
                        }
                    }
                    line = reader.readLine();
                    break;
                case "IRREGULAR":
                    int[][] regions = parseGrid(reader, n);
                    puzzle = new IrregularSudoku.IrregularSudokuBuilder(regions)
                            .withBase(puzzle)
                            .build();
                    line = reader.readLine();
                    while (!line.equals("END IRREGULAR")) {
                        switch (line) {
                            default -> throw new IllegalArgumentException("Unrecognized argument in variant IRREGULAR: " + line);
                        }
                    }
                    line = reader.readLine();
                    break;
                case "ARROW":
                    List<int[][]> arrowSums = new ArrayList<>();
                    List<int[][]> arrowPaths = new ArrayList<>();
                    int[][] path = parsePath(reader);
                    while(path.length > 0) {
                        arrowSums.add(path);
                        path = parsePath(reader);
                        arrowPaths.add(path);
                        path = parsePath(reader);
                    }
                    puzzle = new ArrowSudoku.ArrowSudokuBuilder(puzzle, arrowSums, arrowPaths)
                            .build();
                    line = reader.readLine();
                    while (!line.equals("END ARROW")) {
                        switch (line) {
                            default -> throw new IllegalArgumentException("Unrecognized argument in variant ARROW: " + line);
                        }
                    }
                    line = reader.readLine();
                    break;
                case "ADJACENT CELL":
                    String[][] rowGaps = parseStrGrid(reader, n, n - 1);
                    String[][] colGaps = parseStrGrid(reader, n, n - 1);
                    AdjacentCellSudoku.AdjacentCellSudokuBuilder builder =
                            new AdjacentCellSudoku.AdjacentCellSudokuBuilder(puzzle)
                                    .withRowGaps(rowGaps)
                                    .withColGaps(colGaps);
                    line = reader.readLine();
                    while (!line.equals("END ADJACENT CELL")) {
                        switch (line) {
                            case "NEGATIVE" -> {
                                builder.withNegativeConstraints(true);
                                line = reader.readLine();
                            }
                            case "KROPKI WHITE" -> {
                                builder.withKropkiWhiteConstraint();
                                line = reader.readLine();
                            }
                            case "KROPKI BLACK" -> {
                                builder.withKropkiBlackConstraint();
                                line = reader.readLine();
                            }
                            case "X CONSTRAINT" -> {
                                builder.withXConstraint();
                                line = reader.readLine();
                            }
                            case "V CONSTRAINT" -> {
                                builder.withVConstraint();
                                line = reader.readLine();
                            }
                            default -> throw new IllegalArgumentException("Unrecognized argument in variant ADJACENT CELL: " + line);
                        }
                    }
                    puzzle = builder.build();
                    line = reader.readLine();
                    break;
                case "KILLER":
                    HashMap<Integer, Integer> cageSums = new HashMap<>();
                    int[][] cages = parseGrid(reader, n);
                    line = reader.readLine();
                    while (line != null && !line.equals("")) {
                        String[] row = line.split(" ");
                        cageSums.put(Integer.parseInt(row[0]), Integer.parseInt(row[1]));
                        line = reader.readLine();
                    }
                    puzzle = new KillerSudoku.KillerSudokuBuilder(puzzle, cages, cageSums)
                            .build();
                    line = reader.readLine();
                    while (!line.equals("END KILLER")) {
                        switch (line) {
                            default -> throw new IllegalArgumentException("Unrecognized argument in variant KILLER: " + line);
                        }
                    }
                    line = reader.readLine();
                    break;
                case "SANDWICH":
                    int[] rowSums = parseRow(reader, n);
                    int[] colSums = parseRow(reader, n);
                    puzzle = new SandwichSudoku.SandwichSudokuBuilder(puzzle)
                            .withRowSums(rowSums)
                            .withColSums(colSums)
                            .build();
                    line = reader.readLine();
                    while (!line.equals("END SANDWICH")) {
                        switch (line) {
                            default -> throw new IllegalArgumentException("Unrecognized argument in variant SANDWICH: " + line);
                        }
                    }
                    line = reader.readLine();
                    break;
                case "XSUMS":
                    int[] leftRowSums = parseRow(reader, n);
                    int[] topColSums = parseRow(reader, n);
                    int[] rightRowSums = parseRow(reader, n);
                    int[] bottomColSums = parseRow(reader, n);
                    puzzle = new XSumSudoku.XSumSudokuBuilder(puzzle)
                            .withLeftRowSums(leftRowSums)
                            .withTopColSums(topColSums)
                            .withRightRowSums(rightRowSums)
                            .withBottomColSums(bottomColSums)
                            .build();
                    line = reader.readLine();
                    while (!line.equals("END XSUMS")) {
                        switch (line) {
                            default -> throw new IllegalArgumentException("Unrecognized argument in variant XSUMS: " + line);
                        }
                    }
                    line = reader.readLine();
                    break;
                case "LITTLE KILLER":
                    int[] downUp = parseRow(reader, 2);
                    int downDiagonal = downUp[0];
                    int upDiagonal = downUp[1];
                    leftRowSums = parseRow(reader, n);
                    String[] leftRowDirs = parseRowString(reader, n);
                    topColSums = parseRow(reader, n);
                    String[] topColDirs = parseRowString(reader, n);
                    rightRowSums = parseRow(reader, n);
                    String[] rightRowDirs = parseRowString(reader, n);
                    bottomColSums = parseRow(reader, n);
                    String[] bottomColDirs = parseRowString(reader, n);
                    puzzle = new LittleKillerSudoku.LittleKillerSudokuBuilder(puzzle)
                            .withDownDiagonal(downDiagonal)
                            .withUpDiagonal(upDiagonal)
                            .withLeftRowSums(leftRowSums, leftRowDirs)
                            .withTopColSums(topColSums, topColDirs)
                            .withRightRowSums(rightRowSums, rightRowDirs)
                            .withBottomColSums(bottomColSums, bottomColDirs)
                            .build();
                    line = reader.readLine();
                    while (!line.equals("END LITTLE KILLER")) {
                        switch (line) {
                            default -> throw new IllegalArgumentException("Unrecognized argument in variant LITTLE KILLER: " + line);
                        }
                    }
                    line = reader.readLine();
                    break;
                case "SKYSCRAPER":
                    leftRowSums = parseRow(reader, n);
                    topColSums = parseRow(reader, n);
                    rightRowSums = parseRow(reader, n);
                    bottomColSums = parseRow(reader, n);
                    puzzle = new SkyscraperSudoku.SkyscraperSudokuBuilder(puzzle)
                            .withLeftRowSums(leftRowSums)
                            .withTopColSums(topColSums)
                            .withRightRowSums(rightRowSums)
                            .withBottomColSums(bottomColSums)
                            .build();
                    line = reader.readLine();
                    while (!line.equals("END SKYSCRAPER")) {
                        switch (line) {
                            default -> throw new IllegalArgumentException("Unrecognized argument in variant SKYSCRAPER: " + line);
                        }
                    }
                    line = reader.readLine();
                    break;
                case "PALINDROME":
                    List<int[][]> palindromes = new ArrayList<>();
                    int[][] palindrome = parsePath(reader);
                    while(palindrome.length > 0) {
                        palindromes.add(palindrome);
                        palindrome = parsePath(reader);
                    }
                    puzzle = new PalindromeSudoku.PalindromeSudokuBuilder(puzzle, palindromes)
                            .build();
                    line = reader.readLine();
                    while (!line.equals("END PALINDROME")) {
                        switch (line) {
                            default -> throw new IllegalArgumentException("Unrecognized argument in variant PALINDROME: " + line);
                        }
                    }
                    line = reader.readLine();
                    break;
                case "EXTRA REGION":
                    List<int[][]> regionsList = new ArrayList<>();
                    int[][] region = parsePath(reader);
                    while (region.length > 0) {
                        regionsList.add(region);
                        region = parsePath(reader);
                    }
                    puzzle = new ExtraRegionSudoku.ExtraRegionSudokuBuilder(puzzle)
                            .withRegionsList(regionsList)
                            .build();
                    line = reader.readLine();
                    while (!line.equals("END EXTRA REGION")) {
                        switch (line) {
                            default -> throw new IllegalArgumentException("Unrecognized argument in variant EXTRA REGION: " + line);
                        }
                    }
                    line = reader.readLine();
                    break;
                case "THERMO":
                    List<int[][]> thermos = new ArrayList<>();
                    int[][] thermo = parsePath(reader);
                    while(thermo.length > 0) {
                        thermos.add(thermo);
                        thermo = parsePath(reader);
                    }
                    puzzle = new ThermoSudoku.ThermoSudokuBuilder(puzzle, thermos)
                            .build();
                    line = reader.readLine();
                    while (!line.equals("END THERMO")) {
                        switch (line) {
                            default -> throw new IllegalArgumentException("Unrecognized argument in variant THERMO: " + line);
                        }
                    }
                    line = reader.readLine();
                    break;
                case "DEFICIT/SURPLUS":
                    regions = parseGrid(reader, n);
                    puzzle = new SurplusDeficitSudoku.SurplusDeficitSudokuBuilder(regions)
                            .withBase(puzzle)
                            .build();
                    line = reader.readLine();
                    while (!line.equals("END DEFICIT/SURPLUS")) {
                        switch (line) {
                            default -> throw new IllegalArgumentException("Unrecognized argument in variant DEFICIT/SURPLUS: " + line);
                        }
                    }
                    line = reader.readLine();
                    break;
                case "DIAGONAL":
                    puzzle = new ExtraRegionSudoku.ExtraRegionSudokuBuilder(puzzle)
                            .withDiagonals()
                            .build();
                    line = reader.readLine();
                    while (!line.equals("END DIAGONAL")) {
                        switch (line) {
                            default -> throw new IllegalArgumentException("Unrecognized argument in variant DIAGONAL: " + line);
                        }
                    }
                    line = reader.readLine();
                    break;
                case "KING":
                    puzzle = new KingSudoku.KingSudokuBuilder(puzzle)
                            .build();
                    line = reader.readLine();
                    while (!line.equals("END KING")) {
                        switch (line) {
                            default -> throw new IllegalArgumentException("Unrecognized argument in variant KING: " + line);
                        }
                    }
                    line = reader.readLine();
                    break;
                case "KNIGHT":
                    puzzle = new KnightSudoku.KnightSudokuBuilder(puzzle)
                            .build();
                    line = reader.readLine();
                    while (!line.equals("END KNIGHT")) {
                        switch (line) {
                            default -> throw new IllegalArgumentException("Unrecognized argument in variant KNIGHT: " + line);
                        }
                    }
                    line = reader.readLine();
                    break;
                case "NONCONSECUTIVE":
                    puzzle = new NonconsecutiveSudoku.NonconsecutiveSudokuBuilder(puzzle)
                            .build();
                    line = reader.readLine();
                    while (!line.equals("END NONCONSECUTIVE")) {
                        switch (line) {
                            default -> throw new IllegalArgumentException("Unrecognized argument in variant NONCONSECUTIVE: " + line);
                        }
                    }
                    line = reader.readLine();
                    break;
                default:
                    throw new IllegalArgumentException("Improperly formatted file: " + line);
            }
        }


        return puzzle;
    }

    private static String[] parseRowString(BufferedReader reader, int n) throws IOException {
        String line = reader.readLine();
        String[] row = line.split(" ");
        if (row.length != n) {
            throw new IllegalArgumentException("dir row of wrong length");
        }
        return row;
    }

    private static int[] parseRow(BufferedReader reader, int n) throws IOException {
        String line = reader.readLine();
        String[] strs = line.split(" ");
        if (strs.length != n) {
            throw new IllegalArgumentException("Improper number of sandwich clues provided");
        }
        int[] row = new int[n];
        for (int i = 0; i < n; i++) {
            row[i] = Integer.parseInt(strs[i]);
        }
        return row;
    }

    private static int[][] parsePath(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        List<int[]> coords = new ArrayList<>();
        while (!line.equals("")) {
            String[] strs = line.split(" ");
            if (strs.length != 2) {
                throw new IllegalArgumentException("Improperly formatted thermo");
            }
            int[] coord = new int[2];
            coord[0] = Integer.parseInt(strs[0]);
            coord[1] = Integer.parseInt(strs[1]);
            coords.add(coord);
            line = reader.readLine();
        }
        int[][] path = new int[coords.size()][];
        coords.toArray(path);
        return path;
    }

    private static String[][] parseStrGrid(BufferedReader reader, int rows, int cols) throws IOException {
        String[][] grid = new String[rows][cols];
        for (int i = 0; i < rows; i++) {
            String line = reader.readLine();
            String[] elems = line.split(" ");
            if (elems.length != cols) {
                throw new IllegalArgumentException("Row of improper length while parsing grid");
            }
            grid[i] = elems;
        }
        return grid;
    }

    private static int[][] parseGrid(BufferedReader reader, int rows, int cols) throws IOException {
        int[][] grid = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            String line = reader.readLine();
            String[] elems = line.split(" ");
            if (elems.length != cols) {
                throw new IllegalArgumentException("Row of improper length while parsing grid");
            }
            for (int j = 0; j < cols; j++) {
                grid[i][j] = Integer.parseInt(elems[j]);
            }
        }
        return grid;
    }

    private static int[][] parseGrid(BufferedReader reader, int n) throws IOException {
        return parseGrid(reader, n, n);
    }
}
