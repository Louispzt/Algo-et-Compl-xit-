package com.company;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class DrawText extends Component {
    SortString sortString;
    public DrawText() throws IOException {
        TextFileToListOfString textFileToListOfString = new TextFileToListOfString();
        textFileToListOfString.Open("src/text.dat");
        //System.out.println(textFileToListOfString.getArrayLists().toString());

        sortString = new SortString(textFileToListOfString.getArrayLists());
        sortString.doNodeHashMap();
        sortString.printNeighboors();
        System.out.println(" ");
        sortString.printWords();
    }

    public void paint(Graphics g) {
        Dimension dimension = Fenetre.getDimension();
        int n = 0;
        int n2 = 0;
        int x = 0;
        int y = 0;
        double size = (Math.sqrt(sortString.wordsHashMap.size())) + 1;
        ArrayList<ArrayList<String>> arrayList = sortString.getWords();
        while (n + n2 < sortString.wordsHashMap.size() && n < arrayList.size()){
            if (n2 < arrayList.get(n).size()){
                String str = arrayList.get(n).get(n2);
                g.drawString(str, 20 + x * (int) ((dimension.width) / (size + 2)), 20 + y * (int) ((dimension.height - 20) / size));
                if (x % size == 0 && x != 0){
                    y++;
                    x = 0;
                }
                else
                    x++;
                n2++;
            }
            else{
                n2 = 0;
                n++;
            }
        }
    }
}