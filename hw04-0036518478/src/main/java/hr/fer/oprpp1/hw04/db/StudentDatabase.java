package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class that represents database of students. Database is stored as List of studentRecords entries.
 *
 * @author Mihael Rodek
 */
public class StudentDatabase {

    /**
     * Map of student records, database.
     */
    private HashMap<String, StudentRecord> index;
    /**
     * Collection of student records.
     */
    private List<StudentRecord> studentRecords;

    /**
     * Method used to parse records stored as string to database
     *
     * @param database list of strings to be parsed into database
     */
    public StudentDatabase(List<String> database) {
        studentRecords = new ArrayList<>(database.size());
        index = new HashMap<>();

        for (String string : database) {
            String[] singleEntry = string.split("\\t+");

            if (singleEntry.length != 4)
                throw new IllegalArgumentException("Database is not valid or properly formatted");

            StudentRecord record = new StudentRecord(singleEntry[0], singleEntry[1], singleEntry[2], Integer.parseInt(singleEntry[3]));
            studentRecords.add(record);
            index.put(singleEntry[0], record);
        }
    }

    /**
     * Method that returns @{@link StudentRecord} for given jmbag
     *
     * @param jmbag waanted jmbag
     * @return @{@link StudentRecord} for given jmbag
     */

    public StudentRecord forJMBAG(String jmbag) {
        return index.get(jmbag);
    }

    /**
     * Method that filters database applying given filter
     *
     * @param filter filter to be applied on database
     * @return filtered database
     */

    public List<StudentRecord> filter(IFilter filter) {
        List<StudentRecord> filteredList = new ArrayList<>();
        for (StudentRecord record : studentRecords)
            if (filter.accepts(record))
                filteredList.add(record);
        return filteredList;
    }
}
