package nl.saxion.cds.data_structures;

import nl.saxion.cds.collection.EmptyCollectionException;
import nl.saxion.cds.collection.SaxList;
import nl.saxion.cds.collection.ValueNotFoundException;

import java.awt.print.Printable;
import java.util.Iterator;

public class DoublyLinkedList<T> implements SaxList<T>, Iterable<T> {
    private Node<T> head, tail;
    private int size;

    public DoublyLinkedList() {
        this.tail = null;
        this.head = null;
        this.size = 0;
    }
    /**
     * Check if a given value is in the list
     * Uses V.equals() to check for equality.
     *
     * @param value the value to search for
     * @return if the value is in the collection
     */
    @Override
    public boolean contains(T value) throws EmptyCollectionException {
        if(isEmpty()) throw new EmptyCollectionException();

        Node<T> current = head;
        while(current.getNext() != null){
            if(current.getValue().equals(value)){
                return true;
            }

            current = current.getNext();
        }

        return false;
    }

    /**
     * Get the value in the list at the specific index.
     *
     * @param index the index of the element to retrieve
     * @return value at the given index
     * @throws IndexOutOfBoundsException invalid index
     */
    @Override
    public T get(int index) throws IndexOutOfBoundsException {
        if(index > size || index < 0){
            throw new IndexOutOfBoundsException("Invalid index");
        }

        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }

