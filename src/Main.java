import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    // Map to represent the adjacency list for the graph
    private static Map<String, Map<String, Integer>> adjacencyList = new HashMap<>();

    public static void main(String[] args) {
        String filePath = "distDK.txt"; // File that contains the graph

        // Reading the graph from the file
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String place1 = parts[0];
                String place2 = parts[1];
                int weight = Integer.parseInt(parts[2]);

                // Add the road to the adjacency list for both cities
                adjacencyList
                        .computeIfAbsent(place1, k -> new HashMap<>())
                        .put(place2, weight);
                adjacencyList
                        .computeIfAbsent(place2, k -> new HashMap<>())
                        .put(place1, weight);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Check if the graph is connected, i.e. there's a path from "Helsingør" to every city
        Set<String> visited = new HashSet<>();
        String startingCity = "Helsingør";
        dfs(visited, startingCity);
        boolean isConnected = visited.size() == adjacencyList.size();
        System.out.println(isConnected ? "The graph is connected." : "The graph is not connected.");

        // Find the shortest path from "Helsingør" to "Esbjerg"
        List<String> shortestPath = shortestPath(startingCity, "Esbjerg");
        System.out.println("Shortest path from " + startingCity + " to Esbjerg: " + String.join(" -> ", shortestPath));
    }

    // Depth-first search to check connectivity
    private static void dfs(Set<String> visited, String city) {
        visited.add(city);
        Map<String, Integer> neighbors = adjacencyList.get(city);
        if (neighbors != null) {
            for (String neighbor : neighbors.keySet()) {
                if (!visited.contains(neighbor)) {
                    dfs(visited, neighbor);
                }
            }
        }
    }

    // Dijkstra's algorithm to find the shortest path
    private static List<String> shortestPath(String start, String end) {
        // Map to store the shortest distance from the start city to each city
        Map<String, Integer> distances = new HashMap<>();
        // Map to store the previous city on the path from the start to each city
        Map<String, String> previousCity = new HashMap<>();
        // Priority queue to choose the next city to visit
        PriorityQueue<Map.Entry<String, Integer>> queue = new PriorityQueue<>(Map.Entry.comparingByValue());

        distances.put(start, 0);
        queue.add(new AbstractMap.SimpleEntry<>(start, 0));

        while (!queue.isEmpty()) {
            String city = queue.poll().getKey();

            for (Map.Entry<String, Integer> entry : adjacencyList.getOrDefault(city, new HashMap<>()).entrySet()) {
                String neighbour = entry.getKey();
                int newDistance = distances.getOrDefault(city, Integer.MAX_VALUE) + entry.getValue();
                if (newDistance < distances.getOrDefault(neighbour, Integer.MAX_VALUE)) {
                    distances.put(neighbour, newDistance);
                    previousCity.put(neighbour, city);
                    queue.add(new AbstractMap.SimpleEntry<>(neighbour, newDistance));
                }
            }
        }

        // Reconstruct the shortest path from start to end
        List<String> path = new ArrayList<>();
        for (String city = end; city != null; city = previousCity.get(city)) {
            path.add(city);
        }
        Collections.reverse(path); // Reverse it to get the path from start to end

        return path;
    }
}