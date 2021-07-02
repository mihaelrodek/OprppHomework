package hr.fer.oprpp1.java.hw05.shell.commands;

import hr.fer.oprpp1.java.hw05.shell.Environment;
import hr.fer.oprpp1.java.hw05.shell.ShellCommand;
import hr.fer.oprpp1.java.hw05.shell.ShellStatus;

import java.io.*;
import java.nio.file.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class that represents copy command.
 * It copies file to the given destination.
 *
 * @author Mihael Rodek
 */
public class CopyCommand implements ShellCommand {


    @Override
    public ShellStatus executeCommand(Environment env, String arguments) throws IOException {

        String copyRegex = "\\s*((\"(\\w+)\")|(\\S+))\\s+((\"(\\w+)\")|(\\S+))\\s*";

        Pattern pattern = Pattern.compile(copyRegex);
        Matcher matcher = pattern.matcher(arguments);

        if (matcher.find()) {

            Path sourcePath;
            Path destinationPath;

            if (matcher.group(3) == null)
                sourcePath = Paths.get(matcher.group(4));
            else
                sourcePath = Paths.get(matcher.group(3));
            if (matcher.group(7) == null)
                destinationPath = Paths.get(matcher.group(8));
            else
                destinationPath = Paths.get(matcher.group(7));


            if (Files.exists(destinationPath))
                if (!checkOverwrite(env)) {
                    env.writeln("Operation aborted. File will not be overwritten.");
                    return ShellStatus.CONTINUE;
                }

            try (InputStream pathInput = Files.newInputStream(sourcePath, StandardOpenOption.READ);
                 OutputStream pathOutput = Files.newOutputStream(destinationPath, !Files.exists(destinationPath) ? StandardOpenOption.CREATE : StandardOpenOption.WRITE)) {


                byte[] buffer = new byte[4096];
                while (true) {
                    int readBytes = pathInput.read(buffer);
                    if (readBytes < 1) {
                        env.writeln("File has been copied successfully!");
                        break;
                    }
                    pathOutput.write(Arrays.copyOf(buffer, readBytes));
                }

            } catch (IOException e) {
                env.writeln("Error while performing copy command.");
            }
        }
        return ShellStatus.CONTINUE;
    }

    private boolean checkOverwrite(Environment env) throws IOException {
        env.writeln("Do you want to overwrite the file?");
        return env.readLine().equalsIgnoreCase("yes");
    }

    @Override
    public String getCommandName() {
        return "copy";
    }

    @Override
    public List<String> getCommandDescription() {
        return Collections.singletonList("""
                Copy command.
                The copy command expects two arguments.
                First argument is source file name and second one is destination file name.
                Command works only with files, copying directories is not allowed.
                If destination file exists, user can decide to overwrite it or not.
                If the second argument is directory, original file is copied into that directory using the original file name.""");
    }
}
