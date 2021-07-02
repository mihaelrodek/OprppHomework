package hr.fer.oprpp1.java.hw05.shell;

import hr.fer.oprpp1.java.hw05.shell.commands.*;

import java.util.*;


/**
 *
 */
public class EnvironmentShellImplementation implements Environment {

    private Scanner sc = new Scanner(System.in);
    private static SortedMap<String, ShellCommand> commands;

    private Character promptSymbol = '>';
    private Character moreLinesSymbol = '\\';
    private Character multilineSymbol = '|';

    public EnvironmentShellImplementation() {
        this.commands = new TreeMap<>();
        initialize();
    }

    private void initialize() {
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

    @Override
    public String readLine() throws ShellIOException {
        StringBuilder sb = new StringBuilder();
        String line = "";

        for (line = sc.nextLine(); line.endsWith(moreLinesSymbol.toString()); line = sc.nextLine()) {
            sb.append(line, 0, line.length() - 1);
            write(multilineSymbol + " ");
        }

        return sb.append(line).toString();
    }

    @Override
    public void write(String text) throws ShellIOException {
        System.out.print(text);
    }

    @Override
    public void writeln(String text) throws ShellIOException {
        System.out.println(text);
    }

    @Override
    public SortedMap<String, ShellCommand> commands() {
        return Collections.unmodifiableSortedMap(commands);
    }

    @Override
    public Character getMultilineSymbol() {
        return multilineSymbol;
    }

    @Override
    public void setMultilineSymbol(Character symbol) {
        this.multilineSymbol = symbol;
    }

    @Override
    public Character getPromptSymbol() {
        return promptSymbol;
    }

    @Override
    public void setPromptSymbol(Character symbol) {
        this.promptSymbol = symbol;
    }

    @Override
    public Character getMorelinesSymbol() {
        return moreLinesSymbol;
    }

    @Override
    public void setMorelinesSymbol(Character symbol) {
        this.moreLinesSymbol = symbol;
    }
}
