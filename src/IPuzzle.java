import org.chocosolver.solver.exception.ContradictionException;
import org.chocosolver.solver.variables.IntVar;

public interface IPuzzle<M> {
    void buildModel();

    M getModel();

    int[][] solve();

    void printSolution();

    void writeDokeFile(String fileName);
}
