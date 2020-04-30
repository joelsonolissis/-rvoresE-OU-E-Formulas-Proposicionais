
import java.util.ArrayList;
import java.util.List;

/**
 * @author gg
 *
 */
public class AndOrTree {

    public enum NodeType {
        AND, OR, NOT, LEAF
    }

    private static class TreeNode {

        private String id;
        private NodeType type;
        private ArrayList<TreeNode> childNodes;

        /**
         * Recursive construction of a tree node from an identifier, a list of strings
         * representing the formula and a list of tree nodes that can be used as child
         * nodes
         * 
         * @param id          formula identifier
         * @param f           list of strings that constitute the formula
         * @param usableNodes list of nodes already created whose root identifiers can
         *                    occur in f
         * 
         * @requires f does not contain parenthesis; it may only contain "or", "and",
         *           "not" and identifiers; "or" has the lowest precedence and "not" the
         *           highest.
         */
        private TreeNode(String id, List<String> f, List<TreeNode> usableNodes) {
            if (f.size() == 1) {
                // f is the id of a member of usableNodes or it is a propositional symbol
                String name = f.get(0);
                TreeNode t = occursIn(name, usableNodes);
                if (t != null) {
                    this.id = t.id;
                    this.type = t.type;
                    this.childNodes = t.childNodes;
                } else {
                    this.id = name;
                    this.type = NodeType.LEAF;
                    this.childNodes = new ArrayList<>();
                }
            } else {
                this.id = id;
                this.childNodes = new ArrayList<>();
                int operatorPosition = f.indexOf("or");
                if (operatorPosition != -1) {
                    // the formula has an "or"
                    this.type = NodeType.OR;
                    createChilds(id, f, "or", operatorPosition, 0, childNodes, usableNodes);
                } else {
                    operatorPosition = f.indexOf("and");
                    if (operatorPosition != -1) {
                        // the formula has an "and"
                        this.type = NodeType.AND;
                        createChilds(id, f, "and", operatorPosition, 0, childNodes, usableNodes);
                    } else {
                        // it must be a not
                        this.type = NodeType.NOT;
                        createChilds(id, f, "not", 0, 0, childNodes, usableNodes);
                    }
                }
            }
        }

        private static TreeNode occursIn(String name, List<TreeNode> childs) {
            int i = 0;
            TreeNode result = null;
            Boolean found = false;
            while (i < childs.size() && !found) {
                TreeNode t = childs.get(i);
                if (name.equals(t.id)) {
                    found = true;
                    result = t;
                } else {
                    i++;
                }
            }
            return result;
        }

        /**
         * Creates child nodes, of a node whose node.id is id, from a formula f.
         * 
         * @param id               identifier of the parent node
         * @param f                the list of strings representing the formula
         * @param operator         the main operator (i.e., with lowest precedence) in f
         * @param operatorPosition the position of the first occurrence of that operator
         *                         in f
         * @param nodeNumber       number of the next child to be created
         * @param childNodes       the list of childNodes of the parent node already
         *                         created
         * @param usableNodes      list of nodes already created whose identifiers can
         *                         occur in f
         */
        private static void createChilds(String id, List<String> f, String operator, int operatorPosition,
                int nodeNumber, List<TreeNode> childNodes, List<TreeNode> usableNodes) {
            String formulaId = id + "_" + Integer.toString(nodeNumber);
            if (operator.equals("not")) {
                // it is a unary operator (only one child node)
                List<String> operand = f.subList(1, 2);
                TreeNode newChild = new TreeNode(formulaId, operand, usableNodes);
                childNodes.add(newChild);
            } else {
                // two or more child nodes
                List<String> operand = f.subList(0, operatorPosition);
                TreeNode newChild = new TreeNode(formulaId, operand, usableNodes);
                childNodes.add(newChild);
                if (newChild.id.equals(formulaId)) {
                    // newChild is not a LEAF node or a member of usableNodes
                    nodeNumber++;
                }
                f = f.subList(operatorPosition + 1, f.size());
                int nextPosition = f.indexOf(operator);
                if (nextPosition != -1) {
                    createChilds(id, f, operator, nextPosition, nodeNumber, childNodes, usableNodes);
                } else {
                    childNodes.add(new TreeNode(formulaId, f, usableNodes));
                }
            }
        }

    } // end of class TreeNode

