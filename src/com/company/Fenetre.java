package com.company;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Fenetre extends JFrame {
    static Dimension dimension;

    public Fenetre() throws IOException {
        this.setTitle("Ma première fenêtre Java");
        this.setSize(1800, 900);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DrawText drawText = new DrawText();
        dimension = this.getSize();

        this.add(drawText);

        this.setVisible(true);
    }

    public static Dimension getDimension(){
        return dimension;
    };
}