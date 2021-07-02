package hr.fer.oprpp1.java.hw05.shell.commands;

import hr.fer.oprpp1.java.hw05.shell.Environment;
import hr.fer.oprpp1.java.hw05.shell.ShellCommand;
import hr.fer.oprpp1.java.hw05.shell.ShellStatus;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.List;

/**
 * Class that represents tree command.
 * This class prints a tree with all levels for given directory.
 *
 * @author Mihael Rodek
 */
public class TreeCommand implements ShellCommand {
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) throws IOException {

        try {
            Path path = Paths.get(arguments);

            if (!Files.isDirectory(path)) throw new IOException("Argument must be a directory, please try again.");

            Files.walkFileTree(Paths.get(arguments), new FileVisitor<>() {
                int depthLevel = 1;

                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                    printLevel(dir);
                    depthLevel += 2;
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    printLevel(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) {
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
                    depthLevel -= 2;
                    return FileVisitResult.CONTINUE;
                }

                private void printLevel(Path file) {
                    env.writeln(String.format("%" + depthLevel + "s" + "%s", "", file.getFileName()));
                }
            });
        } catch (IOException e) {
            if (e.getMessage() != null) env.writeln(e.getMessage());
            else env.writeln("Error while performing tree command.");
        }
        return ShellStatus.CONTINUE;
    }

    @Override
    public String getCommandName() {
        return "tree";
    }

    @Override
    public List<String> getCommandDescription() {
        return Collections.singletonList("Tree command." +
                "Command accept a single argument directory name and prints a tree with all levels for given directory.");
    }
}
