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

    public void Open(String URL) throws IOException{
        File file = new File(URL);
        //System.out.println(file.exists());
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        String str = "";
        while ((line = br.readLine()) != null) {
            if (!line.equals("") && !str.equals("")){
                arrayLists.add(str);
                str = "";
            }
            str += line;
        }
        if (str != ""){
            arrayLists.add(str);
        }
        br.close();
    }

    public ArrayList<String> getArrayLists() {
        return arrayLists;
    }
}