package hr.fer.oprpp1.hw08.jnotepadpp;


/**
 * Listener for @{@link MultipleDocumentModel}
 *
 * @author Mihael Rodek
 */
public interface MultipleDocumentListener {

    /**
     * Method called when current document is changed.
     *
     * @param previousModel previous document
     * @param currentModel  document to change to
     */
    void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);

    /**
     * Method called when document is added.
     *
     * @param model document to be added
     */
    void documentAdded(SingleDocumentModel model);


    /**
     * Method called when document is removed.
     *
     * @param model document to be removed
     */
    void documentRemoved(SingleDocumentModel model);
}