    private TreeNode root;

    private AndOrTree(TreeNode n) {
        this.root = n;
    }

    /**
     * Creates the AndOrTree for a single named formula (that has no parenthesis)
     * 
     * The root id is the name of the formula. The leaves of the tree are the
     * propositional symbols in that formula
     * 
     * @param f a named formula
     */
    public AndOrTree(Formula f) {
        ArrayList<TreeNode> childs = new ArrayList<>();
        this.root = new TreeNode(f.getName(), f.getFormula(), childs);
    }

    /**
     * Creates the AndOrTree for a formula f that has been split into a list of
     * named subformulas, combining the tree nodes for those subformulas.
     * 
     * 
     * @param subFormulas a list of named formulas representing f
     * 
     * @requires subFormulas is the list with each of the subformulas of f within
     *           parenthesis, from left to right, followed by the result of
     *           rewriting f, without parenthesis, using those named formulas.
     * 
     *           A subformula in the list may use the names of the previous
     *           subformulas in the list.
     * 
     *           Example: [ ("f_0", ["P", "or", "Q", "or", "not", "R", "and", "T"]),
     *           ("f_1", ["R", "and", "f_0"]), ("f_2", ["not", "f_1"]) ]
     * 
     */
    public AndOrTree(List<Formula> subFormulas) {
        // TO DO
    }

    /**
     * The name of this tree
     * 
     * @return the id of the root node
     */
    public String getId() {
        // TO DO
    }

    /**
     * The type of this tree
     * 
     * @return the NodeType of the root node
     */
    public NodeType getType() {
        // TO DO
    }

    /**
     * The list of subtrees of this tree
     * 
     * @return the list of the AndOrTrees that are the subtrees of the tree
     */
    public List<AndOrTree> getSubtrees() {
        // TO DO
    }

    /**
     * The list with the names of the leaves of this tree
     * 
     * @return a list with the ids of all the leaf nodes, without repetitions
     */
    public List<String> getLeafIds() {
        // TO DO
    }

    /**
     * Checks if the valuation gives values to all the propositional symbols
     * 
     * @param valuation a list of pairs (symbol, value)
     * @return true if the list contains values for all the leaves of the tree,
     *         false otherwise
     */
    public Boolean allLeafs(List<SymbolValue> valuation) {
        // TO DO
    }

    /**
     * Evaluate this AndOrTree given values for the propositional symbols
     * 
     * 
     * @param valuation a list of pairs (symbol, booleanValue)
     * @return true if the formula represented by the tree is true, false otherwise
     * 
     * @requires valuation has a pair relative to each leaf of the tree
     */
    public Boolean evaluate(List<SymbolValue> valuation) {
        // TO DO
    }

    /**
     * Gets the value of the id in the valuation
     * 
     * @param id        a propositional symbol
     * @param valuation a list of pairs (symbol, booleanValue)
     * @return the boolean value of id or null if it is not in the valuation
     */
    private static Boolean getValue(String id, List<SymbolValue> valuation) {
        // TO DO
    }

    /**
     * Textual representation
     */
    public String toString() {
        String currentIndent = "";
        StringBuilder sb = new StringBuilder();
        toStringAux(currentIndent, this, sb);
        return sb.toString();
    }

    private void toStringAux(String currentIndent, AndOrTree tree, StringBuilder sb) {
        if (root != null) {
            sb.append(currentIndent + tree.getId() + ":" + tree.getType().toString() + "\n");
            if (tree.getType() != NodeType.LEAF) {
                List<AndOrTree> subtrees = tree.getSubtrees();
                for (AndOrTree subtree : subtrees) {
                    toStringAux(currentIndent + "    ", subtree, sb);
                }
            }
        } else {
            sb.append("Empty tree");
        }
    }

}
