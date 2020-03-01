package com.company;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;

public class Bibliotheque {
    HashMap<String, String[]> dictionnary;
    HashMap<String, String[]> type;
    boolean isInitialized = false;


    public Bibliotheque () {
        dictionnary = new HashMap<>();
        type = new HashMap<>();
    }

    public void Create() throws IOException {
        File file = new File("src/thes_fr.dat");
        //System.out.println(file.exists());
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = br.readLine();
        String word = "";
        while ((line = br.readLine()) != null) {
            if (!line.contains("(")) {
                word = line.split("\\|")[0];
            }
            else{
                String[] words = line.split("\\|");
                String[] temp = new String[words.length - 1];
                for (int i = 0; i < words.length; i++){
                    if (i == 0) {
                        type.put(word, words[0].substring(1,words[0].length() - 1).split(" "));
                    }
                    else {
                        temp[i - 1] = words[i];
                    }
                }
                dictionnary.put(word, temp);
            }
        }
        br.close();
        isInitialized = true;
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    public String[] getSynonyme(String str){
        if (str.charAt(str.length() - 1) == 's'){
            if (dictionnary.get(str) == null){
                return dictionnary.get(str.substring(0,str.length() - 1));
            }
        }
        return dictionnary.get(str);
    }

    public String[] getType(String str){
        if (str.charAt(str.length() - 1) == 's' || str.charAt(str.length() - 1) == 'e'){
            if (type.get(str) == null){
                return type.get(str.substring(0,str.length() - 1));
            }
        }
        return type.get(str);
    }

    public boolean isUseless(String str){
        String[] types = getType(str);
        if (types != null) {
            for (int i = 0; i < types.length; i++){
                if (types[i].equals("Preposition") || types[i].equals("Determinant") || types[i].equals("Pronom"))
                    return true;
            }
        }
        return false;
    }
}
