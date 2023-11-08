import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FerryLoading {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java FerryLoading <input_file>");
            return;
        }

        String inputFile = args[0];
        File file = new File(inputFile);

        try {
            Scanner scanner = new Scanner(file);

            int numCars = scanner.nextInt();
            int leftLaneLength = scanner.nextInt();
            int rightLaneLength = leftLaneLength; // Equal lengths for simplicity

            int[] carLengths = new int[numCars];
            for (int i = 0; i < numCars; i++) {
                carLengths[i] = scanner.nextInt();
            }

            int[][][] dp = new int[numCars + 1][leftLaneLength + 1][rightLaneLength + 1];

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

            int maxCarCount = dp[numCars][leftLaneLength][rightLaneLength];
            System.out.print(maxCarCount + " ");

            int leftLength = leftLaneLength;
            int rightLength = rightLaneLength;

            int[] placements = new int[numCars];

            for (int i = numCars; i > 0; i--) {
                if (carLengths[i - 1] <= leftLength && dp[i][leftLength][rightLength] == 1 + dp[i - 1][leftLength - carLengths[i - 1]][rightLength]) {
                    placements[i - 1] = 0; // Car goes to the left lane
                    leftLength -= carLengths[i - 1];
                } else if (carLengths[i - 1] <= rightLength) {
                    placements[i - 1] = 1; // Car goes to the right lane
                    rightLength -= carLengths[i - 1];
                }
            }

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