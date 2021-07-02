package hr.fer.oprpp1.hw05.crypto;

public class Utils {

    public static byte[] hexToByte(String keyText) {

        int len = keyText.length();
        byte[] array = new byte[len / 2];

        if (len == 0) return new byte[0];

        if (len % 2 != 0) throw new IllegalArgumentException("Hex is odd number");

        for (int i = 0; i < len; i += 2)
            array[i / 2] = (byte) ((Character.digit(keyText.charAt(i), 16) << 4)
                    + Character.digit(keyText.charAt(i + 1), 16));

        return array;
    }

    public static String byteToHex(byte[] array) {
        StringBuilder sb = new StringBuilder();
        for (byte b : array) sb.append(String.format("%02x", b));
        return sb.toString();
    }
}
