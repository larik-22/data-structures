package data_structures;

import nl.saxion.cds.collection.EmptyCollectionException;
import nl.saxion.cds.data_structures.DoublyLinkedList;
import nl.saxion.cds.data_structures.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DoublyLinkedListTest {
    DoublyLinkedList<String> testStringList = new DoublyLinkedList<>();

    @Test
    public void GivenCreatedNewList_SizeEqualsZero(){
        DoublyLinkedList<String> list = new DoublyLinkedList<>();
        assertEquals(0, list.size());
        assertEquals(true, testStringList.isEmpty());
    }

    /*TODO: Ask Meneer Frederik about those kind of tests*/
    @Test
    public void AddingLastElementToEmptyList_MakesSizeEqualsOne(){
        testStringList.addLast("Hello");
        assertEquals(1, testStringList.size());
    }

    /*TODO: Ask Meneer Frederik about those kind of tests*/
    @Test
    public void AddingLastElementToListContainingOneElement_MakesSizeEqualsTwo(){
        testStringList.addLast("Hello");
        testStringList.addLast("World");
        assertEquals(2, testStringList.size());
    }

    /*TODO: Ask Meneer Frederik about those kind of tests*/
    @Test
    public void AddingLastElementToListWithTwoElements_MakesSizeEqualsThree(){
        testStringList.addLast("Hello");
        testStringList.addLast("World");
        testStringList.addLast("!");
        assertEquals(3, testStringList.size());
    }

    /*TODO: Ask Meneer Frederik about those kind of tests*/
    @Test
    public void AddingFirstElementToEmptyList_MakesSizeEqualsOne(){
        testStringList.addFirst("Hello");
        assertEquals(1, testStringList.size());
    }

    /*TODO: Ask Meneer Frederik about those kind of tests*/
    @Test
    public void AddingFirstElementToListContainingOneElement_MakesSizeEqualsTwo(){
        testStringList.addFirst("Hello");
        testStringList.addFirst("World");
        assertEquals(2, testStringList.size());
    }

    /*TODO: Ask Meneer Frederik about those kind of tests*/
    @Test
    public void AddingFirstElementToListWithTwoElements_MakesSizeEqualsThree(){
        testStringList.addFirst("Hello");
        testStringList.addFirst("World");
        testStringList.addFirst("!");
        assertEquals(3, testStringList.size());
    }

    @Test
    public void AddingElementAtFirstIndexToEmptyList_MakesSizeEqualsOne(){
        testStringList.addAt(0, "Hello");
        assertEquals(1, testStringList.size());
    }


    @Test
    public void RemovingLastElementFromListContainingOneElement_MakesSizeEqualsZero(){
        testStringList.addLast("Hello");
        testStringList.removeLast();
        assertEquals(0, testStringList.size());
    }

    @Test
    public void RemovingFirstElementFromListContainingOneElement_MakesSizeEqualsZero(){
        testStringList.addLast("Hello");
        testStringList.removeFirst();
        assertEquals(0, testStringList.size());
    }

    @Test
    public void ConfirmRemovingFirstElementReturnsCorrectValues(){
        testStringList.addFirst("Hello");
        testStringList.addLast("World");

        String removedFirst = testStringList.removeFirst();
        assertEquals("Hello", removedFirst);
    }
    @Test
    public void ConfirmRemovingLastElementReturnsCorrectValues(){
        testStringList.addFirst("Hello");
        testStringList.addLast("World");

        String removedLast = testStringList.removeLast();
        assertEquals("World", removedLast);
    }

    @Test
    public void ConfirmExceptionIsThrownWhenRemovingElementWithInvalidIndex(){
        assertThrows(IndexOutOfBoundsException.class, () -> testStringList.removeAt(-1));
    }

    @Test
    public void RemovingElementAtFirstIndexFromListContainingOneElement_MakesSizeEqualsZero(){
        testStringList.addAt(0, "Hello");
        testStringList.removeAt(0);
        assertEquals(0, testStringList.size());
    }

    @Test
    public void RemovingElementAtSecondIndexFromListContainingTwoElements_MakesSizeEqualsOne(){
        testStringList.addAt(0, "Hello");
        testStringList.addAt(1, "World");
        testStringList.removeAt(1);
        assertEquals(1, testStringList.size());
    }

    @Test
    public void ConfirmRemovingItemAtIndexReturnsCorrectValues(){
        testStringList.addFirst("Hello");
        testStringList.addLast("World");
        testStringList.addLast("!");

        String removedItem = testStringList.removeAt(1);
        System.out.println(removedItem);
        assertEquals("World", removedItem);
    }

    @Test
    public void ConfirmExceptionIsThrownWhenRemovingElementFromEmptyList(){
        assertThrows(EmptyCollectionException.class, () -> testStringList.removeLast());
        assertThrows(EmptyCollectionException.class, () -> testStringList.removeFirst());
    }

    @Test
    public void ConfirmGettingCorrectItemAtFirstIndex(){
        testStringList.addLast("Hello");
        testStringList.addLast("World");
        testStringList.addLast("!");

        assertEquals("Hello", testStringList.get(0));
        assertEquals("World", testStringList.get(1));
        assertEquals("!", testStringList.get(2));
    }

    @Test
    public void ConfirmExceptionIsThrownWhenGettingInvalidElement(){
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
    public void ConfirmExceptionIsThrownWhenSettingValueAtInvalidIndex(){
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
    public void ConfirmSettingValueReturnsCorrectValue(){
        testStringList.addLast("Hello");
        testStringList.addLast("World");
        testStringList.addLast("!");

        testStringList.set(0, "Hi");
        assertEquals("Hi", testStringList.get(0));

        testStringList.set(1, "There");
        assertEquals("There", testStringList.get(1));
    }

    @Test
    public void ConfirmExceptionIsThrownWhenCheckingIfListContainsValueOnEmptyList(){
        assertThrows(EmptyCollectionException.class, () -> {
            testStringList.contains("1");
        });
    }

    @Test
    public void GivenExistingValueConfirmListContainsThatValue(){
        testStringList.addLast("Hello");
        testStringList.addLast("World");

        assertTrue(testStringList.contains("Hello"));
    }

    @Test
    public void GivenNonExistingValueConfirmListDoesNotContainThatValue(){
        testStringList.addLast("Hello");
        testStringList.addLast("World");

        assertFalse(testStringList.contains("Hi"));
    }

    @BeforeEach
    public void setTestStringList(){
        this.testStringList = new DoublyLinkedList<>();
    }
}
