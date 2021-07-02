package hr.fer.oprpp1.hw04.db;

/**
 * Class that represents getters for first name, last name and jmbag.
 *
 * @author Mihael Rodek
 */
public class FieldValueGetters {
    /**
     * First name getter
     */
    public static final IFieldValueGetter FIRST_NAME = (r) -> r.getFirstName();
    /**
     * Last name getter
     */
    public static final IFieldValueGetter LAST_NAME = (r) -> r.getLastName();
    /**
     * Jmbag getter
     */
    public static final IFieldValueGetter JMBAG = (r) -> r.getJmbag();


}
