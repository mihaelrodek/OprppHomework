package hr.fer.oprpp1.hw04.db;

import java.util.Objects;

/**
 * Class that represents a Student wth first name, last name, jmbag and final grade.
 * It is considered as an entry(column) in database.
 */
public class StudentRecord {

    /**
     * Student's JMBAG
     */
    private String jmbag;
    /**
     * Student's last name
     */
    private String lastName;
    /**
     * Student's first name
     */
    private String firstName;
    /**
     * Student's final grade
     */
    private int finalGrade;

    /**
     * Default constructor
     *
     * @param jmbag      Student's JMBAG
     * @param lastName   Student's last name
     * @param firstName  Student's first name
     * @param finalGrade Student's final grade
     */
    public StudentRecord(String jmbag, String lastName, String firstName, int finalGrade) {
        this.jmbag = jmbag;
        this.lastName = lastName;
        this.firstName = firstName;
        this.finalGrade = finalGrade;
    }

    /**
     * Getter for JMBAG
     *
     * @return jmbag
     */
    public String getJmbag() {
        return jmbag;
    }

    /**
     * Getter for last name
     *
     * @return last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Getter for first name
     *
     * @return first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Getter for final grade
     *
     * @return final grade
     */
    public int getFinalGrade() {
        return finalGrade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentRecord that = (StudentRecord) o;
        return jmbag.equals(that.jmbag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jmbag);
    }

    @Override
    public String toString() {
        return getJmbag() + '\t' + getLastName() + '\t' + getFirstName() + '\t' + getFinalGrade();
    }
}
