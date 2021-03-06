package edu.uiowa.cs.similarity;

import java.util.*;

public class SimM {

    String measure;

    public SimM(String measure) {
        this.measure = measure;
    }

    public double similarity(HashMap<String, Double> Qvalue, HashMap<String, Double> Jvalue) {
        if (measure.equals("cosine")) {
            return cosine(Qvalue, Jvalue);
        } else if (measure.equals("euc")) {
            return euc(Qvalue, Jvalue);
        } else if (measure.equals("eucnorm")) {
            return eucnorm(Qvalue, Jvalue);
        } else {
            return 0;
        }
    }

    public double cosine(HashMap<String, Double> Qvalue, HashMap<String, Double> Jvalue) {
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
        HashMap<String, Double> cal = new HashMap<>(Qvalue);
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
        Set<String> keysJ = Jvalue.keySet();
        HashMap<String, Double> cal = new HashMap<>(Qvalue);
        for (String keyQ : cal.keySet()) {
            cal.replace(keyQ, cal.get(keyQ) / numQ);
        }
        for (String keyJ : keysJ) {
            if (!cal.containsKey(keyJ)) {
                cal.put(keyJ, Jvalue.get(keyJ) / numJ);
            } else {
                Double temp = cal.get(keyJ);
                cal.replace(keyJ, temp - (Jvalue.get(keyJ) / numJ));
            }
        }
        double den = 0;
        for (Double d : cal.values()) {
            den += Math.pow(d, 2);
        }
        return -Math.sqrt(den);
    }
}
