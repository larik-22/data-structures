package nl.saxion.cds.data_structures;

import static org.junit.jupiter.api.Assertions.*;

import nl.saxion.cds.collection.DuplicateKeyException;
import nl.saxion.cds.collection.KeyNotFoundException;
import org.junit.jupiter.api.*;

public class MyBSTTreeTest {
    private MyBST<Integer, String> tree;

    @Test
    public void GivenEmptyList_WhenAddingItem_ConfirmItemAdded(){
        tree.add(1, "One");
        assertTrue(tree.contains(1));
        assertEquals("One", tree.get(1));
    }

    @Test
    public void GivenListWithItems_WhenAddingMoreItems_ConfirmItemsAdded(){
        tree.add(1, "One");
        tree.add(2, "Two");
        tree.add(3, "Three");
        tree.add(4, "Four");
        assertTrue(tree.contains(1));
        assertTrue(tree.contains(2));
        assertTrue(tree.contains(3));
        assertTrue(tree.contains(4));
        assertEquals("One", tree.get(1));
        assertEquals("Two", tree.get(2));
        assertEquals("Three", tree.get(3));
        assertEquals("Four", tree.get(4));
    }

    @Test
    public void GivenListWithItem_WhenTryingToAddDuplicateKey_ExceptionThrown(){
        tree.add(1, "One");
        assertThrows(DuplicateKeyException.class, () -> tree.add(1, "One"));
    }

    @Test
    public void GivenTreeWithRoot_WhenAddingSmallerItem_ConfirmItemAddedToLeft() {
        tree.add(2, "Two");
        tree.add(1, "One");
        assertTrue(tree.contains(2));
        assertTrue(tree.contains(1));
        assertEquals("Two", tree.get(2));
        assertEquals("One", tree.get(1));
    }

    @Test
    public void GivenTreeWithLeftChild_WhenAddingSmallerItem_ConfirmItemAddedToLeftSubtree() {
        tree.add(3, "Three");
        tree.add(2, "Two");
        tree.add(1, "One");
        assertTrue(tree.contains(3));
        assertTrue(tree.contains(2));
        assertTrue(tree.contains(1));
        assertEquals("Three", tree.get(3));
        assertEquals("Two", tree.get(2));
        assertEquals("One", tree.get(1));
    }

    @Test
    public void GivenListWithItems_WhenGettingKeys_ConfirmKeysReturned(){
        tree.add(3, "Three");
        tree.add(2, "Two");
        tree.add(1, "One");
        tree.add(4, "Four");
        tree.add(5, "Five");

        assertEquals(5, tree.size());
        assertEquals(3, tree.getKeys().get(0));
        assertEquals(2, tree.getKeys().get(1));
        assertEquals(1, tree.getKeys().get(2));
        assertEquals(4, tree.getKeys().get(3));
        assertEquals(5, tree.getKeys().get(4));
    }

    @Test
    public void GivenEmptyList_ConfirmIsEmpty(){
        assertTrue(tree.isEmpty());
    }

    @Test
    public void GivenListWithItems_ConfirmIsNotEmpty(){
        tree.add(1, "One");
        assertFalse(tree.isEmpty());
    }

    @Test
    public void GivenListWithItems_WhenVisualized_ConfirmCorrectOutput(){
        System.out.println(tree.graphViz("Test"));
        tree.add(3, "Three");
        tree.add(2, "Two");
        tree.add(1, "One");

        // go to https://dreampuf.github.io/GraphvizOnline to test
        System.out.println(tree.graphViz("Test"));
    }

    @Test
    public void GivenListWithItems_WhenGettingItems_ConfirmCorrectValuesReturned(){
        tree.add(3, "Three");
        tree.add(2, "Two");
        tree.add(1, "One");
        tree.add(4, "Four");
        tree.add(5, "Five");

        assertEquals("Three", tree.get(3));
        assertEquals("Two", tree.get(2));
        assertEquals("One", tree.get(1));
        assertEquals("Four", tree.get(4));
        assertEquals("Five", tree.get(5));
    }

    @Test
    public void GivenListWithItems_WhenGettingNonExistentItem_ConfirmNullReturned(){
        tree.add(1, "One");
        assertNull(tree.get(2));
    }

    @Test
    public void GivenTreeWithRoot_WhenGettingNonExistentLeftItem_ConfirmNullReturned() {
        tree.add(2, "Two");
        assertNull(tree.get(1));
    }

    @Test
    public void GivenTreeWithRoot_WhenGettingNonExistentRightItem_ConfirmNullReturned() {
        tree.add(2, "Two");
        assertNull(tree.get(3));
    }

    // is leaf node test
    @Test
    public void GivenTreeWithRoot_WhenCheckingIfRootIsLeafNode_ConfirmTrue() {
        tree.add(1, "One");
        MyBSTNode<Integer, String> root = tree.getRoot();
        assertTrue(root.isLeaf());
    }

    @Test
    public void GivenTreeWithLeftChild_WhenCheckingIfRootIsLeafNode_ConfirmFalse() {
        tree.add(2, "Two");
        tree.add(1, "One");
        MyBSTNode<Integer, String> root = tree.getRoot();
        assertFalse(root.isLeaf());
    }

    @Test
    public void GivenTreeWithRightChild_WhenCheckingIfRootIsLeafNode_ConfirmFalse() {
        tree.add(1, "One");
        tree.add(2, "Two");
        MyBSTNode<Integer, String> root = tree.getRoot();
        assertFalse(root.isLeaf());
    }

