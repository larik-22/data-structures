package nl.saxion.cds.data_structures;

import java.util.Comparator;

public class MyAVLTree<K extends Comparable<K>, V> extends MyBST<K, V> {
    public MyAVLTree(Comparator<K> comparator) {
        super(comparator);
    }

    @Override
    public void add(K key, V value) {
        super.add(key, value);
        balance(this.getRoot());
    }

    private void balance(MyBSTNode<K, V> node) {
        if (node == null) {
            return;
        }

        int balanceFactor = getBalanceFactor(node);

        // Case 1: LL

        // Case 2: LR

        // Case 3: RR

        // Case 4: RL

    }

    private void rotateRight(MyBSTNode<K, V> node) {

    }

    private void rotateLeft(MyBSTNode<K, V> node) {

    }

    /**
     * Gets balance factor for provided node
     *
     * @param node node to get balance factor for
     * @return balance factor integer value
     */
    protected int getBalanceFactor(MyBSTNode<K, V> node) {
        // get the height of the left and right subtree
        // return the difference between the left and right subtree
        if (node == null) {
            return 0;
        }

        return getHeight(node.getLeft()) - getHeight(node.getRight());
    }

    /**
     * Gets height of provided node
     *
     * @param node node to get height for
     * @return height of the node
     */
    protected int getHeight(MyBSTNode<K, V> node) {
        if (node == null) {
            return 0;
        }

        return Math.max(getHeight(node.getLeft()), getHeight(node.getRight())) + 1;
    }

}
