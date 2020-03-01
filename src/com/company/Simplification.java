package com.company;

public class Simplification {
    String[] text;
    public Simplification() {
    }

    public String simplifier(String str){
        str = str.toLowerCase();
        str = str.replaceAll("," , "");
        str = str.replaceAll("\\." , "&");
        str = str.replaceAll(";" , "");
        str = str.replaceAll("/" , "");
        str = str.replaceAll(" - " , "");
        str = str.replaceAll("\"" , "");
        str = str.replaceAll("\\(" , "");
        str = str.replaceAll("\\)" , "");
        str = str.replaceAll("\\[" , "");
        str = str.replaceAll("\\]" , "");
        str = str.replaceAll("\\{" , "");
        str = str.replaceAll(" \\}" , "");
        str = str.replaceAll("’" , " ");
        str = str.replaceAll("\\'" , " ");
        return str;
    }
}
