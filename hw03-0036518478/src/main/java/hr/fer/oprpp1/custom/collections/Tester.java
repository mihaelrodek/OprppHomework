package hr.fer.oprpp1.custom.collections;

/**
 * Functional interface that has one method and represents tester
 *
 * @param <T> type of the tester
 */
@FunctionalInterface
public interface Tester<T> {

    /**
     * Method for processing tests on given arguments.
     *
     * @param obj object to test
     * @return if condition is satisfied, false otherwise
     */
    boolean test(T obj);

}
