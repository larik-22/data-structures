package nl.saxion.cds.solution.data_structures;

import nl.saxion.cds.collection.EmptyCollectionException;
import nl.saxion.cds.collection.SaxHeap;

import java.util.Comparator;

public class MyMinHeap<V extends Comparable<V>> implements SaxHeap<V> {
    private final MyArrayList<V> heap;
    private final Comparator<V> comparator;

    public MyMinHeap(Comparator<V> comparator) {
        heap = new MyArrayList<>();
        this.comparator = comparator;
    }

    public MyMinHeap(Comparator<V> comparator, MyArrayList<V> elements){
        this.comparator = comparator;
        heap = new MyArrayList<>();

        for (V element : elements) {
            enqueue(element);
        }
    }

    /**
     * Gets an index of a child node
     *
     * @param parentIdx The index of the parent node
     * @param left      Whether to get the left child or the right child
     * @return The index of the child node
     */
    public int getChild(int parentIdx, boolean left) {
        if (left) {
            return 2 * parentIdx + 1;
        } else {
            return 2 * parentIdx + 2;
        }
    }

    /**
     * Gets the index of the parent node
     *
     * @param childIdx The index of the child node
     * @return The index of the parent node or -1 if the child is the root
     */
    public int getParent(int childIdx) {
        if (childIdx == 0) return -1;
        return (childIdx - 1) / 2;
    }

    @Override
    public void enqueue(V value) {
        if (value == null) throw new IllegalArgumentException("Value cannot be null");

        heap.addLast(value);
        int itemIdx = heap.size() - 1;
        int parentIdx = getParent(itemIdx);

        while (parentIdx != -1 && comparator.compare(heap.get(itemIdx), heap.get(parentIdx)) < 0) {
            heap.swap(itemIdx, parentIdx);
            itemIdx = parentIdx;
            parentIdx = getParent(itemIdx);
        }
    }

    @Override
    public V dequeue() throws EmptyCollectionException {
        if (isEmpty()) throw new EmptyCollectionException();

        V root = heap.get(0);
        V lastNode = heap.get(heap.size() - 1);

        heap.set(0, lastNode);
        heap.removeLast();

        int itemIdx = 0;
        int leftChildIdx = getChild(itemIdx, true);
        int rightChildIdx = getChild(itemIdx, false);

        while (leftChildIdx < size()) {
            int smallestChildIdx = leftChildIdx;
            if (rightChildIdx < size() && comparator.compare(heap.get(rightChildIdx), heap.get(leftChildIdx)) < 0) {
                smallestChildIdx = rightChildIdx;
            }

            if (comparator.compare(heap.get(smallestChildIdx), heap.get(itemIdx)) < 0) {
                heap.swap(smallestChildIdx, itemIdx);
                itemIdx = smallestChildIdx;
                leftChildIdx = getChild(itemIdx, true);
                rightChildIdx = getChild(itemIdx, false);
            } else {
                break;
            }
        }

        return root;
    }

    @Override
    public V peek() throws EmptyCollectionException {
        return heap.get(0);
    }

    @Override
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    @Override
    public int size() {
        return heap.size();
    }

    @Override
    public String graphViz(String name) {
        String result = "digraph " + name + " {\n";

        for (int i = 0; i < heap.size(); i++) {
            result += "  " + i + " [label=\"" + heap.get(i) + "\"];\n";
            if (i != 0) {
                int parent = getParent(i);
                result += "  " + parent + " -> " + i + ";\n";
            }
        }
        
        result += "}";
        return result;
    }
}
