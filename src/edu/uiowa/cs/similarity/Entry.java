package edu.uiowa.cs.similarity;

public class Entry implements Comparable<Entry> {

    private final String key;
    private final double value;

    public Entry(String key, double value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public int compareTo(Entry other) {
        return (int) ((this.value - other.value) *1000);
    }

    @Override
    public String toString() {
        return "{" + key + "," + value + "}";
    }
}