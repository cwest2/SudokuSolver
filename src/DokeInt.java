import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

public class DokeInt extends DokeVarCarrier {
    int val;
    IntVar var = null;

    public DokeInt(int val) {
        this.val = val;
    }

    @Override
    public IntVar getVar(Model model) {
        if (var == null) {
            var = model.intVar(val);
        }
        return var;
    }
}
