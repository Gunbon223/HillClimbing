package HC;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Graph {
    static class Node {
        String name;
        int priority;

        public Node(String name, int priority) {
            this.name = name;
            this.priority = priority;
        }

        @Override
        public String toString() {
            return name+priority;
        }
    }

    public static void main(String[] args) {
        Map<String, Integer> trongSo = readVertices("src/HC/input.txt");
        Map<String, List<String>> danhSach = readEdges("src/HC/input.txt");
        if (danhSach != null) {
            String res = hillClimbing(danhSach, trongSo, "A", "B");
            if (res != null) {
                printFile("src/HC/output.txt", res);
                System.out.println(res);
            } else {
                System.out.println("Không tìm thấy đường đi.");
            }
        } else {
            System.out.println("Đã xảy ra lỗi khi đọc file input.txt.");
        }
    }

    public static String hillClimbing(Map<String, List<String>> danhSach, Map<String, Integer> trongSo, String start, String end) {
        //if (!danhSach.containsKey(start) || !danhSach.containsKey(end)) {
        //	return null;
        //}

        Stack<Node> stack = new Stack<>();
        stack.push(new Node(start, trongSo.get(start)));
        List<Node> L1 = new ArrayList<>();
        List<Node> L = new ArrayList<>();
        L.add(new Node(start, trongSo.get(start)));
        List<String> Ke = new ArrayList<>();
        List<String[]> row = new ArrayList<>();
        Map<String, String> visited = new HashMap<>();

        while (!stack.isEmpty()) {
            Node node = stack.pop();
            L.removeIf(n -> n.name.equals(node.name));
            Ke.clear();
            L1.clear();
            List<String> dinhKeList = danhSach.get(node.name);
            if (dinhKeList != null) {
                for (String dinhKe: dinhKeList) {
                    Ke.add(dinhKe);
                    if (!dinhKe.equals(start) && !visited.containsKey(dinhKe)) {
                        L1.add(new Node(dinhKe, trongSo.get(dinhKe)));
                        visited.put(dinhKe, node.name);
                    }
                }
                L1.sort(Comparator.comparingInt(n -> n.priority));
                Collections.reverse(L);
                Collections.reverse(L1);
                for (Node n: L1)
                {
                    stack.push(n);
                    L.add(n);
                }
                Collections.reverse(L);
                Collections.reverse(L1);
            }

            if (node.name.equals(end)) {
                List<String> duongDi = new ArrayList<>();
                duongDi.add(node.name);
                while (!duongDi.get(duongDi.size() - 1).equals(start)) {
                    duongDi.add(visited.get(duongDi.get(duongDi.size() - 1)));
                }
                row.add(new String[] { node.name, "Tìm thấy-TTKT Dừng", null, null });
                StringBuilder result = new StringBuilder();
                for (int i = duongDi.size() - 1; i >= 0; i--) {
                    result.append(duongDi.get(i));
                    if (i != 0) {
                        result.append(" => ");
                    }
                }
                return tabulate(row, new String[] { "Phát triển TT", "Trạng thái kề", "Danh sách L1", "Danh sách L" })
                        + "\nĐường đi là: " + result.toString();
            }
            row.add(new String[] { node.name, String.join(", ", Ke), String.join(", ", L1.toString()), String.join(", ", L.toString()) });
        }

        return null;
    }

    public static Map<String, Integer> readVertices(String filename) {
        Map<String, Integer> vertices = new HashMap<>();
        try (BufferedReader file = new BufferedReader(new FileReader(filename))) {
            String line = file.readLine();
            while (line != null && !line.trim().isEmpty()) {
                String[] temp = line.split(" ");
                String vertex = temp[0];
                int value = Integer.parseInt(temp[1]);
                vertices.put(vertex, value);
                line = file.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return vertices;
    }

    public static Map<String, List<String>> readEdges(String filename) {
        Map<String, List<String>> edges = new HashMap<>();
        try (BufferedReader file = new BufferedReader(new FileReader(filename))) {
            String line = file.readLine();
            while (line != null && !line.trim().isEmpty()) {
                line = file.readLine();
            }
            line = file.readLine(); // Đọc dòng trống
            while (line != null) {
                if (!line.trim().isEmpty()) {
                    String[] temp = line.split(" ");
                    String vertex1 = temp[0];
                    String vertex2 = temp[1];
                    edges.putIfAbsent(vertex1, new ArrayList<>());
                    edges.get(vertex1).add(vertex2);
                }
                line = file.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return edges;
    }

    public static void printFile(String fileName, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String tabulate(List<String[]> rows, String[] headers) {
        StringBuilder sb = new StringBuilder();
        int[] maxLengths = new int[headers.length];

        // Tìm độ dài lớn nhất cho mỗi cột
        for (int i = 0; i < headers.length; i++) {
            maxLengths[i] = headers[i].length();
        }

        for (String[] row : rows) {
            for (int i = 0; i < row.length; i++) {
                if (row[i] != null && row[i].length() > maxLengths[i]) {
                    maxLengths[i] = row[i].length();
                }
            }
        }

        // In tiêu đề
        for (int i = 0; i < headers.length; i++) {
            sb.append(padRight(headers[i], maxLengths[i]));
            if (i < headers.length - 1) {
                sb.append(" | ");
            }
        }
        sb.append("\n");

        // In từng dòng
        for (String[] row : rows) {
            for (int i = 0; i < row.length; i++) {
                String value = (row[i] != null) ? row[i] : ""; // Xử lý giá trị null
                sb.append(padRight(value, maxLengths[i]));
                if (i < row.length - 1) {
                    sb.append(" | ");
                }
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    // Phương thức căn phải giá trị trong một chuỗi với độ rộng cho trước
    private static String padRight(String s, int width) {
        return String.format("%-" + width + "s", s);
    }
}
