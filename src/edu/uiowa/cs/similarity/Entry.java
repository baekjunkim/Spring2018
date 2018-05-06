package edu.uiowa.cs.similarity;

public class Entry implements Comparable<Entry> {

    public final String key;
    public final double value;

    public Entry(String key, double value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public int compareTo(Entry other) {
        if (this.value > other.value) return 1;
        else if (this.value < other.value) return -1;
        else return 0;
    }

    @Override
    public String toString() {
        return "{" + key + "," + value + "}";
    }
}
