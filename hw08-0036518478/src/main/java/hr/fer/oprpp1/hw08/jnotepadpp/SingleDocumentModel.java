package hr.fer.oprpp1.hw08.jnotepadpp;

import javax.swing.*;
import java.nio.file.Path;

/**
 * Interface to represent single document in {@link MultipleDocumentModel}
 *
 * @author Mihael Rodek
 */
public interface SingleDocumentModel {

    /**
     * Method used for getting JTextArea
     *
     * @return JTextArea
     */
    JTextArea getTextComponent();

    /**
     * Method used to get path of the file
     *
     * @return path to the file
     */

    Path getFilePath();

    /**
     * Method used to set path to the file
     *
     * @param path
     */
    void setFilePath(Path path);

    /**
     * Method to check if file is modified
     *
     * @return true if it is modified, false otherwise
     */

    boolean isModified();

    /**
     * Method used to set if file is modified or not
     *
     * @param modified value of modified file
     */
    void setModified(boolean modified);

    /**
     * Method that adds listener for @{@link SingleDocumentModel}
     *
     * @param l listener to be added
     */
    void addSingleDocumentListener(SingleDocumentListener l);


    /**
     * Method that removes listener for @{@link SingleDocumentModel}
     *
     * @param l listener to be removed
     */
    void removeSingleDocumentListener(SingleDocumentListener l);
}
