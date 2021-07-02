package hr.fer.oprpp1.hw05.crypto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class UtilsTest {

    @Test
    void testHexToByte() {
        String hex = "FF00010203";
        byte[] bytes = Utils.hexToByte(hex);
        assertArrayEquals(new byte[]{-1, 0, 1, 2, 3}, bytes);
    }

    @Test
    void testHexToByteReturnsZero() {
        String hex = "";
        byte[] bytes = Utils.hexToByte(hex);
        assertArrayEquals(new byte[]{}, bytes);
    }

    @Test
    void testHexToByteHexLength() {
        String hex = "FF00010203";
        byte[] bytes = Utils.hexToByte(hex);
        assertTrue(bytes.length == 5);
    }

    @Test
    void testHexToByteThrowsException() {
        String hex = "A";
        assertThrows(IllegalArgumentException.class, () -> Utils.hexToByte(hex));
    }

    @Test
    void testByteToHex() {
        byte[] bytes = {-1, 0, 1, 2, 3};
        String bytesString = Utils.byteToHex(bytes);
        assertEquals("FF00010203", bytesString);
    }

    @Test
    void testByteToHexEmptyArray() {
        byte[] bytes = {};
        String bytesString = Utils.byteToHex(bytes);
        assertEquals("", bytesString);
    }
}
