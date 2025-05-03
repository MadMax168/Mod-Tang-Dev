package mod.tang.dev;

import java.util.*;

public class Algorithm {

    public String dijkstra(Map<Node, List<Edge>> adjL, Node start, Node dest) {
        Map<Node, Integer> dist = new HashMap<>();
        Map<Node, Node> prev = new HashMap<>();
        Set<Node> isVisited = new HashSet<>();

        for (Node node : adjL.keySet()) {
            dist.put(node, Integer.MAX_VALUE);
            prev.put(node, null);
        }
        dist.put(start, 0);

        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(dist::get));
        pq.add(start);

        while (!pq.isEmpty()) {
            Node u = pq.poll();
            if (isVisited.contains(u)) continue;
            isVisited.add(u);

            for (Edge edge : adjL.getOrDefault(u, List.of())) {
                Node v = edge.target;
                int newDist = dist.get(u) + edge.weight;

                if (newDist < dist.get(v)) {
                    dist.put(v, newDist);
                    prev.put(v, u);
                    pq.add(v);
                }
            }
        }

        return path(prev, dist, start, dest);
    }

    public String aStar(Map<Node, List<Edge>> graph, Node start, Node goal) {
        Map<Node, Integer> gScore = new HashMap<>();
        Map<Node, Double> fScore = new HashMap<>();
        Map<Node, Node> prev = new HashMap<>();
        Set<Node> isVisited = new HashSet<>();
    
        for (Node node : graph.keySet()) {
            gScore.put(node, Integer.MAX_VALUE);
            fScore.put(node, Double.MAX_VALUE);
        }
        gScore.put(start, 0);
        fScore.put(start, start.distanceTo(goal));
    
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingDouble(fScore::get));
        pq.add(start);
    
        while (!pq.isEmpty()) {
            Node current = pq.poll();
    
            if (current.equals(goal)) {
                return buildPath(prev, gScore, start, goal);
            }
    
            if (isVisited.contains(current)) continue;
            isVisited.add(current);
    
            for (Edge edge : graph.getOrDefault(current, List.of())) {
                Node neighbor = edge.target;
                int tentativeG = gScore.get(current) + edge.weight;
    
                if (tentativeG < gScore.get(neighbor)) {
                    prev.put(neighbor, current);
                    gScore.put(neighbor, tentativeG);
                    fScore.put(neighbor, tentativeG + neighbor.distanceTo(goal));
                    pq.add(neighbor);
                }
            }
        }
    
        return "No Path";
    }    

    public String path(Map<Node, Node> prev, Map<Node, Integer> dist, Node start, Node dest) {
        if (dist.get(dest) == Integer.MAX_VALUE) return "No Path";

        List<Node> path = new ArrayList<>();
        for (Node at = dest; at != null; at = prev.get(at)) path.add(at);
        Collections.reverse(path);

        int distance = dist.get(dest);
        int time = (int) (distance / 1.4);

        StringJoiner sj = new StringJoiner(" -> ");
        for (Node node : path) sj.add(node.toString());

        return sj.toString() + " | distance = " + distance + " | time = " + time + "m/s";
    }
} 