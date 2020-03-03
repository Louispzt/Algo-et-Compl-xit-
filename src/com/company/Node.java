package com.company;

import org.w3c.dom.NamedNodeMap;

import javax.annotation.processing.SupportedSourceVersion;
import java.util.HashMap;
import java.util.Map;

public class Node {
    String str;
    int value;
    Map<Node, Integer> neighboors;
    Node nodeMaxVal;
    int maxval = 0;

    public Node(String str) {
        this.str = str;
        value = 1;
        neighboors = new HashMap<>();
        nodeMaxVal = this;
    }

    public void addNeihboor(Node neighboor){
        if (neighboors.get(neighboor) != null){
            int n = neighboors.get(neighboor);
            if (n + 1 > maxval )
                nodeMaxVal = neighboor;
            neighboors.replace(neighboor, n, n + 1);
        }
        else{
            neighboors.put(neighboor, 1);
        }
    }

    public Node getMaxNeighboor() {return nodeMaxVal; }

    public String getString(){
        return str;
    }

    public void increaseValue() { value++; }

    public int getValue() { return value; }
}
