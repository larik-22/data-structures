package nl.saxion.cds.solution.data_structures;

import nl.saxion.cds.collection.EmptyCollectionException;
import nl.saxion.cds.collection.SaxSearchable;
import nl.saxion.cds.collection.ValueNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class MyArrayListTest {
    // Make sure a lot of resizing has to be done
    private static final int BIG_NUMBER_OF_ELEMENTS = 5000;
    private MyArrayList<String> list;

    @BeforeEach
    void createExampleList() {
        list = new MyArrayList<>();
        list.addLast("2");
        list.addLast("23");
        list.addLast("a");
        list.addLast("dd");
        list.addLast("7a");
    }


    @Test
    void GivenEmptyList_WhenCallingGetters_ConfirmListIsActuallyEmpty() {
        MyArrayList<Object> myArrayList = new MyArrayList<>();
        assertTrue(myArrayList.isEmpty());
        assertEquals(0, myArrayList.size());
        assertEquals("[ ]", myArrayList.toString());
    }

    @Test
    void GivenEmptyList_WhenAccessingAnyIndex_ThenIndexOutOfBoundsThrown() {
        MyArrayList<Object> myEmptyList = new MyArrayList<>();
        assertThrows(IndexOutOfBoundsException.class, () -> myEmptyList.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> myEmptyList.get(1));
        assertThrows(IndexOutOfBoundsException.class, () -> myEmptyList.addAt(-1, 666));
        assertThrows(IndexOutOfBoundsException.class, () -> myEmptyList.addAt(1, 666));
        assertThrows(IndexOutOfBoundsException.class, () -> myEmptyList.set(-1, 666));
        assertThrows(IndexOutOfBoundsException.class, () -> myEmptyList.set(1, 666));
        assertThrows(EmptyCollectionException.class, myEmptyList::removeFirst);
        assertThrows(EmptyCollectionException.class, myEmptyList::removeLast);
    }

    @Test
    void GivenSheetsList_WhenNoChanges_ConfirmInitialContent() {
        assertEquals(5, list.size());
        assertFalse(list.isEmpty());
        assertEquals("[ 2 23 a dd 7a ]", list.toString());

        // Testing GraphViz can best be done manually (copy past to https://dreampuf.github.io/GraphvizOnline)
        System.out.println(list.graphViz());
    }

    @Test
    void GivenSheetsList_WhenCallingContains_ConfirmCorrectResponses() {
        // Test edge cases in contains()
        assertTrue(list.contains("2"));
        assertTrue(list.contains("7a"));
        assertFalse(list.contains("huh?"));
    }

    @Test
    void GivenSheetsList_WhenAddingAtBeginning_ConfirmChangesAreCorrect() {
        // Insert at front
        list.addFirst("b3");
        assertEquals(6, list.size());
        assertFalse(list.isEmpty());
        assertEquals("[ b3 2 23 a dd 7a ]", list.toString());

        assertThrows(ValueNotFoundException.class, () -> list.remove("huh?"));
    }

    @Test
    void GivenSheetsList_WhenAddingAtIndex_ConfirmChangesAreCorrect() {
        // Insert before
        list.addAt(4, "b3");
        assertEquals(6, list.size());
        assertFalse(list.isEmpty());
        assertEquals("[ 2 23 a dd b3 7a ]", list.toString());
    }

    @Test
    void GivenSheetsList_WhenRemovingElement_ConfirmChangesAreCorrect() {
        // Remove specific element
        list.remove("dd");
        assertEquals("[ 2 23 a 7a ]", list.toString());
        assertEquals(4, list.size());
        assertFalse(list.isEmpty());
    }

    @Test
    void GivenSheetsList_WhenRemovingAllElement_ConfirmChangesAreCorrect() {
        // Edge cases remove
        list.remove("7a");
        list.remove("2");
        assertEquals("[ 23 a dd ]", list.toString());
        assertEquals(3, list.size());
        // Further empty the list.
        assertEquals("23", list.removeFirst());
        assertEquals("dd", list.removeLast());
        assertEquals("a", list.removeFirst());
        // Confirm emtpyness of the list.
        assertEquals("[ ]", list.toString());
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
        assertThrows(EmptyCollectionException.class, list::removeFirst);
        assertThrows(EmptyCollectionException.class, list::removeLast);
    }

    @Test
    void GivenSheetsList_WhenAccessingIndexOutOfBounds_ThenExceptionIsThrown() {
        assertThrows(IndexOutOfBoundsException.class, () -> list.removeAt(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.removeAt(list.size()));
    }

    @Test
    void GivenSheetsList_WhenAddingNullValues_ThenListContainsNullValues() {
        list.addAt(0, null); // check addAt with index equals first element
        list.addAt(5, null); // check addAt with index equals last element
        list.addAt(7, null); // check addAt with index just after last element
        assertEquals("[ null 2 23 a dd null 7a null ]", list.toString());
        assertEquals(8, list.size());
        assertFalse(list.isSorted(String::compareTo));

        // Testing GraphViz can best be done manually (copy past to https://dreampuf.github.io/GraphvizOnline)
        System.out.println(list.graphViz());

        // Remove specific element
        list.remove("dd");
        list.remove(null);
        list.remove(null);
        list.remove(null);

        // Remove element using the iterator.
        var iterator = list.iterator();
        while (iterator.hasNext()) {
            String element = iterator.next();
            if (element.compareTo("a") == 0) {
                iterator.remove();
            }
        }
        assertEquals(3, list.size());
        assertFalse(list.isEmpty());
        assertEquals("[ 2 23 7a ]", list.toString());

        // Confirm exception is thrown when no more null values are present.
        assertThrows(ValueNotFoundException.class, () -> list.remove(null));
    }

    @Test
    void GivenListWithIntegers_WhenQuicksorted_ThenListIsSorted() {
        MyArrayList<Integer> list3 = createIntegerArrayList();
        list3.quickSort(Integer::compareTo);
        System.out.println(list3);
        assertTrue(list3.isSorted(Integer::compareTo));
        // Loop through the list and confirm each value is larger than the previous
        int last = -100;
        for (int current : list3) {
            assertTrue(current >= last);
            last = current;
        }
    }

    MyArrayList<Integer> createIntegerArrayList() {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.addLast(8);
        list.addLast(1);
        list.addLast(5);
        list.addLast(14);
        list.addLast(4);
        list.addLast(15);
        list.addLast(12);
        list.addLast(6);
        list.addLast(2);
        list.addLast(11);
        list.addLast(10);
        list.addLast(7);
        list.addLast(9);
        return list;
    }

    @Test
    void GivenLargeList_WhenMakingChanges_ConfirmStateRemainsCorrect() {
        MyArrayList<Integer> list = new MyArrayList<>();
        for (int i = 0; i < BIG_NUMBER_OF_ELEMENTS; ++i) {
            list.addLast(i);
        }
        assertEquals(BIG_NUMBER_OF_ELEMENTS, list.size());

        // Test removing all elements one by one
        assertEquals(BIG_NUMBER_OF_ELEMENTS, list.size());
        for (int i = BIG_NUMBER_OF_ELEMENTS / 2; i > 0; --i) {
            assertEquals(i, list.removeAt(i));
            list.removeLast();
        }
        assertFalse(list.contains(0));
        assertEquals(0, list.size());

        // Create a list of random integers to test with simpleSort()
        MyArrayList<Integer> list2 = new MyArrayList<>();
        var random = new Random();
        for (int i = 0; i < BIG_NUMBER_OF_ELEMENTS; ++i) {
            list2.addLast(random.nextInt(0, BIG_NUMBER_OF_ELEMENTS));
        }
        assertEquals(BIG_NUMBER_OF_ELEMENTS, list2.size());
        assertFalse(list2.isSorted(Integer::compareTo));
        list2.simpleSort(Integer::compareTo);
        assertTrue(list2.isSorted(Integer::compareTo));

        // Create a list of random integers to test with quickSort()
        MyArrayList<Integer> list3 = new MyArrayList<>();
        for (int i = 0; i < BIG_NUMBER_OF_ELEMENTS; ++i) {
            list3.addLast(random.nextInt(0, BIG_NUMBER_OF_ELEMENTS));
        }
        assertEquals(BIG_NUMBER_OF_ELEMENTS, list3.size());
        assertFalse(list3.isSorted(Integer::compareTo));
        list3.quickSort(Integer::compareTo);
        assertTrue(list3.isSorted(Integer::compareTo));

        // Test BinarySearch
        int v = list3.get(0);
        int i = list3.binarySearch(Integer::compareTo, v);
        assertEquals(v, list3.get(i)); // Compare value, because list may contain double values
        v = list3.get(BIG_NUMBER_OF_ELEMENTS - 1);
        i = list3.binarySearch(Integer::compareTo, v);
        assertEquals(v, list3.get(i));
        v = list3.get(BIG_NUMBER_OF_ELEMENTS - 2);
        i = list3.binarySearch(Integer::compareTo, v);
        assertEquals(v, list3.get(i));
        assertEquals(SaxSearchable.NOT_FOUND, list3.binarySearch(Integer::compareTo, -1));

        // Test linearSearch
        v = list3.get(0);
        i = list3.linearSearch(v); // Compare value, because list may contain double values
        assertEquals(v, list3.get(i));
        v = list3.get(BIG_NUMBER_OF_ELEMENTS - 1);
        i = list3.linearSearch(v);
        assertEquals(v, list3.get(i));
        v = list3.get(BIG_NUMBER_OF_ELEMENTS - 2);
        i = list3.linearSearch(v);
        assertEquals(v, list3.get(i));
        assertEquals(SaxSearchable.NOT_FOUND, list3.linearSearch(-1));
    }

    @Test
    void GivenSortedList_WhenUsingIsSorted_ConfirmTrueReturned() {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);
        list.addLast(4);
        list.addLast(5);
        assertTrue(list.isSorted(Integer::compareTo));
    }

    @Test
    void GivenUnsortedList_WhenCheckingIfSorted_ConfirmFalseReturned(){
        MyArrayList<Integer> list = new MyArrayList<>();
        list.addLast(4);
        list.addLast(2);
        list.addLast(8);

        assertFalse(list.isSorted(Integer::compareTo));
    }

    @Test
    void GivenList_WhenUsingBinarySearch_CorrectIndexIsReturned(){
        MyArrayList<Integer> nums = new MyArrayList<>();
        nums.addLast(1);
        nums.addLast(2);
        nums.addLast(3);
        nums.addLast(4);
        nums.addLast(5);
        nums.addLast(6);
        nums.addLast(7);
        nums.addLast(8);
        nums.addLast(9);
        nums.addLast(10);

        assertEquals(0, nums.binarySearch(Integer::compareTo, 1));
        assertEquals(1, nums.binarySearch(Integer::compareTo, 2));
        assertEquals(2, nums.binarySearch(Integer::compareTo, 3));
        assertEquals(3, nums.binarySearch(Integer::compareTo, 4));
        assertEquals(4, nums.binarySearch(Integer::compareTo, 5));
        assertEquals(5, nums.binarySearch(Integer::compareTo, 6));
        assertEquals(6, nums.binarySearch(Integer::compareTo, 7));
        assertEquals(7, nums.binarySearch(Integer::compareTo, 8));
        assertEquals(8, nums.binarySearch(Integer::compareTo, 9));
        assertEquals(9, nums.binarySearch(Integer::compareTo, 10));
    }

    @Test
    void GivenUnsortedList_WhenUsingBinarySearch_ConfirmNegativeValueReturned(){
        MyArrayList<Integer> nums = new MyArrayList<>();
        nums.addLast(100);
        nums.addLast(20);
        nums.addLast(31);
        nums.addLast(22);
        nums.addLast(5);

        assertEquals(-1, nums.binarySearch(Integer::compareTo, 100));
    }

    @Test
    void GivenListOfIntegers_WhenSearchingForPivot_ConfirmCorrectPivotIndexIsReturned(){
        MyArrayList<Integer> list = new MyArrayList<>();
        list.addLast(9);
        list.addLast(4);
        list.addLast(7);
        list.addLast(10);
        list.addLast(3);
        list.addLast(6);
        list.addLast(17);

        int i = list.splitInPlace(Integer::compareTo, 0, list.size() - 1);
        assertEquals(4, i);
    }

    @Test
    void GivenListOfIntegers_WhenQuickSorting_ConfirmIsSorted(){
        MyArrayList<Integer> list = new MyArrayList<>();
        list.addLast(9);
        list.addLast(4);
        list.addLast(7);
        list.addLast(10);
        list.addLast(3);
        list.addLast(6);
        list.addLast(17);

        list.quickSort(Integer::compareTo);
        assertTrue(list.isSorted(Integer::compareTo));
    }

    @Test
    void GivenUnsortedList_WhenUsingSelectionSort_ConfirmListIsSorted(){
        MyArrayList<Integer> list = new MyArrayList<>();
        list.addLast(9);
        list.addLast(4);
        list.addLast(7);
        list.addLast(10);
        list.addLast(3);
        list.addLast(6);
        list.addLast(17);

        list.selectionSort(Integer::compareTo);
        assertTrue(list.isSorted(Integer::compareTo));
    }

    @Test
    void GivenUnsortedList_WhenUsingInsertionSort_ConfirmListIsSorted(){
        MyArrayList<Integer> list = new MyArrayList<>();
        list.addLast(9);
        list.addLast(4);
        list.addLast(7);
        list.addLast(10);
        list.addLast(3);
        list.addLast(6);
        list.addLast(17);

        list.insertionSort(Integer::compareTo);
        assertTrue(list.isSorted(Integer::compareTo));
    }

    @Test
    void GivenListWithItems_WhenClearing_ConfirmListIsEmpty(){
        MyArrayList<Integer> list = new MyArrayList<>();
        list.addLast(9);
        list.addLast(4);
        list.addLast(7);
        list.addLast(10);
        list.addLast(3);
        list.addLast(6);
        list.addLast(17);

        list.clear();
        assertTrue(list.isEmpty());
    }

    @Test
    void GivenListWithElements_WhenSwappingElements_ThenElementsAreSwapped() {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);

        // Swap first and last elements
        list.swap(0, 2);

        assertEquals(3, list.get(0));
        assertEquals(2, list.get(1));
        assertEquals(1, list.get(2));
    }

    @Test
    void GivenListWithElements_WhenSwappingSameIndex_ThenElementsRemainUnchanged() {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);

        // Swap element with itself
        list.swap(1, 1);

        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
        assertEquals(3, list.get(2));
    }

    @Test
    void GivenListWithElements_WhenSwappingInvalidIndices_ThenAssertionErrorThrown() {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);

        assertThrows(AssertionError.class, () -> list.swap(-1, 2));
        assertThrows(AssertionError.class, () -> list.swap(0, 3));
    }

    @Test
    void GivenEmptyList_WhenCheckingIfSorted_ThenTrueReturned() {
        MyArrayList<Integer> list = new MyArrayList<>();
        assertTrue(list.isSorted(Integer::compareTo));
    }

    @Test
    void GivenSingleElementList_WhenCheckingIfSorted_ThenTrueReturned() {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.addLast(1);
        assertTrue(list.isSorted(Integer::compareTo));
    }

    @Test
    void GivenListWithAllNullValues_WhenCheckingIfSorted_ThenTrueReturned() {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.addLast(null);
        list.addLast(null);
        list.addLast(null);
        assertTrue(list.isSorted(Integer::compareTo));
    }

    @Test
    void GivenListWithMixedNullAndNonNullValues_WhenCheckingIfSorted_ThenFalseReturned() {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.addLast(null);
        list.addLast(1);
        list.addLast(null);
        assertFalse(list.isSorted(Integer::compareTo));
    }

    @Test
    void GivenListWithNullValuesAtEnd_WhenCheckingIfSorted_ThenTrueReturned() {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.addLast(1);
        list.addLast(2);
        list.addLast(null);
        list.addLast(null);
        assertFalse(list.isSorted(Integer::compareTo));
    }
}