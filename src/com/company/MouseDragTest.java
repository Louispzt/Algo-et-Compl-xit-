package com.company;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JPanel;

public class MouseDragTest extends JPanel {

    public int polyType;
    public int nbPoly;
    HashMap<String, OvalString> ovalMap;
    ArrayList<ArrayList<String>> linkList;
    OvalString oSelectionned;
    private Point mousePt;
    private Point zero;
    public static FontMetrics fm;
    static int treshold;
    SortString sortString;
    private boolean tresholdChanged;
    boolean zoomChanged;
    float zoom;
    float oldZoom;
    Point offset;

    public MouseDragTest() throws IOException {
        zoomChanged = false;
        tresholdChanged = true;
        treshold = 2;
        polyType = 0;
        nbPoly = 3;
        zoom = 1;
        oldZoom = 1;
        mousePt = getMousePosition();
        Font font;
        zero = new Point(0, 0);

        TextFileToListOfString textFileToListOfString = new TextFileToListOfString();
        textFileToListOfString.Open("src/text.dat");
        //System.out.println(textFileToListOfString.getArrayLists().toString());
        font = new Font("SansSerif", Font.BOLD, 20);
        sortString = new SortString(textFileToListOfString.getArrayLists());
        sortString.doNodeHashMap();
        sortString.printWords(0);
        sortString.printNeighboors(0);
        oSelectionned = null;






        this.requestFocus();



        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                System.out.println(e.getKeyCode());
            }
        });


        this.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                mousePt = e.getPoint();
                for (String str : ovalMap.keySet()){
                    OvalString o = ovalMap.get(str);
                    if (Math.abs(e.getPoint().x - o.x) < o.ovalW / 2 && Math.abs(e.getPoint().y - o.y) < o.ovalH / 2){
                        oSelectionned = o;
                        System.out.println(oSelectionned);
                    }
                }
            }

            public void mouseReleased(MouseEvent e){
                oSelectionned = null;
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int dx = e.getX() - mousePt.x;
                int dy = e.getY() - mousePt.y;
                if (oSelectionned != null)
                {
                    oSelectionned.update(dx, dy);
                }
                else{
                    for (String str : ovalMap.keySet()){
                        OvalString o = ovalMap.get(str);
                        o.update(dx, dy);
                    }
                }
                mousePt = e.getPoint();
                repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
            }
        });

        this.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (offset == null){
                    offset = e.getPoint();
                    mousePt = e.getPoint();
                }
                double fact = 1/1.1;
                if (e.getWheelRotation() > 0)
                    zoom /= 1.1;
                else{
                    fact = 1.1;
                    zoom *= 1.1;
                }
                doThis();
                System.out.println(zoom);
                int dx = e.getX() - mousePt.x;
                int dy = e.getY() - mousePt.y;
                offset.setLocation(offset.x + dx / zoom, offset.y + dy / zoom);
                System.out.println(offset);
                int x;
                int y;
                for (String w : ovalMap.keySet()){
                    OvalString o = ovalMap.get(w);
                    x = (int) (e.getX() + dx*zoom + (o.x - e.getX()) * zoom);
                    y = (int) (e.getY() + dy*zoom + (o.y - e.getY()) * zoom);
                    ovalMap.replace(w, new OvalString(x, y, w, o.weight));
                }
                System.out.println("old " + zero);
                zero = new Point( zero.x + (int) (e.getX() * (1/fact)), zero.y + (int) (e.getY() * (1/fact)));
                System.out.println("current " + zero);
                mousePt = e.getPoint();
                oldZoom = zoom;
                repaint();
            }
        });
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (tresholdChanged){
            doThis();
            zoom = 1;
            offset = null;
            tresholdChanged = false;
        }

        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.red);
        for (int i = 0; i < linkList.size(); i++){
            for (String str : linkList.get(i)){
                String[] word = str.split(", ");
                OvalString o1 = ovalMap.get(word[0]);
                OvalString o2 = ovalMap.get(word[1]);
                g2.setStroke(new BasicStroke(2 * (treshold + i + 1)));
                g2.draw(new Line2D.Float(o1.x, o1.y, o2.x, o2.y));
            }
        }


        for (String str : ovalMap.keySet()){
            OvalString oS = ovalMap.get(str);
            g.setFont(new Font("Serif", Font.BOLD , 10 + 10*oS.weight));
            fm = g.getFontMetrics();
            oS.update();
            g.setColor(Color.orange);
            g.fillOval(oS.ovalX, oS.ovalY, oS.ovalW, oS.ovalH);
            g.setColor(Color.black);
            g.drawString(oS.word, oS.wordX, oS.wordY);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1200, 800);
    }

    public void doThis(){
        ovalMap = new HashMap<>();
        linkList = new ArrayList<>();

        ArrayList<ArrayList<String>> arrayList = sortString.getWords(treshold);
        System.out.println(" ");


        int size = 0;
        for (ArrayList<String> arr : arrayList){
            size += arr.size();
        }

        if (polyType == 0){
            int col = (int) (Math.ceil(Math.sqrt(size)));
            int line = (int) Math.ceil((float) size / col);

            int W = getWidth() / (col + 1);
            int H = getHeight() / (line + 1);

            int n = 0;
            for (int i = 0; i < arrayList.size(); i++){
                ArrayList<String> arr = arrayList.get(i);
                for (String s : arr) {
                    OvalString o = new OvalString(W + W * (n % col), H + H * (n / col), s, treshold + i + 1);
                    n++;
                    ovalMap.put(s, o);
                }
            }
        }
        else if (polyType == 1 || polyType == 2){
            int x = 0;
            int y = 0;
            int n = 0;
            int lenX = getWidth() / 2;
            int lenY = getHeight() / 2;
            if (polyType == 1){
                int len = Math.min(lenX, lenY);
                lenX = len;
                lenY = len;
            }
            for (int i = 0; i < arrayList.size(); i++){
                ArrayList<String> arr = arrayList.get(i);
                for (String s : arr) {
                    x = (int) (getWidth() / 2 + Math.cos((float) n / (float) size * 2 * 3.14159265358) * lenX * 0.9);
                    y = (int) (getHeight() / 2 + Math.sin((float) n / (float) size * 2 * 3.14159265358) * lenY * 0.9);
                    OvalString o = new OvalString(x, y, s, treshold + i + 1);
                    n++;
                    ovalMap.put(s, o);
                }
            }
        }


        linkList = sortString.getNeighboors(treshold);
    }

    public void addTreshold(int n) {
        if (treshold + n >= 0){
            treshold += n;
            tresholdChanged = true;
            doThis();
            repaint();
        }
        System.out.println(treshold);
    }
}
