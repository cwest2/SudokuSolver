import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

public abstract class DokeVarCarrier {

    public abstract IntVar getVar(Model model);
}
