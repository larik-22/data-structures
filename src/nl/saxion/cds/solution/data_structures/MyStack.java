package nl.saxion.cds.solution.data_structures;

import nl.saxion.cds.collection.EmptyCollectionException;
import nl.saxion.cds.collection.SaxStack;

public class MyStack<T> implements SaxStack<T> {
    private final DoublyLinkedList<T> stack;

    public MyStack() {
        stack = new DoublyLinkedList<>();
    }

    @Override
    public void push(T value) {
        stack.addFirst(value);
    }

    @Override
    public T pop() throws EmptyCollectionException {
        if(stack.isEmpty()) throw new EmptyCollectionException();
        return stack.removeFirst();
    }

    @Override
    public T peek() throws EmptyCollectionException {
        if(stack.isEmpty()) throw new EmptyCollectionException();
        return stack.get(0);
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public int size() {
        return stack.size();
    }

    @Override
    public String graphViz(String name) {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph ").append(name).append(" {\n");
        sb.append("    rankdir=LR;\n");
        sb.append("    node [shape=record];\n");

        int index = 0;
        for (T value : stack) {
            sb.append("    node").append(index).append(" [label=\"{").append(value).append("}\"];\n");
            if (index > 0) {
                sb.append("    node").append(index - 1).append(" -> node").append(index).append(";\n");
            }
            index++;
        }

        sb.append("}\n");
        return sb.toString();
    }
}
