package hr.fer.oprpp1.hw08.jnotepadpp;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class that represents default single document model.
 *
 * @author Mihael Rodek
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {

    /**
     * List of listeners.
     */
    private List<SingleDocumentListener> listeners = new ArrayList<>();


    /**
     * Text area used to write text.
     */
    private JTextArea textArea;

    /**
     * File path.
     */
    private Path path;

    /**
     * Flag for modified status.
     */
    private boolean modified = false;


    public DefaultSingleDocumentModel(Path path, String context) {
        this.path = path;
        this.textArea = new JTextArea(context);
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                modified = true;
                for (SingleDocumentListener l : listeners)
                    l.documentModifyStatusUpdated(DefaultSingleDocumentModel.this);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                modified = true;
                for (SingleDocumentListener l : listeners)
                    l.documentModifyStatusUpdated(DefaultSingleDocumentModel.this);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                modified = true;
                for (SingleDocumentListener l : listeners)
                    l.documentModifyStatusUpdated(DefaultSingleDocumentModel.this);
            }
        });
    }


    @Override
    public JTextArea getTextComponent() {
        return textArea;
    }

    @Override
    public Path getFilePath() {
        return path;
    }

    @Override
    public void setFilePath(Path path) {
        this.path = Objects.requireNonNull(path, "Path cannot be null");
        for (SingleDocumentListener l : listeners)
            l.documentFilePathUpdated(this);

    }

    @Override
    public boolean isModified() {
        return modified;
    }

    @Override
    public void setModified(boolean modified) {
        this.modified = modified;
        for (SingleDocumentListener l : listeners)
            l.documentModifyStatusUpdated(DefaultSingleDocumentModel.this);
    }

    @Override
    public void addSingleDocumentListener(SingleDocumentListener l) {
        listeners.add(l);
    }

    @Override
    public void removeSingleDocumentListener(SingleDocumentListener l) {
        listeners.remove(l);
    }
}
