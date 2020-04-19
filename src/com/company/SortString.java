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

    public SortString(ArrayList<String> arrayList) throws IOException {
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
            arrayList.set(i0, arrayList.get(i0).toLowerCase());
            String[] sentences = arrayList.get(i0).replaceAll("[^a-zA-Z0-9\\. éèêôûï\\-\\']+","").split("\\.");
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
                                String word = longerWord + ", " + tinierWord;
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

    public void printNeighboors(int treshold){
        ArrayList<ArrayList<String>> arrayList = getNeighboors(treshold);
        for (int i = 0; i < arrayList.size(); i++){
            StringBuilder strings = new StringBuilder(i + 1 + " : ");
            for (int j = 0; j < arrayList.get(i).size(); j++){
                strings.append(arrayList.get(i).get(j)).append(" ");
            }
        }
    }

    public ArrayList<ArrayList<String>> getNeighboors(int treshold) {
        ArrayList<ArrayList<String>> arrayList = new ArrayList<>();
        for (int i = 0; i < maxNeighboorVal; i++){
            arrayList.add(new ArrayList<>());
        }
        if (maxNeighboorVal > 0){

            for (String str :
                    neighboorsHashMap.keySet()) {
                String[] words = str.split(", ");
                if (wordsHashMap.get(words[0]) > treshold && wordsHashMap.get(words[1]) > treshold)
                    arrayList.get(neighboorsHashMap.get(str) - 1).add(str);
            }
        }
        return arrayList;
    }

    public ArrayList<ArrayList<String>> getWords(int treshold) {
        ArrayList<ArrayList<String>> arrayList = new ArrayList<>();
        for (int i = 0; i < Math.max(1, maxWordVal - treshold); i++){
            arrayList.add(new ArrayList<>());
        }
        for (String str :
                wordsHashMap.keySet()) {
            if (wordsHashMap.get(str) >  treshold)
                arrayList.get(wordsHashMap.get(str) - 1 - treshold).add(str);
        }
        return arrayList;
    }
}
