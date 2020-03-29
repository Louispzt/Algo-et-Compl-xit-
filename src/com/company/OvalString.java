package com.company;

import java.awt.*;

public class OvalString{
    public int x;
    public int y;
    public int ovalW;
    public int ovalH;
    public int ovalX;
    public int ovalY;
    public int wordX;
    public int wordY;
    public int weight;
    public String word;

    public OvalString(int x, int y, String word, int weight){
        this.x = x;
        this.y = y;
        this.weight = weight;
        this.word = word;
    }

    public void update(){
        FontMetrics fm = MouseDragTest.fm;
        ovalW = 40 + fm.stringWidth(word);
        ovalH = 20 + fm.getMaxAscent();
        ovalX = x - ovalW / 2;
        ovalY = y - ovalH / 2;
        wordX = x + 20 - ovalW / 2 ;
        wordY = y + fm.getMaxAscent() / 3;
    }

    public void set(int x, int y){
        this.x = x;
        this.y = y;
        update();
    }

    public void update(int dx, int dy){
        x += dx;
        y += dy;
        update();
    }

    @Override
    public String toString() {
        return "OvalString{" +
                "x=" + x +
                ", y=" + y +
                ", ovalW=" + ovalW +
                ", ovalH=" + ovalH +
                ", ovalX=" + ovalX +
                ", ovalY=" + ovalY +
                ", wordX=" + wordX +
                ", wordY=" + wordY +
                ", word='" + word + '\'' +
                '}';
    }
}
