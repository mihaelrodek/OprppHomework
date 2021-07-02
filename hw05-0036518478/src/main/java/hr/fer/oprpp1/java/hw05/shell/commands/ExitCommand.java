package hr.fer.oprpp1.java.hw05.shell.commands;

import hr.fer.oprpp1.java.hw05.shell.Environment;
import hr.fer.oprpp1.java.hw05.shell.ShellCommand;
import hr.fer.oprpp1.java.hw05.shell.ShellStatus;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Class that represents exit command.
 * This command is used to exit the shell and terminate the program.
 *
 * @author Mihael Rodek
 */
public class ExitCommand implements ShellCommand {
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) throws IOException {
        if (arguments.length() == 0) {
            return ShellStatus.TERMINATE;
        } else {
            env.writeln("Invalid command, exit must run without arguments");
            return ShellStatus.CONTINUE;
        }
    }

    @Override
    public String getCommandName() {
        return "exit";
    }

    @Override
    public List<String> getCommandDescription() {
        return Collections.singletonList("Exit command.\n" +
                "Command that exits current shell and stops the program.");
    }
}
