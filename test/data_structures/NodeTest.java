package data_structures;

import nl.saxion.cds.data_structures.Node;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NodeTest {
    @Test
    public void GivenNewNode_WhenGettingValue_CorrectDataReturned() {
        Node<Integer> node = new Node<>(5);
        node.setNext(new Node<>(10));
        node.setPrevious(new Node<>(3));

        assertEquals(5, node.getValue());
    }

    @Test
    public void GivenNewNode_WhenSettingValue_CorrectDataReturned() {
        Node<Integer> node = new Node<>(5);
        node.setValue(10);

        assertEquals(10, node.getValue());
    }

    @Test
    public void GivenNewNode_WhenSettingNext_CorrectDataReturned() {
        Node<Integer> node = new Node<>(5);
        Node<Integer> next = new Node<>(10);
        node.setNext(next);

        assertEquals(10, node.getNext().getValue());
        assertEquals(next, node.getNext());
    }

    @Test
    public void GivenNewNode_WhenSettingPrevious_CorrectDataReturned() {
        Node<Integer> node = new Node<>(5);
        Node<Integer> previous = new Node<>(3);
        node.setPrevious(previous);

        assertEquals(3, node.getPrevious().getValue());
        assertEquals(previous, node.getPrevious());
    }

    @Test
    public void GivenNode_WhenComparingWithNullOrDifferentClass_ReturnsFalse() {
        Node<Integer> node = new Node<>(5);
        assertFalse(node.equals(null)); // Test with null
        assertFalse(node.equals("string")); // Test with different class
    }
}
