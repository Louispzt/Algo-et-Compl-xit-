package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class Bouton extends JButton implements MouseListener{
    int width;
    boolean clicked;
    BoutonEnum bEnum;

    public Bouton(String str, BoutonEnum bEnum){
        super(str);
        clicked = false;
        this.bEnum = bEnum;
        this.addMouseListener(this);
    }

    public Bouton(String str, int width, BoutonEnum bEnum){
        super(str);
        clicked = false;
        this.width = width;
        this.bEnum = bEnum;
        this.addMouseListener(this);
    }

    @Override
    public void paintComponent(Graphics g){
        if (width != 0){
            super.setSize(width, super.getHeight());
        }
        super.paintComponent(g);
    }

    @Override
    public void mouseClicked(MouseEvent event) {
    }

    @Override
    public void mousePressed(MouseEvent event) {
        clicked = true;
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        if (clicked) {
            try {
                Main.f.clicked(bEnum);
            } catch (IOException e) {
                e.printStackTrace();
            }
            clicked = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}