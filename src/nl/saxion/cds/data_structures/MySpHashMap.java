package nl.saxion.cds.data_structures;

import nl.saxion.cds.collection.DuplicateKeyException;
import nl.saxion.cds.collection.KeyNotFoundException;
import nl.saxion.cds.collection.SaxHashMap;
import nl.saxion.cds.collection.SaxList;

public class MySpHashMap<K, V> implements SaxHashMap<K, V> {
    private int size;
    private final int MAX_LOAD_FACTOR = 70;

    private MyArrayList<Entry<K, V>>[] table;
    private MyArrayList<K> keys;

    public MySpHashMap(int length) {
        table = new MyArrayList[length];
        for (int i = 0; i < table.length; i++) {
            table[i] = new MyArrayList<>();
        }

        keys = new MyArrayList<>();
    }

    public MySpHashMap() {
        this(10);
    }

    public int getLoadFactor() {
        return (100 * size) / table.length;
    }

    @Override
    public boolean contains(K key) {
        return keys.contains(key);
    }

    //TODO: Explain in docs why exception is thrown
    @Override
    public V get(K key) throws KeyNotFoundException {
        int index = Math.abs(key.hashCode() % table.length);
        MyArrayList<Entry<K, V>> list = table[index];

        for (Entry<K, V> entry : list) {
            if (entry.getKey().equals(key)) {
                return entry.getValue();
            }
        }

        throw new KeyNotFoundException("Key not found: " + key);
    }

    @Override
    public void add(K key, V value) throws DuplicateKeyException {
        if (contains(key)) {
            throw new DuplicateKeyException("Key already exists: " + key);
        }

        if (getLoadFactor() >= MAX_LOAD_FACTOR) {
            rehash();
            add(key, value);
        } else {
            int index = Math.abs(key.hashCode() % table.length);
            table[index].addLast(new Entry<>(key, value));
            size++;
            keys.addLast(key);
        }

    }

    private void rehash() {
        System.out.printf("Rehashing: %d -> %d\n", table.length, table.length * 2);

        MyArrayList<Entry<K, V>>[] oldTable = table;

        table = new MyArrayList[oldTable.length * 2];
        for (int i = 0; i < table.length; i++) {
            table[i] = new MyArrayList<>();
        }

        for (MyArrayList<Entry<K, V>> list : oldTable) {
            for (Entry<K, V> entry : list) {
                int index = Math.abs(entry.getKey().hashCode() % table.length);
                table[index].addLast(entry);
            }
        }

        size = keys.size();
    }


    @Override
    public V remove(K key) throws KeyNotFoundException {
        V value = get(key);

        int index = Math.abs(key.hashCode() % table.length);
        table[index].remove(new Entry<>(key, value));
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
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public String graphViz(String name) {
        return "";
    }
}
