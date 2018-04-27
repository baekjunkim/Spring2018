package edu.uiowa.cs.similarity;

// Interface for classess to define vector form of data structure
public interface Vector<K, V> {

    public void add(K key, V value);

    public boolean contains(K key);

    public V get(K key);

    public void remove(K key);

    public void replace(K key, V newValue);
}
