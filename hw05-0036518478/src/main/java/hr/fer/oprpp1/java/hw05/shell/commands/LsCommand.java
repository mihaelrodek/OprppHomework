package hr.fer.oprpp1.java.hw05.shell.commands;

import hr.fer.oprpp1.java.hw05.shell.Environment;
import hr.fer.oprpp1.java.hw05.shell.ShellCommand;
import hr.fer.oprpp1.java.hw05.shell.ShellStatus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Class that represents ls command.
 * This class writes a directory listing of given directory.
 *
 * @author Mihael Rodek
 */
public class LsCommand implements ShellCommand {
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) throws IOException {

        Path path = Paths.get(arguments.trim());

        Files.list(path).forEach(file -> {
            try {
                if (!Files.isDirectory(path)) throw new IOException();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                BasicFileAttributeView faView = Files.getFileAttributeView(path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
                BasicFileAttributes attributes = faView.readAttributes();
                FileTime fileTime = attributes.creationTime();
                String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));

                String permissionsString = String.format("%c%c%c%c",
                        Files.isDirectory(file) ? 'd' : '-',
                        Files.isReadable(file) ? 'r' : '-',
                        Files.isWritable(file) ? 'w' : '-',
                        Files.isExecutable(file) ? 'x' : '-');

                String formatted = String.format("%s %10d %s %s",
                        permissionsString, Files.size(file), formattedDateTime, file.getFileName());

                env.writeln(formatted);
            } catch (IOException ex) {
                env.writeln("Error occurred while trying to perform ls command due to path format.");
            }
        });

        return ShellStatus.CONTINUE;
    }

    @Override
    public String getCommandName() {
        return "ls";
    }

    @Override
    public List<String> getCommandDescription() {
        return Collections.singletonList("""
                Ls command.
                Command takes a single argument directory and writes a directory listing.
                The output consists of 4 columns.
                First column indicates if current object is directory (d), readable (r), writable (w) and executable (x).
                Second column contains object size in bytes.
                Third column represents creation date and time in given format: yyyy-MM-dd HH:mm:ss
                Fourth column represents file name.""");
    }
}
