package hr.fer.oprpp1.java.hw05.shell;

import java.io.IOException;
import java.util.List;

/**
 * Interface that represents a single shell command.
 *
 * @author Mihael Rodek
 */
public interface ShellCommand {

    /**
     * Method that executes the given command in given Environment.
     *
     * @param env       environment to execute the command in
     * @param arguments string containing all command line arguments
     * @return {@link ShellStatus} as a result of command
     */
    ShellStatus executeCommand(Environment env, String arguments) throws IOException;

    /**
     * Method that returns the name of the given command.
     *
     * @return name of command
     */
    String getCommandName();

    /**
     * Method that returns the description of the given command.
     *
     * @return description of given command
     */
    List<String> getCommandDescription();

}
