package hr.fer.oprpp1.hw04.db;

/**
 * Functional interface used for filtering records on given condition.
 */
@FunctionalInterface
public interface IFilter {
    /**
     * Filter method that checks if record satisfies query.
     *
     * @param record record to be checked
     * @return true if it accepts query, false otherwise
     */
    public boolean accepts(StudentRecord record);
}
