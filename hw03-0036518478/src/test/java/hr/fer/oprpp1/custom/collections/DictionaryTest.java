package hr.fer.oprpp1.custom.collections;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DictionaryTest {
    @Test
    public void testAddValueAndReturn() {
        Dictionary<String, Float> dictionary = new Dictionary<>();

        dictionary.put("First", 1.f);
        dictionary.put("Second", 2.f);

        assertEquals(1, dictionary.get("First"));
    }

    @Test
    public void testEmptyInline() {

        assertTrue(new Dictionary<>().isEmpty());
    }

    @Test
    public void testAddValueAndRemove() {
        Dictionary<String, Double> dictionary = new Dictionary<>();

        dictionary.put("First", 1.);
        dictionary.put("Second", 2.);

        dictionary.remove("First");
        assertNull(dictionary.get("First"));
    }

    @Test
    public void testGetSize() {
        Dictionary<String, Integer> dictionary = new Dictionary<>();

        dictionary.put("First", 1);
        dictionary.put("Second", 2);

        assertEquals(2, dictionary.size());
    }

    @Test
    public void testGetSizeClear() {
        Dictionary<String, Integer> dictionary = new Dictionary<>();

        dictionary.put("First", 1);
        assertEquals(1, dictionary.size());
        dictionary.clear();
        assertEquals(0, dictionary.size());
    }

    @Test
    public void testEmpty() {
        Dictionary<String, Integer> dictionary = new Dictionary<>();
        assertTrue(dictionary.isEmpty());
    }

    @Test
    public void testNotEmpty() {
        Dictionary<String, Integer> dictionary = new Dictionary<>();
        dictionary.put("dva", 2);
        assertFalse(dictionary.isEmpty());
    }

    @Test
    public void testEmptyAfterClear() {
        Dictionary<String, Integer> dictionary = new Dictionary<>();

        dictionary.put("First", 1);
        dictionary.put("Second", 2);
        dictionary.clear();
        assertTrue(dictionary.isEmpty());
    }

    @Test
    public void testIsEmptyAfterClearAndSzeZero() {
        Dictionary<String, Integer> dictionary = new Dictionary<>();

        dictionary.put("First", 1);
        dictionary.put("Second", 2);
        dictionary.clear();
        assertTrue(dictionary.isEmpty());
        assertTrue(dictionary.size() == 0);
    }

    @Test
    public void testRemoveGetsOldValue() {
        Dictionary<String, Integer> dictionary = new Dictionary<>();

        dictionary.put("First", 1);
        dictionary.put("Second", 2);

        assertEquals(1, dictionary.remove("First"));
    }

    @Test
    public void testRemoveGetsOldValueString() {
        Dictionary<String, String> dictionary = new Dictionary<>();

        dictionary.put("First", "str");
        dictionary.put("Second", "rrs");

        assertEquals("str", dictionary.remove("First"));
    }

    @Test
    public void testPutOverwritesInteger() {
        Dictionary<Integer, Integer> dictionary = new Dictionary<>();

        dictionary.put(2, 1);
        dictionary.put(3, 2);

        dictionary.put(3, 3);

        assertEquals(3, dictionary.get(3));
    }

    @Test
    public void testPutOverWritesRemove() {
        Dictionary<String, Integer> dictionary = new Dictionary<>();

        dictionary.put("First", 1);
        dictionary.put("Second", 2);

        dictionary.put("First", 3);

        assertEquals(3, dictionary.get("First"));

        dictionary.remove("First");
        dictionary.remove("Second");
        assertTrue(dictionary.size() == 0);
    }

    @Test
    public void testRemoveAndEmpty() {
        Dictionary<String, Integer> dictionary = new Dictionary<>();

        dictionary.put("Prvi", 1);
        dictionary.put("Second", 2);

        dictionary.remove("Prvi");
        assertFalse(dictionary.isEmpty());
        assertEquals(1, dictionary.size());
    }
}
