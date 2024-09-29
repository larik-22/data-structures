package nl.saxion.cds.data_structures;

import nl.saxion.cds.data_structures.MyStack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MyStackTest {
    private MyStack<Integer> testStack;

    @Test
    public void GivenStack_WhenPushingItems_ConfirmSizeIncreases() {
        testStack.push(1);
        assertEquals(1, testStack.size());
        testStack.push(2);
        assertEquals(2, testStack.size());
        testStack.push(3);
        assertEquals(3, testStack.size());
    }

    @Test
    public void GivenStack_WhenPoppingItems_ConfirmSizeDecreases() {
        testStack.push(1);
        testStack.push(2);
        testStack.push(3);
        assertEquals(3, testStack.pop());
        assertEquals(2, testStack.pop());
        assertEquals(1, testStack.pop());
        assertEquals(0, testStack.size());
    }

    @Test
    public void GivenStack_WhenPeeking_ConfirmCorrectValue() {
        testStack.push(1);
        assertEquals(1, testStack.peek());
        testStack.push(2);
        assertEquals(2, testStack.peek());
        testStack.push(3);
        assertEquals(3, testStack.peek());
    }

    @Test
    public void GivenStack_WhenPushingThreeItems_ConfirmSizeEqualsThree() {
        testStack.push(1);
        testStack.push(2);
        testStack.push(3);
        assertEquals(3, testStack.size());
    }

    @Test
    public void GivenStack_WhenCheckingIfEmpty_ConfirmCorrectValues() {
        assertTrue(testStack.isEmpty());
        testStack.push(1);
        assertFalse(testStack.isEmpty());
    }

    @Test
    public void GivenEmptyStack_WhenPopping_ConfirmExceptionThrown() {
        assertThrows(Exception.class, () -> testStack.pop());
    }

    @Test
    public void GivenEmptyStack_WhenPeeking_ConfirmExceptionThrown() {
        assertThrows(Exception.class, () -> testStack.peek());
    }

    @BeforeEach
    public void setUp() {
        testStack = new MyStack<>();
    }
}
