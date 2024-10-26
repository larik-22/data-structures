package nl.saxion.cds.solution.data_structures;

import java.util.Objects;

//make accept only printable objects, those that have a toString method implemented
public class MyDllNode<T> {
    private T value;
    private MyDllNode<T> next, previous;

    public MyDllNode(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public MyDllNode<T> getNext() {
        return next;
    }

    public void setNext(MyDllNode<T> next) {
        this.next = next;
    }

    public MyDllNode<T> getPrevious() {
        return previous;
    }

    public void setPrevious(MyDllNode<T> previous) {
        this.previous = previous;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyDllNode<?> node = (MyDllNode<?>) o;
        return Objects.equals(value, node.value);
    }
}
