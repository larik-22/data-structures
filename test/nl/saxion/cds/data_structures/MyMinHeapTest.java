package nl.saxion.cds.data_structures;

import nl.saxion.cds.collection.EmptyCollectionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MyMinHeapTest {
    private MyMinHeap<Integer> heap;

    @Test
    public void GivenEmptyHeap_WhenCheckingIfEmpty_ThenReturnsTrue() {
        assertTrue(heap.isEmpty());
    }

    @Test
    public void GivenHeapWithOneElement_WhenCheckingIfEmpty_ThenReturnsFalse() {
        heap.enqueue(1);
        assertFalse(heap.isEmpty());
    }

    @Test
    public void GivenEmptyHeap_WhenAddingValues_ConfirmValuesAreAtCorrectPosition() {
        heap.enqueue(3);
        heap.enqueue(2);
        // check if 2 is at the top
        assertEquals(2, heap.peek());

        heap.enqueue(1);
        // check if 1 was at the top
        assertEquals(1, heap.dequeue());

        // check if 2 is at the top
        assertEquals(2, heap.dequeue());

        // check if 3 is at the top
        assertEquals(3, heap.dequeue());
    }

    @Test
    public void GivenHeapWithElements_WhenDequeuing_ConfirmCorrectOrder() {
        heap.enqueue(5);
        heap.enqueue(3);
        heap.enqueue(8);
        heap.enqueue(1);
        heap.enqueue(2);

        assertEquals(1, heap.dequeue());
        assertEquals(2, heap.dequeue());
        assertEquals(3, heap.dequeue());
        assertEquals(5, heap.dequeue());
        assertEquals(8, heap.dequeue());
    }

    @Test
    public void GivenHeapWithRightSmallerThanLeft_WhenDequeuing_ThenRightChildIsSelected() {
        heap.enqueue(10);
        heap.enqueue(15);
        heap.enqueue(5);
        heap.enqueue(20);
        heap.enqueue(17);
        heap.enqueue(3);

        // Dequeue the elements and check the order
        assertEquals(3, heap.dequeue());
        assertEquals(5, heap.dequeue());
        assertEquals(10, heap.dequeue());
        assertEquals(15, heap.dequeue());
        assertEquals(17, heap.dequeue());
        assertEquals(20, heap.dequeue());
    }

    @Test
    public void GivenEmptyHeap_WhenDequeuing_ThenThrowException() {
        assertThrows(EmptyCollectionException.class, () -> heap.dequeue());
    }

    @Test
    public void GivenNullValue_WhenEnqueuing_ThenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> heap.enqueue(null));
    }

    @Test
    public void GivenHeapWithElements_WhenEnqueuing_ConfirmHeapPropertyMaintained() {
        heap.enqueue(10);
        heap.enqueue(4);
        heap.enqueue(15);
        heap.enqueue(1);
        heap.enqueue(7);

        assertEquals(1, heap.peek());
        heap.enqueue(0);
        assertEquals(0, heap.peek());
    }


    @Test
    public void GivenHeapWithElements_WhenVisualizing_ConfirmCorrectOutput() {
        // 30, 10, 20, 15, 11, 8
        heap.enqueue(30);
        heap.enqueue(10);
        heap.enqueue(20);
        heap.enqueue(15);
        heap.enqueue(11);
        heap.enqueue(8);

        // check on https://dreampuf.github.io/GraphvizOnline/ with the following input:
        System.out.println(heap.graphViz("b"));
    }

    @BeforeEach
    public void setUp() {
        heap = new MyMinHeap<>(Integer::compareTo);
    }
}
