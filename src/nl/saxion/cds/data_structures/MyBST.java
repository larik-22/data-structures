package nl.saxion.cds.data_structures;

import nl.saxion.cds.collection.DuplicateKeyException;
import nl.saxion.cds.collection.KeyNotFoundException;
import nl.saxion.cds.collection.SaxBinaryTree;
import nl.saxion.cds.collection.SaxList;

import java.util.Comparator;

public class MyBST<K extends Comparable<K>, V> implements SaxBinaryTree<K, V> {
    private MyBSTNode<K, V> root;
    private Comparator<K> comparator;
    private MyArrayList<K> keys;
    private int size;

    public MyBST(Comparator<K> comparator) {
        this.comparator = comparator;
        this.keys = new MyArrayList<>();
    }

    // testing purposes only
    protected MyBSTNode<K, V> getRoot() {
        return root;
    }

    // for avl tree
    protected void setRoot(MyBSTNode<K, V> root) {
        this.root = root;
    }

    @Override
    public boolean contains(K key) {
        return keys.contains(key);
    }

    @Override
    public V get(K key) {
        return root.get(key, comparator);
    }

    @Override
    public void add(K key, V value) throws DuplicateKeyException {
        if (keys.contains(key)) {
            throw new DuplicateKeyException("Key already exists");
        }

        if (size() == 0) {
            root = new MyBSTNode<>(key, value);
        } else {
            root.add(new MyBSTNode<>(key, value), comparator);
        }

        keys.addLast(key);
        size++;
    }

    @Override
    public V remove(K key) throws KeyNotFoundException {
        if (!keys.contains(key)) {
            throw new KeyNotFoundException("Key not found");
        }

        // assuming key is present, we can remove it and decrease size in any condition
        V value;
        if (size == 1) {
            System.out.println("Removing root");
            value = root.getEntry().getValue();
            root = null;
        } else {
            value = root.remove(key, comparator, null);
        }
        keys.remove(key);
        size--;
        return value;
    }

    @Override
    public SaxList<K> getKeys() {
        return keys;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * TODO: write in docs
     * Finds the parent of the given node.
     * @param key The key of the node whose parent is to be found.
     * @return The parent node, or null if the node is the root or not found.
     */
    protected MyBSTNode<K, V> findParent(K key) {
        if (root == null || root.getEntry().getKey().equals(key)) {
            return null;
        }

        MyBSTNode<K, V> parent = null;
        MyBSTNode<K, V> current = root;

        while (current != null) {
            int cmp = comparator.compare(key, current.getEntry().getKey());
            if (cmp == 0) {
                return parent;
            } else if (cmp < 0) {
                parent = current;
                current = current.getLeft();
            } else {
                parent = current;
                current = current.getRight();
            }
        }

        return null;
    }

    public String graphViz(String name) {
        String dotStr = "digraph " + name + " {";
        if (root != null) {
            dotStr += root.toDot();
        }
        dotStr += "}";

        return dotStr;
    }
}
