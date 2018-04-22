package edu.uiowa.cs.similarity;

import opennlp.tools.stemmer.*;

import java.io.*;
import java.util.*;

public class Main {

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
                Scanner stopwords = new Scanner(new File("stopwords.txt"));
                HashSet<String> stop = new HashSet<>();
                while (stopwords.hasNext()) {
                    stop.add(stopwords.next().replace("'", ""));
                }
                // index file
                String filePath = command.substring(6);
                System.out.println("Indexing " + filePath);
                Scanner file = new Scanner(new File(filePath)).useDelimiter("\\.|\\!|\\?");
                PorterStemmer ps = new PorterStemmer();
                while (file.hasNext()) {
                    String[] list = file.next().trim().replace("\r\n", " ").replaceAll(",|--|:|;|\"|'", "").toLowerCase().split("\\s");
                    for (int i = 0; i < list.length; i++) {
                        if (stop.contains(list[i])) {
                            System.out.println("remove: " + list[i]);
                            list[i] = "";
                        } else {
                            list[i] = ps.stem(list[i]);
                            System.out.println("stem: " + list[i] + " / " + stop.contains(list[i]));
                        }
                    }
                    ArrayList<String> arr = new ArrayList<>();
                    for (int i = 0; i < list.length; i++) {
                        if (!list[i].equals("")) {
                            arr.add(list[i]);
                        }
                    }
                    if (arr.size() > 0) {
                        fileList.add(arr);
                    }
                }
                file.close();

                System.out.println(fileList.toString());
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
