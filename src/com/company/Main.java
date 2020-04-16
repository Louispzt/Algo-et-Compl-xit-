package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {
    static String file;
    static TextFileToListOfString text;
    static MouseDragTest mouseDragTest;
    static Fenetre f;

    public static void main(String[] args) throws IOException {
        reload();
        f = new Fenetre(mouseDragTest);
        f.setTitle("Projet Algo et Compléxité");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void reload() throws IOException {
        if (text == null ){
            text = new TextFileToListOfString();
            text.open("src/text.dat");
            mouseDragTest = new MouseDragTest(text.arrayLists);
            return;
        }
        FileDialog dialog = new FileDialog((Frame)null, "Select File to Open");
        dialog.setMode(FileDialog.LOAD);
        dialog.setVisible(true);
        file = dialog.getFile();
        String directory = dialog.getDirectory();
        if (file == null){
            JInternalFrame frame = new JInternalFrame();
            JOptionPane.showMessageDialog(frame,
                    "File not found or not specified.\nUsing old one.",
                    "Error file not found",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        try{
            text = new TextFileToListOfString();
            text.open(directory + file);
        } catch (IOException e) {
            text.open("src/text.dat");
            JInternalFrame frame = new JInternalFrame();
            JOptionPane.showMessageDialog(frame,
                    "An error has occured.\nUsing default document.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        mouseDragTest = new MouseDragTest(text.arrayLists);
        System.out.println(text.arrayLists.toString());
    }
}
