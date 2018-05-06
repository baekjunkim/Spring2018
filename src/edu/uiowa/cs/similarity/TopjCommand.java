package edu.uiowa.cs.similarity;

import java.util.*;

public class TopjCommand {

    String measure;
    String Q;
    Integer J;
    HashMap<String, Double> center = null;

    public TopjCommand(String measure) {
        this.measure = measure;
        this.Q = "";
    }

    public TopjCommand(String measure, String Q) {
        this.measure = measure;
        this.Q = Q;
    }
    
    public TopjCommand(String measure, HashMap<String, Double> center) {
        this.measure = measure;
        this.center = center;
    }

    public boolean Qcheck(HashSet<String> words) {
        return words.contains(Q);
    }

    public PriorityQueue<Entry> topj(
            HashSet<String> words,
            HashMap<String, HashMap<String, Double>> vectors) {
        PriorityQueue<Entry> pairs = new PriorityQueue<>((a, b) -> b.compareTo(a));
        SimM Measure = new SimM(measure);
        for (String word : words) {
            if (center != null) {
                pairs.add(new Entry(word, Measure.similarity(center, vectors.get(word))));
            } else {
                if (!Q.equals(word)) {
                    pairs.add(new Entry(word, Measure.similarity(vectors.get(Q), vectors.get(word))));
                }
            }
        }
        return pairs;
    }

}
