package hr.fer.oprpp1.java.hw05.shell.commands;

import hr.fer.oprpp1.java.hw05.shell.Environment;
import hr.fer.oprpp1.java.hw05.shell.ShellCommand;
import hr.fer.oprpp1.java.hw05.shell.ShellStatus;

import java.util.Collections;
import java.util.List;

/**
 * Class that represents symbol command.
 * This class prints symbol of shell for given shell name or can change a shell symbol.
 *
 * @author Mihael Rodek
 */
public class SymbolCommand implements ShellCommand {
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {

        String[] args = arguments.split(" ");
        String argument = args[0].toUpperCase();
        Character oldSymbol, newSymbol;

        if (args.length == 1) {
            Character symbol;
            switch (argument) {
                case "PROMPT" -> symbol = env.getPromptSymbol();
                case "MORELINES" -> symbol = env.getMorelinesSymbol();
                case "MULTILINE" -> symbol = env.getMultilineSymbol();
                default -> {
                    env.writeln("Error occurred while trying to change the symbol.");
                    return ShellStatus.CONTINUE;
                }
            }
            env.writeln("Symbol for " + argument.toUpperCase() + " is '" + symbol + "'");

        } else if (args.length == 2) {
            switch (argument) {
                case "PROMPT" -> {
                    if (args.length == 2 && args[1].length() == 1) {
                        oldSymbol = env.getPromptSymbol();
                        newSymbol = args[1].charAt(0);
                        env.setPromptSymbol(newSymbol);
                    } else {
                        env.writeln("Error occurred while trying to change the symbol.");
                        return ShellStatus.CONTINUE;
                    }

                }
                case "MORELINES" -> {
                    if (args.length == 2 && args[1].length() == 1) {
                        oldSymbol = env.getMorelinesSymbol();
                        newSymbol = args[1].charAt(0);
                        env.setMorelinesSymbol(newSymbol);
                    } else {
                        env.writeln("Error occurred while trying to change the symbol.");
                        return ShellStatus.CONTINUE;
                    }
                }
                case "MULTILINE" -> {
                    if (args.length == 2 && args[1].length() == 1) {
                        oldSymbol = env.getMultilineSymbol();
                        newSymbol = args[1].charAt(0);
                        env.setMultilineSymbol(newSymbol);
                    } else {
                        env.writeln("Error occurred while trying to change the symbol.");
                        return ShellStatus.CONTINUE;
                    }
                }
                default -> {
                    env.writeln("Error occurred while trying to change the symbol.");
                    return ShellStatus.CONTINUE;
                }
            }
            env.writeln("Symbol for " + argument.toUpperCase() + " changed from '" + oldSymbol + "' to '" + newSymbol + "'.");
        }

        return ShellStatus.CONTINUE;
    }

    @Override
    public String getCommandName() {
        return "symbol";
    }

    @Override
    public List<String> getCommandDescription() {
        return Collections.singletonList("""
                Symbol command.
                Command accepts one or two arguments.
                If only one argument is used it prints symbol of shell for given symbol name.
                If used with two arguments, symbol is set to given character.""");
    }
}
