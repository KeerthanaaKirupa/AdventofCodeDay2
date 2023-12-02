import java.io.*;
import java.nio.file.*;
import java.util.regex.*;
import java.util.List;

public class SecondPuzzle {

    public static void main(String[] args) {
        // Path to the file containing the input
        Path filePath = Paths.get("src/input.txt");

        // Regular expression pattern to match the individual color counts
        String countPatternRegex = "(\\d+) (blue|red|green)";
        Pattern countPattern = Pattern.compile(countPatternRegex);

        // Variable to hold the sum of all products
        int sumOfProducts = 0;

        String outputFilePath = "src/output2.txt"; // Replace with the desired output file path

        try {
            // Read all lines from the file
            List<String> allLines = Files.readAllLines(filePath);

            for (String line : allLines) {
                // Variables to hold the maximum counts for the current line
                int maxBlue = 0;
                int maxRed = 0;
                int maxGreen = 0;

                // Split the line into instances (segments separated by semicolons)
                String[] instances = line.substring(line.indexOf(':') + 1).split(";");

                // Process each instance
                for (String instance : instances) {
                    // Counters for each color in the instance
                    int instanceBlueCount = 0;
                    int instanceRedCount = 0;
                    int instanceGreenCount = 0;

                    // Match and sum individual cube counts in the instance
                    Matcher countMatcher = countPattern.matcher(instance);

                    while (countMatcher.find()) {
                        int count = Integer.parseInt(countMatcher.group(1));
                        String color = countMatcher.group(2);
                        switch (color) {
                            case "blue":
                                instanceBlueCount = count;
                                break;
                            case "red":
                                instanceRedCount = count;
                                break;
                            case "green":
                                instanceGreenCount = count;
                                break;
                        }

                        // Update maximum counts for the current line
                        maxBlue = Math.max(maxBlue, instanceBlueCount);
                        maxRed = Math.max(maxRed, instanceRedCount);
                        maxGreen = Math.max(maxGreen, instanceGreenCount);
                    }
                }

                // Calculate the product of the maximums
                int product = maxBlue * maxRed * maxGreen;

                // Add the product to the sum
                sumOfProducts += product;
            }

            // Output the sum of all products
            System.out.println("Sum of all products: " + sumOfProducts);
            saveSumToFile(outputFilePath, sumOfProducts);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveSumToFile(String filePath, int sum) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("Sum of Game ID: " + sum);
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }
}