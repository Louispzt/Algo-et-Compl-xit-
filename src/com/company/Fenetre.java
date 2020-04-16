package com.company;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static com.company.BoutonEnum.*;

public class Fenetre extends JFrame{
    JLabel treshold;
    TextDisplay textDisplay;
    TextDisplay detailDisplay;
    BoutonEnum onScreen;
    String oldText;

    public Fenetre(MouseDragTest mouseDragTest){
        oldText = "";
        this.setTitle("Bouton");
        this.setSize(1280, 720);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        //On définit le layout à utiliser sur le content pane
        this.setLayout(new BorderLayout());
        //On ajoute le bouton au content pane de la JFrame
        //Au nord
        Box boxNorth = Box.createHorizontalBox();
        boxNorth.add(new Bouton("Ouvrir un Fichier texte", OPEN));
        boxNorth.add(new Bouton("Editer/Ecrire un texte", TXT));
        boxNorth.add(new Bouton("Voir le Graphe", GRAPHE));
        boxNorth.add(new Bouton("Détails", DETAILS));

        JPanel wrapperNorth = new JPanel();
        wrapperNorth.setLayout(new BoxLayout(wrapperNorth, BoxLayout.X_AXIS));
        wrapperNorth.add(boxNorth);
        this.getContentPane().add(wrapperNorth, BorderLayout.NORTH);

        Box boxEast = Box.createVerticalBox();
        boxEast.add(new Bouton("increase",93, INCREASE));
        treshold = new JLabel("  treshold : 0");
        boxEast.add(treshold);
        boxEast.add(new Bouton("drecrease",93, DECREASE));

        JPanel wrapperEast = new JPanel(new GridBagLayout());
        wrapperEast.add(boxEast);


        this.getContentPane().add(wrapperEast, BorderLayout.EAST);

        this.getContentPane().add(mouseDragTest, BorderLayout.CENTER,0);
        onScreen = GRAPHE;

        Box boxWest = Box.createVerticalBox();
        boxWest.add(new Bouton("Réordonner",101, REINITIALISER));
        boxWest.add(new Bouton("Circculaire",101, CIRCULAIRE));
        boxWest.add(new Bouton("Ellipsoïde",101, ELLIPSE));
        boxWest.add(new Bouton("A la ligne",101, LIGNE));

        JPanel wrapperWest = new JPanel(new GridBagLayout());
        wrapperWest.add(boxWest);

        this.getContentPane().add(wrapperWest, BorderLayout.WEST);
        this.setVisible(true);
    }

    public void clicked(BoutonEnum bEnum) throws IOException {
        switch (bEnum){
            case DETAILS:
                if (onScreen != DETAILS){
                    detailsclicked();
                    onScreen = DETAILS;
                }
                break;
            case OPEN:
                Main.reload();
                textDisplay = new TextDisplay(Main.text.arrayLists);
                this.repaint();
                this.revalidate();
                switch (onScreen){
                    case DETAILS:
                        detailsclicked();
                        break;
                    case TXT:
                        txtclicked();
                        break;
                    case GRAPHE:
                        grapheclicked();
                        break;
                    default:
                        break;
                }
                break;
            case TXT:
                if (onScreen != TXT){
                    txtclicked();
                    onScreen = TXT;
                }
                break;
            case GRAPHE:
                if (onScreen != GRAPHE){
                    grapheclicked();
                    onScreen = GRAPHE;
                }

                break;
            case INCREASE:
                Main.mouseDragTest.addTreshold(1);
                treshold.setText("  treshold : "+MouseDragTest.treshold);
                if (onScreen == DETAILS){
                    detailsclicked();
                }
                Main.mouseDragTest.repaint();
                break;
            case DECREASE:
                Main.mouseDragTest.addTreshold(-1);
                treshold.setText("  treshold : "+MouseDragTest.treshold);
                if (onScreen == DETAILS){
                    detailsclicked();
                }
                Main.mouseDragTest.repaint();
                break;
            case REINITIALISER:
                Main.mouseDragTest.doThis();
                Main.mouseDragTest.repaint();
                break;
            case CIRCULAIRE:
                Main.mouseDragTest.polyType = 1;
                Main.mouseDragTest.doThis();
                Main.mouseDragTest.repaint();
                break;
            case ELLIPSE:
                Main.mouseDragTest.polyType = 2;
                Main.mouseDragTest.doThis();
                Main.mouseDragTest.repaint();
                break;
            case LIGNE:
                Main.mouseDragTest.polyType = 0;
                Main.mouseDragTest.doThis();
                Main.mouseDragTest.repaint();
                break;
        }
    }

    private void grapheclicked() throws IOException {
        if (textDisplay == null){
            textDisplay = new TextDisplay(Main.text.arrayLists);
        }
        else if (!oldText.equals(textDisplay.getText())){
            Main.mouseDragTest = new MouseDragTest(new TextFileToListOfString().read(textDisplay.getText()));
            Main.mouseDragTest.doThis();
            oldText = textDisplay.getText();
        }

        this.getContentPane().remove(0);
        this.getContentPane().add(Main.mouseDragTest, BorderLayout.CENTER,0);
        treshold.setText("  treshold : "+MouseDragTest.treshold);
        this.repaint();
        this.revalidate();
    }

    private void txtclicked() {
        this.getContentPane().remove(0);
        if (textDisplay == null){
            textDisplay = new TextDisplay(Main.text.arrayLists);
        }
        this.getContentPane().add(new JScrollPane (textDisplay), BorderLayout.CENTER,0);
        this.repaint();
        this.revalidate();
    }

    public void detailsclicked(){
        this.getContentPane().remove(0);
        detailDisplay = new TextDisplay(Main.mouseDragTest);
        this.getContentPane().add(new JScrollPane (detailDisplay), BorderLayout.CENTER,0);
        this.repaint();
        this.revalidate();
    }
}