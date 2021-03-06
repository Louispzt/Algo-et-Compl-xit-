package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TextFileToListOfString {

    ArrayList<String> arrayLists;

    public TextFileToListOfString(){
        arrayLists = new ArrayList<>();
    }

    public void open(String URL) throws IOException{
        File file = new File(URL);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            arrayLists.add(line);
        }
        br.close();
    }

    public ArrayList<String> read(String text){
        String str = "";
        for (String sentences : text.split("\n")){
            if (!sentences.isEmpty()){
                arrayLists.add(sentences);
            }
        }
        return arrayLists;
    }

    public ArrayList<String> getArrayLists() {
        return arrayLists;
    }
}