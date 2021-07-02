package hr.fer.oprpp1.custom.collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleHashtableTest {
    @Test
    public void testHashTablePutsValues() {
        SimpleHashtable<String, Integer> testTable = new SimpleHashtable<>(2);

        testTable.put("Ivana", 2);
        testTable.put("Ante", 2);
        testTable.put("Jasna", 2);
        testTable.put("Kristina", 2);
        assertEquals(2, testTable.get("Ivana"));


        testTable.put("Ivana", 9); // overwrites old grade for Ivana
        testTable.put("Josip", 100);

        assertEquals(testTable.put("Marko", 2), null);
        assertEquals(testTable.put("Marko", 3), 2);
        assertEquals(2, testTable.get("Kristina"));
        assertEquals(9, testTable.get("Ivana"));
        assertEquals(6, testTable.size());
    }

    private static SimpleHashtable<String, Integer> empty;
    private static SimpleHashtable<String, Integer> one;
    private static SimpleHashtable<String, Integer> five;

    @BeforeEach
    void setUp() {
        empty = new SimpleHashtable<>();

        one = new SimpleHashtable<>();
        one.put("1", 1);

        five = new SimpleHashtable<>();
        five.put("1", 1);
        five.put("2", 2);
        five.put("3", 3);
        five.put("4", 4);
        five.put("5", 5);
    }

    @Test
    void testConstructors() {
        assertThrows(IllegalArgumentException.class, () -> new SimpleHashtable<>(0));

        assertEquals(0, empty.size());

        SimpleHashtable<String, Integer> emptyFirst = new SimpleHashtable<>(10);
        assertEquals(0, emptyFirst.size());

        SimpleHashtable<String, Integer> emptySecond = new SimpleHashtable<>(150);
        assertEquals(0, emptySecond.size());
    }

    @Test
    void testIsEmpty() {
        assertTrue(empty.isEmpty());
        assertFalse(one.isEmpty());
        assertFalse(five.isEmpty());
    }

    @Test
    void testSize() {
        assertEquals(0, empty.size());
        assertEquals(1, one.size());
        assertEquals(5, five.size());
    }

    @Test
    void testClear() {
        empty.clear();
        assertTrue(empty.isEmpty());
        assertEquals(0, empty.size());

        one.clear();
        assertTrue(one.isEmpty());
        assertEquals(0, one.size());

        five.clear();
        assertTrue(five.isEmpty());
        assertEquals(0, five.size());
    }

    @Test
    void testGetNull() {
        assertNull(empty.get(null));
        assertNull(one.get(null));
        assertNull(five.get(null));
    }

    @Test
    void testGet() {
        assertNull(empty.get(null));
        assertNull(one.get(null));
        assertNull(five.get(null));

        assertNull(empty.get(1));
        assertNull(empty.get("1"));

        assertNull(one.get(1));
        assertEquals(1, one.get("1"));

        assertNull(five.get(1));
        assertEquals(1, five.get("1"));
        assertEquals(3, five.get("3"));
        assertEquals(5, five.get("5"));
        assertNull(five.get("6"));
    }

    @Test
    void testPutNullKey() {
        assertThrows(NullPointerException.class, () -> empty.put(null, 0));
        assertThrows(NullPointerException.class, () -> one.put(null, 0));
        assertThrows(NullPointerException.class, () -> five.put(null, 0));
    }

    @Test
    void testPutNullValue() {
        assertNull(empty.get("1"));
        assertFalse(empty.containsValue(null));
        empty.put("1", null);
        assertNull(empty.get("1"));
        assertTrue(empty.containsValue(null));
        empty.put("1", 1);
        assertEquals(1, empty.get("1"));
        assertFalse(empty.containsValue(null));
    }

    @Test
    void testPut() {
        for (int i = 2; i <= 1000; i++) {
            one.put("1", one.get("1") + 1);
            assertEquals(1, one.size());
            assertEquals(i, one.get("1"));
        }

        assertNull(five.get("10"));
        five.put("10", 10);
        assertEquals(10, five.get("10"));
    }

    @Test
    void testContainsNullKey() {
        assertFalse(empty.containsKey(null));
        assertFalse(one.containsKey(null));
        assertFalse(five.containsKey(null));
    }

    @Test
    void testContainsKeyAndValue() {
        for (int i = 1; i <= 10; i++) {
            empty.put(String.valueOf(i), i);
            assertEquals(i, empty.size());
            assertEquals(i, empty.get(String.valueOf(i)));
        }

        for (int i = 1; i <= 10; i++) {
            assertTrue(empty.containsKey(String.valueOf(i)));
            assertTrue(empty.containsValue(i));
        }
    }

    @Test
    void testRemove() {
        assertEquals(0, empty.size());
        empty.remove(null);
        empty.remove("1");
        assertEquals(0, empty.size());

        assertEquals(1, one.size());
        one.remove(null);
        assertEquals(1, one.size());
        one.remove("1");
        assertEquals(0, one.size());
    }

    @Test
    void testToString() {
        assertEquals("[]", empty.toString());
        assertEquals("[1=1]", one.toString());
    }

    @Test
    public void testHashTableToString() {
        SimpleHashtable<String, Integer> testTable = new SimpleHashtable<>(2);

        testTable.put("Ivana", 2);
        testTable.put("Ante", 2);
        testTable.put("Jasna", 2);
        testTable.put("Ivana", 5); // overwrites old grade for Ivana

        assertEquals("[Ante=2, Ivana=5, Jasna=2]", testTable.toString());
    }

    @Test
    public void testAddClear() {

        SimpleHashtable<String, Integer> testTable = new SimpleHashtable<>(2);
        testTable.put("Ivana", 2);
        testTable.put("Ante", 2);
        testTable.put("Jasna", 2);
        testTable.put("Kristina", 2);
        testTable.put("Ivana", 5);
        testTable.put("Josip", 100);
        testTable.clear();
        assertTrue(testTable.isEmpty());
        assertEquals(0, testTable.size());

    }

    @Test
    public void testContainsKey() {
        SimpleHashtable<String, Integer> testTable = new SimpleHashtable<>(2);

        testTable.put("Ivana", 2);
        testTable.put("Ante", 2);
        testTable.put("Jasna", 2);
        testTable.put("Kristina", 2);
        testTable.put("Ivana", 5); // overwrites old grade for Ivana
        testTable.put("Josip", 100);

        assertTrue(testTable.containsKey("Kristina"));
        assertEquals(testTable.get("Ivana"), 5);
    }

    @Test
    public void testContainsValue() {
        SimpleHashtable<String, Integer> testTable = new SimpleHashtable<>(2);

        testTable.put("Ivana", 2);
        testTable.put("Ante", 2);
        testTable.put("Jasna", 2);
        testTable.put("Kristina", 2);
        testTable.put("Ivana", 5); // overwrites old grade for Ivana
        testTable.put("Josip", 100);

        assertTrue(testTable.containsValue(100));
    }

    @Test
    public void testRemoveElement() {
        SimpleHashtable<String, Integer> testTable = new SimpleHashtable<>(2);

        testTable.put("Ivana", 2);
        testTable.put("Ante", 2);
        testTable.put("Jasna", 2);
        testTable.put("Kristina", 2);
        testTable.put("Ivana", 5); // overwrites old grade for Ivana
        testTable.put("Josip", 100);

        testTable.remove("Ivana");

        assertFalse(testTable.containsKey("Ivana"));
        assertTrue(testTable.containsKey("Jasna"));
    }


    @Test
    public void testRemoveElement2() {
        SimpleHashtable<String, Integer> testTable = new SimpleHashtable<>(2);

        testTable.put("Ivana", 2);
        testTable.put("Ante", 2);
        testTable.put("Jasna", 2);
        testTable.put("Kristina", 2);
        testTable.put("Ivana", 5); // overwrites old grade for Ivana
        testTable.put("Josip", 100);

        assertTrue(testTable.containsKey("Ante"));
        assertTrue(testTable.containsKey("Ivana"));

        testTable.remove("Ante");

        assertFalse(testTable.containsKey("Ante"));
        assertTrue(testTable.containsKey("Ivana"));
    }


    @Test
    public void testHashtableIteratorInForEach() {
        SimpleHashtable<String, Integer> testTable = new SimpleHashtable<>(2);

        testTable.put("Ivana", 2);
        testTable.put("Ante", 2);
        testTable.put("Jasna", 2);
        testTable.put("Kristina", 2);
        testTable.put("Ivana", 5); // overwrites old grade for Ivana
        testTable.put("Josip", 100);

        StringBuilder result = new StringBuilder();

        for (var element : testTable) {
            result.append(element.getKey()).append(element.getValue());
        }

        assertEquals("Josip100Ante2Kristina2Jasna2Ivana5", result.toString());
    }


    @Test
    public void testHashtableIteratorRemoveValid() {
        SimpleHashtable<String, Integer> testTable = new SimpleHashtable<>(2);

        testTable.put("Ivana", 2);
        testTable.put("Ante", 2);
        testTable.put("Jasna", 2);
        testTable.put("Kristina", 2);
        testTable.put("Ivana", 5); // overwrites old grade for Ivana
        testTable.put("Josip", 100);

        var it = testTable.iterator();

        while (it.hasNext()) {
            if (it.next().getKey().equals("Ivana"))
                it.remove();
        }
        assertFalse(testTable.containsKey("Ivana"));
    }


    @Test
    public void testHashtableIteratorNextThrowsException() {
        SimpleHashtable<String, Integer> testTable = new SimpleHashtable<>(2);

        testTable.put("Ivana", 2);
        testTable.put("Ante", 2);

        var it = testTable.iterator();

        it.next();
        it.next();

        assertThrows(NoSuchElementException.class, it::next);
    }


    @Test
    public void testHashtableIteratorConcurrentModificationError() {
        SimpleHashtable<String, Integer> testTable = new SimpleHashtable<>(2);

        testTable.put("Ivana", 2);
        testTable.put("Ante", 2);

        var it = testTable.iterator();

        it.next();
        testTable.put("Lucija", 2);

        assertThrows(ConcurrentModificationException.class, it::next);
    }


    @Test
    public void testHashtableDoubleIterator() {
        // create collection:
        SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
        // fill data:
        examMarks.put("Ivana", 2);
        examMarks.put("Ante", 2);
        examMarks.put("Jasna", 2);
        examMarks.put("Kristina", 5);
        examMarks.put("Ivana", 5); // overwrites old grade for Ivana
        StringBuilder sb = new StringBuilder();
        for (SimpleHashtable.TableEntry<String, Integer> pair : examMarks) {
            sb.append(String.format("%s => %d\n", pair.getKey(), pair.getValue()));
        }


    }
}