    @Test
    public void GivenTreeWithItems_WhenCheckingRootSiblings_ConfirmCorrectValuesReturned() {
        tree.add(3, "Three");
        tree.add(2, "Two");
        tree.add(1, "One");
        tree.add(4, "Four");
        tree.add(5, "Five");

        MyBSTNode<Integer, String> root = tree.getRoot();
        assertEquals(2, root.getLeft().getEntry().getKey());
        assertEquals(4, root.getRight().getEntry().getKey());
    }

    @Test
    public void GivenListWithRootNode_WhenRemovingRootNode_ConfirmNodeRemoved() {
        tree.add(1, "One");
        assertEquals("One", tree.remove(1));
        assertFalse(tree.contains(1));
    }

    @Test
    public void GivenListWithRootNode_WhenRemovingNonExistentNode_ConfirmExceptionThrown() {
        tree.add(1, "One");
        assertThrows(KeyNotFoundException.class, () -> tree.remove(2));
    }

    @Test
    public void GivenTreeWithLeafNode_WhenRemovingLeafNode_ConfirmNodeRemoved() {
        tree.add(1, "One");
        tree.add(2, "Two");
        assertEquals("Two", tree.remove(2));
        assertFalse(tree.contains(2));
        assertTrue(tree.contains(1));
    }

    @Test
    public void GivenTreeWithNodeHavingOnlyLeftChild_WhenRemovingNode_ConfirmNodeRemoved() {
        tree.add(2, "Two");
        tree.add(1, "One");
        assertEquals("Two", tree.remove(2));
        assertFalse(tree.contains(2));
        assertTrue(tree.contains(1));
    }

    @Test
    public void GivenTreeWithNodeHavingOnlyRightChild_WhenRemovingNode_ConfirmNodeRemoved() {
        tree.add(1, "One");
        tree.add(2, "Two");
        assertEquals("One", tree.remove(1));
        assertFalse(tree.contains(1));
        assertTrue(tree.contains(2));
    }

    @Test
    public void GivenTreeWithNodeHavingTwoChildren_WhenRemovingNode_ConfirmNodeRemoved() {
        tree.add(2, "Two");
        tree.add(1, "One");
        tree.add(3, "Three");
        assertEquals("Two", tree.remove(2));
        assertFalse(tree.contains(2));
        assertTrue(tree.contains(1));
        assertTrue(tree.contains(3));
    }
    @Test
    public void GivenTreeWithNodeHavingOnlyLeftChild_WhenRemovingNode_ConfirmParentUpdated() {
        tree.add(3, "Three");
        tree.add(2, "Two");
        tree.add(1, "One");
        assertEquals("Two", tree.remove(2));
        assertFalse(tree.contains(2));
        assertTrue(tree.contains(1));
        assertTrue(tree.contains(3));
        assertEquals(tree.getRoot().getLeft().getEntry().getKey(), Integer.valueOf(1));
    }

    @Test
    public void GivenTreeWithNodeHavingOnlyRightChild_WhenRemovingNode_ConfirmParentUpdated() {
        tree.add(1, "One");
        tree.add(2, "Two");
        tree.add(3, "Three");
        assertEquals("Two", tree.remove(2));
        assertFalse(tree.contains(2));
        assertTrue(tree.contains(1));
        assertTrue(tree.contains(3));
        assertEquals(tree.getRoot().getRight().getEntry().getKey(), Integer.valueOf(3));
    }

    @Test
    public void GivenTreeWithLeafNodeAsLeftChild_WhenRemovingLeafNode_ConfirmParentUpdated() {
        tree.add(3, "Three");
        tree.add(2, "Two");
        tree.add(1, "One");
        assertEquals("One", tree.remove(1));
        assertFalse(tree.contains(1));
        assertTrue(tree.contains(2));
        assertTrue(tree.contains(3));
        assertNull(tree.getRoot().getLeft().getLeft());
    }

    @Test
    public void GivenTreeWithLeafNodeAsRightChild_WhenRemovingLeafNode_ConfirmParentUpdated() {
        tree.add(1, "One");
        tree.add(2, "Two");
        tree.add(3, "Three");
        assertEquals("Three", tree.remove(3));
        assertFalse(tree.contains(3));
        assertTrue(tree.contains(1));
        assertTrue(tree.contains(2));
        assertNull(tree.getRoot().getRight().getRight());
    }

    @Test
    public void GivenEmptyTree_WhenFindingMin_ConfirmNullReturned() {
        MyBSTNode<Integer, String> root = null;
        assertNull(root);
    }

    @Test
    public void GivenTreeWithOneNode_WhenFindingMin_ConfirmRootReturned() {
        MyBSTNode<Integer, String> root = new MyBSTNode<>(1, "One");
        assertEquals(root, root.findMin(root));
    }

    @Test
    public void GivenTreeWithMultipleNodes_WhenFindingMin_ConfirmMinNodeReturned() {
        MyBSTNode<Integer, String> root = new MyBSTNode<>(3, "Three");
        MyBSTNode<Integer, String> left = new MyBSTNode<>(2, "Two");
        MyBSTNode<Integer, String> leftmost = new MyBSTNode<>(1, "One");
        root.add(left, Integer::compareTo);
        root.add(leftmost, Integer::compareTo);
        assertEquals(leftmost, root.findMin(root));
    }

    @BeforeEach
    public void setUp() {
        tree = new MyBST<>(Integer::compareTo);
    }
}
