package nl.saxion.cds.data_structures;

import java.util.Comparator;

public class MyPriorityQueue<T> extends MyQueue<PriorityNode<T>> {
    private final Comparator<PriorityNode<T>> comparator;

    public MyPriorityQueue() {
        super();
        this.comparator = Comparator.comparingDouble(PriorityNode::getPriority);
    }

    public void enqueue(T value, double priority) {
        PriorityNode<T> newNode = new PriorityNode<>(value, priority);
        if (isEmpty()) {
            super.enqueue(newNode);
        } else {
            DoublyLinkedList<PriorityNode<T>> temp = new DoublyLinkedList<>();
            while (!isEmpty() && comparator.compare(newNode, peek()) < 0) {
                temp.addLast(super.dequeue());
            }

            super.enqueue(newNode);

            while (!temp.isEmpty()) {
                super.enqueue(temp.removeFirst());
            }
        }
    }

    @Override
    public PriorityNode<T> peek() {
        return super.peek();
    }

    @Override
    public PriorityNode<T> dequeue() {
        return super.dequeue();
    }
}

class PriorityNode<T> {
    private final T value;
    private final double priority;

    public PriorityNode(T value, double priority) {
        this.value = value;
        this.priority = priority;
    }

    public T getValue() {
        return value;
    }

    public double getPriority() {
        return priority;
    }
}
