package hr.fer.oprpp1.java.hw05.shell.commands;

import hr.fer.oprpp1.java.hw05.shell.Environment;
import hr.fer.oprpp1.java.hw05.shell.ShellCommand;
import hr.fer.oprpp1.java.hw05.shell.ShellStatus;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;

/**
 * Class that represents help command.
 * This command can list names of all supported commands or print name and the description of selected command
 * depending on user request.
 *
 * @author Mihael Rodek
 */
public class HelpCommand implements ShellCommand {

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) throws IOException {

        SortedMap<String, ShellCommand> commands = env.commands();

        if (arguments.split("\\s+").length > 1) {
            env.writeln("Command " + getCommandName() + " must have 0 or 1 argument.");
        } else if (arguments.length() == 0) {
            env.writeln("Available commands are: ");
            for (String commandName : commands.keySet()) env.write(commandName + " ");
            env.writeln("");
            env.writeln("If you want to know more about single command use command help followed by the name of command");
        } else {
            ShellCommand command = commands.get(arguments);
            if (command == null)
                env.writeln("This command doesn't exist.");
            else
                for (String string : command.getCommandDescription()) env.writeln(string);

        }
        return ShellStatus.CONTINUE;
    }

    @Override
    public String getCommandName() {
        return "help";
    }

    @Override
    public List<String> getCommandDescription() {
        return Collections.singletonList("""
                Help command.
                If command is started with no arguments, it lists names of all supported commands.
                If command is started with single argument, it prints name and the description of selected command.
                If such command doesn't exist appropriate message is printed.""");
    }
}
