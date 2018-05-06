package edu.uiowa.cs.similarity;

import java.io.*;
import java.util.*;

public class Main {

    private static void printMenu() {
        System.out.println("Supported commands:");
        System.out.println("help - Print the supported commands");
        System.out.println("index FILE - Read in and index the file given by FILE(String)");
        System.out.println("sentences - Print currently indexed sentences");
        System.out.println("vectors - Print semantic descriptor vector for each unique word");
        System.out.println("topj Q J - Print the J(Integer) most simliar words to Q(String)");
        System.out.println("measure SIM - Change similarity measure for topj as SIM (choose one from \"cosine\", \"euc\", and \"eucnorm\")");
        System.out.println("kmeans K ITERS - Run and print K(Integer)-mean clustering for ITERS(Integer) number of iterations");
        System.out.println("representatives J - Print the J(Integer) number of representative(s) from each cluster");
        System.out.println("quit - Quit this program");
    }
//index C:\Users\Baekjun Kim\Desktop\U of Iowa\2017 - 2018 academic year\2018 Spring (13sh - 97 total)\CS 2230 Computer Science 2 - Data Structures (Brandon Myers)\Assignment\Project\easy_sanity_test.txt

    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        HashSet<String> words = new HashSet<>();
        ArrayList<ArrayList<String>> sentences = new ArrayList<>();
        HashMap<String, HashMap<String, Double>> vectors = new HashMap<>();
        ArrayList<String> topj = new ArrayList<>();
        String similarityMeasure = "cosine";
        ArrayList<HashMap<String, Double>> centroid = null;
        ArrayList<HashSet<String>> cluster = new ArrayList<>();

        while (true) {
            System.out.print("> ");
            String command = input.readLine();
            if (command.equals("help") || command.equals("h")) {
                printMenu();
            } else if (command.contains("index ")) {
                String filePath = command.substring(6);
                System.out.println("Indexing " + filePath);
                try {
                    SentencesCommand indexSentences = new SentencesCommand(filePath);
                    indexSentences.sentences(sentences, words);
                    VectorsCommand indexVectors = new VectorsCommand();
                    indexVectors.vectors(sentences, vectors);
                } catch (FileNotFoundException e) {
                    System.err.println("File Not Found");
                }
            } else if (command.equals("sentences")) {
                System.out.println(sentences);
                System.out.println("Num sentences");
                System.out.println(sentences.size());
            } else if (command.equals("vectors")) {
                System.out.println(vectors);
            } else if (command.contains("topj ")) {
                topj.clear();
                int space = command.lastIndexOf(" ");
                String Q = command.substring(5, space);
                Integer J = Integer.parseInt(command.substring(space + 1));
                TopjCommand sanity = new TopjCommand(similarityMeasure, Q);
                if (sanity.Qcheck(words)) {
                    PriorityQueue<Entry> pairs = sanity.topj(words, vectors);
                    for (int i = 0; i < J; i++) {
                        topj.add("Pair" + pairs.poll().toString());
                    }
                    System.out.println(topj);
                } else {
                    System.err.println("Cannot compute top-" + J + " similarity to " + Q);
                }
            } else if (command.contains("measure ")) {
                String measurement = command.substring(8);
                if (measurement.equals("cosine")) {
                    similarityMeasure = "cosine";
                    System.out.println("Similarity measure is cosine similarity");
                } else if (measurement.equals("euc")) {
                    similarityMeasure = "euc";
                    System.out.println("Similarity measure is negative euclidean distance");
                } else if (measurement.equals("eucnorm")) {
                    similarityMeasure = "eucnorm";
                    System.out.println("Similarity measure is negative euclidean distance between norms");
                } else {
                    System.err.println("Unrecognized command");
                }
            } else if (command.contains("kmeans ")) {
                int[] spaces = new int[2];
                spaces[0] = command.indexOf(" ");
                spaces[1] = command.lastIndexOf(" ");
                Integer K = Integer.parseInt(command.substring(spaces[0] + 1, spaces[1]));
                Integer iters = Integer.parseInt(command.substring(spaces[1] + 1));
                Kmeans kmean = new Kmeans(K, iters);
                centroid = new ArrayList<>(K);
                kmean.k_means(centroid, cluster, words, vectors);
                for (int k = 0; k < K; k++) {
                    System.out.println("Cluster " + k);
                    System.out.println(cluster.get(k));
                }
            } else if (command.contains("representatives ")) {
                int space = command.indexOf(" ");
                Integer J = Integer.parseInt(command.substring(space + 1));
                if (centroid == null) {
                    System.err.println("Cluster is empty");
                } else {
                    for (int k = 0; k < centroid.size(); k++) {
                        System.out.println(J + " representative(s) from Cluster " + k);
                        TopjCommand sanity = new TopjCommand("euc", centroid.get(k));
                        PriorityQueue<Entry> pairs = sanity.topj(cluster.get(k), vectors);
                        ArrayList<String> rep = new ArrayList<String>(J);
                        for (int j = 0; j < J; j++) {
                            rep.add(pairs.poll().key);
                        }
                        System.out.println(rep);
                    }
                }
            } else if (command.equals("quit")) {
                System.exit(0);
            } else {
                System.err.println("Unrecognized command");
            }
        }
    }
}
