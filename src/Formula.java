import java.util.ArrayList;
import java.util.List;

/**
 * A class that represents named formulas. A named formula has a name and a
 * non-empty list of tokens (strings) that constitute the formula
 * 
 * The list of tokens has no parenthesis
 * 
 * @author gg
 *
 */
public class Formula {
    /*
     * Pair whose first field is the formula identifier and the second field is the
     * list of tokens that constitutes the formula
     */
    private Pair<String, ArrayList<String>> namedFormula;

    /**
     * Constructor
     * 
     * @param name    The new formula identifier
     * @param formula The list of tokens of the new formula
     */
    public Formula(String name, List<String> formula) {
        this.namedFormula = new Pair<>(name, new ArrayList<>(formula));
    }

    /**
     * This formula identifier
     */
    public String getName() {
        return namedFormula.getFirst();
    }

    /**
     * This formula list of tokens
     */
    public List<String> getFormula() {
        return namedFormula.getSecond();
    }

    /**
     * Textual representation
     */
    public String toString() {
        List<String> formula = getFormula();
        StringBuilder sf = new StringBuilder(" ");
        for (String token : formula) {
            sf.append(token);
            sf.append(" ");
        }
        return (getName() + " = " + sf.toString());
    }

}
