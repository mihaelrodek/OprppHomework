package hr.fer.oprpp1.hw04.db;

/**
 * Strategy interface responsible for obtaining a requested field value from given StudentRecord.
 *
 * @author Mihael Rodek
 */
public interface IFieldValueGetter {

    /**
     * Method that is responsible for obtaining field from given record.
     *
     * @param record record to be obtained
     * @return value of the obtained record field
     */
    String get(StudentRecord record);
}
