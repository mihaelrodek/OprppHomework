package hr.fer.oprpp1.hw04.db;

/**
 * Class that represents operators.
 */
public class ComparisonOperators {

    /**
     * Operator less
     */
    public static final IComparisonOperator LESS = (v1, v2) -> v1.compareTo(v2) < 0;
    /**
     * Operator less or equals
     */
    public static final IComparisonOperator LESS_OR_EQUALS = (v1, v2) -> v1.compareTo(v2) <= 0;
    /**
     * Operator greater
     */
    public static final IComparisonOperator GREATER = (v1, v2) -> v1.compareTo(v2) > 0;
    /**
     * Operator greater or equals
     */
    public static final IComparisonOperator GREATER_OR_EQUALS = (v1, v2) -> v1.compareTo(v2) >= 0;
    /**
     * Operator equals
     */
    public static final IComparisonOperator EQUALS = (v1, v2) -> v1.equals(v2);
    /**
     * Operator not equals
     */
    public static final IComparisonOperator NOT_EQUALS = (v1, v2) -> !v1.equals(v2);
    /**
     * Operator like
     */
    public static final IComparisonOperator LIKE = (v1, v2) -> {
        if ((v2.length() - v2.replace("*", "").length()) > 1)
            throw new UnsupportedOperationException("Too many *!");
        String regex = v2.replace("*", "(.*)");
        return v1.matches(regex);
    };
}


