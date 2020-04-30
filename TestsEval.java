import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class TestsEval {

    @Test
    public void testA() {
        // TreeA, ValuationA1, ValuationA2, ValuationA3
        StringBuilder error = new StringBuilder();
        String testAllLeafs = "treeA.allLeafs on ";
        String testEvaluate = "treeA.evaluate on ";
        try {
            List<Formula> listFormulas = new ArrayList<>();
            String[] af1 = { "C", "and", "B" };
            String[] af2 = { "A", "or", "tf_1", "or", "D" };
            String[] af3 = { "not", "tf_1", "and", "tf_2" };
            List<String> lf1 = Arrays.asList(af1);
            Formula f1 = new Formula("tf_1", lf1);
            listFormulas.add(f1);
            List<String> lf2 = Arrays.asList(af2);
            Formula f2 = new Formula("tf_2", lf2);
            listFormulas.add(f2);
            List<String> lf3 = Arrays.asList(af3);
            Formula f3 = new Formula("tf_4", lf3);
            listFormulas.add(f3);
            AndOrTree t = new AndOrTree(listFormulas);

            // test incomplete valuation
            List<SymbolValue> values = buildValuation("C,A,D", "true, true, true");
            if (t.allLeafs(values))
                error.append(testAllLeafs + values.toString() + nl());
            // test complete valuations and tree evaluation
            values = buildValuation("A,B,C,D", "false,true,false, false");
            Boolean expectedValue = false;
            if (!t.allLeafs(values))
                error.append(testAllLeafs + values.toString() + nl());
            else {
                if (!expectedValue.equals(t.evaluate(values)))
                    error.append(testEvaluate + values.toString() + nl());
            }
            values = buildValuation("A,B,C,D", "false,true,false, true");
            expectedValue = true;
            if (!t.allLeafs(values))
                error.append(testAllLeafs + values.toString() + nl());
            else {
                if (!expectedValue.equals(t.evaluate(values)))
                    error.append(testEvaluate + values.toString() + nl());

            }
        } catch (Exception e) {
            error.append("Exception " + e.getMessage() + nl()).toString();
        }
        String errorMessage = error.toString();
        if (!errorMessage.equals(""))
            fail(errorMessage);

    }

    @Test
    public void testB() {
        // TreeB, ValuationB1, ValuationB2, ValuationB3
        StringBuilder error = new StringBuilder();
        String testAllLeafs = "treeB.allLeafs on ";
        String testEvaluate = "treeB.evaluate on ";
        try {
            List<Formula> listFormulas = new ArrayList<>();
            String[] af1 = { "A", "or", "B" };
            String[] af2 = { "not", "tf_1", "and", "C" };
            String[] af3 = { "A", "or", "tf_1", "and", "D" };
            String[] af4 = { "tf_2", "or", "tf_1", "or", "tf_3" };
            List<String> lf1 = Arrays.asList(af1);
            Formula f1 = new Formula("tf_1", lf1);
            listFormulas.add(f1);
            List<String> lf2 = Arrays.asList(af2);
            Formula f2 = new Formula("tf_2", lf2);
            listFormulas.add(f2);
            List<String> lf3 = Arrays.asList(af3);
            Formula f3 = new Formula("tf_3", lf3);
            listFormulas.add(f3);
            List<String> lf4 = Arrays.asList(af4);
            Formula f4 = new Formula("tf_4", lf4);
            listFormulas.add(f4);
            AndOrTree t = new AndOrTree(listFormulas);

            // test incomplete valuation
            List<SymbolValue> values = buildValuation("D,C,A", "true,true,true");
            if (t.allLeafs(values))
                error.append(testAllLeafs + values.toString() + nl());
            // test complete valuations and tree evaluation
            values = buildValuation("D,C,A,B", "false,false,false,false");
            Boolean expectedValue = false;
            if (!t.allLeafs(values))
                error.append(testAllLeafs + values.toString() + nl());
            else {
                if (!expectedValue.equals(t.evaluate(values)))
                    error.append(testEvaluate + values.toString() + nl());
            }
            values = buildValuation("D,C,A,B", "false,true,true, true");
            expectedValue = true;
            if (!t.allLeafs(values))
                error.append(testAllLeafs + values.toString() + nl());
            else {
                if (!expectedValue.equals(t.evaluate(values)))
                    error.append(testEvaluate + values.toString() + nl());

            }
        } catch (Exception e) {
            error.append("Exception " + e.getMessage() + nl()).toString();
        }
        String errorMessage = error.toString();
        if (!errorMessage.equals(""))
            fail(errorMessage);

    }

    private static List<SymbolValue> buildValuation(String symbols, String values) {
        List<SymbolValue> pairs = new ArrayList<>();
        String[] aSymbols = symbols.trim().split("\\s*,\\s*");
        String[] aValues = values.trim().split("\\s*,\\s*");

        Boolean value;
        for (int i = 0; i < aSymbols.length; i++) {
            if (aValues[i].equals("true"))
                value = true;
            else
                value = false;
            pairs.add(new SymbolValue(aSymbols[i], value));
        }
        return pairs;
    }

    private static String nl() {
        return System.lineSeparator();
    }

}