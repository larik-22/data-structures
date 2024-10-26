package nl.saxion.cds.solution.data_structures;

import static org.junit.jupiter.api.Assertions.*;

import nl.saxion.cds.collection.DuplicateKeyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.*;

public class MyAVLTreeTest {
    private MyAVLTree<Integer, String> tree;

    @Test
    public void GivenTreeWithElements_ConfirmCorrectHeightAfterRotation() {
        // 40, 20, 10, 25, 30, 22, 50, 60
        tree.add(40, "40");
        tree.add(20, "20");
        tree.add(10, "10"); // should trigger a rotation to the right (LL)

        //debugging
        System.out.println(tree.graphViz("avl"));

        tree.add(25, "25");
        tree.add(30, "30"); // should trigger a LR rotation
        tree.add(22, "22"); // should trigger a RL rotation

        //debugging
        System.out.println(tree.graphViz("avl"));
        tree.add(50, "50"); // should trigger a rotation to the left (RR)

        //debugging
        System.out.println(tree.graphViz("avl"));
        assertEquals(3, tree.getHeight());
    }

    @Test
    public void GivenTreeWithRoot_WhenTryingToAddDuplicateKey_ExceptionIsThrown(){
        tree.add(10, "10");
        tree.add(20, "20");
        assertThrows(DuplicateKeyException.class, () -> tree.add(10, "10"));
        assertThrows(DuplicateKeyException.class, () -> tree.add(20, "20"));
    }

    @Test
    public void GivenNode_ConfirmCorrectBalanceFactor() {
        MyBSTNode<Integer, String> node = new MyBSTNode<>(10, "10");
        assertEquals(0, tree.getBalanceFactor(node), "Balance factor of a single node should be 0");

        node.setLeft(new MyBSTNode<>(5, "5"));
        assertEquals(1, tree.getBalanceFactor(node), "Balance factor should be 1 when left subtree has one node");

        node.setRight(new MyBSTNode<>(15, "15"));
        assertEquals(0, tree.getBalanceFactor(node), "Balance factor should be 0 when both subtrees have one node");

        node.getLeft().setLeft(new MyBSTNode<>(2, "2"));
        assertEquals(1, tree.getBalanceFactor(node), "Balance factor should be 1 when left subtree is deeper by one level");

        node.getRight().setRight(new MyBSTNode<>(20, "20"));
        assertEquals(0, tree.getBalanceFactor(node), "Balance factor should be 0 when both subtrees are equally deep");
    }

    @Test
    public void GivenListWithNoElements_ConfirmHeightZero() {
        assertEquals(0, tree.getHeight());
    }

    @Test
    public void GivenList_WhenAddingElements_ConfirmSizeIncreases() {
        tree.add(10, "10");
        assertEquals(1, tree.size());
        tree.add(20, "20");
        assertEquals(2, tree.size());
    }

    @Test
    public void GivenList_WhenAddingElements_ConfirmCorrectKeys(){
        tree.add(10, "10");
        tree.add(20, "20");
        tree.add(30, "30");
        tree.add(40, "40");
        tree.add(50, "50");
        assertEquals(10, tree.getKeys().get(0));
        assertEquals(20, tree.getKeys().get(1));
        assertEquals(30, tree.getKeys().get(2));
        assertEquals(40, tree.getKeys().get(3));
        assertEquals(50, tree.getKeys().get(4));

        assertTrue(tree.contains(10));
        assertTrue(tree.contains(20));
    }

    public void GivenNullNode_ConfirmBalanceFactorIsZero() {
        assertEquals(0, tree.getBalanceFactor(null), "Balance factor of a null node should be 0");
    }

    @BeforeEach
    public void setUp() {
        tree = new MyAVLTree<>(Integer::compareTo);
    }
}
