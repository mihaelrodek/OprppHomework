package hr.fer.oprpp1.java.hw05.shell.commands;

import hr.fer.oprpp1.java.hw05.shell.Environment;
import hr.fer.oprpp1.java.hw05.shell.ShellCommand;
import hr.fer.oprpp1.java.hw05.shell.ShellStatus;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.List;

/**
 * Class that represents hexdump command.
 * This command produces hex output of given file name.
 *
 * @author Mihael Rodek
 */
public class HexdumpCommand implements ShellCommand {

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {

        StringBuilder stringBuilder = new StringBuilder();

        try (InputStream inputStream = Files.newInputStream(Paths.get(arguments.trim()), StandardOpenOption.READ)) {

            int position = 0x0;

            byte[] buffer = new byte[16];
            for (; true; position += 0x10) {

                stringBuilder.setLength(0);
                int read = inputStream.read(buffer);
                if (read < 1) break;

                stringBuilder.append(String.format("%08X: ", position));


                for (int i = 0; i < 16; i++) {
                    if (i < read)
                        stringBuilder.append(String.format("%02X", buffer[i])).append(" ");
                    else
                        stringBuilder.append(String.format("   "));

                    if (i == 7) {
                        stringBuilder.setLength(stringBuilder.length() - 1);
                        stringBuilder.append("|");
                    }
                }
                replaceWithDot(buffer, read);


                stringBuilder.append(" | " + new String(buffer));
                env.writeln(stringBuilder.toString());
            }
        } catch (IOException ex) {
            env.writeln("Error occurred while hex-dumping given file.");
        }
        return ShellStatus.CONTINUE;
    }

    private void replaceWithDot(byte[] buffer, int read) {
        for (int i = 0; i < read; i++)
            if (buffer[i] < 32 || buffer[i] > 127)
                buffer[i] = 46;
    }

    @Override
    public String getCommandName() {
        return "hexdump";
    }

    @Override
    public List<String> getCommandDescription() {
        return Collections.singletonList("Hexdump command.\n" +
                "Command takes a single argument that is file name and produces its hex-output.");

    }
}
