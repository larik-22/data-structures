package nl.saxion.cds.solution.data_structures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MyPriorityQueueTest {
    private MyPriorityQueue<Integer> queue;

    @BeforeEach
    public void setUp(){
        queue = new MyPriorityQueue<>();
    }

    @Test
    public void givenEmptyQueue_whenEnqueueElement_thenElementIsEnqueued() {
        queue.enqueue(10, 1.0);
        assertEquals(10, queue.peek().getValue());
    }

    @Test
    public void givenNonEmptyQueue_whenEnqueueHigherPriorityElement_thenElementIsEnqueuedAtFront() {
        queue.enqueue(10, 1.0);
        queue.enqueue(20, 0.5);
        assertEquals(20, queue.peek().getValue());
    }

    @Test
    public void givenNonEmptyQueue_whenEnqueueLowerPriorityElement_thenElementIsEnqueuedAtEnd() {
        queue.enqueue(10, 1.0);
        queue.enqueue(20, 2.0);
        assertEquals(10, queue.peek().getValue());
    }

    @Test
    public void givenNonEmptyQueue_whenEnqueueMultipleElements_thenElementsAreEnqueuedInCorrectOrder() {
        queue.enqueue(10, 1.0);
        queue.enqueue(20, 0.5);
        queue.enqueue(30, 1.5);
        assertEquals(20, queue.dequeue().getValue());
        assertEquals(10, queue.dequeue().getValue());
        assertEquals(30, queue.dequeue().getValue());
    }

}
