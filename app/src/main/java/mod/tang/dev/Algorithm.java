package mod.tang.dev;

import java.util.*;

public class Algorithm {
    public String dijkstra(int[][] matrix, int start, int dest) {
        int[] dist = new int[matrix.length];
        int[] prev = new int[matrix.length];
        boolean[] isVisited = new boolean[matrix.length];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;
        Arrays.fill(prev, -1);

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        pq.add(new int[]{start, 0});

        return dijkstraUtil(matrix, pq, dist, prev, isVisited, start, dest);
    }

    public String dijkstraUtil(int [][]matrix, PriorityQueue<int[]> pq, int[] dist, int[] prev, boolean[] isVisited, int start, int dest){
        while(!pq.isEmpty()){
            int[] curr = pq.poll();
            int u = curr[0]; // u == node
            int distance = curr[1];
            
            if (isVisited[u]) continue;
            isVisited[u] = true;

            for (int i = 0; i < matrix.length; i++){
                if (matrix[u][i] != Integer.MAX_VALUE && !isVisited[i]){
                    int alt = distance + matrix[u][i]; // alt == new distance
                    if (alt < dist[i]) {
                        dist[i] = alt;
                        prev[i] = u;
                        pq.add(new int[]{i, alt});
                    }
                }
            }
        }

        return path(prev, start, dest, dist[dest]);
    }

    // public String aStar(int[][] matrix, int start, int dest) {
        
    //     return path(prev, start, dest, dist[dest]);
    // }

    public String path(int[] prev, int start, int dest, int dist) {
        if (dist == Integer.MAX_VALUE) return "No Path";

        List<Integer> path = new ArrayList<>();
        for (int at = dest; at != -1; at = prev[at]) path.add(at);
        Collections.reverse(path);

        StringJoiner to = new StringJoiner(" -> ");
        for (int node : path) to.add(String.valueOf(node));

        return to.toString() + " | distance = " + dist;
    }
}