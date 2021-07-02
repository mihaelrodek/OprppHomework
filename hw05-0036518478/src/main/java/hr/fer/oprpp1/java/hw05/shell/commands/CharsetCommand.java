package hr.fer.oprpp1.java.hw05.shell.commands;

import hr.fer.oprpp1.java.hw05.shell.Environment;
import hr.fer.oprpp1.java.hw05.shell.ShellCommand;
import hr.fer.oprpp1.java.hw05.shell.ShellStatus;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;

/**
 * Class that represents charsets command.
 * This command is used to list names of all supported charsets.
 *
 * @author Mihael Rodek
 */

public class CharsetCommand implements ShellCommand {
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) throws IOException {
        printAvailableCharsets(env);
        return ShellStatus.CONTINUE;
    }

    @Override
    public String getCommandName() {
        return "charset";
    }

    public static void printAvailableCharsets(Environment env) throws IOException {
        env.writeln("Available charsets are:");
        for (var c : Charset.availableCharsets().keySet()) {
            env.writeln(c);
        }
    }

    @Override
    public List<String> getCommandDescription() {
        return Collections.singletonList("""
                Charset command.
                Command charsets takes no arguments and lists names of all supported charsets.
                A single charset name is written per line.""");
    }
}
