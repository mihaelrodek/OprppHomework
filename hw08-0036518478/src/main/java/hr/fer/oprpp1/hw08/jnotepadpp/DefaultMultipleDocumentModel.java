package hr.fer.oprpp1.hw08.jnotepadpp;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {


    /**
     * List of listeners.
     */
    private List<MultipleDocumentListener> listeners = new ArrayList<>();

    /**
     * List of documents that represent collection of {@link SingleDocumentModel} objects.
     */
    private List<SingleDocumentModel> documents = new ArrayList<>();

    /**
     * Current document.
     */
    private SingleDocumentModel document ;


    /**
     * Basic constructor.
     */
    public DefaultMultipleDocumentModel() {
        this.addChangeListener(e -> {
            SingleDocumentModel oldDocument = document;
            int index = getSelectedIndex();
            document = documents.get(index);
            notifyCurrentDocumentChanged(oldDocument, document);
        });
    }

    private void notifyCurrentDocumentChanged(SingleDocumentModel oldDoc, SingleDocumentModel newDoc) {
        for (var l : listeners) {
            l.currentDocumentChanged(oldDoc, newDoc);
        }
    }

    @Override
    public SingleDocumentModel createNewDocument() {
        return addDocument(null, "");

    }

    @Override
    public SingleDocumentModel getCurrentDocument() {
        return document;
    }

    @Override
    public SingleDocumentModel loadDocument(Path path){
        Objects.requireNonNull(path, "Path mustn't be null!");


        if (!Files.isReadable(path)) throw new RuntimeException("Invalid path to the file!");

        for (SingleDocumentModel doc : documents) {
            if (path.equals(doc.getFilePath())&& doc.getFilePath() != null) {
                notifyCurrentDocumentChanged(document, doc);
                document = doc;
                return doc;
            }
        }

        byte[] okteti;

        try {
            okteti = Files.readAllBytes(path);
        } catch (Exception ex) {
            throw new RuntimeException("File is not readable");
        }

        return addDocument(path,  new String(okteti, StandardCharsets.UTF_8));
    }

    private SingleDocumentModel addDocument(Path path, String pathText) {
        SingleDocumentModel newDocument = new DefaultSingleDocumentModel(path, pathText);

        documents.add(newDocument);
        notifyAdded(newDocument);
        notifyCurrentDocumentChanged(document, newDocument);
        document = newDocument;
        newDocument.addSingleDocumentListener(new SingleDocumentListener() {
            int index = DefaultMultipleDocumentModel.this.documents.indexOf(model);

            @Override
            public void documentModifyStatusUpdated(SingleDocumentModel model) {

                if (model.isModified())
                    DefaultMultipleDocumentModel.this.setIconAt(index, getIcon("redDisk.png"));
                else
                    DefaultMultipleDocumentModel.this.setIconAt(index, getIcon("greenDisk.png"));
            }

            @Override
            public void documentFilePathUpdated(SingleDocumentModel model) {
                setTitleAt(index, model.getFilePath().getFileName().toString());
                setToolTipTextAt(index, model.getFilePath().toString());
            }
        });

        addDocumentToPane(newDocument);
        return newDocument;
    }

    private void addDocumentToPane(SingleDocumentModel newDocument) {
        JPanel panel = new JPanel();
        JScrollPane scrollPane = new JScrollPane();

        scrollPane.add(newDocument.getTextComponent());

        panel.setLayout(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);

        String fileName = "(unnamed)";
        String fullName = "(unnamed)";

        if (newDocument.getFilePath() != null) {
            fileName = newDocument.getFilePath().getFileName().toString();
            fullName = newDocument.getFilePath().toAbsolutePath().toString();
        }

        insertTab(fileName
                , getIcon("greenDisk.png")
                , panel
                , fullName
                , getTabCount());
        setSelectedComponent(panel);
    }

    private ImageIcon getIcon(String icon) {

        InputStream is = this.getClass().getResourceAsStream(icon);
        if (is == null)
            throw new RuntimeException("Icon " + icon + " doesn't exist!");

        byte[] bytes;
        try {
            bytes = is.readAllBytes();
            is.close();
        } catch (IOException e) {
            throw new RuntimeException("Error while reading icon!");
        }

        return new ImageIcon(new ImageIcon(bytes).getImage().getScaledInstance(12, 12, Image.SCALE_SMOOTH));
    }

    private void notifyAdded(SingleDocumentModel document) {
        for (MultipleDocumentListener l : listeners)
            l.documentAdded(document);
    }

    @Override
    public void saveDocument(SingleDocumentModel model, Path newPath) {
        Objects.requireNonNull(model, "Document mustn't be null");
        for (SingleDocumentModel document : documents) {
            if (document.equals(model)) {
                continue;
            }

            if (document.getFilePath() != null && document.getFilePath().equals(newPath) && !document.equals(model)) {
                throw new RuntimeException("File is already opened!");
            }
        }

        byte[] podaci = model.getTextComponent().getText().getBytes(StandardCharsets.UTF_8);

        if (newPath == null) {
            newPath = model.getFilePath();
        } else {
            document.setFilePath(newPath);
        }

        try {
            Files.write(newPath, podaci);
        } catch (IOException e1) {
            throw new RuntimeException("Error while writing to file!");
        }

        document.setModified(false);
    }

    @Override
    public void closeDocument(SingleDocumentModel model) {
        if (documents.size() == 0) throw new RuntimeException("There are no opened documents!");
        else if (documents.size() == 1) createNewDocument();

        int newTabIndex = documents.indexOf(model) - 1;

        removeTabAt(documents.indexOf(model));

        if (newTabIndex < 0)
            newTabIndex = 0;

        setSelectedIndex(newTabIndex);
        documents.remove(model);
        document = documents.get(newTabIndex);

        for (MultipleDocumentListener l : listeners) {
            l.documentRemoved(model);
        }

    }

    @Override
    public void addMultipleDocumentListener(MultipleDocumentListener l) {
        listeners.add(l);
    }

    @Override
    public void removeMultipleDocumentListener(MultipleDocumentListener l) {
        listeners.remove(l);
    }

    @Override
    public int getNumberOfDocuments() {
        return documents.size();
    }

    @Override
    public SingleDocumentModel getDocument(int index) {
        return documents.get(index);
    }

    @Override
    public Iterator<SingleDocumentModel> iterator() {
        return documents.iterator();
    }

}
