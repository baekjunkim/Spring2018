    package edu.uiowa.cs.similarity;

import java.util.*;

public class VectorsCommand {

    public void vectors(ArrayList<ArrayList<String>> sentences, HashMap<String, HashMap<String, Double>> vectors) {
        for (ArrayList<String> sentence : sentences) {
            HashSet<String> uniqueWord = new HashSet<>();
            if (sentence.size() > 1) {
                for (String word : sentence) {
                    uniqueWord.add(word);
                }
            }
            for (String key : uniqueWord) {
                if (!vectors.containsKey(key)) {
                    vectors.put(key, new HashMap<>());
                }
                for (String keySub : uniqueWord) {
                    if (!key.equals(keySub)) {
                        if (vectors.get(key).containsKey(keySub)) {
                            Double valueDouble = vectors.get(key).get(keySub);
                            vectors.get(key).replace(keySub, valueDouble + 1.0);
                        } else {
                            vectors.get(key).put(keySub, 1.0);
                        }
                    }
                }
            }
        }
    }
}
