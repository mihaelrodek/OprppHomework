package hr.fer.oprpp1.hw04.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;


/**
 * Main method that starts and simulates the program.
 *
 * @author Mihael Rodek
 */
public class MainStudentDatabase {
    private static StudentDatabase database;

    /**
     * Entry point to a program that takes no arguments.
     *
     * @param args Unused command line arguments.
     */
    public static void main(String[] args) {

        try {
            List<String> lines = Files.readAllLines(Paths.get("C:/Eclipse/eclipse-workspace/eclipse-workspaces/OprppHomework/hw04-0036518478/database.txt"), StandardCharsets.UTF_8);
            database = new StudentDatabase(lines);
        } catch (IOException e) {
            System.err.println("Couldn't load database file");
            System.exit(1);
        } catch (IllegalArgumentException ex) {
            System.err.println(ex.getMessage());
            System.exit(1);
        }

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            String line = sc.nextLine().trim();
            try {
                if (line.equals("exit")) {
                    System.out.println("Bye!");
                    break;
                }
                if (!line.trim().startsWith("query"))
                    throw new IllegalArgumentException("Invalid expression");

                line = line.replaceFirst("query", "").trim();
                QueryParser parser = new QueryParser(line);
                if (parser.isDirectQuery()) {
                    StudentRecord r = database.forJMBAG(parser.getQueriedJMBAG());
                    System.out.println(r.getJmbag() + " | " + r.getLastName() + " | " + r.getFirstName());
                } else if (parser.getQuery().size() == 0) {
                    System.out.println("No record selected.");
                } else {
                    for (StudentRecord r : database.filter(new QueryFilter(parser.getQuery()))) {
                        System.out.println(r.getJmbag() + " | " + r.getLastName() + " | " + r.getFirstName());
                    }
                }
            } catch (RuntimeException ex) {
                System.out.println(ex.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected error ocurred ");
                System.exit(1);
            }
        }
        sc.close();

    }
}
