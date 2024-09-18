package data_structures;

import nl.saxion.cds.collection.EmptyCollectionException;
import nl.saxion.cds.collection.ValueNotFoundException;
import nl.saxion.cds.data_structures.DoublyLinkedList;
import nl.saxion.cds.data_structures.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DoublyLinkedListTest {
    DoublyLinkedList<String> testStringList = new DoublyLinkedList<>();

    @Test
    public void givenEmptyList_WhenCheckingSize_ConfirmSizeIsZeroAndEmpty(){
        DoublyLinkedList<String> list = new DoublyLinkedList<>();
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
    }

    @Test
    public void givenList_WhenAddingLastItems_ConfirmCorrectListSize(){
        testStringList.addLast("Hello");
        assertEquals(1, testStringList.size());
        testStringList.addLast("World");
        assertEquals(2, testStringList.size());
        testStringList.addLast("!");
        assertEquals(3, testStringList.size());
    }

    @Test
    public void givenList_WhenAddingFirstItems_ConfirmCorrectListSize(){
        testStringList.addFirst("Hello");
        assertEquals(1, testStringList.size());
        testStringList.addFirst("World");
        assertEquals(2, testStringList.size());
        testStringList.addFirst("!");
        assertEquals(3, testStringList.size());
    }

    @Test
    public void givenListWithElement_WhenAddingToInvalidIndex_ExceptionIsThrown(){
        testStringList.addLast("Hello");
        assertThrows(IndexOutOfBoundsException.class, () -> testStringList.addAt(2, "World"));
        assertThrows(IndexOutOfBoundsException.class, () -> testStringList.addAt(-1, "World"));
    }

    @Test
    public void givenEmptyList_WhenAddingElementToIndexZero_ElementIsAddedA(){
        testStringList.addAt(0, "Hello");
        assertEquals(1, testStringList.size());
        assertEquals("Hello", testStringList.get(0));
    }

    @Test
    public void givenListWithElements_WhenAddingElementAtTakenIndex_AddsItemAtCorrectPosition(){
        testStringList.addLast("Hello");
        testStringList.addLast("World");
        testStringList.addLast("!");

        testStringList.addAt(1, "To");
        assertEquals("To", testStringList.get(1));
        assertEquals(4, testStringList.size());
    }

    @Test
    public void givenListWithOneItem_WhenRemovingLastItem_ConfirmSizeZero(){
        testStringList.addLast("Hello");
        testStringList.removeLast();
        assertEquals(0, testStringList.size());
    }

    @Test
    public void givenListWithOneItem_WhenRemovingFirstItem_ConfirmSizeZero(){
        testStringList.addLast("Hello");
        testStringList.removeFirst();
        assertEquals(0, testStringList.size());
    }

    @Test
    public void givenListWithItems_WhenRemovingFirstItem_ConfirmCorrectValueIsDeleted(){
        testStringList.addFirst("Hello");
        testStringList.addLast("World");
        testStringList.addLast("!");

        String removedFirst = testStringList.removeFirst();
        assertEquals("Hello", removedFirst);

        removedFirst = testStringList.removeFirst();
        assertEquals("World", removedFirst);
    }

    @Test
    public void givenListWithItems_WhenRemovingLastItem_ConfirmCorrectValueIsDeleted(){
        testStringList.addFirst("Hello");
        testStringList.addLast("World");
        testStringList.addLast("!");

        String removedLast = testStringList.removeLast();
        assertEquals("!", removedLast);

        removedLast = testStringList.removeLast();
        assertEquals("World", removedLast);
    }

    @Test
    public void givenEmptyList_WhenRemovingItemAtIncorrectIndex_ExceptionIsThrown(){
        testStringList.addLast("Hello");

        assertThrows(IndexOutOfBoundsException.class, () -> testStringList.removeAt(1));
        assertThrows(IndexOutOfBoundsException.class, () -> testStringList.removeAt(-1));
    }

    @Test
    public void givenListWithOneItem_WhenRemovingItemAtIndexZero_ConfirmSizeZero(){
        testStringList.addAt(0, "Hello");
        testStringList.removeAt(0);
        assertEquals(0, testStringList.size());
    }

    @Test
    public void givenListWithItems_WhenRemovingLastItemByIndex_ConfirmSizeOneAndCorrectValueIsLeft(){
        testStringList.addAt(0, "Hello");
        testStringList.addAt(1, "World");
        testStringList.removeAt(1);
        assertEquals(1, testStringList.size());
        assertEquals("Hello", testStringList.get(0));
    }

    @Test
    public void givenListWithItems_WhenDeletingItem_ConfirmCorrectValueIsDeleted(){
        testStringList.addFirst("Hello");
        testStringList.addLast("World");
        testStringList.addLast("!");

        String removedItem = testStringList.removeAt(1);
        assertEquals("World", removedItem);
    }

    @Test
    public void givenEmptyList_WhenRemovingElements_ExceptionIsThrown(){
        assertThrows(EmptyCollectionException.class, () -> testStringList.removeLast());
        assertThrows(EmptyCollectionException.class, () -> testStringList.removeFirst());
        assertThrows(IndexOutOfBoundsException.class, () -> testStringList.removeAt(0));
    }

    @Test
    public void givenList_WhenGettingElementByIndex_ConfirmCorrectValuesReturned(){
        testStringList.addLast("Hello");
        testStringList.addLast("World");
        testStringList.addLast("!");

        assertEquals("Hello", testStringList.get(0));
        assertEquals("World", testStringList.get(1));
        assertEquals("!", testStringList.get(2));
    }

    @Test
    public void givenList_WhenGettingElementByIncorrectIndex_ConfirmExceptionIsThrown(){
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
    public void givenList_WhenSettingElementAtIncorrectIndex_ExceptionIsThrown(){
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
    public void givenListWithItems_WhenSettingItems_ConfirmValuesAreChangedCorrectly(){
        testStringList.addLast("Hello");
        testStringList.addLast("World");
        testStringList.addLast("!");

        testStringList.set(0, "Hi");
        assertEquals("Hi", testStringList.get(0));

        testStringList.set(1, "There");
        assertEquals("There", testStringList.get(1));
    }

    @Test
    public void givenEmptyList_WhenCheckedIfContainsElement_ExceptionIsThrown(){
        assertThrows(EmptyCollectionException.class, () -> {
            testStringList.contains("1");
        });
    }

    @Test
    public void givenListWithElement_WhenCheckingIfListContainsThatElement_ConfirmReturnsTrue(){
        testStringList.addLast("Hello");
        testStringList.addLast("World");

        assertTrue(testStringList.contains("Hello"));
    }

    @Test
    public void givenList_WhenCheckingIfContainsWrongElement_FalseIsReturned(){
        testStringList.addLast("Hello");
        testStringList.addLast("World");

        assertFalse(testStringList.contains("Hi"));
    }

    @Test
    public void givenList_WhenRemovingItemByIncorrectValue_ExceptionIsThrown(){
        testStringList.addLast("Hello");
        assertThrows(ValueNotFoundException.class, () -> {
            testStringList.remove("World");
        });
    }

    /*** Todo: Write about it in docs //// AAAAASK IF YOU SHOULD USE OTHER EXCEPTION ***/
    @Test
    public void givenEmptyList_WhenRemovingItemByValue_ExceptionIsThrown(){
        assertThrows(ValueNotFoundException.class, () -> {
            testStringList.remove("World");
        });
    }

    @Test
    public void givenListWithElements_WhenRemovingIncorrectItemsByValue_ExceptionIsThrown(){
        assertThrows(ValueNotFoundException.class, () -> {
            testStringList.addLast("Hello");
            testStringList.addLast("World");
            testStringList.addLast("To");
            testStringList.addLast("You");

            testStringList.remove("Hi");
        });
    }

    @Test
    public void givenListWithOneElement_WhenRemovingItemByValue_ListSizeIsZero(){
        testStringList.addLast("Hello");
        testStringList.remove("Hello");

        assertEquals(0, testStringList.size());
    }

    @Test
    public void givenListWithElements_WhenRemovingItemByValue_ListSizeIsCorrect(){
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
    public void givenList_WhenUsingIterator_ConfirmCorrectValuesAreReturned(){
        testStringList.addLast("Hello");
        testStringList.addLast("World");
        testStringList.addLast("To");
        testStringList.addLast("You");

        Iterator<String> iterator = testStringList.iterator();
        assertEquals("Hello", iterator.next());
        assertEquals("World", iterator.next());
        assertEquals("To", iterator.next());
        assertEquals("You", iterator.next());
    }

    @Test
    public void givenList_WhenUsingIterator_ConfirmCorrectNextBehaviour(){
        testStringList.addLast("Hello");
        testStringList.addLast("World");
        testStringList.addLast("To");
        testStringList.addLast("You");

        Iterator<String> iterator = testStringList.iterator();
        iterator.next();
        assertTrue(iterator.hasNext());

        iterator.next();
        assertTrue(iterator.hasNext());

        iterator.next();
        assertTrue(iterator.hasNext());

        iterator.next();
        assertFalse(iterator.hasNext());
    }

//    @Test
//    public void GivenEmptyList_WhenRemovingAllElements_ExceptionIsThrown(){
//        assertThrows(ValueNotFoundException.class, () -> {
//           testStringList.removeAll("Exception");
//        });
//    }

//    @Test
//    public void GivenListWithValues_WhenRemovingAllElementInstances_ConfirmElementIsRemovedFromEverywhere(){
//        testStringList.addLast("Hello");
//        testStringList.addLast("World");
//        testStringList.addLast("World");
//        testStringList.addLast("World");
//        testStringList.addLast("Hello");
//        testStringList.addLast("Hello");
//        testStringList.addLast("Hello");
//        testStringList.addLast("World");
//        testStringList.addLast("World");
//        testStringList.addLast("World");
//        testStringList.addLast("Hello");
//
//        testStringList.removeAll("Hello");
//        testStringList.print();
//    }

    @Test
    public void givenEmptyList_WhenUsingIterator_ConfirmHasNextIsFalse(){
        // Case 1: List is empty
        Iterator<String> iterator = testStringList.iterator();
        assertFalse(iterator.hasNext());
    }

    @Test
    public void givenVisualizedList_WhenUsingGraphViz_ConfirmResultsEqual(){
        testStringList.addLast("Hello");
        testStringList.addLast("World");

        String expectedOutput = "digraph \"Dl\" {\n" +
                "    rankdir=LR;\n" +
                "    node [shape=record];\n" +
                "    n0 [label=\"{ <ref1> | <data> Hello | <ref2> }\"];\n" +
                "    n1 [label=\"{ <ref1> | <data> World | <ref2> }\"];\n" +
                "    n0:ref2:c -> n1:data:n [arrowhead=vee, arrowtail=dot, dir=both, tailclip=false];\n" +
                "    n1:ref1:c -> n0:data:s [arrowhead=vee, arrowtail=dot, dir=both, tailclip=false];\n" +
                "}";

        System.out.println(testStringList.graphViz("Dl"));
        assertEquals(expectedOutput, testStringList.graphViz("Dl"));
    }

    @BeforeEach
    public void setTestStringList(){
        this.testStringList = new DoublyLinkedList<>();
    }
}
