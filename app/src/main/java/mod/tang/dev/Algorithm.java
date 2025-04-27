package mod.tang.dev;

import java.util.*;

public class Algorithm {
    private static final int INF = Integer.MAX_VALUE;

    public String dijkstra(int[][] matrix, int start, int dest) {
        int numVer = matrix.length;
        boolean[] isVisited = new boolean[numVer];
        int[] dist = new int[numVer];
        int[] parent = new int[numVer];
        Arrays.fill(dist, INF);
        dist[start] = 0;
        Arrays.fill(parent, -1);
        
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        pq.add(new int[]{start, 0});

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int node = current[0];
            int distance = current[1];

            if (isVisited[node]) {
                continue;
            }

            isVisited[node] = true;

            for (int neighbor = 0; neighbor < numVer; neighbor++) {
                if (matrix[node][neighbor] != INF && !isVisited[neighbor]) {
                    int newDist = distance + matrix[node][neighbor];
                    if (newDist < dist[neighbor]) {
                        dist[neighbor] = newDist;
                        parent[neighbor] = node;
                        pq.add(new int[]{neighbor, newDist});
                    }
                }
            }
        }

        StringBuilder path = new StringBuilder();
        if (dist[dest] == INF) {
            return "No path found";
        }

        List<Integer> pathList = new ArrayList<>();
        for (int at = dest; at != -1; at = parent[at]) {
            pathList.add(at);
        }

        Collections.reverse(pathList);

        for (int i = 0; i < pathList.size(); i++) {
            path.append(pathList.get(i));
            if (i < pathList.size() - 1) {
                path.append(" -> ");
            }
        }

        return "Shortest Path: " + path.toString() + " | distance: " + dist[dest];
    }

    public String aStar(int[][] matrix, int start, int dest) {
        
        return "";
    }
}