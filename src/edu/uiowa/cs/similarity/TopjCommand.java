package edu.uiowa.cs.similarity;

import java.util.*;

public class TopjCommand {

    public boolean Qcheck(HashSet<String> words, String Q) {
        return words.contains(Q);
    }

    public double cos(HashMap<String, Double> Qvalue, HashMap<String, Double> Jvalue) {
        Set<String> keys;
        if (Qvalue.size() > Jvalue.size()) {
            keys = Jvalue.keySet();
        } else {
            keys = Qvalue.keySet();
        }
        double den = 0;
        for (String key : keys) {
            if (Qvalue.containsKey(key) && Jvalue.containsKey(key)) {
                den += Qvalue.get(key) * Jvalue.get(key);
            }
        }
        double numQ = 0;
        for (Double q : Qvalue.values()) {
            numQ += Math.pow(q, 2);
        }
        double numJ = 0;
        for (Double j : Jvalue.values()) {
            numJ += Math.pow(j, 2);
        }
        return den / Math.pow(numQ * numJ, 0.5);
    }

    public void topj(HashSet<String> words, HashMap<String, HashMap<String, Double>> vectors, ArrayList<String> topj, String Q, Integer J) {
        topj.clear();
        PriorityQueue<Entry> pairs = new PriorityQueue<>((a, b) -> b.compareTo(a));
        for (String word : words) {
            if (!Q.equals(word)) {
                pairs.add(new Entry(word, cos(vectors.get(Q), vectors.get(word))));
            }
        }
        for (int i = 0; i < J; i++) {
            topj.add("Pair" + pairs.poll().toString());
        }
    }

}
