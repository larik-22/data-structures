package data_structures;

import nl.saxion.cds.collection.EmptyCollectionException;
import nl.saxion.cds.collection.ValueNotFoundException;
import nl.saxion.cds.data_structures.DoublyLinkedList;
import nl.saxion.cds.data_structures.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class DoublyLinkedListTest {
    DoublyLinkedList<String> testStringList = new DoublyLinkedList<>();

    @Test
    public void GivenEmptyList_WhenCheckingSize_ConfirmSizeIsZeroAndEmpty(){
        DoublyLinkedList<String> list = new DoublyLinkedList<>();
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
    }

    /*TODO: Ask Meneer Frederik about those kind of tests*/
    @Test
    public void GivenList_WhenAddingLastItems_ConfirmCorrectListSize(){
        testStringList.addLast("Hello");
        assertEquals(1, testStringList.size());
        testStringList.addLast("World");
        assertEquals(2, testStringList.size());
        testStringList.addLast("!");
        assertEquals(3, testStringList.size());
    }

    /*TODO: Ask Meneer Frederik about those kind of tests*/
    @Test
    public void GivenList_WhenAddingFirstItems_ConfirmCorrectListSize(){
        testStringList.addFirst("Hello");
        assertEquals(1, testStringList.size());
        testStringList.addFirst("World");
        assertEquals(2, testStringList.size());
        testStringList.addFirst("!");
        assertEquals(3, testStringList.size());
    }

    @Test
    public void GivenListWithElement_WhenAddingToInvalidIndex_ExceptionIsThrown(){
        testStringList.addLast("Hello");
        assertThrows(IndexOutOfBoundsException.class, () -> testStringList.addAt(2, "World"));
        assertThrows(IndexOutOfBoundsException.class, () -> testStringList.addAt(-1, "World"));
    }

    @Test
    public void GivenEmptyList_WhenAddingElementToIndexZero_ElementIsAddedA(){
        testStringList.addAt(0, "Hello");
        assertEquals(1, testStringList.size());
        assertEquals("Hello", testStringList.get(0));
    }

    @Test
    public void GivenListWithElements_WhenAddingElementAtTakenIndex_AddsItemAtCorrectPosition(){
        testStringList.addLast("Hello");
        testStringList.addLast("World");
        testStringList.addLast("!");

        testStringList.addAt(1, "To");
        assertEquals("To", testStringList.get(1));
        assertEquals(4, testStringList.size());
    }

    @Test
    public void GivenListWithOneItem_WhenRemovingLastItem_ConfirmSizeZero(){
        testStringList.addLast("Hello");
        testStringList.removeLast();
        assertEquals(0, testStringList.size());
    }

    @Test
    public void GivenListWithOneItem_WhenRemovingFirstItem_ConfirmSizeZero(){
        testStringList.addLast("Hello");
        testStringList.removeFirst();
        assertEquals(0, testStringList.size());
    }

    @Test
    public void GivenListWithItems_WhenRemovingFirstItem_ConfirmCorrectValueIsDeleted(){
        testStringList.addFirst("Hello");
        testStringList.addLast("World");
        testStringList.addLast("!");

        String removedFirst = testStringList.removeFirst();
        assertEquals("Hello", removedFirst);

        removedFirst = testStringList.removeFirst();
        assertEquals("World", removedFirst);
    }

    @Test
    public void GivenListWithItems_WhenRemovingLastItem_ConfirmCorrectValueIsDeleted(){
        testStringList.addFirst("Hello");
        testStringList.addLast("World");
        testStringList.addLast("!");

        String removedLast = testStringList.removeLast();
        assertEquals("!", removedLast);

        removedLast = testStringList.removeLast();
        assertEquals("World", removedLast);
    }

    @Test
    public void GivenEmptyList_WhenRemovingItemAtIncorrectIndex_ExceptionIsThrown(){
        testStringList.addLast("Hello");

        assertThrows(IndexOutOfBoundsException.class, () -> testStringList.removeAt(1));
        assertThrows(IndexOutOfBoundsException.class, () -> testStringList.removeAt(-1));
    }

    @Test
    public void GivenListWithOneItem_WhenRemovingItemAtIndexZero_ConfirmSizeZero(){
        testStringList.addAt(0, "Hello");
        testStringList.removeAt(0);
        assertEquals(0, testStringList.size());
    }

    @Test
    public void GivenListWithItems_WhenRemovingLastItemByIndex_ConfirmSizeOneAndCorrectValueIsLeft(){
        testStringList.addAt(0, "Hello");
        testStringList.addAt(1, "World");
        testStringList.removeAt(1);
        assertEquals(1, testStringList.size());
        assertEquals("Hello", testStringList.get(0));
    }

    @Test
    public void GivenListWithItems_WhenDeletingItem_ConfirmCorrectValueIsDeleted(){
        testStringList.addFirst("Hello");
        testStringList.addLast("World");
        testStringList.addLast("!");

        String removedItem = testStringList.removeAt(1);
        assertEquals("World", removedItem);
    }

    @Test
    public void GivenEmptyList_WhenRemovingElements_ExceptionIsThrown(){
        assertThrows(EmptyCollectionException.class, () -> testStringList.removeLast());
        assertThrows(EmptyCollectionException.class, () -> testStringList.removeFirst());
        assertThrows(IndexOutOfBoundsException.class, () -> testStringList.removeAt(0));
    }

    @Test
    public void GivenList_WhenGettingElementByIndex_ConfirmCorrectValuesReturned(){
        testStringList.addLast("Hello");
        testStringList.addLast("World");
        testStringList.addLast("!");

        assertEquals("Hello", testStringList.get(0));
        assertEquals("World", testStringList.get(1));
        assertEquals("!", testStringList.get(2));
    }

    @Test
    public void GivenList_WhenGettingElementByIncorrectIndex_ConfirmExceptionIsThrown(){
        assertThrows(IndexOutOfBoundsException.class, () -> {
            testStringList.get(-1);
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            testStringList.get(1);
        });

        testStringList.addLast("Hello");
        assertThrows(IndexOutOfBoundsException.class, () -> {
            testStringList.get(2);
        });
    }

    @Test
    public void GivenList_WhenSettingElementAtIncorrectIndex_ExceptionIsThrown(){
        assertThrows(IndexOutOfBoundsException.class, () -> {
            testStringList.set(-1, "Hello");
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            testStringList.set(1, "Hello");
        });

        testStringList.addLast("Hello");
        assertThrows(IndexOutOfBoundsException.class, () -> {
            testStringList.set(2, "Hello");
        });
    }

    @Test
    public void GivenListWithItems_WhenSettingItems_ConfirmValuesAreChangedCorrectly(){
        testStringList.addLast("Hello");
        testStringList.addLast("World");
        testStringList.addLast("!");

        testStringList.set(0, "Hi");
        assertEquals("Hi", testStringList.get(0));

        testStringList.set(1, "There");
        assertEquals("There", testStringList.get(1));
    }

    @Test
    public void GivenEmptyList_WhenCheckedIfContainsElement_ExceptionIsThrown(){
        assertThrows(EmptyCollectionException.class, () -> {
            testStringList.contains("1");
        });
    }

    @Test
    public void GivenListWithElement_WhenCheckingIfListContainsThatElement_ConfirmReturnsTrue(){
        testStringList.addLast("Hello");
        testStringList.addLast("World");

        assertTrue(testStringList.contains("Hello"));
    }

    @Test
    public void GivenList_WhenCheckingIfContainsWrongElement_FalseIsReturned(){
        testStringList.addLast("Hello");
        testStringList.addLast("World");

        assertFalse(testStringList.contains("Hi"));
    }

    @Test
    public void GivenList_WhenRemovingItemByIncorrectValue_ExceptionIsThrown(){
        testStringList.addLast("Hello");
        assertThrows(ValueNotFoundException.class, () -> {
            testStringList.remove("World");
        });
    }

    /*** Todo: Write about it in docs //// AAAAASK IF YOU SHOULD USE OTHER EXCEPTION ***/
    @Test
    public void GivenEmptyList_WhenRemovingItemByValue_ExceptionIsThrown(){
        assertThrows(ValueNotFoundException.class, () -> {
            testStringList.remove("World");
        });
    }

    @Test
    public void GivenListWithElements_WhenRemovingIncorrectItemsByValue_ExceptionIsThrown(){
        assertThrows(ValueNotFoundException.class, () -> {
            testStringList.addLast("Hello");
            testStringList.addLast("World");
            testStringList.addLast("To");
            testStringList.addLast("You");

            testStringList.remove("Hi");
        });
    }

    @Test
    public void GivenListWithOneElement_WhenRemovingItemByValue_ListSizeIsZero(){
        testStringList.addLast("Hello");
        testStringList.remove("Hello");

        assertEquals(0, testStringList.size());
    }

    @Test
    public void GivenListWithElements_WhenRemovingItemByValue_ListSizeIsCorrect(){
        testStringList.addLast("Hello");
        testStringList.addLast("World");
        testStringList.addLast("To");
        testStringList.addLast("You");

        testStringList.remove("You");
        assertEquals("To", testStringList.get(2));
        assertEquals(3, testStringList.size());

        testStringList.remove("World");
        assertEquals("To", testStringList.get(1));
        assertEquals(2, testStringList.size());
    }

    // test the iterator
    @Test
    public void GivenList_WhenUsingIterator_ConfirmCorrectValues(){
        testStringList.addLast("Hello");
        testStringList.addLast("World");
        testStringList.addLast("To");
        testStringList.addLast("You");

        Iterator<String> iterator = testStringList.iterator();
        assertEquals("Hello", iterator.next());
        assertEquals("World", iterator.next());
        assertEquals("To", iterator.next());
        assertEquals("You", iterator.next());
        assertEquals(false, iterator.hasNext());
    }

    @BeforeEach
    public void setTestStringList(){
        this.testStringList = new DoublyLinkedList<>();
    }
}
