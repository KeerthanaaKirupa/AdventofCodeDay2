import java.io.*;
import java.nio.file.*;
import java.util.regex.*;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // Path to the file containing the input
        Path filePath = Paths.get("src/input.txt");

        // Regular expression pattern to match the individual color counts
        String countPatternRegex = "(\\d+) (blue|red|green)";
        Pattern countPattern = Pattern.compile(countPatternRegex);

        // Cut off values
        final int redCutoff = 12, greenCutoff = 13, blueCutoff = 14;

        // Sum of game IDs
        int sumOfGameIds = 0;

        String outputFilePath = "src/output.txt"; // Replace with the desired output file path

        try {
            // Read all lines from the file
            List<String> allLines = Files.readAllLines(filePath);

            for (String line : allLines) {
                // Extract the game ID
                String gameIdStr = line.split(":")[0].replaceAll("[^\\d]", "");
                int gameId = Integer.parseInt(gameIdStr);

                // Split the line into instances (segments separated by semicolons)
                String[] instances = line.substring(line.indexOf(':') + 1).split(";");

                // Assume the game is valid until proven otherwise
                boolean isValidGame = true;

                // Process each instance
                for (String instance : instances) {
                    // Counters for each color in the instance
                    int blueCount = 0;
                    int redCount = 0;
                    int greenCount = 0;

                    // Match and sum individual cube counts in the instance
                    Matcher countMatcher = countPattern.matcher(instance);

                    while (countMatcher.find()) {
                        int count = Integer.parseInt(countMatcher.group(1));
                        String color = countMatcher.group(2);
                        switch (color) {
                            case "blue":
                                blueCount += count;
                                break;
                            case "red":
                                redCount += count;
                                break;
                            case "green":
                                greenCount += count;
                                break;
                        }
                    }

                    // Check against cutoff values for the instance
                    if (blueCount > blueCutoff || redCount > redCutoff || greenCount > greenCutoff) {
                        isValidGame = false; // This instance fails the criteria, so the game is invalid
                        break; // No need to check other instances in this game
                    }
                }

                // If the game is valid, add its ID to the sum
                if (isValidGame) {
                    sumOfGameIds += gameId;
                }
            }

            // Output the sum of game IDs that meet the criteria
            System.out.println("Sum of Game IDs: " + sumOfGameIds);
            saveSumToFile(outputFilePath, sumOfGameIds);

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