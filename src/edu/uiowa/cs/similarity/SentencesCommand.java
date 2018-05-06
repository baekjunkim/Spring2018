package edu.uiowa.cs.similarity;

import opennlp.tools.stemmer.*;
import java.io.*;
import java.util.*;

public class SentencesCommand {

    String filePath;

    public SentencesCommand(String filePath) {
        this.filePath = filePath;
    }

    public void sentences(
            ArrayList<ArrayList<String>> sentences, 
            HashSet<String> words) throws FileNotFoundException {
        // import stopwords
        Scanner stopwords = new Scanner(new File("stopwords.txt"));
        HashSet<String> stop = new HashSet<>();
        while (stopwords.hasNext()) {
            stop.add(stopwords.next().replace("'", ""));
        }
        // index file
        Scanner file = new Scanner(new File(filePath)).useDelimiter("\\.|\\!|\\?");
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
            // for topj command
            if (sentence.size() > 1) {
                for (String word : sentence) {
                    words.add(word);
                }
            }
        }
    }
}
