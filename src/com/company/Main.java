package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame f = new JFrame("Oui");
                MouseDragTest mouseDragTest;
                try {
                    mouseDragTest = new MouseDragTest();
                    f.add(mouseDragTest);
                    f.addKeyListener(new KeyListener() {

                        @Override
                        public void keyTyped(KeyEvent e) {
                        }

                        @Override
                        public void keyReleased(KeyEvent e) {
                        }

                        @Override
                        public void keyPressed(KeyEvent e) {
                            if (e.getKeyCode() == 107){
                                mouseDragTest.addTreshold(1);
                                f.repaint();
                            }
                            if (e.getKeyCode() == 109){
                                mouseDragTest.addTreshold(-1);
                                f.repaint();
                            }
                            if (e.getKeyCode() == 79) {
                                mouseDragTest.polyType = (mouseDragTest.polyType + 1) % mouseDragTest.nbPoly;
                                mouseDragTest.doThis();
                                f.repaint();
                            }
                            System.out.println("Pressed " + e.getKeyCode());
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
                f.setPreferredSize(new Dimension(1200,800));
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.pack();
                f.setLocationRelativeTo(null);
                f.setVisible(true);
                //OvalString ovalString = new OvalString();
            }
        });
    }
}
