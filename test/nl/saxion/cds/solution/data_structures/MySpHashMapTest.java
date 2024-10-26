package nl.saxion.cds.solution.data_structures;

import nl.saxion.cds.collection.DuplicateKeyException;
import nl.saxion.cds.collection.KeyNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MySpHashMapTest {
    private MySpHashMap<Integer, String> map;

    @Test
    public void GivenEmptyList_WhenCheckingIfContainsItem_ConfirmFalseReturned() {
        assertFalse(map.contains(1));
    }

    @Test
    public void GivenListWithOneItem_WhenCheckingIfContainsItem_ConfirmTrueReturned() {
        map.add(1, "one");
        assertTrue(map.contains(1));
    }

    @Test
    public void GivenListWithItems_WhenCheckingForNonExistingItem_ConfirmFalseReturned() {
        map.add(1, "one");
        map.add(2, "two");
        map.add(3, "three");
        assertFalse(map.contains(4));
    }

    @Test
    public void GivenListWithItems_WhenGettingItemByKey_ConfirmCorrectItemReturned() {
        map.add(1, "one");
        map.add(2, "two");
        map.add(3, "three");

        assertEquals("one", map.get(1));
        assertEquals("two", map.get(2));
        assertEquals("three", map.get(3));
    }

    @Test
    public void GivenListWithItems_WhenGettingNonExistingItemByKey_ConfirmKeyNotFoundExceptionThrown() {
        map.add(1, "one");
        map.add(2, "two");
        map.add(3, "three");

        assertThrows(KeyNotFoundException.class, () -> map.get(4));
    }

    @Test
    public void GivenListWithItems_WhenAddingDuplicateKey_ConfirmDuplicateKeyExceptionThrown() {
        map.add(1, "one");
        map.add(2, "two");
        map.add(3, "three");

        assertThrows(DuplicateKeyException.class, () -> map.add(1, "one"));
    }

    @Test
    public void GivenEmptyList_WhenAddingItem_ConfirmItemAddedAndSizeIncreased() {
        map.add(1, "one");

        assertEquals(1, map.size());
        assertTrue(map.contains(1));
    }

    @Test
    public void GivenListWithItems_WhenRemovingItem_ConfirmItemRemovedAndSizeDecreased() {
        map.add(1, "one");
        map.add(2, "two");
        map.add(3, "three");

        map.remove(2);
        assertEquals(2, map.size());
        assertFalse(map.contains(2));

        map.remove(1);
        assertEquals(1, map.size());
        assertFalse(map.contains(1));

        assertEquals("three", map.get(3));
    }

    @Test
    public void GivenListWithItems_WhenRemovingNonExistingItem_ConfirmKeyNotFoundExceptionThrown() {
        map.add(1, "one");
        map.add(2, "two");
        map.add(3, "three");

        assertThrows(KeyNotFoundException.class, () -> map.remove(4));
    }

    @Test
    public void GivenListWithItems_WhenAddingItemsUntilRehashing_ConfirmRehashingPerformed() {
        for (int i = 0; i < 10; i++) {
            map.add(i, "item" + i);
        }

        assertEquals(10, map.size());
    }

    @Test
    public void GivenListWithNoItems_WhenCheckingIfEmpty_ConfirmTrueReturned() {
        assertTrue(map.isEmpty());
    }

    @Test
    public void GivenListWithItems_WhenCheckingIfEmpty_ConfirmFalseReturned() {
        map.add(1, "one");
        assertFalse(map.isEmpty());
    }

    @Test
    public void GivenListWithItems_WhenGettingKeys_ConfirmKeysReturned() {
        map.add(1, "one");
        map.add(2, "two");
        map.add(3, "three");

        assertEquals(3, map.getKeys().size());
        assertTrue(map.getKeys().contains(1));
        assertTrue(map.getKeys().contains(2));
        assertTrue(map.getKeys().contains(3));
    }

    @Test
    public void GivenHashMap_ConfirmCorrectGraphVizOutput(){
        map.add(1, "one");
        map.add(2, "two");
        map.add(3, "three");

        System.out.println(map.graphViz("Map"));
    }

    @BeforeEach
    public void setUp() {
        map = new MySpHashMap<>();
    }
}
