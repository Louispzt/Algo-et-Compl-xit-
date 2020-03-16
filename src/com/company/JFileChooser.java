package com.company;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;


public class JFileChooser extends JPanel
        implements ActionListener {
    JButton chooseAFile;
    JButton start;

    javax.swing.JFileChooser chooser;
    String choosertitle;

    public JFileChooser() {
        chooseAFile = new JButton("Choose a file");
        chooseAFile.addActionListener(this);
        add(chooseAFile);

        start = new JButton("Start");
        start.addActionListener(this);
        add(start);
    }

    public void actionPerformed(ActionEvent e) {
        int result;

        chooser = new javax.swing.JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle(choosertitle);
        //
        // disable the "All files" option.
        //
        chooser.setAcceptAllFileFilterUsed(false);
        //
        if (chooser.showOpenDialog(this) == javax.swing.JFileChooser.APPROVE_OPTION) {
            System.out.println("getCurrentDirectory(): "
                    +  chooser.getCurrentDirectory());
            System.out.println("getSelectedFile() : "
                    +  chooser.getSelectedFile());
        }
        else {
            System.out.println("No Selection ");
        }
    }

    public Dimension getPreferredSize(){
        return new Dimension(200, 200);
    }

    public static void main(String s[]) {
        JFrame frame = new JFrame("");
        JFileChooser panel = new JFileChooser();
        frame.addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                }
        );
        frame.getContentPane().add(panel,"Center");
        frame.setSize(panel.getPreferredSize());
        frame.setVisible(true);
    }
}