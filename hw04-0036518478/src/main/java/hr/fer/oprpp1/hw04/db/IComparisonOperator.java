package hr.fer.oprpp1.hw04.db;


/**
 * Strategy interface used to compare certain conditions
 *
 * @author Mihael Rodek
 */
public interface IComparisonOperator {

    /**
     * Compares if two values satisfy a certain condition.
     *
     * @param value1 left side of condition
     * @param value2 right side of condition
     * @return true if condition is satisfied, false otherwise
     */
    public boolean satisfied(String value1, String value2);
}
