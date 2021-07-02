package hr.fer.oprpp1.hw05.crypto;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

public class Crypto {

    public static void main(String[] args) {
        if (args.length != 2 && args.length != 3) {
            throw new IllegalArgumentException("Illegal number of arguments.");
        }

        Scanner scanner = new Scanner(System.in);

        switch (args[0].toLowerCase()) {
            case "checksha" -> {
                if (args.length != 2)
                    throw new IllegalArgumentException("Checksha command must have exactly two arguments.");

                System.out.println("Please provide expected sha-256 digest for hw05test.bin: ");
                System.out.print("> ");
                String digest = scanner.nextLine();

                checksha(args[1], digest);
            }
            case "encrypt", "decrypt" -> {
                if (args.length != 3)
                    throw new IllegalArgumentException("Decrypt and encrypt commands must have exactly three arguments.");

                System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
                System.out.print("> ");
                String password = scanner.nextLine();
                System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
                System.out.print("> ");
                String vector = scanner.nextLine();

                crypt(args[1], args[2], args[0], password, vector);
            }

            default -> throw new IllegalArgumentException("Unknown command. Available commands are: checksha, encrypt, decrypt.");

        }
    }


    private static void checksha(String path, String expectedChecksum) {

        try (InputStream fileInput = Files.newInputStream(Paths.get(path), StandardOpenOption.READ)) {

            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] buffer = new byte[4096];

            while (true) {
                int readBytes = fileInput.read(buffer);
                if (readBytes < 1) break;
                messageDigest.update(buffer, 0, readBytes);
            }

            byte[] finalDigest = messageDigest.digest();
            String checksum = Utils.byteToHex(finalDigest);

            if (expectedChecksum.equals(checksum)) {
                System.out.println("Digesting completed. Digest of hw05test.bin matches expected digest.");
            } else {
                System.out.println("Digesting completed. "
                        + "Digest of hw05test.bin does not match the expected digest. Digest was: "
                        + expectedChecksum);
            }
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static void crypt(String inputFile, String outputFile, String cryptMethod, String keyText, String ivText) {

        try (InputStream pathInput = Files.newInputStream(Paths.get(inputFile), StandardOpenOption.READ);
             OutputStream pathOutput = Files.newOutputStream(Paths.get(outputFile), StandardOpenOption.WRITE)) {

            Cipher cipher = cipherInitialization(cryptMethod, keyText, ivText);

            byte[] buffer = new byte[4096];
            while (true) {
                int readBytes = pathInput.read(buffer);
                if (readBytes < 1) break;

                pathOutput.write(cipher.update(buffer, 0, readBytes));
            }

            pathOutput.write(cipher.doFinal());

            if (cryptMethod.equals("encrypt"))
                System.out.println("Encryption completed. Generated file " + outputFile + " based on file " + inputFile + ");");
            else
                System.out.println("Decryption completed. Generated file " + outputFile + " based on file " + inputFile + ".");


        } catch (IOException | BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }

    private static Cipher cipherInitialization(String cryptMethod, String keyText, String ivText) {
        Cipher cipher = null;

        try {
            SecretKeySpec keySpec = new SecretKeySpec(Utils.hexToByte(keyText), "AES");
            AlgorithmParameterSpec paramSpec = new IvParameterSpec(Utils.hexToByte(ivText));
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(cryptMethod.equals("encrypt") ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return cipher;
    }

}
