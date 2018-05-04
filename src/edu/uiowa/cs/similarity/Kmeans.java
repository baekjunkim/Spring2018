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
        ArrayList<PriorityQueue<Entry>> pairs = new ArrayList<>();
        cluster.clear();
        // generate k random points
        String[] centroid = new String[K];
        for (int k = 0; k < K; k++) {
            int random_assign = new Random().nextInt(words.size());
            int random = 0;
            for (String word : words) {
                if (random_assign == random) {
                    centroid[k] = word;
                    break;
                } else {
                    random++;
                }
            }
        }
        SimM Measure = new SimM("euc");
        // for i in 0 to iters
        for (int i = 0; i < iters; i++) {
            System.out.println("--------------------------------------------------"); /////////
//            System.out.println(i);
            pairs.clear();
            // clusters = [[], [], [], ..., []] // k of these
            for (int k = 0; k < K; k++) {
                pairs.add(new PriorityQueue<>());
            }
            // for p in points
            // find m in means s.t. euclidean_distance(m, p) is minimized
            for (String word : words) {
                int index = 0;
                double distance = -Double.MAX_VALUE;
                for (int k = 0; k < K; k++) {
                    double compare = Measure.similarity(vectors.get(centroid[k]), vectors.get(word));
                    if (distance < compare) {
                        distance = compare;
                        index = k;
                    }
                }
                pairs.get(index).add(new Entry(word, distance));
            }
            // to find average distance
            //// from here
            double total_dis = 0;
            for (int k = 0; k < K; k++) {
                for (Entry e : pairs.get(k)) {
                    total_dis += e.value;
                    System.out.println(e.value);
                }
            }
            System.out.println(total_dis / words.size());
            //// to here
            for (int k = 0; k < K; k++) {
                PriorityQueue<Entry> center = new PriorityQueue<>((a, b) -> b.compareTo(a));
                for (Entry e1 : pairs.get(k)) {
                    double distance_sum = 0;
                    for (Entry e2 : pairs.get(k)) {
                        distance_sum += Measure.similarity(vectors.get(e1.key), vectors.get(e2.key));
                    }
                    center.add(new Entry(e1.key, distance_sum));
                    centroid[k] = center.poll().key;
                }
            }
        }
        for (int k = 0; k < K; k++) {
            cluster.add(new HashSet<>());
            int s = pairs.get(k).size();
            for (int j = 0; j < s; j++) {
                cluster.get(k).add(pairs.get(k).poll().key);
            }
        }
    }
}
