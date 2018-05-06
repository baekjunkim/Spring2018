package edu.uiowa.cs.similarity;

import java.util.*;

public class Kmeans {

    Integer K;
    Integer iters;

    public Kmeans(Integer K, Integer iters) {
        this.K = K;
        this.iters = iters;
    }

    public String rand_init(HashSet<String> words) {
        int random_assign = new Random().nextInt(words.size());
        int random = 0;
        String random_word = "";
        for (String word : words) {
            if (random_assign == random) {
                random_word = word;
                break;
            } else {
                random++;
            }
        }
        return random_word;
    }

    public void k_means(
            ArrayList<HashMap<String, Double>> centroid, 
            ArrayList<HashSet<String>> cluster, 
            HashSet<String> words, 
            HashMap<String, HashMap<String, Double>> vectors) {
        ArrayList<HashSet<String>> pairs = new ArrayList<>();
        cluster.clear();
        // generate k random points
        for (int k = 0; k < K; k++) {
            boolean check = true;
            while (check) {
                check = false;
                String str = rand_init(words);
                for (HashMap<String, Double> map : centroid) {
                    if (vectors.get(str).equals(map)) {
                        check = true;
                    }
                }
                centroid.add(vectors.get(str));
            }
        }
        SimM Measure = new SimM("euc");
        // for i in 0 to iters
        double prev_avg = Double.MAX_VALUE;
        for (int i = 0; i < iters; i++) {
            pairs.clear();
            // clusters = [[], [], [], ..., []] // k of these
            for (int k = 0; k < K; k++) {
                pairs.add(new HashSet<>());
            }
            // for p in points
            // find m in means s.t. euclidean_distance(m, p) is minimized
            double total_distance = 0;
            for (String word : words) {
                int index = 0;
                double distance = -Double.MAX_VALUE;
                for (int k = 0; k < K; k++) {
                    double compare = Measure.similarity(centroid.get(k), vectors.get(word));
                    if (distance < compare) {
                        distance = compare;
                        index = k;
                    }
                }
                // add p to the cluster for m
                pairs.get(index).add(word);
                total_distance += Math.pow(distance, 2);
            }
            double cur_avg = total_distance / words.size();
            // for i in 0 to k
            for (int k = 0; k < K; k++) {
                HashMap<String, Double> center = new HashMap<>();
                for (String s1 : pairs.get(k)) {
                    for (String s2 : vectors.get(s1).keySet()) {
                        if (center.containsKey(s2)) {
                            Double d = center.get(s2);
                            center.replace(s2, d + 1.0);
                        } else {
                            center.put(s2, 1.0);
                        }
                    }
                }
                for (String s : center.keySet()) {
                    Double d = center.get(s);
                    center.replace(s, d / pairs.get(k).size());
                }
                // means[i] = centroid o the points in clusters[i]
                centroid.set(k, center);
            }
            if (Math.abs(prev_avg - cur_avg) < 0.00001) {
                System.out.println("Clustering is converged at iteration " + i);
                break;
            } else {
                System.out.println("Iteration: " + (i+1) + " / Avg. Distance: " + cur_avg);
                prev_avg = cur_avg;
            }
        }
        for (int k = 0; k < K; k++) {
            cluster.add(new HashSet<>());
            for (String s : pairs.get(k)) {
                cluster.get(k).add(s);
            }
        }
    }
}
