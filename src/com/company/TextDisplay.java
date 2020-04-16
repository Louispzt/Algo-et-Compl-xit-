package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TextDisplay extends JTextPane {
    public TextDisplay(ArrayList<String> arrayList){
        super();
        super.setMargin(new Insets(5,5,5,5));
        super.setForeground(Color.white);
        StringBuilder str = new StringBuilder();
        for (String st : arrayList){
            str.append(st).append("\n\n");
        }
        super.setText(str.toString());

        super.setBackground(Color.gray);
    }

    public TextDisplay(MouseDragTest mouseDragTest) {
        Map<String, String> map = new HashMap<>();
        for (int i = MouseDragTest.treshold; i < mouseDragTest.linkList.size(); i++){
            for (String words : mouseDragTest.linkList.get(i)){
                String[] w = words.split(", ");
                map.put(w[0], map.get(w[0])+ " " + w[1]);
                map.put(w[1], map.get(w[1])+ " " + w[0]);
            }
        }
        String str = "";
        for (String key : map.keySet()){
            str += key + " : " + map.get(key).substring(5) + "\n\n";
        }
        super.setText(str);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
