import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

public class DokeRangeVar extends DokeVarCarrier {
    int lb;
    int hb;
    IntVar var = null;

    public DokeRangeVar(int lb, int hb) {
        this.lb = lb;
        this.hb = hb;
    }

    @Override
    public IntVar getVar(Model model) {
        if (var == null) {
            var = model.intVar(lb, hb);
        }
        return var;
    }
}
