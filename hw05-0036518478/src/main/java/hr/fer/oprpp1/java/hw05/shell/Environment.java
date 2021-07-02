package hr.fer.oprpp1.java.hw05.shell;

import java.io.IOException;
import java.util.SortedMap;

/**
 * Interface that represents models Environment.
 * Environment is used for communicating between operating system and shell.
 *
 * @author Mihael Rodek
 */
public interface Environment {

    String readLine() throws ShellIOException, IOException;

    void write(String text) throws ShellIOException, IOException;

    void writeln(String text) throws ShellIOException;

    SortedMap<String, ShellCommand> commands();

    Character getMultilineSymbol();

    void setMultilineSymbol(Character symbol);

    Character getPromptSymbol();

    void setPromptSymbol(Character symbol);

    Character getMorelinesSymbol();

    void setMorelinesSymbol(Character symbol);
}
