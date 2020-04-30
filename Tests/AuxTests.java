import java.util.ArrayList;
import java.util.Stack;

public class AuxTests {

    private static final String ID_PREFIX = "tf_";

    public enum NodeType {
        AND, OR, NOT, LEAF
    }

    /*
     * formula must be a string corresponding to a formula, where all words and
     * symbols are separated by spaces
     */
    public static ArrayList<Formula> extractSubformulas(String formula) {
        String[] sTokens = formula.trim().split("\\s+");
        ArrayList<Formula> subFormulas = identifySubformulas(sTokens);
        return subFormulas;
    }

    private static ArrayList<Formula> identifySubformulas(String[] sTokens) {
        Stack<String> tokenStack = new Stack<>();
        ArrayList<Formula> subFormulas = new ArrayList<>();
        identifySubformulasAux(sTokens, 0, tokenStack, 0, subFormulas);
        return subFormulas;
    }

    private static void identifySubformulasAux(String[] sTokens, int i, Stack<String> tokenStack, int nextFormulaNumber,
            ArrayList<Formula> subFormulas) {
        if (i < sTokens.length) {
            if (sTokens[i].equals(")")) {
                // Build a new subformula f with the sequence of Strings in tokenStack from the
                // nearest "(" up
                Formula f = unstackBuildFormula(tokenStack, nextFormulaNumber);
                subFormulas.add(f);
                i++;
                // the f identifier replaces this subformula in the tokenStack
                tokenStack.push(f.getName());
                nextFormulaNumber++;
                identifySubformulasAux(sTokens, i, tokenStack, nextFormulaNumber, subFormulas);
            } else {
                // advance one token and recursively call identifySubformulasAux
                tokenStack.push(sTokens[i]);
                i++;
                identifySubformulasAux(sTokens, i, tokenStack, nextFormulaNumber, subFormulas);
            }
        } else {
            // Build a new formula from the whole sequence of Strings in tokenStack
            Formula f = unstackBuildFormula(tokenStack, nextFormulaNumber);
            subFormulas.add(f);
        }
    }

    private static Formula unstackBuildFormula(Stack<String> tokenStack, int formulaNumber) {
        int distanceInStack = tokenStack.search("(");
        String formulaId = ID_PREFIX + Integer.toString(formulaNumber);
        ArrayList<String> tokens = new ArrayList<>();
        if (distanceInStack == -1) {
            // No "(" found, whence the formula begins at the base of tokenStack
            while (!tokenStack.isEmpty()) {
                tokens.add(0, tokenStack.pop());
            }
        } else {
            for (int j = 1; j < distanceInStack; j++) {
                tokens.add(0, tokenStack.pop());
            }
            // pop the "("
            tokenStack.pop();
        }
        return new Formula(formulaId, tokens);
    }

}
