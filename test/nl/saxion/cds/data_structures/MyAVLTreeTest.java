package nl.saxion.cds.data_structures;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.*;

public class MyAVLTreeTest {
    private MyAVLTree<Integer, String> tree;

    @Test
    public void GivenTreeWithElements_ConfirmCorrectHeight() {
        // Trigger LR rotation
        tree.add(30, "30");
        tree.add(35, "35");
        tree.add(20, "20");
        tree.add(19, "19");
        tree.add(18, "18");


        System.out.println(tree.graphViz("es"));
//        assertEquals(5, tree.getHeight(tree.getRoot()));
    }

    @BeforeEach
    public void setUp() {
        tree = new MyAVLTree<>(Integer::compareTo);
    }
}
