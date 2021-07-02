package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LinkedListIndexedCollectionTest {

    private LinkedListIndexedCollection<Integer> makeInsertedCollection() {
        LinkedListIndexedCollection<Integer> collInsert = new LinkedListIndexedCollection<>();
        collInsert.insert(1, 0);
        collInsert.insert(2, 1);
        collInsert.insert(3, 2);
        collInsert.insert(4, 3);
        collInsert.insert(5, 4);
        collInsert.insert(6, 5);
        collInsert.insert(7, 6);
        return collInsert;
    }

    private LinkedListIndexedCollection<Integer> makeCollection() {
        LinkedListIndexedCollection<Integer> collAdd = new LinkedListIndexedCollection<>();
        collAdd.add(1);
        collAdd.add(2);
        collAdd.add(3);
        collAdd.add(4);
        collAdd.add(5);
        collAdd.add(6);
        collAdd.add(7);
        return collAdd;
    }

    @Test
    void isEmptyTest() {
        assertTrue(new LinkedListIndexedCollection<Integer>().isEmpty());
    }

    @Test
    void isEmptyTestNotEmpty() {
        assertFalse(makeCollection().isEmpty());
    }

    @Test
    void sizeTestZero() {
        assertEquals(new LinkedListIndexedCollection<Integer>().size(), 0);
    }

    @Test
    void sizeTestEmpty() {
        assertTrue(new LinkedListIndexedCollection<Integer>().isEmpty());
    }

    @Test
    void sizeTestEquals() {
        assertEquals(makeCollection().size(), 7);
    }

    @Test
    void sizeTestNotEquals() {
        assertNotEquals(makeCollection().size(), 19);
    }

    @Test
    void containsTest() {
        LinkedListIndexedCollection<Integer> col = makeCollection();
        col.add(23);
        assertTrue(col.contains(23));
    }

    @Test
    void containsTestFalse() {
        LinkedListIndexedCollection<Integer> col = makeCollection();
        col.add(13);
        assertFalse(col.contains(222));
    }

    @Test
    void containsTestNull() {
        LinkedListIndexedCollection<Integer> col = makeCollection();
        assertFalse(col.contains(null));
    }

    @Test
    void insertTestThrows() {
        LinkedListIndexedCollection<Integer> col = new LinkedListIndexedCollection<>();
        assertThrows(IndexOutOfBoundsException.class, () -> col.insert(2, -12));
    }

    @Test
    void containsTestInsertAndRemove() {
        LinkedListIndexedCollection<Integer> col = makeInsertedCollection();
        assertTrue(col.contains(3));
        assertThrows(IndexOutOfBoundsException.class, () -> col.remove(-122));

    }

    @Test
    void LinkedListIndexedCollectionTestNullAdd() {
        LinkedListIndexedCollection<Integer> col = new LinkedListIndexedCollection<>();

        col.add(0);
        assertThrows(NullPointerException.class, () -> col.add(null));
        assertFalse(col.contains(2));
    }

    @Test
    void isEmptyAddRemove() {

        LinkedListIndexedCollection<Integer> collection = new LinkedListIndexedCollection<>();
        assertTrue(collection.isEmpty());
        collection.add(15);

        assertEquals(collection.size(), 1);
        assertFalse(collection.isEmpty());
        collection.remove(0);
        assertTrue(collection.isEmpty());
        assertEquals(collection.size(), 0);
    }

    @Test
    void addTest() {
        LinkedListIndexedCollection<Integer> col = new LinkedListIndexedCollection<>();
        col.add(13);
        col.add(14);
        assertEquals(col.size(), 2);
    }

    @Test
    void addAllTest() {
        LinkedListIndexedCollection<Integer> col = new LinkedListIndexedCollection<>();
        LinkedListIndexedCollection<Integer> colAddAll = new LinkedListIndexedCollection<>();
        col.add(13);
        col.add(14);
        colAddAll.addAll(col);
        assertEquals(col.size(), 2);
        assertEquals(colAddAll.size(), 2);
        assertEquals(13, colAddAll.get(0));
    }

    @Test
    void addTestNull() {
        LinkedListIndexedCollection<Integer> col = makeCollection();
        assertThrows(NullPointerException.class, () -> col.add(null));
    }

    @Test
    void addTestContains() {
        LinkedListIndexedCollection<String> col = new LinkedListIndexedCollection<>();
        col.add("Proba");
        assertEquals(col.size(), 1);
        assertFalse(col.contains("proba"));
        assertTrue(col.contains("Proba"));
        assertFalse(col.isEmpty());
        col.remove("Proba");
        assertTrue(col.isEmpty());
    }

    @Test
    public void testRemoveString() {
        LinkedListIndexedCollection<String> strCol = new LinkedListIndexedCollection<>();
        strCol.insert("Marko", 0);
        strCol.insert("sunce", 1);
        strCol.insert("ti", 2);
        strCol.insert("žarko", 3);
        strCol.insert("Ej", 0);
        strCol.remove(0);
        assertEquals("Marko", strCol.get(0));
    }

    @Test
    public void forEachTest() {
        LinkedListIndexedCollection<Integer> col = new LinkedListIndexedCollection<>();
        class P implements Processor {
            public void process(Object o) {
                System.out.println(o);
            }
        }
        col.add(0);
    }

    @Test
    public void toArrayTestAdd() {
        LinkedListIndexedCollection<Integer> col = new LinkedListIndexedCollection<>();
        col.add(2);
        col.add(6);
        col.add(11);
        col.add(-5);

        Object[] toArray = col.toArray();

        assertEquals(2, toArray[0]);
        assertEquals(2, col.get(0));
        assertEquals(11, toArray[2]);
    }

    @Test
    void toArrayTestTrue() {
        LinkedListIndexedCollection<Integer> col = makeCollection();
        Object[] array = col.toArray();
        int i = 0;
        while (i < col.size()) {
            assertSame(array[i], i + 1);
            i++;
        }
    }

    @Test
    void clearTest() {
        LinkedListIndexedCollection<Integer> col = makeInsertedCollection();
        assertTrue(col.contains(1));
        col.clear();
        assertEquals(col.size(), 0);
    }

    @Test
    void clearTestNew() {
        LinkedListIndexedCollection<Integer> col = new LinkedListIndexedCollection<>();
        col.add(1);
        assertTrue(col.contains(1));
        col.remove(0);
        assertEquals(col.size(), 0);
        col.clear();
        assertEquals(col.size(), 0);
    }

    @Test
    void LinkedListIndexedCollectionDefault() {
        LinkedListIndexedCollection<Integer> col = new LinkedListIndexedCollection<>();
        assertEquals(col.size(), 0);
    }

    @Test
    void LinkedListIndexedCollectionToArrayAndOutOfBounds() {
        LinkedListIndexedCollection<Integer> col = makeCollection();
        LinkedListIndexedCollection<Integer> another = makeInsertedCollection();

        Object[] colArray = col.toArray();
        Object[] colHelpArray = another.toArray();
        for (int i = 0; i < colArray.length; i++) {
            assertSame(colArray[i], colHelpArray[i]);
        }
        assertThrows(IndexOutOfBoundsException.class, () -> col.get(-1));

    }

    @Test
    void LinkedListIndexedCollectionOtherNullCollection() {
        assertThrows(NullPointerException.class, () -> new LinkedListIndexedCollection<>(null));
    }

    @Test
    void addTestResizingAndRemoving() {
        LinkedListIndexedCollection<Integer> col = new LinkedListIndexedCollection<>();

        col.add(1);
        assertEquals(col.size(), 1);
        col.add(2);
        assertEquals(col.size(), 2);
        assertNotEquals(col.size(), 0);
        col.add(3);
        col.add(4);
        col.add(5);
        assertEquals(col.size(), 5);

        col.add(6);
        col.add(7);
        assertEquals(col.size(), 7);

        assertEquals((int) col.get(0), 1);

        col.remove(5);
        assertTrue(col.contains(7));
    }

    @Test
    void testAddAndRemove() {
        LinkedListIndexedCollection<Integer> col = makeInsertedCollection();
        col.add(69);
        col.remove(1);

        assertEquals((int) col.get(1), 3);
        assertEquals((int) col.get(6), 69);

    }

    @Test
    void testAddAndGetBackwards() {
        LinkedListIndexedCollection<Integer> col = new LinkedListIndexedCollection<>();
        col.add(69);
        col.add(14);
        col.add(23);
        col.add(64);
        col.add(23);
        col.add(13);
        col.add(17);

        assertEquals((int) col.get(1), 14);
        assertEquals((int) col.get(6), 17);
        assertNotEquals(col.get(0), "proba");

        col.remove(4);

        assertEquals((int) col.get(5), 17);
    }

    @Test
    void throwsIndexOutOfBounds() {
        LinkedListIndexedCollection<Integer> col = makeCollection();
        assertThrows(IndexOutOfBoundsException.class, () -> col.insert(null, -10));
    }

    @Test
    void testIndexOf() {
        LinkedListIndexedCollection<Integer> col = makeInsertedCollection();
        assertEquals(col.indexOf(6), 5);
    }

    @Test
    void testIndexOfFalse() {
        LinkedListIndexedCollection<Integer> col = makeInsertedCollection();
        assertNotEquals(col.indexOf(6), 6);
    }

}

