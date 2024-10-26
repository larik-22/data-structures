package nl.saxion.cds.solution.data_structures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MyQueueTest {
    private MyQueue<Integer> testQueue;

    @Test
    public void GivenQueue_WhenEnqueuingItems_ConfirmSizeIncreases() {
        testQueue.enqueue(1);
        assertEquals(1, testQueue.size());
        testQueue.enqueue(2);
        assertEquals(2, testQueue.size());
        testQueue.enqueue(3);
        assertEquals(3, testQueue.size());
    }

    @Test
    public void GivenQueue_WhenDequeuingItems_ConfirmSizeDecreases() {
        testQueue.enqueue(1);
        testQueue.enqueue(2);
        testQueue.enqueue(3);
        assertEquals(1, testQueue.dequeue());
        assertEquals(2, testQueue.dequeue());
        assertEquals(3, testQueue.dequeue());
        assertEquals(0, testQueue.size());
    }

    @Test
    public void GivenQueue_WhenPeeking_ConfirmCorrectValue() {
        testQueue.enqueue(1);
        assertEquals(1, testQueue.peek());
        testQueue.enqueue(2);
        assertEquals(1, testQueue.peek());
        testQueue.enqueue(3);
        assertEquals(1, testQueue.peek());
    }

    @Test
    public void GivenQueue_WhenEnqueuingThreeItems_ConfirmSizeEqualsThree() {
        testQueue.enqueue(1);
        testQueue.enqueue(2);
        testQueue.enqueue(3);
        assertEquals(3, testQueue.size());
    }

    @Test
    public void GivenQueue_WhenCheckingIfEmpty_ConfirmCorrectValues() {
        assertTrue(testQueue.isEmpty());
        testQueue.enqueue(1);
        assertFalse(testQueue.isEmpty());
    }

    @Test
    public void GivenEmptyQueue_WhenDequeuing_ConfirmExceptionThrown() {
        assertThrows(Exception.class, () -> testQueue.dequeue());
    }

    @Test
    public void GivenEmptyQueue_WhenPeeking_ConfirmExceptionThrown() {
        assertThrows(Exception.class, () -> testQueue.peek());
    }

    @Test
    public void GivenQueue_WhenGraphViz_ConfirmCorrectOutput() {
        testQueue.enqueue(1);
        testQueue.enqueue(2);
        testQueue.enqueue(3);

        // watch output online
        System.out.println(testQueue.graphViz("test"));
    }

    @Test
    public void GivenQueueWithElements_WhenCheckingIfContainsElement_ConfirmTrue(){
        testQueue.enqueue(1);
        testQueue.enqueue(2);

        assertTrue(testQueue.contains(1));
        assertTrue(testQueue.contains(2));
    }

    @BeforeEach
    public void setUp() {
        testQueue = new MyQueue<>();
    }

}