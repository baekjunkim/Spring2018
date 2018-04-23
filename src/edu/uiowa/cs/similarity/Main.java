package edu.uiowa.cs.similarity;

import opennlp.tools.stemmer.*;

import java.io.*;
import java.util.*;

public class Main {
    // index C:\Users\Baekjun Kim\Desktop\U of Iowa\2017 - 2018 academic year\2018 Spring (13sh - 97 total)\CS 2230 Computer Science 2 - Data Structures (Brandon Myers)\Assignment\Project\cleanup_test.txt

    private static void printMenu() {
        System.out.println("Supported commands:");
        System.out.println("help - Print the supported commands");
        System.out.println("index FILE - Read in and index the file given by FILE");
        System.out.println("sentences - Print currently indexed sentences");
        System.out.println("quit - Quit this program");
    }

    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<ArrayList<String>> fileList = new ArrayList<>();

        while (true) {
            System.out.print("> ");
            String command = input.readLine();
            if (command.equals("help") || command.equals("h")) {
                printMenu();
            } else if (command.contains("index ")) {
                // stop words
                Scanner stopwords = new Scanner(new File("C:\\Users\\Baekjun Kim\\Desktop\\U of Iowa\\2017 - 2018 academic year\\2018 Spring (13sh - 97 total)\\CS 2230 Computer Science 2 - Data Structures (Brandon Myers)\\Assignment\\Project\\stopwords.txt"));
                HashSet<String> stop = new HashSet<>();
                while (stopwords.hasNext()) {
                    stop.add(stopwords.next().replace("'", ""));
                }
                // index file
                fileList.clear();
                String filePath = command.substring(6);
                System.out.println("Indexing " + filePath);
                Scanner file = new Scanner(new File(filePath)).useDelimiter("\\.|\\!|\\?");
                PorterStemmer ps = new PorterStemmer();
                while (file.hasNext()) {
                    String s = file.next().trim().replace("\r\n", " ").replaceAll(",|--|:|;|\"|'", "").toLowerCase();
                    String[] list = s.split("\\s");
                    for (int i = 0; i < list.length; i++) {
                        if (stop.contains(list[i])) {
                            list[i] = "";
                        } else {
                            list[i] = ps.stem(list[i]);
                        }
                    }
                    ArrayList<String> arr = new ArrayList<>();
                    for (String word : list) {
                        if (!word.equals("")) {
                            arr.add(word);
                        }
                    }
                    if (arr.size() > 0) {
                        fileList.add(arr);
                    }
                }
                file.close();
            } else if (command.equals("sentences")) {
                System.out.println(fileList.toString());
                System.out.println("Num sentences");
                System.out.println(fileList.size());
            } else if (command.equals("quit")) {
                System.exit(0);
            } else {
                System.err.println("Unrecognized command");
            }
        }
    }
}
