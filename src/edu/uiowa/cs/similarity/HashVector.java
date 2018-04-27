package edu.uiowa.cs.similarity;

import java.util.HashMap;

public class HashVector<String, Double> implements Vector<String, Double> {

    HashMap<String, Double> vector;

    @Override
    public void add(String key, Double value) {
        vector.put(key, value);
    }

    @Override
    public boolean contains(String key) {
        return vector.containsKey(key);
    }

    @Override
    public Double get(String key) {
        return vector.get(key);
    }

    @Override
    public void remove(String key) {
        vector.remove(key);
    }

    @Override
    public void replace(String key, Double newValue) {
        vector.replace(key, newValue);
    }

}
