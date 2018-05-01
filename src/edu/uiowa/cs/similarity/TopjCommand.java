package edu.uiowa.cs.similarity;

import java.util.*;

public class TopjCommand {

    String measure;
    String Q;
    Integer J;

    public TopjCommand(String measure) {
        this.measure = measure;
        this.Q = "";
        this.J = 0;
    }
    
    public TopjCommand(String measure, String Q, Integer J) {
        this.measure = measure;
        this.Q = Q;
        this.J = J;
    }

    public boolean Qcheck(HashSet<String> words) {
        return words.contains(Q);
    }

    public void topj(HashSet<String> words, HashMap<String, HashMap<String, Double>> vectors, ArrayList<String> topj) {
        topj.clear();
        PriorityQueue<Entry> pairs = new PriorityQueue<>((a, b) -> b.compareTo(a));
        SimM Measure = new SimM(measure);
        for (String word : words) {
            if (!Q.equals(word)) {
                pairs.add(new Entry(word, Measure.similarity(vectors.get(Q), vectors.get(word))));
            }
        }
        for (int i = 0; i < J; i++) {
            topj.add("Pair" + pairs.poll().toString());
        }
    }

}
