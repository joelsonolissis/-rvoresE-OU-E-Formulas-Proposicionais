import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

/**
 * 
 * @author gg
 *
 */
public class RunSemana8 {

    private static final String ID_PREFIX = "f_";

    /**
     * Analyses a formula in order to identify the parenthesised subformulas, and
     * uses class AndOrTree to construct the corresponding tree and to evaluate it,
     * given a valuation
     * 
     * @param args - args[0] is the name of a text file containing a propositional
     *             formula and a valuation
     * 
     */
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(new File(args[0]))) {
            String sFormula = sc.nextLine();

            // insert spaces around the parenthesis and print the formula
            String[] parenthesis = { "(", ")" };
            for (String p : parenthesis) {
                int pIndex = sFormula.indexOf(p);
                StringBuilder spacedFormula = new StringBuilder("");
                while (pIndex != -1) {
                    spacedFormula.append(sFormula.substring(0, pIndex)).append(" " + p + " ");
                    sFormula = sFormula.substring(pIndex + 1, sFormula.length());
                    pIndex = sFormula.indexOf(p);
                }
                spacedFormula.append(sFormula);
                sFormula = spacedFormula.toString();
            }
            System.out.println("Formula: ");
            System.out.println(sFormula);
            System.out.println();

            // Transform the formula into a sequence of tokens
            String[] sTokens = sFormula.trim().split("\\s+");

            // Extract and print the (named) subformulas
            ArrayList<Formula> subFormulas = identifySubformulas(sTokens);
            System.out.println("Subformulas:");
            printFormulas(subFormulas);

            // Build and print the AndOrTree
            AndOrTree wholeTree = new AndOrTree(subFormulas);
            System.out.println("And-Or Tree:");
            System.out.println(wholeTree.toString());

            // Read and print the valuation
            List<SymbolValue> valuation = new ArrayList<>();
            while (sc.hasNextLine()) {
                String[] sPair = sc.nextLine().trim().split("\\s+");
                Boolean value = Boolean.valueOf(sPair[1]);
                valuation.add(new SymbolValue(sPair[0], value));
            }
            System.out.println("Valuation of the propositional symbols:");
            printValuation(valuation);
            System.out.println();

            // check the valuation and evaluate the AndOrTree
            if (wholeTree.allLeafs(valuation)) {
                Boolean result = wholeTree.evaluate(valuation);
                System.out.println("The tree evaluates to " + result);
            } else {
                System.out.println("The valuation does not give a value to all the leaves");
            }

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.out.println("Execution failed!");
        }

    }

    private static void printValuation(List<SymbolValue> values) {
        for (SymbolValue pair : values) {
            System.out.println("  " + pair.toString());
        }
        System.out.println();
    }

    private static void printFormulas(ArrayList<Formula> subFormulas) {
        for (Formula f : subFormulas) {
            String sf = f.toString();
            System.out.println("   " + sf);
        }
        System.out.println();
    }

    /**
     * Transforms a formula with parts within parenthesis into a list of several
     * named subformulas without parenthesis.
     * 
     * @param sTokens List of tokens corresponding to the whole formula
     * @return List of named formulas, that correspond to each of the subformulas of
     *         f within parenthesis, from left to right, followed by the result of
     *         rewriting f, without parenthesis, using those named formulas.
     * 
     * @requires !sTokens.isEmpty()
     */
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
