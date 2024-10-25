package nl.saxion.cds.data_structures;

import nl.saxion.cds.collection.DuplicateKeyException;
import nl.saxion.cds.collection.SaxList;

import java.util.Comparator;

public class MyAVLTree<K extends Comparable<K>, V> extends MyBST<K, V> {
    private int size;
    private MyArrayList<K> keys;

    public MyAVLTree(Comparator<K> comparator) {
        super(comparator);

        this.size = 0;
        this.keys = new MyArrayList<>();
    }

    @Override
    public void add(K key, V value) {
        this.setRoot(add(this.getRoot(), key, value));
        this.keys.addLast(key);
        this.size++;
    }

    private MyBSTNode<K, V> add(MyBSTNode<K, V> node, K key, V value) {
        if (node == null) {
            return new MyBSTNode<>(key, value);
        }

        int cmp = key.compareTo(node.getEntry().getKey());
        if (cmp < 0) {
            node.setLeft(add(node.getLeft(), key, value));
        } else if (cmp > 0) {
            node.setRight(add(node.getRight(), key, value));
        } else {
            throw new DuplicateKeyException("Duplicate key: " + key);
        }

        return balance(node);
    }

    private MyBSTNode<K, V> balance(MyBSTNode<K, V> node) {
        int balanceFactor = getBalanceFactor(node);

        if (balanceFactor > 1) { // Left-heavy
            if (getBalanceFactor(node.getLeft()) < 0) { // LR case
                node.setLeft(rotateLeft(node.getLeft()));
            }
            return rotateRight(node); // LL case
        }

        if (balanceFactor < -1) { // Right-heavy
            if (getBalanceFactor(node.getRight()) > 0) { // RL case
                node.setRight(rotateRight(node.getRight()));
            }
            return rotateLeft(node); // RR case
        }

        return node; // No rotation needed
    }

    private MyBSTNode<K, V> rotateRight(MyBSTNode<K, V> node) {
        MyBSTNode<K, V> left = node.getLeft();
        MyBSTNode<K, V> leftRight = left.getRight();

        // Perform rotation
        left.setRight(node);
        node.setLeft(leftRight);

        // Return new root
        return left;
    }

    private MyBSTNode<K, V> rotateLeft(MyBSTNode<K, V> node) {
        MyBSTNode<K, V> right = node.getRight();
        MyBSTNode<K, V> rightLeft = right.getLeft();

        // Perform rotation
        right.setLeft(node);
        node.setRight(rightLeft);

        // Return new root
        return right;
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

    public int getHeight() {
        return getHeight(this.getRoot());
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public SaxList<K> getKeys() {
        return keys;
    }

    @Override
    public boolean contains(K key) {
        return keys.contains(key);
    }
}
