package HC;
import java.io.*;
import java.util.*;


public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph();
        graph.addEdge("A", "B", 1);
        graph.addEdge("A", "C", 3);
        graph.addEdge("B", "C", 2);
        graph.addEdge("B", "D", 5);
        graph.addEdge("C", "E", 1);
        graph.addEdge("D", "E", 3);
        graph.hillClimbingSearch("A", "E");
    }
}