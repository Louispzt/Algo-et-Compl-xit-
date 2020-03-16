package com.company;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class SortString {
    HashMap<String, Integer> neighboorsHashMap;
    HashMap<String, Integer> wordsHashMap;
    StopWords stopWords;
    ArrayList<String> arrayList;
    int maxNeighboorVal;
    int maxWordVal;
    Simplification simplification;

    public SortString(ArrayList<String> arrayList) throws IOException {
        simplification = new Simplification();
        this.arrayList = arrayList;
        neighboorsHashMap = new HashMap<>();
        wordsHashMap = new HashMap<>();
        stopWords = new StopWords();
        stopWords.initStopWords("french");
        maxNeighboorVal = 0;
        maxWordVal = 0;
    }

    public void doNodeHashMap() {
        for (int i0 = 0; i0 < arrayList.size(); i0++){
            //String[] sentences = simplification.simplifier(arrayList.get(i0)).split("\\.");
            String[] sentences = arrayList.get(i0).replaceAll("[^a-zA-Z0-9\\. éèêôûï\\-\\']+","").split("\\.");
            System.out.println(Arrays.toString(sentences));
            for (int i = 0; i < sentences.length; i++) {
                String[] words = sentences[i].split(" ");
                for (int j = 0; j < words.length; j++) {
                    if (words[j].length() >= 2 && !stopWords.getStopWordsList().contains(words[j])) {
                        if (!wordsHashMap.containsKey(words[j])) {
                            wordsHashMap.put(words[j], 1);
                        } else {
                            int value = wordsHashMap.get(words[j]);
                            wordsHashMap.replace(words[j], value, value + 1);
                            if (value + 1 > maxWordVal)
                                maxWordVal = value + 1;
                        }
                        for (int k = j; k < words.length; k++) {
                            if (words[k].length() >= 2 && words[j].length() >= 2 && j != k && !stopWords.getStopWordsList().contains(words[j]) && !stopWords.getStopWordsList().contains(words[k])) {
                                String longerWord = words[j];
                                String tinierWord = words[k];
                                if (words[j].length() < words[k].length()) {
                                    longerWord = words[k];
                                    tinierWord = words[j];
                                }
                                String word = "[" + longerWord + ", " + tinierWord + "]";
                                if (neighboorsHashMap.containsKey(word)) {
                                    int value = neighboorsHashMap.get(word);
                                    neighboorsHashMap.replace(word, value, value + 1);
                                    if (value + 1 > maxNeighboorVal)
                                        maxNeighboorVal = value + 1;
                                } else {
                                    neighboorsHashMap.put(word, 1);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void printNeighboors(){
        ArrayList<ArrayList<String>> arrayList = getNeighboors();
        for (int i = 0; i < arrayList.size(); i++){
            String strings = i + 1 + " : ";
            for (int k = 0; k < arrayList.get(i).size(); k++){
                strings += arrayList.get(i).get(k) + " ";
            }
            System.out.println(strings);
        }
    }

    public ArrayList<ArrayList<String>> getNeighboors() {
        ArrayList<ArrayList<String>> arrayList = new ArrayList<>();
        for (int i = 0; i < maxNeighboorVal; i++){
            arrayList.add(new ArrayList<>());
        }
        for (String str :
                neighboorsHashMap.keySet()) {
            arrayList.get(neighboorsHashMap.get(str) - 1).add(str);
        }
        return arrayList;
    }

    public void printWords(){
        ArrayList<ArrayList<String>> arrayList = getWords();
        for (int i = 0; i < arrayList.size(); i++){
            String strings = i + 1 + " : ";
            for (int k = 0; k < arrayList.get(i).size(); k++){
                strings += arrayList.get(i).get(k) + " ";
            }
            System.out.println(strings);
        }
    }

    public ArrayList<ArrayList<String>> getWords() {
        ArrayList<ArrayList<String>> arrayList = new ArrayList<>();
        for (int i = 0; i < maxWordVal; i++){
            arrayList.add(new ArrayList<>());
        }
        for (String str :
                wordsHashMap.keySet()) {
            arrayList.get(wordsHashMap.get(str) - 1).add(str);
        }
        return arrayList;
    }
}
