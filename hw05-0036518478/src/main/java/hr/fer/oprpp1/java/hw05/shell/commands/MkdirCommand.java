package hr.fer.oprpp1.java.hw05.shell.commands;

import hr.fer.oprpp1.java.hw05.shell.Environment;
import hr.fer.oprpp1.java.hw05.shell.ShellCommand;
import hr.fer.oprpp1.java.hw05.shell.ShellStatus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class that represents mkdir command.
 * This class creates a new directory with appropriate structure.
 *
 * @author Mihael Rodek
 */
public class MkdirCommand implements ShellCommand {

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {

        String mkdirRegex = "\\w+";
        Pattern pattern = Pattern.compile(mkdirRegex);
        Matcher matcher = pattern.matcher(arguments);

        try {
            if (matcher.find()) {
                Files.createDirectories(Paths.get(arguments));
                env.writeln("Directory created!");
            } else env.writeln("Entered invalid file name, please try again.");

        } catch (IOException e) {
            env.writeln("Error occurred while trying to create new directory");
        }
        return ShellStatus.CONTINUE;
    }

    @Override
    public String getCommandName() {
        return "mkdir";
    }

    @Override
    public List<String> getCommandDescription() {
        return Collections.singletonList("Mkdir command.\n" +
                "Command takes a single argument directory name and creates the appropriate directory structure.");
    }
}
