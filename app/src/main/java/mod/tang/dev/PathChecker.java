package mod.tang.dev;

import java.util.*;

public class PathChecker {
    
    public static void checkPath(int[] status, String path) {
        // ลบ "Shortest Path:" และ "distance:" ออกจาก path ก่อน
        String cleanedPath = path.replace("Shortest Path:", "").replace("distance:", "").trim();
        
        // แยกโหนดต่างๆ โดยใช้ "->"
        String[] nodes = cleanedPath.split(" -> ");
        List<Integer> pathNodes = new ArrayList<>();
        
        // แปลงเป็น Integer และเพิ่มลงใน pathNodes
        for (String node : nodes) {
            try {
                // แปลงเป็นตัวเลข (ลบช่องว่างและเครื่องหมาย | ถ้ามี)
                String cleanNode = node.replaceAll("[^0-9]", "").trim();
                pathNodes.add(Integer.parseInt(cleanNode));
            } catch (NumberFormatException e) {
                System.out.println("Error parsing node: " + node);
            }
        }
        
        // คำนวณเส้นทางใหม่โดยลบโหนดที่มีสถานะเป็น 0
        List<Integer> importantNodes = new ArrayList<>();
        importantNodes.add(pathNodes.get(0));  // เพิ่มโหนดเริ่มต้น
        
        // ตรวจสอบทุกคู่โหนดในเส้นทาง
        for (int i = 0; i < pathNodes.size() - 1; i++) {
            int start = pathNodes.get(i);
            int end = pathNodes.get(i + 1);

            // ตรวจสอบสถานะของโหนดเริ่มต้นและโหนดปลายทาง
            if (status[start] == 1 && status[end] == 1) {
                importantNodes.add(end);  // ถ้าโหนดมีสถานะ 1 ให้นำโหนดนี้ไปไว้ในเส้นทาง
            }
        }

        // สร้างเส้นทางใหม่จากโหนดสำคัญ
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