package mod.tang.dev;

import java.util.*;

public class PathChecker {
    
    public static void checkPath(int[] status, String path) {
        String cleanedPath = path.replace("Shortest Path:", "").replace("distance:", "").trim();
        
        String[] nodes = cleanedPath.split(" -> ");
        List<Integer> pathNodes = new ArrayList<>();
        
        for (String node : nodes) {
            try {
                String cleanNode = node.replaceAll("[^0-9]", "").trim();
                pathNodes.add(Integer.parseInt(cleanNode));
            } catch (NumberFormatException e) {
                System.out.println("Error parsing node: " + node);
            }
        }
        
        List<Integer> importantNodes = new ArrayList<>();
        importantNodes.add(pathNodes.get(0));
        
        for (int i = 0; i < pathNodes.size() - 1; i++) {
            int start = pathNodes.get(i);
            int end = pathNodes.get(i + 1);

            if (status[start] == 1 && status[end] == 1) {
                importantNodes.add(end);
            }
        }

        StringBuilder newPath = new StringBuilder();
        for (int i = 0; i < importantNodes.size(); i++) {
            newPath.append(importantNodes.get(i));
            if (i < importantNodes.size() - 1) {
                newPath.append(" -> ");
            }
        }
        
        System.out.println("Shortest Path: " + newPath);
    }
}