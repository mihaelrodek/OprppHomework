package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ArrayIndexedCollectionTest {

    private ArrayIndexedCollection makeInsertedCollection() {
        ArrayIndexedCollection collInsert = new ArrayIndexedCollection();
        collInsert.insert(1, 0);
        collInsert.insert(2, 1);
        collInsert.insert(3, 2);
        collInsert.insert(6, 3);
        collInsert.insert(6, 4);
        collInsert.insert(9, 5);
        collInsert.insert(2, 6);
        return collInsert;
    }

    private ArrayIndexedCollection makeCollection() {
        ArrayIndexedCollection collAdd = new ArrayIndexedCollection();
        collAdd.add(1);
        collAdd.add(2);
        collAdd.add(3);
        collAdd.add(4);
        collAdd.add(5);
        return collAdd;
    }

    @Test
    void isEmptyTest() {
        assertTrue(new ArrayIndexedCollection().isEmpty());
    }

    @Test
    void isEmptyTestNotEmpty() {
        assertFalse(makeCollection().isEmpty());
    }

    @Test
    void sizeTestEmptyTrue() {
        assertEquals(new ArrayIndexedCollection(3).size(), 0);
    }

    @Test
    void sizeTestEmptyFalse() {
        assertNotEquals(new ArrayIndexedCollection(3).size(), 2);
    }

    @Test
    void sizeTestEqualsTrue() {
        assertEquals(makeCollection().size(), 5);
    }

    @Test
    void sizeTestEqualsFalse() {
        assertNotEquals(makeCollection().size(), 6);
    }

    @Test
    void containsTest() {
        ArrayIndexedCollection col = makeCollection();
        col.add(23.2);
        assertTrue(col.contains(23.2));
    }

    @Test
    void containsTestFalse() {
        ArrayIndexedCollection col = makeCollection();
        col.add(23.2);
        assertFalse(col.contains(69.420));
    }

    @Test
    void containsTestInsert() {
        ArrayIndexedCollection col = makeInsertedCollection();
        assertFalse(col.contains(7));
    }

    @Test
    void ArrayIndexedCollectionTestNullAdd() {
        ArrayIndexedCollection col = new ArrayIndexedCollection(2);

        col.add(2);
        assertThrows(NullPointerException.class, () -> col.add(null));
        assertTrue(col.contains(2));
    }

    @Test
    void testIsEmptyAddRemove() {

        ArrayIndexedCollection collection = new ArrayIndexedCollection();
        assertTrue(collection.isEmpty());

        collection.add(6);

        assertFalse(collection.isEmpty());

        collection.remove(0);

        assertTrue(collection.isEmpty());

    }

    @Test
    void addTestAndResize() {
        ArrayIndexedCollection col = new ArrayIndexedCollection(1);
        col.add(2);
        col.add(6);
        assertEquals(col.size(), 2);
    }

    @Test
    void addTestAndAnotherCollectionAndResize() {
        ArrayIndexedCollection col = makeInsertedCollection();
        ArrayIndexedCollection col2 = new ArrayIndexedCollection(col, 1);
        col2.add(2);
        col2.add(6);
        assertNotEquals(col2.size(), 2);
        assertEquals(col2.size(), 9);
    }

    @Test
    void addTestNull() {
        ArrayIndexedCollection col = makeCollection();
        assertThrows(NullPointerException.class, () -> col.add(null));
    }

    @Test
    void addTestContains() {
        ArrayIndexedCollection col = new ArrayIndexedCollection(2);
        col.add("test");
        assertEquals(col.size(), 1);
        assertFalse(col.contains("testa"));
        assertTrue(col.contains("test"));
        assertFalse(col.isEmpty());
    }

    @Test
    public void storageOfNull() {
        ArrayIndexedCollection col = new ArrayIndexedCollection(2);
        assertThrows(NullPointerException.class, () -> col.add(null));
    }

    @Test
    public void toArrayTestAdd() {

        ArrayIndexedCollection col = new ArrayIndexedCollection(23);

        col.add(6);
        col.add(14);
        col.add(-5);
        Object[] toArray = col.toArray();

        assertEquals(6, toArray[0]);
        assertEquals(6, col.get(0));
        assertEquals(-5, toArray[2]);

    }

    @Test
    void toArrayTestNotEmpty() {
        ArrayIndexedCollection col = makeCollection();
        Object[] array = col.toArray();
        int i = 0;
        while (i < col.size()) {
            assertSame(array[i], i + 1);
            i++;
        }
    }

    @Test
    void clearTest() {
        ArrayIndexedCollection col = makeInsertedCollection();
        assertTrue(col.contains(1));
        col.clear();
        assertNotEquals(col.size(), 5);
        assertEquals(col.size(), 7);

    }

    @Test
    void ArrayIndexedCollectionDefaultTest() {
        ArrayIndexedCollection col = new ArrayIndexedCollection();
        assertEquals(col.size(), 0);
    }

    @Test
    void ArrayIndexedCollectionTestCapacityLowerThanOne() {
        assertThrows(IllegalArgumentException.class, () -> new ArrayIndexedCollection(-5));
    }

    @Test
    void predefinedArraySizeAndCollectionAndSmallerThanOtherCollection() {
        ArrayIndexedCollection col = makeCollection();
        ArrayIndexedCollection colhelp = new ArrayIndexedCollection(col, 15);

        Object[] colArray = col.toArray();
        Object[] helper = colhelp.toArray();

        for (int i = 0; i < colArray.length; i++) {
            assertSame(colArray[i], helper[i]);
        }

        ArrayIndexedCollection colsmaller = new ArrayIndexedCollection(col, 10);

        Object[] colSmallerArray = colsmaller.toArray();

        for (int i = 0; i < colArray.length; i++) {
            assertSame(colArray[i], colSmallerArray[i]);
        }
        assertThrows(IndexOutOfBoundsException.class, () -> colsmaller.remove(230));
    }

    @Test
    void ArrayIndexedCollectionOtherNullCollection() {
        assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection(null));
    }

    @Test
    void addTestResizingAndRemoving() {
        ArrayIndexedCollection col = new ArrayIndexedCollection(2);

        col.add(1);
        assertEquals(col.size(), 1);
        col.add(2);
        col.add(17);
        col.add(4);
        assertEquals(col.size(), 4);

        col.add(6);
        col.add(7);
        assertEquals(col.size(), 6);

        assertEquals(col.get(0), 1);
        assertNotEquals(col.get(4), "Nije jednako");

        col.remove(5);
        assertFalse(col.contains(127));

    }

    @Test
    void testAddAndRemove() {
        ArrayIndexedCollection col = makeInsertedCollection();
        col.add(69);
        col.remove(1);

        assertEquals(col.get(1), 3);
        assertEquals(col.get(6), 69);
        assertNotEquals(col.get(0), "tessst");

    }

    @Test
    void testRemove() {
        ArrayIndexedCollection col = new ArrayIndexedCollection(3);
        col.add(69);
        col.add(1);
        col.remove(0);
        assertEquals(col.get(0), 1);
    }

    @Test
    void testRemoveThrows() {
        ArrayIndexedCollection col = new ArrayIndexedCollection();
        assertThrows(IndexOutOfBoundsException.class, () -> col.remove(0));
    }

    @Test
    void testIndexOf() {
        ArrayIndexedCollection col = makeInsertedCollection();
        assertEquals(col.indexOf(6), 3);
    }

    @Test
    void testInsertIndexOutOfBounds() {
        ArrayIndexedCollection col = new ArrayIndexedCollection(3);
        assertThrows(IndexOutOfBoundsException.class, () -> col.insert(5, -11));
        assertThrows(IndexOutOfBoundsException.class, () -> col.insert(5, 11));
    }

    @Test
    public void addAllTest() {
        ArrayIndexedCollection test = new ArrayIndexedCollection();
        ArrayIndexedCollection testAdd = new ArrayIndexedCollection();
        test.add(4);
        test.add(-9);
        test.add(9);

        testAdd.addAll(test);

        assertEquals(4, testAdd.get(0));
        assertEquals(4, test.get(0));
        assertEquals(-9, testAdd.get(1));
        assertEquals(-9, test.get(1));
        assertEquals(9, testAdd.get(2));
        assertEquals(test.size(), 3);
        assertEquals(testAdd.size(), 3);

    }

    @Test
    public void ArrayIndexedCollectionTestNullCapacity() {
        assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection(null));
    }

    @Test
    public void addAllTestWithString() {
        ArrayIndexedCollection test = new ArrayIndexedCollection();
        ArrayIndexedCollection testAdd = new ArrayIndexedCollection();
        test.add(0);
        test.add(-9);
        test.add("Marko");
        test.add("Darko");
        testAdd.addAll(test);

        assertEquals(0, testAdd.get(0));
        assertEquals(0, test.get(0));
        assertEquals(-9, testAdd.get(1));
        assertEquals("Marko", testAdd.get(2));
        assertNotEquals(testAdd.get(3), "Marko");

    }

}
