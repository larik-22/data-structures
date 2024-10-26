package nl.saxion.cds.solution.data_structures;

import nl.saxion.cds.solution.data_models.Entry;

import java.util.Comparator;
import java.util.Random;

public class MyBSTNode<K, V> {
    private Entry<K, V> entry;
    private MyBSTNode<K, V> left, right;

    public Entry<K, V> getEntry() {
        return entry;
    }

    public MyBSTNode<K, V> getLeft() {
        return left;
    }

    public MyBSTNode<K, V> getRight() {
        return right;
    }

    public void setLeft(MyBSTNode<K, V> left) {
        this.left = left;
    }

    public void setRight(MyBSTNode<K, V> right) {
        this.right = right;
    }

    public MyBSTNode(K key, V value) {
        this.entry = new Entry<>(key, value);
        this.left = null;
        this.right = null;
    }

    public void add(MyBSTNode<K, V> node, Comparator<K> comparator) {
        if (comparator.compare(node.getEntry().getKey(), entry.getKey()) < 0) {
            if (left == null) {
                left = node;
            } else {
                left.add(node, comparator);
            }
        } else {
            if (right == null) {
                right = node;
            } else {
                right.add(node, comparator);
            }
        }
    }

    public V remove(K key, Comparator<K> comparator, MyBSTNode<K, V> parent) {
        // STEPS:
        // 1. Find the node with the key to be removed.
        // 2. If the node is a leaf node, then remove the node.
        // 3. If the node has only one child, then replace the node with its child.
        // 4. If the node has two children, then find the minimum node in the right subtree of the node.

        // CASE 1: Is it leaf?
        // CASE 2: Does it have only one child?
        // CASE 3: Does it have two children?

        if (comparator.compare(key, entry.getKey()) < 0) {
            // go left
            if (left != null) {
                return left.remove(key, comparator, this);
            }
        } else if (comparator.compare(key, entry.getKey()) > 0) {
            // go right
            if (right != null) {
                return right.remove(key, comparator, this);
            }
        } else {
            // found the node
            V removedValue = entry.getValue();
            // Case 1: Node is a leaf node
            if (isLeaf()) {
                if (parent != null) {
                    if (parent.left == this) {
                        parent.left = null;
                    } else {
                        parent.right = null;
                    }
                }
                return removedValue;
            } else if (left == null || right == null) {
                // Case 2: Node has only one child
                MyBSTNode<K, V> child = (left != null) ? left : right;
                if (parent != null) {
                    if (parent.left == this) {
                        parent.left = child;
                    } else {
                        parent.right = child;
                    }
                } else {
                    // If the node to be removed is the root node
                    this.entry = child.entry;
                    this.left = child.left;
                    this.right = child.right;
                }
                return removedValue;
            } else {
                // Case 3: Node has two children.
                // Find min node in right subtree and replace it with the current node.
                MyBSTNode<K, V> minNode = findMin(right);
                Entry<K, V> minEntry = minNode.getEntry();
                remove(minEntry.getKey(), comparator, this);
                V oldValue = entry.getValue();

                this.entry = minEntry;
                return oldValue;
            }
        }

        return null;
    }

    public V get(K key, Comparator<K> comparator) {
        // STEPS:
        // 1. Compare the key of the current node with the key.
        // 2. If the key is equal to the current node's key, then return the value of the current node.
        // 3. IF the key is less than the current node's key, then move to the left child.
        // 4. If the key is greater than the current node's key, then move to the right child.

        if (comparator.compare(key, entry.getKey()) == 0) {
            return entry.getValue();
        } else if (comparator.compare(key, entry.getKey()) < 0) {
            // go left
            if (left != null) {
                return left.get(key, comparator);
            }
        } else {
            // go right
            if (right != null) {
                return right.get(key, comparator);
            }
        }

        return null;
    }

    /**
     * Finds the furthest left node from the current node.
     *
     * @param node The current node.
     * @return The furthest left node.
     */
    protected MyBSTNode<K, V> findMin(MyBSTNode<K, V> node) {
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node;
    }

    protected boolean isLeaf() {
        return left == null && right == null;
    }

    public String toDot() {
        Random rnd = new Random();
        String dotStr = "";
        if (left != null) {
            dotStr += entry.getValue() + "->" + left.getEntry().getValue() + " ";
            dotStr += left.toDot();
        } else {
            String nullNodeName = "l" + entry.getValue() + rnd.nextInt(1000);
            dotStr += nullNodeName + "[shape=\"point\"] " + entry.getValue() + "->" + nullNodeName + " ";
        }
        if (right != null) {
            dotStr += entry.getValue() + "->" + right.getEntry().getValue() + " ";
            dotStr += right.toDot();
        } else {
            String nullNodeName = "r" + entry.getValue() + rnd.nextInt(1000);
            dotStr += nullNodeName + "[shape=\"point\"] " + entry.getValue() + "->" + nullNodeName + " ";
        }
        return dotStr;
    }
}

