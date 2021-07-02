package hr.fer.oprpp1.hw08.jnotepadpp;

import java.io.IOException;

/**
 * Listener for @{@link SingleDocumentModel}
 *
 * @author Mihael Rodek
 */
public interface SingleDocumentListener {

    /**
     * This method is called when modify status is updated.
     *
     * @param model
     */
    void documentModifyStatusUpdated(SingleDocumentModel model) ;

    /**
     * This method is called when file path is updated.
     *
     * @param model
     */
    void documentFilePathUpdated(SingleDocumentModel model);

}
