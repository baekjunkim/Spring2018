package edu.uiowa.cs.similarity;

import java.io.*;
import java.util.*;

public class Main {

    private static void printMenu() {
        System.out.println("Supported commands:");
        System.out.println("help - Print the supported commands");
        System.out.println("index FILE - Read in and index the file given by FILE");
        System.out.println("sentences - Print currently indexed sentences");
        System.out.println("vectors - Print semantic descriptor vector for each unique word");
        System.out.println("topj WORD INTEGER - Print the INTEGER most simliar words to WORD");
        System.out.println("measure MEASURE - Change similarity measure for topj as MEASURE (select from \"cos\", \"eun\", and \"eunnorm\")");
        System.out.println("quit - Quit this program");
    }

    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        HashSet<String> words = new HashSet<>();
        ArrayList<ArrayList<String>> sentences = new ArrayList<>();
        HashMap<String, HashMap<String, Double>> vectors = new HashMap<>();
        ArrayList<String> topj = new ArrayList<>();
        String similarityMeasure = "cos";

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
                int space = command.lastIndexOf(" ");
                String Q = command.substring(5, space);
                Integer J = Integer.parseInt(command.substring(space + 1));
                TopjCommand sanity = new TopjCommand(similarityMeasure, Q, J);
                if (sanity.Qcheck(words)) {
                    sanity.topj(words, vectors, topj);
                    System.out.println(topj);
                } else {
                    System.err.println("Cannot compute top-" + J + " similarity to " + Q);
                }
            } else if (command.contains("measure ")) {
                String measurement = command.substring(8);
                if (measurement.equals("cos")) {
                    similarityMeasure = "cos";
                } else if (measurement.equals("euc")) {
                    similarityMeasure = "euc";
                } else if (measurement.equals("eucnorm")) {
                    similarityMeasure = "eucnorm";
                } else {
                    System.err.println("Unrecognized command");
                }
            } else if (command.equals("quit")) {
                System.exit(0);
            } else {
                System.err.println("Unrecognized command");
            }
        }
    }
}
