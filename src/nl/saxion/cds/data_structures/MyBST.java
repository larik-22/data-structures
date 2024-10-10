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

    public String graphViz(String name) {
        String dotStr = "digraph " + name + " {";
        if (root != null) {
            dotStr += root.toDot();
        }
        dotStr += "}";

        return dotStr;
    }
}
