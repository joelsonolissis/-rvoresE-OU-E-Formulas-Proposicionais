/**
 * Represents a pair (TreeNode identifier, boolValue) where identifier is a
 * propositional symbol and boolValue is that symbol's value
 * 
 * @author gg
 *
 */
public class SymbolValue {

	private Pair<String, Boolean> symbVal;

	/**
	 * Constructor
	 * 
	 * @param name The symbol for the new object
	 * @param b    The value for the new object
	 */
	public SymbolValue(String name, Boolean b) {
		this.symbVal = new Pair<>(name, b);
	}

	/**
	 * The symbol of this object
	 */
	public String getSymbol() {
		return symbVal.getFirst();
	}

	/**
	 * The value of this object
	 */
	public Boolean getValue() {
		return symbVal.getSecond();
	}

	/**
	 * Textual representation
	 */
	public String toString() {
		return "( " + getSymbol() + ", " + getValue() + " )";
	}

	/**
	 * This object is equal to other object?
	 * 
	 * @param o The other object
	 * @returns true if this object is equal to o; false if not
	 */
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof SymbolValue)) {
			return false;
		}
		SymbolValue sv = (SymbolValue) o;
		return getSymbol().equals(sv.getSymbol()) && getValue().equals(sv.getValue());

	}

}