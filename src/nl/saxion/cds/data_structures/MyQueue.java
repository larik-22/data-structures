package nl.saxion.cds.data_structures;

import nl.saxion.cds.collection.EmptyCollectionException;
import nl.saxion.cds.collection.SaxQueue;

public class MyQueue<T> implements SaxQueue<T> {
    private final DoublyLinkedList<T> queue;

    public MyQueue() {
        queue = new DoublyLinkedList<>();
    }

    @Override
    public void enqueue(T value) {
        queue.addLast(value);
    }

    @Override
    public T dequeue() throws EmptyCollectionException {
        return queue.removeFirst();
    }

    @Override
    public T peek() throws EmptyCollectionException {
        if (queue.isEmpty()) throw new EmptyCollectionException();
        return queue.get(0);
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    @Override
    public int size() {
        return queue.size();
    }

    @Override
    public String graphViz(String name) {
        return "";
    }
    //TODO: FIX UGLY
    public boolean contains(T v) {
        return queue.contains(v);
    }
}
