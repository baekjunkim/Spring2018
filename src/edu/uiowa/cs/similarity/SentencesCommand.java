package edu.uiowa.cs.similarity;

import opennlp.tools.stemmer.*;
import java.io.*;
import java.util.*;

public class SentencesCommand {

    public void sentences(ArrayList<ArrayList<String>> sentences, String filePath, HashSet<String> words) throws FileNotFoundException {
        // import stopwords
        Scanner stopwords = new Scanner(new File("C:\\Users\\Baekjun Kim\\Desktop\\U of Iowa\\2017 - 2018 academic year\\2018 Spring (13sh - 97 total)\\CS 2230 Computer Science 2 - Data Structures (Brandon Myers)\\Assignment\\Project\\stopwords.txt"));
        HashSet<String> stop = new HashSet<>();
        while (stopwords.hasNext()) {
            stop.add(stopwords.next().replace("'", ""));
        }
        // index file
        Scanner file = new Scanner(new File(filePath)).useDelimiter("\\.|\\!|\\?");
        sentences.clear();
        PorterStemmer ps = new PorterStemmer();
        while (file.hasNext()) {
            String s = file.next().trim().replace("\r\n", " ").replaceAll(",|--|:|;|\"|'", "").toLowerCase();
            String[] list = s.split("\\s");
            ArrayList<String> sentence = new ArrayList<>();
            for (String word : list) {
                if (!stop.contains(word) && !word.equals("")) {
                    sentence.add(ps.stem(word));
                }
            }
            if (sentence.size() > 0) {
                sentences.add(sentence);
            }
            if (sentence.size() > 1) {
                for (String word : sentence) {
                    words.add(word);
                }
            }
        }
    }
}
