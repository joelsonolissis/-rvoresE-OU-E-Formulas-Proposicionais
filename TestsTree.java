import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;

public class TestsTree {

    @Test
    public void test1() {
        // Formula1
        String formula = "( C and D ) or ( A and not ( C and not B ) ) or E or F";
        String expectedType = "OR";
        int expectedSubtreesNumber = 4;
        String[] symbols = { "A", "B", "C", "D", "E", "F" };
        List<String> expectedLeaves = Arrays.asList(symbols);
        String error = treeBuildAndGet(formula, expectedType, expectedSubtreesNumber, expectedLeaves);
        if (!error.equals(""))
            fail(error);
    }

    @Test
    public void test2() {
        // Formula2
        String formula = "A and not ( C and E ) and ( B  or  D ) ";
        String expectedType = "AND";
        int expectedSubtreesNumber = 3;
        String[] symbols = { "A", "B", "C", "D", "E" };
        List<String> expectedLeaves = Arrays.asList(symbols);
        String error = treeBuildAndGet(formula, expectedType, expectedSubtreesNumber, expectedLeaves);
        if (!error.equals(""))
            fail(error);
    }

    @Test
    public void test3() {
        // Formula3
        String formula = "not ( ( F and Q and R ) or not T )";
        String expectedType = "NOT";
        int expectedSubtreesNumber = 1;
        String[] symbols = { "F", "Q", "R", "T" };
        List<String> expectedLeaves = Arrays.asList(symbols);
        String error = treeBuildAndGet(formula, expectedType, expectedSubtreesNumber, expectedLeaves);
        if (!error.equals(""))
            fail(error);
    }

    private static String treeBuildAndGet(String formula, String expectedType, int expectedBranches,
            List<String> expectedLeaves) {
        StringBuilder error = new StringBuilder();
        try {
            ArrayList<Formula> subFormulas = AuxTests.extractSubformulas(formula);
            AndOrTree produced = new AndOrTree(subFormulas);
            error.append(check_s("Type", expectedType, produced.getType().toString()));
            error.append(check_i("Subtrees", expectedBranches, produced.getSubtrees().size()));
            error.append(check_l("LeafIds", expectedLeaves, produced.getLeafIds()));
            error.append(checkTrees(false, subFormulas, produced));
            return error.toString();
        } catch (Exception e) {
            return error.append("Exception " + e.getMessage() + nl()).toString();
        }
    }

    private static String check_s(String op, String expected, String produced) {
        String error = "";
        if (expected.compareTo(produced) != 0) {
            error = "get" + op + ": " + expected + ", " + produced + nl();
        }
        return error;
    }

    private static String check_i(String op, Integer expected, int produced) {
        String error = "";
        if (expected.compareTo(produced) != 0) {
            error = "get" + op + ": " + expected + ", " + produced + nl();
        }
        return error;
    }

    private static String check_l(String op, List<String> expected, List<String> produced) {
        String error = "";
        expected.sort(Comparator.naturalOrder());
        produced.sort(Comparator.naturalOrder());
        if (!expected.equals(produced)) {
            error = "get" + op + ": " + expected.toString() + ", " + produced.toString() + nl();
        }
        return error;
    }

    private static String checkTrees(Boolean flag, List<Formula> subFormulas, AndOrTree produced) {
//        if (flag) {
//            // build the expected tree and compare its structure with the produced tree
//            expected = new AndOrTreeS(subFormulas);
//
//            if (!equalAndOrTrees(expected, produced))
//                return "Tree structure" + nl();
//
//        } else
        return "";
    }

    private static String nl() {
        return System.lineSeparator();
    }

}