package com.company;

import java.util.HashMap;
import java.util.Map;

public class Node {
    String str;
    int value;
    Map<Node, Integer> neighboors;

    public Node(String str) {
        this.str = str;
        value = 1;
        neighboors = new HashMap<>();
    }

    public void addNeihboor(Node neighboor){
        if (neighboor.getNeighboor().containsKey(str)){
            System.out.println("oui");
            int n = neighboors.get(neighboor);
            neighboors.replace(neighboor, n, n + 1);
        }
        else{
            neighboors.put(neighboor, 1);
        }
    }

    public String getString(){
        return str;
    }

    public Map getNeighboor(){
        return neighboors;
    }

    public void increaseValue() { value++; }

    public int getValue() { return value; }
}
