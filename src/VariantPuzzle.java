import com.google.ortools.sat.IntVar;

public abstract class VariantPuzzle extends AbstractPuzzle {
    AbstractPuzzle base;

    public int getN() {
        return base.getN();
    }

    public IntVar[][] getRows() {
        return base.getRows();
    }

    public IntVar[][] getCols() {
        return base.getCols();
    }

    public VariantPuzzle(AbstractPuzzle base) {
        this.base = base;
        base.buildModel();
    }
}
