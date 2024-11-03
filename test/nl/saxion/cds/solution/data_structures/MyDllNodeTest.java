package nl.saxion.cds.solution.data_structures;

import nl.saxion.cds.solution.data_models.MyDllNode;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MyDllNodeTest {
    @Test
    public void GivenNewNode_WhenGettingValue_CorrectDataReturned() {
        MyDllNode<Integer> node = new MyDllNode<>(5);
        node.setNext(new MyDllNode<>(10));
        node.setPrevious(new MyDllNode<>(3));

        assertEquals(5, node.getValue());
    }

    @Test
    public void GivenNewNode_WhenSettingValue_CorrectDataReturned() {
        MyDllNode<Integer> node = new MyDllNode<>(5);
        node.setValue(10);

        assertEquals(10, node.getValue());
    }

    @Test
    public void GivenNewNode_WhenSettingNext_CorrectDataReturned() {
        MyDllNode<Integer> node = new MyDllNode<>(5);
        MyDllNode<Integer> next = new MyDllNode<>(10);
        node.setNext(next);

        assertEquals(10, node.getNext().getValue());
        assertEquals(next, node.getNext());
    }

    @Test
    public void GivenNewNode_WhenSettingPrevious_CorrectDataReturned() {
        MyDllNode<Integer> node = new MyDllNode<>(5);
        MyDllNode<Integer> previous = new MyDllNode<>(3);
        node.setPrevious(previous);

        assertEquals(3, node.getPrevious().getValue());
        assertEquals(previous, node.getPrevious());
    }

    @Test
    public void GivenNode_WhenComparingWithNullOrDifferentClass_ReturnsFalse() {
        MyDllNode<Integer> node = new MyDllNode<>(5);
        assertFalse(node.equals(null)); // Test with null
        assertFalse(node.equals("string")); // Test with different class
    }
}
