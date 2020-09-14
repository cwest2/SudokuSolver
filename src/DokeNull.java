import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

public class DokeNull extends DokeVarCarrier {
    @Override
    public IntVar getVar(Model model) {
        return null;
    }
}
