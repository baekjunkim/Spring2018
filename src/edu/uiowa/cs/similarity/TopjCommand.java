package edu.uiowa.cs.similarity;

import java.util.*;

public class TopjCommand {

    String measure;
    String Q;
    Integer J;

    public TopjCommand(String measure, String Q, Integer J) {
        this.measure = measure;
        this.Q = Q;
        this.J = J;
    }

    public boolean Qcheck(HashSet<String> words) {
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

    public double euc(HashMap<String, Double> Qvalue, HashMap<String, Double> Jvalue) {
        Set<String> keysJ = Jvalue.keySet();
        HashMap<String, Double> cal = Qvalue;
        for (String keyJ : keysJ) {
            if (!cal.containsKey(keyJ)) {
                cal.put(keyJ, Jvalue.get(keyJ));
            } else {
                Double temp = cal.get(keyJ);
                cal.replace(keyJ, temp - Jvalue.get(keyJ));
            }
        }
        double num = 0;
        for (Double d : cal.values()) {
            num += Math.pow(d, 2);
        }
        return -num;
    }

    public double eucnorm(HashMap<String, Double> Qvalue, HashMap<String, Double> Jvalue) {
        double numQ = 0;
        for (Double q : Qvalue.values()) {
            numQ += Math.pow(q, 2);
        }
        double numJ = 0;
        for (Double j : Jvalue.values()) {
            numJ += Math.pow(j, 2);
        }
        double den = 0;
        HashSet<String> duplicate = new HashSet<>();
        duplicate.addAll(Qvalue.keySet());
        duplicate.addAll(Jvalue.keySet());
        for (String word : duplicate) {
            if (Qvalue.containsKey(word) && Jvalue.containsKey(word)) {
                den += (Qvalue.get(word) / numQ) - (Jvalue.get(word) / numJ);
            } else if (Qvalue.containsKey(word) && !Jvalue.containsKey(word)) {
                den += Qvalue.get(word) / numQ;
            } else {
                den += Jvalue.get(word) / numJ;
            }
        }
        return -den;
    }

    public double similarity(HashMap<String, Double> Qvalue, HashMap<String, Double> Jvalue) {
        if (measure.equals("cos")) {
            return cos(Qvalue, Jvalue);
        } else if (measure.equals("euc")) {
            return euc(Qvalue, Jvalue);
        } else {
            return eucnorm(Qvalue, Jvalue);
        }
    }

    public void topj(HashSet<String> words, HashMap<String, HashMap<String, Double>> vectors, ArrayList<String> topj) {
        topj.clear();
        PriorityQueue<Entry> pairs = new PriorityQueue<>((a, b) -> b.compareTo(a));
        for (String word : words) {
            if (!Q.equals(word)) {
                pairs.add(new Entry(word, similarity(vectors.get(Q), vectors.get(word))));
            }
        }
        for (int i = 0; i < J; i++) {
            topj.add("Pair" + pairs.poll().toString());
        }
    }

}
