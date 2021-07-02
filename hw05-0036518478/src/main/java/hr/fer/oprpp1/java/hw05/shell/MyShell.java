package hr.fer.oprpp1.java.hw05.shell;

import hr.fer.oprpp1.java.hw05.shell.commands.*;

import java.io.IOException;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Class that represents MyShell.
 * MyShell is a simple shell used for doing basic operation on
 * files and directories that support following built-in commands:
 * charsets, cat, ls, tree, copy, mkdir, hexdump, symbol, help, and exit.
 *
 * @author Mihael Rodek
 */
public class MyShell {

    private static Environment environment = new EnvironmentShellImplementation();
    private static SortedMap<String, ShellCommand> commands = new TreeMap<>();

    static {
        commands.put("charset", new CharsetCommand());
        commands.put("cat", new CatCommand());
        commands.put("ls", new LsCommand());
        commands.put("tree", new TreeCommand());
        commands.put("copy", new CopyCommand());
        commands.put("mkdir", new MkdirCommand());
        commands.put("hexdump", new HexdumpCommand());
        commands.put("symbol", new SymbolCommand());
        commands.put("help", new HelpCommand());
        commands.put("exit", new ExitCommand());
    }

    public static void main(String[] args) throws IOException {

        environment.writeln("Welcome to MyShell v 1.0");
        ShellStatus shellStatus = ShellStatus.CONTINUE;

        do {

            environment.write(environment.getPromptSymbol().toString() + " ");
            String line = environment.readLine();

            while (line.endsWith(environment.getMorelinesSymbol().toString())) {
                environment.write(environment.getMultilineSymbol().toString() + " ");
                line = line.substring(0, line.length() - 1) + environment.readLine();
            }

            String[] split = line.strip().split(" ", 2);
            String commandName = split[0];
            String arguments = "";
            if (!(split.length < 2))
                arguments = split[1];


            ShellCommand shellCommand = commands.get(commandName.toLowerCase());
            if (shellCommand == null) {
                environment.writeln("Unknown command: " + commandName);
            } else shellStatus = shellCommand.executeCommand(environment, arguments);

        } while (shellStatus != ShellStatus.TERMINATE);
    }
}
