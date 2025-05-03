package mod.tang.dev;

import java.util.*;

public class Algorithm {

    public static class SearchState {
        public Map<Node, Double> dist;
        public Map<Node, Node> prev;
        public Set<Node> isVisited;

        public SearchState(Map<Node, Double> dist, Map<Node, Node> prev, Set<Node> isVisited) {
            this.dist = dist;
            this.prev = prev;
            this.isVisited = isVisited;
        }
    }

    public SearchState setup(Map<Node, List<Edge>> adjL, Node start) {
        Map<Node, Double> dist = new HashMap<>();
        Map<Node, Node> prev = new HashMap<>();
        Set<Node> isVisited = new HashSet<>();

        for (Node node : adjL.keySet()) {
            dist.put(node, Double.POSITIVE_INFINITY);
            prev.put(node, null);
        }
        dist.put(start, 0.0);

        return new SearchState(dist, prev, isVisited);
    }

    public String dijkstraUtil(Map<Node, List<Edge>> adjL, Node start, Node dest, double scaleRatio) {
        SearchState state = setup(adjL, start);

        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingDouble(state.dist::get));
        pq.add(start);

        while (!pq.isEmpty()) {
            Node u = pq.poll();
            if (state.isVisited.contains(u)) continue;
            state.isVisited.add(u);

            for (Edge edge : adjL.getOrDefault(u, List.of())) {
                Node v = (edge.node1.equals(u)) ? edge.node2 : edge.node1;
                double weight = edge.getLength(scaleRatio);
                double newDist = state.dist.get(u) + weight;

                if (newDist < state.dist.get(v)) {
                    state.dist.put(v, newDist);
                    state.prev.put(v, u);
                    pq.add(v);
                }
            }
        }

        return path(state.prev, state.dist, start, dest);
    }

    public String aStarUtil(Map<Node, List<Edge>> adjL, Node start, Node dest, double scaleRatio) {
        SearchState state = setup(adjL, start);
        Map<Node, Double> fScore = new HashMap<>();

        for (Node node : adjL.keySet()) {
            fScore.put(node, Double.POSITIVE_INFINITY);
        }
        fScore.put(start, heuristic(start, dest));

        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingDouble(fScore::get));
        pq.add(start);

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            if (current.equals(dest)) {
                return path(state.prev, state.dist, start, dest);
            }

            if (state.isVisited.contains(current)) continue;
            state.isVisited.add(current);

            for (Edge edge : adjL.getOrDefault(current, List.of())) {
                Node neighbor = (edge.node1.equals(current)) ? edge.node2 : edge.node1;
                double weight = edge.getLength(scaleRatio);
                double tentativeG = state.dist.get(current) + weight;

                if (tentativeG < state.dist.get(neighbor)) {
                    state.prev.put(neighbor, current);
                    state.dist.put(neighbor, tentativeG);
                    fScore.put(neighbor, tentativeG + heuristic(neighbor, dest));
                    pq.add(neighbor);
                }
            }
        }

        return "No Path";
    }

    public String path(Map<Node, Node> prev, Map<Node, Double> dist, Node start, Node dest) {
        if (dist.get(dest) == Double.POSITIVE_INFINITY) return "No Path";

        List<Node> route = new ArrayList<>();
        for (Node at = dest; at != null; at = prev.get(at)) {
            route.add(at);
        }
        Collections.reverse(route);

        double distance = dist.get(dest);
        int time = (int) (distance / 1.4);  // Approximate time(m/s)

        StringJoiner sj = new StringJoiner(" -> ");
        for (Node node : route) {
            sj.add(node.label);
        }

        return sj.toString() + " | distance = " + String.format("%.2f", distance) + " | time = " + time + "s";
    }

    private double heuristic(Node a, Node b) {
        double dx = a.x - b.x;
        double dy = a.y - b.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
}
