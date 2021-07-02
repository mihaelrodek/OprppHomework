package hr.fer.oprpp1.hw08.jnotepadpp;

import hr.fer.oprpp1.hw08.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LJMenu;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizationProvider;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Collator;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class JNotepadPP extends JFrame {

    private JLabel time = new JLabel();
    private JLabel length = new JLabel();
    private JLabel info = new JLabel();


    private MultipleDocumentModel model;
    private static final String DEFAULT_PATH = "Untitled";
    private Action blankDocumentAction;
    private Action openAction;
    private Action closeAction;
    private Action saveAction;
    private Action saveAsAction;
    private Action cutAction;
    private Action copyAction;
    private Action pasteAction;
    private Action exitAction;
    private Action statisticsAction;
    private Action ascendingAction;
    private Action descendingAction;
    private Action upperCaseAction;
    private Action lowerCaseAction;
    private Action invertCaseAction;
    private Action engAction;
    private Action hrAction;
    private Action deAction;


    FormLocalizationProvider formLocalizationProvider;

    public static void main(String[] args) {

        LocalizationProvider.getInstance().setLanguage("en");
        SwingUtilities.invokeLater(() -> new JNotepadPP().setVisible(true));

    }

    public JNotepadPP() {
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.setTitle("JNotepad++");
        this.setLocationRelativeTo(null);
        this.setSize(800, 600);
        this.formLocalizationProvider = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
        initGUI();
    }

    void initGUI() {

        DefaultMultipleDocumentModel tab = new DefaultMultipleDocumentModel();
        BorderLayout borderLayout = new BorderLayout();

        this.getContentPane().setLayout(borderLayout);
        this.getContentPane().add(tab, BorderLayout.CENTER);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitAction.actionPerformed(null);
            }
        });

        this.model = tab;

        model.createNewDocument();

        String path;
        if (model.getCurrentDocument().getFilePath() != null) {
            path = model.getCurrentDocument().getFilePath().getFileName().toString();
        } else path = DEFAULT_PATH;

        setTitle(path + " - JNotepad++");

        initActions();
        createMenu();
        createToolbar();
        createStatus();
        createClock();

        model.addMultipleDocumentListener(new MultipleDocumentListener() {

            @Override
            public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
                String changedPath;
                if (currentModel.getFilePath() != null) {
                    changedPath = currentModel.getFilePath().getFileName().toString();
                } else changedPath = DEFAULT_PATH;
                setTitle(changedPath + " - JNotepad++");
                currentModel.getTextComponent().addCaretListener(e -> statusChanged());
                statusChanged();

            }

            @Override
            public void documentAdded(SingleDocumentModel model) {

            }

            @Override
            public void documentRemoved(SingleDocumentModel model) {

            }
        });

        model.getCurrentDocument().getTextComponent().addCaretListener(e -> statusChanged());

        statusChanged();
    }

    private void createClock() {

        new Timer(1000, e -> {
            Calendar c = Calendar.getInstance();

            int day = c.get(Calendar.DAY_OF_MONTH);
            int month = c.get(Calendar.MONTH) + 1;
            int year = c.get(Calendar.YEAR);
            int second = c.get(Calendar.SECOND);
            int minute = c.get(Calendar.MINUTE);
            int hour = c.get(Calendar.HOUR_OF_DAY);

            time.setText(String.format(" %d-%02d-%02d %02d:%02d:%02d ",
                    year, day, month, hour, minute, second));
        }).start();
    }

    private void createToolbar() {
        JToolBar toolBar = new JToolBar(formLocalizationProvider.getString("tools"));
        toolBar.setFloatable(true);

        toolBar.add(new JButton(blankDocumentAction));
        toolBar.add(new JButton(openAction));
        toolBar.add(new JButton(saveAction));
        toolBar.add(new JButton(saveAsAction));

        toolBar.addSeparator();

        toolBar.add(new JButton(cutAction));
        toolBar.add(new JButton(copyAction));
        toolBar.add(new JButton(pasteAction));

        toolBar.addSeparator();

        toolBar.add(new JButton(statisticsAction));

        toolBar.addSeparator();

        toolBar.add(new JButton(closeAction));
        toolBar.add(new JButton(exitAction));

        this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
    }

    private void createStatus() {

        JToolBar statusBar = new JToolBar(formLocalizationProvider.getString("stats"));

        statusBar.setLayout(new BorderLayout());
        statusBar.setFloatable(true);

        statusBar.add(length, BorderLayout.WEST);
        statusBar.add(info, BorderLayout.CENTER);
        statusBar.add(time, BorderLayout.EAST);

        this.getContentPane().add(statusBar, BorderLayout.PAGE_END);
    }

    private void createMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new LJMenu("file", formLocalizationProvider);
        JMenu helpMenu = new LJMenu("help", formLocalizationProvider);
        JMenu editMenu = new LJMenu("edit", formLocalizationProvider);
        JMenu lanMenu = new LJMenu("languages", formLocalizationProvider);
        JMenu toolsMenu = new LJMenu("tools", formLocalizationProvider);
        JMenu subCaseMenu = new LJMenu("case", formLocalizationProvider);
        JMenu subSortMenu = new LJMenu("menu", formLocalizationProvider);

        menuBar.add(fileMenu);

        fileMenu.add(new JMenuItem(blankDocumentAction));
        fileMenu.add(new JMenuItem(openAction));
        fileMenu.add(new JMenuItem(saveAction));
        fileMenu.add(new JMenuItem(saveAsAction));
        fileMenu.addSeparator();
        fileMenu.add(new JMenuItem(closeAction));
        fileMenu.add(new JMenuItem(exitAction));

        menuBar.add(editMenu);

        editMenu.add(new JMenuItem(copyAction));
        editMenu.add(new JMenuItem(cutAction));
        editMenu.add(new JMenuItem(pasteAction));

        menuBar.add(helpMenu);

        helpMenu.add(new JMenuItem(statisticsAction));

        menuBar.add(lanMenu);

        lanMenu.add(new JMenuItem(hrAction));
        lanMenu.add(new JMenuItem(engAction));
        lanMenu.add(new JMenuItem(deAction));

        menuBar.add(toolsMenu);

        subCaseMenu.add(new JMenuItem(upperCaseAction));
        subCaseMenu.add(new JMenuItem(lowerCaseAction));
        subCaseMenu.add(new JMenuItem(invertCaseAction));

        toolsMenu.add(subCaseMenu);

        toolsMenu.addSeparator();
        subSortMenu.add(new JMenuItem(ascendingAction));
        subSortMenu.add(new JMenuItem(descendingAction));

        toolsMenu.add(subSortMenu);

        this.setJMenuBar(menuBar);
    }


    private void initActions() {
        blankDocumentAction = new LocalizableAction("new", formLocalizationProvider) {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.createNewDocument();
                statusChanged();
            }
        };
        blankDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
        blankDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
        blankDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Create new blank document.");

        openAction = new LocalizableAction("open", formLocalizationProvider) {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser();
                jfc.setDialogTitle("Open file");
                if (jfc.showOpenDialog(JNotepadPP.this) == JFileChooser.CANCEL_OPTION) {
                    return;
                }
                try {
                    model.loadDocument(jfc.getSelectedFile().toPath());
                } catch (RuntimeException | IOException ex) {
                    ex.printStackTrace();
                }
                statusChanged();
            }
        };
        openAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
        openAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
        openAction.putValue(Action.SHORT_DESCRIPTION, "Open single file from disk");

        saveAction = new LocalizableAction("save", formLocalizationProvider) {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (model.getCurrentDocument().getFilePath() == null) {
                    saveAsAction.actionPerformed(e);
                    return;
                }

                try {
                    model.saveDocument(model.getCurrentDocument(), null);
                } catch (RuntimeException ex) {
                    JOptionPane.showMessageDialog(JNotepadPP.this, "Error while saving to " + model.getCurrentDocument()
                                    .getFilePath(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                JOptionPane.showMessageDialog(JNotepadPP.this, "Saved.", "Info", JOptionPane
                        .INFORMATION_MESSAGE);
            }
        };
        saveAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
        saveAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
        saveAction.putValue(Action.SHORT_DESCRIPTION, "Save file");

        saveAsAction = new LocalizableAction("saveas", formLocalizationProvider) {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser();
                jfc.setDialogTitle("Save file");
                if (jfc.showSaveDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
                    JOptionPane.showMessageDialog(JNotepadPP.this, "Nothing saved.", "Warning", JOptionPane
                            .WARNING_MESSAGE);
                    return;
                }
                Path savePath = jfc.getSelectedFile().toPath();

                if (Files.exists(savePath)) {
                    String saveMessage = "File already exists. Are you sure you want to save to selected file?";
                    int pressed = JOptionPane.showConfirmDialog(JNotepadPP.this,
                            saveMessage, "Saving",
                            JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                    if (pressed == JOptionPane.NO_OPTION) {
                        return;
                    }
                }

                try {
                    model.saveDocument(model.getCurrentDocument(), savePath);
                } catch (RuntimeException ex) {
                    JOptionPane.showMessageDialog(JNotepadPP.this, "Error while saving to " + savePath,
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                JOptionPane.showMessageDialog(JNotepadPP.this, "Saved.", "Info", JOptionPane
                        .INFORMATION_MESSAGE);
            }
        };
        saveAsAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
        saveAsAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_W);
        saveAsAction.putValue(Action.SHORT_DESCRIPTION, "Save As file");

        closeAction = new LocalizableAction("closetab", formLocalizationProvider) {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!model.getCurrentDocument().isModified()) {
                    model.closeDocument(model.getCurrentDocument());
                    return;
                }
                String closeMessage = "There is unsaved work! Do you want to close tab?";
                int pressed = JOptionPane.showConfirmDialog(JNotepadPP.this,
                        closeMessage, "Exit",
                        JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
                if (pressed == JOptionPane.YES_OPTION) {
                    model.closeDocument(model.getCurrentDocument());
                }
                statusChanged();
            }
        };

        closeAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control D"));
        closeAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
        closeAction.putValue(Action.SHORT_DESCRIPTION, "Close");

        cutAction = new LocalizableAction("cut", formLocalizationProvider) {
            Action action = new DefaultEditorKit.CutAction();

            @Override
            public void actionPerformed(ActionEvent e) {
                action.actionPerformed(e);
            }
        };

        cutAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
        cutAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
        cutAction.putValue(Action.SHORT_DESCRIPTION, "Cut");

        copyAction = new LocalizableAction("copy", formLocalizationProvider) {
            Action action = new DefaultEditorKit.CopyAction();

            @Override
            public void actionPerformed(ActionEvent e) {
                action.actionPerformed(e);
            }
        };

        copyAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
        copyAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
        copyAction.putValue(Action.SHORT_DESCRIPTION, "Copy");

        pasteAction = new LocalizableAction("paste", formLocalizationProvider) {
            Action action = new DefaultEditorKit.PasteAction();

            @Override
            public void actionPerformed(ActionEvent e) {
                action.actionPerformed(e);
            }
        };

        pasteAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
        pasteAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_V);
        pasteAction.putValue(Action.SHORT_DESCRIPTION, "Paste");


        statisticsAction = new LocalizableAction("stats", formLocalizationProvider) {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = model.getCurrentDocument().getTextComponent().getText();

                int numberOfNonBlankCharacters = text.replaceAll("\\s+", "").length();
                int numberOfLines = text.length() - text.replaceAll("\n", "").length() + 1;

                String statisticmessage = "Number of characters: " + text.length() + "\n" + "Number of non blank characters: " +
                        "" + numberOfNonBlankCharacters + "\n" + "Number of lines: " + numberOfLines;

                JOptionPane.showMessageDialog(JNotepadPP.this, statisticmessage, "Info", JOptionPane
                        .INFORMATION_MESSAGE);
            }
        };

        statisticsAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control B"));
        statisticsAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_B);
        statisticsAction.putValue(Action.SHORT_DESCRIPTION, "Statistics");

        exitAction = new LocalizableAction("exit", formLocalizationProvider) {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < model.getNumberOfDocuments(); i++) {
                    if (model.getDocument(i).isModified()) {
                        String unsavedmessage = "There is unsaved work! Do you want to close tab" + i + " ?";
                        int pressed = JOptionPane.showConfirmDialog(JNotepadPP.this,
                                unsavedmessage, "Exiting",
                                JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
                        if (pressed == JOptionPane.YES_OPTION) {
                            model.closeDocument(model.getDocument(i));
                        } else {
                            return;
                        }
                        statusChanged();
                    }
                }
                dispose();
            }
        };

        exitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Q"));
        exitAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_Q);
        exitAction.putValue(Action.SHORT_DESCRIPTION, "Exit");

        engAction = new LocalizableAction("en", formLocalizationProvider) {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocalizationProvider.getInstance().setLanguage("en");
            }
        };

        engAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control E"));
        engAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
        engAction.putValue(Action.SHORT_DESCRIPTION, "To English");

        hrAction = new LocalizableAction("hr", formLocalizationProvider) {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocalizationProvider.getInstance().setLanguage("hr");
            }
        };

        hrAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control K"));
        hrAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_K);
        hrAction.putValue(Action.SHORT_DESCRIPTION, "To Croatian");

        deAction = new LocalizableAction("de", formLocalizationProvider) {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocalizationProvider.getInstance().setLanguage("de");
            }
        };

        deAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control P"));
        deAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_P);
        deAction.putValue(Action.SHORT_DESCRIPTION, "To German");

        upperCaseAction = new LocalizableAction("upper", formLocalizationProvider) {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextArea editor = model.getCurrentDocument().getTextComponent();
                Caret caret = editor.getCaret();
                Document doc = editor.getDocument();
                int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
                int offset = len != 0 ? Math.min(caret.getDot(), caret.getMark()) : editor.getDocument().getLength();

                try {
                    String text = doc.getText(offset, len).toUpperCase();
                    doc.remove(offset, len);
                    doc.insertString(offset, text, null);
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
            }
        };
        upperCaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control U"));
        upperCaseAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);
        upperCaseAction.putValue(Action.SHORT_DESCRIPTION, "To upper case");

        lowerCaseAction = new LocalizableAction("lower", formLocalizationProvider) {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextArea editor = model.getCurrentDocument().getTextComponent();
                Caret caret = editor.getCaret();
                Document doc = editor.getDocument();
                int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
                int offset = len != 0 ? Math.min(caret.getDot(), caret.getMark()) : editor.getDocument().getLength();

                try {
                    String text = doc.getText(offset, len).toLowerCase();
                    doc.remove(offset, len);
                    doc.insertString(offset, text, null);
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
            }
        };
        lowerCaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control L"));
        lowerCaseAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);
        lowerCaseAction.putValue(Action.SHORT_DESCRIPTION, "To lower case");

        invertCaseAction = new LocalizableAction("invert", formLocalizationProvider) {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextArea editor = model.getCurrentDocument().getTextComponent();
                Caret caret = model.getCurrentDocument().getTextComponent().getCaret();

                int len = Math.abs(caret.getDot() - caret.getMark());
                int offset = len != 0 ? Math.min(caret.getDot(), caret.getMark()) : editor.getDocument().getLength();


                Document doc = editor.getDocument();
                try {
                    String text = doc.getText(offset, len);
                    text = changeCase(text);
                    doc.remove(offset, len);
                    doc.insertString(offset, text, null);
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
            }

        };

        invertCaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control T"));
        invertCaseAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
        invertCaseAction.putValue(Action.SHORT_DESCRIPTION, "Invert case");

        ascendingAction = new LocalizableAction("asc", formLocalizationProvider) {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextArea editor = model.getCurrentDocument().getTextComponent();
                Caret caret = model.getCurrentDocument().getTextComponent().getCaret();

                int len = Math.abs(caret.getDot() - caret.getMark());

                int offset = len != 0 ? Math.min(caret.getDot(), caret.getMark()) : editor.getDocument().getLength();


                Document doc = editor.getDocument();
                try {
                    offset = editor.getLineStartOffset(editor.getLineOfOffset(offset));
                    len = editor.getLineEndOffset(editor.getLineOfOffset(len + offset));
                    String text = doc.getText(offset, len - offset);

                    List<String> sort = Arrays.asList(text.split("\\r?\\n"));
                    Collections.sort(sort, (o1, o2) -> Collator.getInstance().compare(o1, o2));
                    int lines = editor.getLineCount();
                    doc.remove(offset, len - offset);
                    for (String string : sort) {
                        doc.insertString(offset, string + (--lines > 0 ? "\n" : ""), null);
                        offset += string.length() + 1;
                    }
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
            }
        };

        ascendingAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control F"));
        ascendingAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_F);
        ascendingAction.putValue(Action.SHORT_DESCRIPTION, "Sort ascending");


        descendingAction = new LocalizableAction("desc", formLocalizationProvider) {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextArea editor = model.getCurrentDocument().getTextComponent();
                Caret caret = model.getCurrentDocument().getTextComponent().getCaret();

                int len = Math.abs(caret.getDot() - caret.getMark());

                int offset = len != 0 ? Math.min(caret.getDot(), caret.getMark()) : editor.getDocument().getLength();


                Document doc = editor.getDocument();
                try {
                    offset = editor.getLineStartOffset(editor.getLineOfOffset(offset));
                    len = editor.getLineEndOffset(editor.getLineOfOffset(len + offset));
                    String text = doc.getText(offset, len - offset);

                    List<String> sort = Arrays.asList(text.split("\\r?\\n"));
                    Collections.sort(sort, (o1, o2) -> Collator.getInstance().compare(o2, o1));
                    int lines = editor.getLineCount();
                    doc.remove(offset, len - offset);
                    for (String string : sort) {
                        doc.insertString(offset, string + (--lines > 0 ? "\n" : ""), null);
                        offset += string.length() + 1;
                    }
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
            }
        };
        descendingAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control G"));
        descendingAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_G);
        descendingAction.putValue(Action.SHORT_DESCRIPTION, "Sort descending");


    }

    private String changeCase(String text) {
        char[] znakovi = text.toCharArray();
        for (int i = 0; i < znakovi.length; i++) {
            char c = znakovi[i];
            if (Character.isLowerCase(c)) {
                znakovi[i] = Character.toUpperCase(c);
            } else if (Character.isUpperCase(c)) {
                znakovi[i] = Character.toLowerCase(c);
            }
        }
        return new String(znakovi);
    }

    private void statusChanged() {
        JTextArea editor = model.getCurrentDocument().getTextComponent();
        int caretPosition = editor.getCaretPosition();
        length.setText(String.format("%s: %d", formLocalizationProvider.getString("length"), editor.getText().length()));
        try {
            int currentLine = editor.getLineOfOffset(caretPosition);
            int currentColumn = caretPosition - editor.getLineStartOffset(currentLine);
            int selected = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
            info.setText(String.format("    %s:%d %s:%d %s:%d", formLocalizationProvider.getString("ln"), currentLine + 1,
                    formLocalizationProvider.getString("col"), currentColumn, formLocalizationProvider.getString("sel"), selected));

        } catch (BadLocationException ignored) {
        }

    }
}