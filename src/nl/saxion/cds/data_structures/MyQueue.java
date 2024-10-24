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
        StringBuilder sb = new StringBuilder();
        sb.append("digraph ").append(name).append(" {\n");
        sb.append("    rankdir=LR;\n");
        int index = 0;
        for (T item : queue) {
            sb.append("    node").append(index).append(" [label=\"").append(item).append("\"];\n");
            if (index > 0) {
                sb.append("    node").append(index - 1).append(" -> node").append(index).append(";\n");
            }
            index++;
        }
        sb.append("}\n");
        return sb.toString();
    }

    //Had to add this method to make breadthFirstSearch work
    public boolean contains(T v) {
        return queue.contains(v);
    }
}
