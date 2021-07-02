package hr.fer.oprpp1.java.hw05.shell.commands;

import hr.fer.oprpp1.java.hw05.shell.Environment;
import hr.fer.oprpp1.java.hw05.shell.ShellCommand;
import hr.fer.oprpp1.java.hw05.shell.ShellStatus;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class that represents cat command.
 * This command is used to open given file and write its content to console.
 *
 * @author Mihael Rodek
 */

public class CatCommand implements ShellCommand {

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) throws IOException {
        String catRegex = "\\s*((\"(.+)\")|(\\S+))(\\s+([\\w]+))?\\s*";

        Pattern pattern = Pattern.compile(catRegex);
        Matcher matcher = pattern.matcher(arguments);

        try {
            if (!matcher.find()) throw new IOException();

            Path file;
            Charset charset = Charset.defaultCharset();

            if (matcher.group(6) != null)
                try {
                    charset = Charset.forName(matcher.group(6));
                } catch (Exception e) {
                    env.writeln("Unsupported charset. Please provide a different one.");
                    CharsetCommand.printAvailableCharsets(env);
                    return ShellStatus.CONTINUE;
                }

            if (matcher.group(3) == null) {
                file = Paths.get(matcher.group(4));
            } else {
                file = Paths.get(matcher.group(3));
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file.toFile()), charset));

            String line;
            while ((line = br.readLine()) != null) {
                env.writeln(line);
            }

            br.close();

        } catch (IOException e) {
            env.writeln("Illegal cat command. Exception occurred while trying to write to the shell.");
        } finally {
            return ShellStatus.CONTINUE;
        }
    }

    @Override
    public String getCommandName() {
        return "cat";
    }

    @Override
    public List<String> getCommandDescription() {
        return Collections.singletonList("""
                Cat command.
                This command opens given file and writes its content to console.
                Command cat takes one or two arguments.
                The first argument is mandatory and is path to some file. The second argument is charset name that should be used to interpret chars from bytes.
                If second argument is not provided, a default platform charset is used.""");
    }
}
