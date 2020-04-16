package com.company;

import java.io.*;
import java.util.ArrayList;

public class StopWords {

    ArrayList<String> stopWordsList;

    public StopWords(){
        stopWordsList = new ArrayList<>();
    }

    public void initStopWords(String lang) throws IOException {
        File file = new File("src/stop_words_"+ lang +".dat");
        //System.out.println(file.exists());
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;

        while ((line = br.readLine()) != null) {
            stopWordsList.add(line);
        }
        br.close();
    }

    public ArrayList<String> getStopWordsList() {
        return stopWordsList;
    }
}