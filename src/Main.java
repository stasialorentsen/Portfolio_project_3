import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // Path to the text file containing distances
        String filePath = "distDK.txt";

        // Map to store distances between cities
        Map<String, Integer> distances = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Read each line from the file
            while ((line = br.readLine()) != null) {
                // Split the line by commas to extract city names and distance
                String[] parts = line.split(",");
                // Check if the line has correct format (city1,city2,distance)
                if (parts.length == 3) {
                    // Concatenate city names with a hyphen to form a unique key
                    String cities = parts[0] + "-" + parts[1];
                    // Parse the distance value to an integer
                    int distance = Integer.parseInt(parts[2]);
                    // Store the distance in the map with the concatenated city names as key
                    distances.put(cities, distance);
                } else {
                    // Print a warning message for lines with incorrect format
                    System.out.println("Invalid data format: " + line);
                }
            }
        } catch (IOException e) {
            // Print stack trace if an IO exception occurs
            e.printStackTrace();
        }

        // Create a graph using the distances between cities
        Graph graph = new Graph();
        for (Map.Entry<String, Integer> entry : distances.entrySet()) {
            String[] cities = entry.getKey().split("-");
            String city1 = cities[0];
            String city2 = cities[1];
            int distance = entry.getValue();
            // Add edges between cities with the corresponding distances
            graph.addEdge(city1, city2, distance);
        }

        // At this point, the graph is created based on the distances between cities
    }

    static class Graph {
        Map<String, Map<String, Integer>> adjacencyList;

        public Graph() {
            adjacencyList = new HashMap<>();
        }

        public void addEdge(String source, String destination, int weight) {
            // Add the destination city and distance to the source city's adjacency list
            adjacencyList.computeIfAbsent(source, k -> new HashMap<>()).put(destination, weight);
            // Add the source city and distance to the destination city's adjacency list (assuming undirected graph)
            adjacencyList.computeIfAbsent(destination, k -> new HashMap<>()).put(source, weight);
        }
    }
}
