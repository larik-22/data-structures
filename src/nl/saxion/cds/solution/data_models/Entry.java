package nl.saxion.cds.solution.data_models;

public class Entry<Key, Val> {
    private Key key;
    private Val value;

    public Entry(Key key, Val value) {
        this.key = key;
        this.value = value;
    }

    public Key getKey() {
        return key;
    }

    public Val getValue() {
        return value;
    }

    // TODO: TEST
    public void setValue(Val value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entry<?, ?> entry = (Entry<?, ?>) o;
        return key.equals(entry.key);
    }
}