package edu.uiowa.cs.similarity;

import java.util.*;

public class Kmeans {

    Integer K;
    Integer iters;

    public Kmeans(Integer K, Integer iters) {
        this.K = K;
        this.iters = iters;
    }

    public void k_means(ArrayList<HashSet<String>> cluster, HashSet<String> words, HashMap<String, HashMap<String, Double>> vectors) {
        cluster.clear();
        String[] centroid = new String[K];
        for (int i = 0, j = 0; i < K; i++) {
            int random = new Random().nextInt(words.size());
            for (String word : words) {
                if (random == j) {
                    centroid[i] = word;
                    break;
                } else {
                    j++;
                }
            }
        }
        ArrayList<PriorityQueue<Entry>> pairs = new ArrayList<>();
        SimM Measure = new SimM("euc");
        for (int i = 0; i < iters; i++) {
            pairs.clear();
            for (String word : words) {
                int clus = 0;
                double clu = Double.MIN_VALUE;
                for (int j = 0; j < K; j++) {
                    double compare = Measure.similarity(vectors.get(centroid[j]), vectors.get(word));
                    if (clu < compare) {
                        clu = compare;
                        clus = j;
                    }
                }
                // clusters from instruction
                pairs.get(clus).add(new Entry(word, clu));
                
            }
            // centroid re-setting
            for (int k = 0; i < K; i++) {
                centroid[i] = pairs.get(k).get(pairs.get(k).size() / 2);
            }
        }
    }

}
