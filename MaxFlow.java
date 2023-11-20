//Name: Rohit Mhatre
//Assignment 7: Maximum Flow
//Compiling instructions:
//Step 1: javac MaxFlow.java
//Step 2: java MaxFlow <filename.txt> (Ex. java MaxFlow maxflow4.txt)
import java.io.*;
import java.util.*;

public class MaxFlow {
    private static final int INF = Integer.MAX_VALUE;

    public static void main(String[] args) {
        // Check if the input file is provided as a command-line argument
        if (args.length != 1) {
            System.out.println("Please provide the input file for calculating max flow.");
            System.exit(1);
        }

        // Retrieve the input file name
        String inputFileName = args[0];

        try {
            // Read input from the file using a Scanner
            Scanner scanner = new Scanner(new File(inputFileName));

            // Read the number of vertices and edges from the input
            int vertices = scanner.nextInt();
            int edges = scanner.nextInt();

            // Create a graph represented by an adjacency matrix
            int[][] graph = new int[vertices][vertices];

            // Populate the graph with edge weights from the input
            for (int i = 0; i < edges; i++) {
                int start = scanner.nextInt();
                int end = scanner.nextInt();
                int weight = scanner.nextInt();

                // Assign the weight to the corresponding edge in the graph
                graph[start][end] = weight;
            }

            // Define the source and target vertices for max flow calculation
            int source = 0;
            int target = vertices - 1;

            // Calculate and print the maximum flow in the graph
            int maxFlow = maxFlow(graph, source, target);
            System.out.println(maxFlow);

            // Close the scanner to release resources
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
    }

    // Function to calculate the maximum flow
    public static int maxFlow(int[][] graph, int source, int target) {
        int n = graph.length;

        // Create a residue graph to keep track of remaining capacities
        int[][] residue = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                residue[i][j] = graph[i][j];
            }
        }

        // Array to store parent nodes in augmenting paths
        int[] parent = new int[n];

        // Variable to store the total maximum flow
        int maxFlow = 0;

        // Iterate while there exists an augmenting path in the residue graph
        while (bfs(residue, source, target, parent)) {
            // Find the minimum capacity in the augmenting path
            int pathFlow = INF;
            for (int v = target; v != source; v = parent[v]) {
                int u = parent[v];
                pathFlow = Math.min(pathFlow, residue[u][v]);
            }

            // Update the residue graph by subtracting the path flow
            // from forward edges and adding it to backward edges
            for (int v = target; v != source; v = parent[v]) {
                int u = parent[v];
                residue[u][v] -= pathFlow;
                residue[v][u] += pathFlow;
            }

            // Update the total maximum flow
            maxFlow += pathFlow;
        }

        // Return the calculated maximum flow
        return maxFlow;
    }

    // Breadth-First Search to find augmenting paths in the residue graph
    private static boolean bfs(int[][] graph, int source, int target, int[] parent) {
        int n = graph.length;
        boolean[] visited = new boolean[n];
        Arrays.fill(visited, false);

        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);
        visited[source] = true;
        parent[source] = -1;

        while (!queue.isEmpty()) {
            int u = queue.poll();

            // Explore neighbors of the current vertex in the residue graph
            for (int v = 0; v < n; v++) {
                if (!visited[v] && graph[u][v] > 0) {
                    // If there is available capacity, add the neighbor to the queue
                    queue.add(v);
                    parent[v] = u;
                    visited[v] = true;
                }
            }
        }

        // Return whether there exists an augmenting path to the target vertex
        return visited[target];
    }
}