        return current.getValue();
    }

    /**
     * Add the given value at the end of the list
     *
     * @param value the value to add
     */
    @Override
    public void addLast(T value) {
        Node<T> newNode = new Node<>(value);

        if(isEmpty()){
            head = newNode;
            tail = head;
        } else if (size == 1){
            // we only have head, so we need to set the tail
            tail = newNode;
            head.setNext(tail);
            tail.setPrevious(head);
        } else {
            //1. take the current tail and set its next to the new node
            //2. take the new node and set its previous to the current tail
            //3. set the new node as the new tail
            tail.setNext(newNode);
            newNode.setPrevious(tail);

            tail = newNode;
        }

        size++;
    }

    /**
     * Add the given value at the beginning of the list
     *
     * @param value the value to add
     */
    @Override
    public void addFirst(T value) {
        Node<T> newNode = new Node<>(value);

        if(isEmpty()){
            head = newNode;
            tail = head;
        } else if(size == 1){
            // 1. Take the current head and set its previous to the new node
            // 2. Take the new node and set its next to the current head
            // 3. Set the new node as the new tail
            // 4. Set the new node as the new head
            head.setPrevious(newNode);
            newNode.setNext(head);

            head = newNode;
        } else {
            head.setPrevious(newNode);
            newNode.setNext(head);

            head = newNode;
        }


        size++;
    }

    /**
     * Add (insert) the given value at the "index"th position in the list
     * Throws an IndexOutOfBoundsException if the index < 0 or >= size
     *
     * @param index index where the value is to be added
     * @param value the value to add
     * @throws IndexOutOfBoundsException invalid index
     */
    @Override
    public void addAt(int index, T value) throws IndexOutOfBoundsException {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Invalid index");
        }

        if(index == 0) {
            addFirst(value);
        } else if(index == size) {
            addLast(value);
        } else {
            Node<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.getNext();
            }
            // 1. take the previous of the current node and set its next to the new node
            // 2. take the new node and set its previous to the previous of the current node
            Node<T> newNode = new Node<>(value);
            Node<T> currentPrev = current.getPrevious();

            currentPrev.setNext(newNode);
            newNode.setPrevious(currentPrev);
            newNode.setNext(current);
            current.setPrevious(newNode);

            size++;
        }
    }

    /**
     * Sets the given value at the "index"th position in the list
     * Throws an IndexOutOfBoundsException if the index < 0 or >= size
     *
     * @param index index where the value is to be set
     * @param value the value to set
     * @throws IndexOutOfBoundsException invalid index
     */
    @Override
    public void set(int index, T value) throws IndexOutOfBoundsException {
        if(index > size || index < 0){
            throw new IndexOutOfBoundsException("Invalid index");
        }

        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }

        //Easy way
        current.setValue(value);
    }

    /**
     * Removes the last element of the list.
     * Throws an EmptyCollectionException if the list is empty
     *
     * @return the removed value
     * @throws EmptyCollectionException nothing to remove
     */
    @Override
    public T removeLast() throws EmptyCollectionException {
        if(isEmpty()) throw new EmptyCollectionException();

        // If there is only one Node in list, remove it and set head and tail to null;
        if(size == 1){
            T value = head.getValue();
            head = null;
            tail = null;
            size--;
            return value;
        }
        T value = tail.getValue();
        tail = tail.getPrevious();
        tail.setNext(null);
        size--;

        // make sure that if after the removal the size is 1, the head and tail are the same
        if(size == 1){
            head = tail;
        }

        return value;
    }

    /**
     * Removes the first element of the list.
     * Throws an EmptyCollectionException if the list is empty
     *
     * @return the removed value
     * @throws EmptyCollectionException nothing to remove
     */
    @Override
    public T removeFirst() throws EmptyCollectionException {
        if(isEmpty()) throw new EmptyCollectionException();

        if (size == 1) {
            T value = head.getValue();
            head = null;
            tail = null;
            size--;

            return value;
        }

        T value = head.getValue();
        head = head.getNext();
        head.setPrevious(null);

        size--;

        // make sure that if after the removal the size is 1, the head and tail are the same
        if(size == 1){
            tail = head;
        }

        return value;
    }

    /**
     * Removes the element at position index of the list.
     * Throws an EmptyCollectionException if the list is empty
     *
     * @param index index where the value is to be removed
     * @return the removed value
     * @throws IndexOutOfBoundsException invalid index
     */
    @Override
    public T removeAt(int index) throws IndexOutOfBoundsException {
        if(index >= size || index < 0){
            throw new IndexOutOfBoundsException("Invalid index");
        }

        if(index == 0){
            return removeFirst();
        } else if (index == size - 1){
            return removeLast();
        } else {
            Node<T> current = head;

            for (int i = 0; i < index; i++) {
                current = current.getNext();
            }

            Node<T> currentPrev = current.getPrevious();
            Node<T> currentNext = current.getNext();
            currentPrev.setNext(currentNext);
            currentNext.setPrevious(currentPrev);
            T value = current.getValue();
            size--;

            return value;
        }
    }

    /**
     * Removes the element on the "index"th position in the list.
     * Throws an ValueNotFoundException if the value is not in the list
     * @param value value to remove
     * @throws ValueNotFoundException value not found
     */
    @Override
    public void remove(T value) throws ValueNotFoundException {
        if (isEmpty()) {
            throw new ValueNotFoundException("Value not found in the list");
        }

        Node<T> current = head;

        int currentIndex = 0;
        while (current != null) {
            if (current.getValue().equals(value)) {
                if (current.equals(head)) {
                    removeFirst();
                } else if (current.equals(tail)) {
                    removeLast();
                } else {
                    removeAt(currentIndex);
                }
                return;
            }

            current = current.getNext();
            currentIndex++;
        }

        throw new ValueNotFoundException("Value not found in the list");
    }

    /**
     * Removes all elements with the given value from the list.
     * Throws an ValueNotFoundException if the value is not in the list
     * @param value value to remove
     * @throws ValueNotFoundException value not found
     */
    public void removeAll(T value) throws ValueNotFoundException {
        if (isEmpty()) {
            throw new ValueNotFoundException("Value not found in the list");
        }

        Node<T> current = head;

        int currentIndex = 0;
        while (current != null) {
            if (current.getValue().equals(value)) {
                if (current.equals(head)) {
                    removeFirst();
                } else if (current.equals(tail)) {
                    removeLast();
                } else {
                    removeAt(currentIndex);
                }
            }

            current = current.getNext();
            currentIndex++;
        }
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<T> iterator() {
        return new DlIterator();
    }

    private class DlIterator implements Iterator<T> {
        private Node<T> current = head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            T value = current.getValue();
            current = current.getNext();
            return value;
        }

    }

    /**
     * Determines if the collection has no elements
     *
     * @return if the collection has no elements
     */
    @Override
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Determines the number of elements in this collection
     *
     * @return size of this collection
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Create a String representation of the data in GraphViz (see <a href="https://graphviz.org">GraphViz</a>)
     * format, which you can print-copy-paste on the site see <a href="https://dreampuf.github.io/GraphvizOnline">GraphViz online</a>.
     *
     * @param name name of the produced graph
     * @return a GraphViz string representation of this collection
     */
    @Override
    public String graphViz(String name) {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph \"").append(name).append("\" {\n");
        sb.append("    rankdir=LR;\n");
        sb.append("    node [shape=record];\n");

        Node<T> current = head;
        int index = 0;
        while (current != null) {
            sb.append("    n").append(index).append(" [label=\"{ <ref1> | <data> ")
                    .append(current.getValue().toString()).append(" | <ref2> }\"];\n");

            current = current.getNext();
            index++;
        }

        for (int i = 0; i < index - 1; i++) {
            sb.append("    n").append(i).append(":ref2:c -> n").append(i + 1).append(":data:n [arrowhead=vee, arrowtail=dot, dir=both, tailclip=false];\n");
            sb.append("    n").append(i + 1).append(":ref1:c -> n").append(i).append(":data:s [arrowhead=vee, arrowtail=dot, dir=both, tailclip=false];\n");
        }

        sb.append("}");

        return sb.toString();
    }
}
