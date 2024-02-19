package HC;

import java.io.*;
import java.util.*;

public class Graph {
    private Map<String, Map<String, Integer>> graph;
    private List<String> path;

    public Graph() {
        this.graph = new HashMap<>();
        this.path = new ArrayList<>();
    }

    public void addEdge(String start, String end, int weight) {
        graph.computeIfAbsent(start, k -> new HashMap<>()).put(end, weight);
    }

    public void hillClimbingSearch(String start, String goal) {
        path.add(start);
        int totalCost = 0;

        while (!start.equals(goal)) {
            Map<String, Integer> neighbors = graph.get(start);

            if (neighbors == null || neighbors.isEmpty()) {
                System.out.println("No path found.");
                return;
            }

            Map.Entry<String, Integer> minNeighbor = Collections.min(neighbors.entrySet(), Map.Entry.comparingByValue());
            String nextVertex = minNeighbor.getKey();
            int cost = minNeighbor.getValue();

            totalCost += cost;
            path.add(nextVertex);

            start = nextVertex;
        }

        System.out.println("Path: " + String.join(" -> ", path));
        System.out.println("Total Cost: " + totalCost);
    }

    public void readGraphFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length == 3) {
                    String start = parts[0];
                    String end = parts[1];
                    int weight = Integer.parseInt(parts[2]);
                    addEdge(start, end, weight);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeResultToFile(String filePath, String result) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Graph search = new Graph();


        search.readGraphFromFile("graph_input.txt");


        Scanner scanner = new Scanner(System.in);
        System.out.print("Trang thai dau: ");
        String startVertex = scanner.next();
        System.out.print("Trang thai ket thuc: ");
        String goalVertex = scanner.next();


        search.hillClimbingSearch(startVertex, goalVertex);


        String result = "Path: " + String.join(" -> ", search.path) + "\nTong buoc di: " + search.path.size();
        search.writeResultToFile("output.txt", result);
    }
}