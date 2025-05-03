package mod.tang.dev;

import java.util.*;

public class PathChecker {

    public static void checkPath(boolean[] status, String path) {
        String cleanPath = path.split("\\|")[0].trim();  // จะได้ "0 -> 9 -> 10"
    
        // แยกตาม " -> "
        String[] nodes = cleanPath.split(" -> ");
        List<Integer> pathNodes = new ArrayList<>();
        
        for (String node : nodes) {
            try {
                pathNodes.add(Integer.parseInt(node.trim()));
            } catch (NumberFormatException e) {
                System.out.println("Invalid node: " + node);
            }
        }

        List<Integer> markNodes = new ArrayList<>();
        markNodes.add(pathNodes.get(0));
        
        for (int i = 0; i < pathNodes.size() - 1; i++) {
            int start = pathNodes.get(i);
            int end = pathNodes.get(i + 1);

            if (status[start] == true && status[end] == true) {
                markNodes.add(end);
            }
        }

        StringBuilder newPath = new StringBuilder();
        for (int i = 0; i < markNodes.size(); i++) {
            newPath.append(markNodes.get(i));
            if (i < markNodes.size() - 1) {
                newPath.append(" -> ");
            }
        }

        System.out.println("Shortest Path(Important-Node): " + newPath);
    }
}