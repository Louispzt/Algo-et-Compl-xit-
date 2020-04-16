package com.company;

import java.awt.*;
import java.awt.event.*;
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
    private Point translate;
    public static FontMetrics fm;
    static int treshold;
    private boolean tresholdChanged;
    boolean zoomChanged;
    float zoom;
    float oldZoom;
    Point offset;
    SortString sortString;

    public MouseDragTest(ArrayList<String> arrayList) throws IOException {
        zoomChanged = false;
        tresholdChanged = true;
        treshold = 0;
        polyType = 0;
        nbPoly = 3;
        zoom = 1;
        oldZoom = 1;
        mousePt = getMousePosition();
        Font font;
        translate = new Point(0, 0);
        setBackground(new Color(50,50,50));
        sortString = new SortString(arrayList);
        sortString.doNodeHashMap();
        oSelectionned = null;
        ovalMap = new HashMap<>();
        linkList = new ArrayList<>();






        this.requestFocus();



        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
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
                double fact = 1+ 1/(double)3;
                if (e.getWheelRotation() > 0)
                    fact = 1/fact;
                int dx = e.getX() - mousePt.x;
                int dy = e.getY() - mousePt.y;
                offset.setLocation(offset.x * fact + dx * fact, offset.y * fact + dy * fact);
                for (String w : ovalMap.keySet()) {
                    OvalString o = ovalMap.get(w);
                    int x = (int) (-e.getX() * (fact - 1) + o.x * fact);
                    int y = (int) (-e.getY() * (fact - 1) + o.y * fact);
                    ovalMap.replace(w, new OvalString(x, y, w, o.weight));
                }
                mousePt = e.getPoint();
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
        g2.setColor(Color.white);
        for (int i = 0; i < linkList.size(); i++){
            for (String str : linkList.get(i)){
                String[] word = str.split(", ");
                OvalString o1 = ovalMap.get(word[0]);
                OvalString o2 = ovalMap.get(word[1]);
                g2.setStroke(new BasicStroke(2*(i + 1)));
                g2.draw(new Line2D.Float(o1.x, o1.y, o2.x, o2.y));
            }
        }

        for (String str : ovalMap.keySet()){
            OvalString oS = ovalMap.get(str);
            g.setFont(new Font("Serif", Font.BOLD , 10 + 5*oS.weight));
            fm = g.getFontMetrics();
            oS.update();
            g.setColor(Color.orange);
            g.fillOval(oS.ovalX, oS.ovalY, oS.ovalW, oS.ovalH);
            g.setColor(Color.red);
            g.drawString(oS.word, oS.wordX, oS.wordY);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1200, 800);
    }

    public void doThis(){
        ovalMap.clear();
        linkList.clear();

        ArrayList<ArrayList<String>> arrayList = sortString.getWords(treshold);


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
                    OvalString o = new OvalString(W + W * (n % col) + translate.x, H + H * (n / col) + translate.y, s, treshold + i + 1);
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
    }
}
