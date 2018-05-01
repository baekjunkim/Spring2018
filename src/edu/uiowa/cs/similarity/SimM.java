package edu.uiowa.cs.similarity;

import java.util.*;

public class SimM {

    String measure;

    public SimM(String measure) {
        this.measure = measure;
    }

    public double similarity(HashMap<String, Double> Qvalue, HashMap<String, Double> Jvalue) {
        if (measure.equals("cos")) {
            return cos(Qvalue, Jvalue);
        } else if (measure.equals("euc")) {
            return euc(Qvalue, Jvalue);
        } else if (measure.equals("eucnorm")) {
            return eucnorm(Qvalue, Jvalue);
        } else {
            return 0;
        }
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
        return -Math.sqrt(num);
    }

    public double eucnorm(HashMap<String, Double> Qvalue, HashMap<String, Double> Jvalue) {
        double numQ = 0;
        for (Double q : Qvalue.values()) {
            numQ += Math.pow(q, 2);
        }
        numQ = Math.sqrt(numQ);
        double numJ = 0;
        for (Double j : Jvalue.values()) {
            numJ += Math.pow(j, 2);
        }
        numJ = Math.sqrt(numJ);
        double den = 0;
        HashSet<String> duplicate = new HashSet<>();
        duplicate.addAll(Qvalue.keySet());
        duplicate.addAll(Jvalue.keySet());
        for (String word : duplicate) {
            if (Qvalue.containsKey(word) && Jvalue.containsKey(word)) {
                den += Math.pow(((Qvalue.get(word) / numQ) - (Jvalue.get(word) / numJ)), 2);
            } else if (Qvalue.containsKey(word) && !Jvalue.containsKey(word)) {
                den += Math.pow((Qvalue.get(word) / numQ), 2);
            } else {
                den += Math.pow((Jvalue.get(word) / numJ), 2);
            }
        }
        return -Math.sqrt(den);
    }

}
