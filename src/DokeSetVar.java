import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

public class DokeSetVar extends DokeVarCarrier{
    int[] set;
    IntVar var = null;

    public DokeSetVar(int[] set) {
        this.set = set;
    }

    @Override
    public IntVar getVar(Model model) {
        if (var == null) {
            var = model.intVar(set);
        }
        return var;
    }
}
