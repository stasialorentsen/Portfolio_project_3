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

        // At this point, distances are stored in the 'distances' map
    }
}
