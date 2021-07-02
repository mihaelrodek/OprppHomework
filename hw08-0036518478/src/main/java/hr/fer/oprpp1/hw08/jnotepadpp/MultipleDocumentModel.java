package hr.fer.oprpp1.hw08.jnotepadpp;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Interface to represent multiple document structure.
 *
 * @author Mihael Rodek
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {
    /**
     * Method that creates new document.
     *
     * @return new instance of {@link SingleDocumentModel}
     */
    SingleDocumentModel createNewDocument();

    /**
     * Method that returns current active document.
     *
     * @return current active {@link SingleDocumentModel}
     */
    SingleDocumentModel getCurrentDocument();

    /**
     * Method that loads document from given path
     *
     * @param path path from which document is loaded from
     * @return new instance of {@link SingleDocumentModel}
     */

    SingleDocumentModel loadDocument(Path path) throws IOException;

    /**
     * Method used to save document to the given path
     *
     * @param model   document to be saved
     * @param newPath path to be saved to
     */
    void saveDocument(SingleDocumentModel model, Path newPath);

    /**
     * Method that closes given document
     *
     * @param model document to be closed
     */
    void closeDocument(SingleDocumentModel model);

    /**
     * Method that adds listeners to this multiple document structure
     *
     * @param l listeners to be added
     */
    void addMultipleDocumentListener(MultipleDocumentListener l);

    /**
     * Method that removes listeners to this multiple document structure
     *
     * @param l listeners to be removed
     */
    void removeMultipleDocumentListener(MultipleDocumentListener l);

    /**
     * Method used for getting current number of documents.
     *
     * @return number of documents
     */
    int getNumberOfDocuments();

    /**
     * Method used for getting document at given index.
     *
     * @param index index of document to get
     * @return document at given index
     */
    SingleDocumentModel getDocument(int index);

}
