import org.chocosolver.solver.variables.IntVar;

public abstract class VariantPuzzle extends AbstractPuzzle {
    AbstractPuzzle base;

    @Override
    public int getN() {
        return base.getN();
    }

    @Override
    public IntVar[][] getRows() {
        return base.getRows();
    }

    @Override
    public IntVar[][] getCols() {
        return base.getCols();
    }
}
