package hr.fer.oprpp1.hw04.db;

/**
 * Class that represents a single conditional expression for database
 */
public class ConditionalExpression {

    /**
     * Represents strategy field
     */
    private IFieldValueGetter field;
    /**
     * Represents string literal
     */
    private String string;
    /**
     * Represents strategy opeartor
     */
    private IComparisonOperator operator;

    /**
     * Default constructor that creates a single conditional expression
     *
     * @param field    field to compare
     * @param string   string to compare to
     * @param operator comparing operator that is performed
     */
    public ConditionalExpression(IFieldValueGetter field, String string, IComparisonOperator operator) {
        this.field = field;
        this.string = string;
        this.operator = operator;
    }

    /**
     * Getter for field
     *
     * @return field
     */

    public IFieldValueGetter getField() {
        return field;
    }

    /**
     * Getter for string
     *
     * @return string
     */
    public String getString() {
        return string;
    }

    /**
     * Getter for operator
     *
     * @return operator
     */
    public IComparisonOperator getOperator() {
        return operator;
    }

}
