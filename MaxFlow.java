//Assignment 7: Maximum Flow
//First draft
import java.io.*;
import java.util.*;

public class MaxFlow {
    private static final int INF = Integer.MAX_VALUE;

    // Returns the maximum flow in the given graph from source to sink
    public static int maxFlow(int[][] graph, int source, int sink) {
        int n = graph.length;
        int[][] residualGraph = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                residualGraph[i][j] = graph[i][j];
            }
        }

        int[] parent = new int[n];
        int maxFlow = 0;

        while (bfs(residualGraph, source, sink, parent)) {
            int pathFlow = INF;
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                pathFlow = Math.min(pathFlow, residualGraph[u][v]);
            }

            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                residualGraph[u][v] -= pathFlow;
                residualGraph[v][u] += pathFlow;
            }

            maxFlow += pathFlow;
        }

        return maxFlow;
    }

    // Breadth-First Search to find augmenting paths
    private static boolean bfs(int[][] graph, int source, int sink, int[] parent) {
        int n = graph.length;
        boolean[] visited = new boolean[n];
        Arrays.fill(visited, false);

        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);
        visited[source] = true;
        parent[source] = -1;

        while (!queue.isEmpty()) {
            int u = queue.poll();

            for (int v = 0; v < n; v++) {
                if (!visited[v] && graph[u][v] > 0) {
                    queue.add(v);
                    parent[v] = u;
                    visited[v] = true;
                }
            }
        }

        return visited[sink];
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java MaxFlow <inputFileName>");
            System.exit(1);
        }

        String inputFileName = args[0];

        try {
            Scanner scanner = new Scanner(new File(inputFileName));
            
            int vertices = scanner.nextInt();
            int edges = scanner.nextInt();
            
            int[][] graph = new int[vertices][vertices];
            
            for (int i = 0; i < edges; i++) {
                int start = scanner.nextInt();
                int end = scanner.nextInt();
                int weight = scanner.nextInt();
                
                graph[start][end] = weight;
            }
            
            int source = 0;
            int sink = vertices - 1;

            int maxFlow = maxFlow(graph, source, sink);
            System.out.println("Maximum Flow: " + maxFlow);
            
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + inputFileName);
            e.printStackTrace();
        }
    }
}
