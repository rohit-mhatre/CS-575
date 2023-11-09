//Assignment 6: Ferry Loading

import java.io.*;
import java.util.*;


public class FerryLoading {

    public static void main(String[] args) {
        // Check if the input file is provided as a command line argument
        if (args.length < 1) {
            System.out.println("please provide correct file");
            return;
        }

        // Get the input file from the command line arguments
        String inputFile = args[0];
        File file = new File(inputFile);

        try {
            // Create a scanner to read from the input file
            Scanner scanner = new Scanner(file);

            // Read the number of cars and the left lane length from the input
            int numCars = scanner.nextInt();
            int leftLaneLength = scanner.nextInt();
            int rightLaneLength = leftLaneLength; // Equal lengths for simplicity

            // Read the lengths of each car into an array
            int[] carLengths = new int[numCars];
            for (int i = 0; i < numCars; i++) {
                carLengths[i] = scanner.nextInt();
            }

            // Create a dynamic programming array to store the maximum number of cars that can be loaded
            int[][][] dp = new int[numCars + 1][leftLaneLength + 1][rightLaneLength + 1];

            // Dynamic programming: Fill in the dp array
            for (int i = 0; i <= numCars; i++) {
                for (int j = 0; j <= leftLaneLength; j++) {
                    for (int k = 0; k <= rightLaneLength; k++) {
                        if (i == 0) {
                            dp[i][j][k] = 0;
                        } else {
                            dp[i][j][k] = dp[i - 1][j][k]; // Car not loaded
                            if (carLengths[i - 1] <= j) {
                                dp[i][j][k] = Math.max(dp[i][j][k], 1 + dp[i - 1][j - carLengths[i - 1]][k]); // Car loaded on the left lane
                            }
                            if (carLengths[i - 1] <= k) {
                                dp[i][j][k] = Math.max(dp[i][j][k], 1 + dp[i - 1][j][k - carLengths[i - 1]]); // Car loaded on the right lane
                            }
                        }
                    }
                }
            }

            // Initialize variables to track the maximum number of cars that can be loaded
            int maxCarCount = 0;
            int leftLength = leftLaneLength;
            int rightLength = rightLaneLength;

            // Create an array to store the lane placements of each car
            int[] placements = new int[numCars];

            // Calculate the maximum number of cars that can be loaded and their placements
            for (int i = numCars; i > 0; i--) {
                if (carLengths[i - 1] <= leftLength && dp[i][leftLength][rightLength] == 1 + dp[i - 1][leftLength - carLengths[i - 1]][rightLength]) {
                    placements[i - 1] = 0; // Car goes to the left lane
                    leftLength -= carLengths[i - 1];
                    maxCarCount++;
                } else if (carLengths[i - 1] <= rightLength) {
                    placements[i - 1] = 1; // Car goes to the right lane
                    rightLength -= carLengths[i - 1];
                    maxCarCount++;
                } else {
                    maxCarCount--;
                    break; // Car can't be loaded, break from the loop
                }
            }

            // Print the maximum number of cars that can be loaded and their placements
            System.out.print(maxCarCount + " ");
            for (int i = 0; i < maxCarCount; i++) {
                if (placements[i] == 0) {
                    System.out.print("L");
                } else {
                    System.out.print("R");
                }
            }
            System.out.println();

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + inputFile);
        }
    }
}


