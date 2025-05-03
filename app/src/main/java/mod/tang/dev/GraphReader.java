package mod.tang.dev;

import org.json.JSONArray;
import org.json.JSONObject;
// import org.w3c.dom.*;
// import javax.xml.parsers.DocumentBuilderFactory;
// import javax.xml.parsers.DocumentBuilder;

// import java.io.ByteArrayInputStream;
// import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class GraphReader {
    private static final int INF = Integer.MAX_VALUE;

    public static Object[] readJson(String jsonStr) {
        JSONObject obj = new JSONObject(jsonStr);
        int n = obj.length() - 1;
        int[][] matrix = initMatrix(n);
        boolean[] status = new boolean[n];
        
        JSONObject statusJson = obj.getJSONObject("isSpecial");
        
        for (int i = 0; i < n; i++) {
            status[i] = statusJson.getBoolean(String.valueOf(i));
        }
    
        for (String key : obj.keySet()) {
            if ("status".equals(key)) continue;
            int from = Integer.parseInt(key);
            JSONArray neighbors = obj.getJSONArray(key);
            
            for (int i = 0; i < neighbors.length(); i++) {
                JSONObject edge = neighbors.getJSONObject(i);
                int to = edge.optInt("to", -1);
                if (to == -1) continue;
    
                int weight = edge.optInt("weight", -1);
                if (weight == -1) continue;
    
                matrix[from][to] = weight;
                matrix[to][from] = weight;
            }
        }
        
        return new Object[]{matrix, status};
    }    

    // public static Object[] readXml(String xmlStr) {
    //     try {
    //         DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    //         DocumentBuilder builder = factory.newDocumentBuilder();
    //         ByteArrayInputStream input = new ByteArrayInputStream(xmlStr.getBytes(StandardCharsets.UTF_8));
    //         Document doc = builder.parse(input);

    //         NodeList nodeList = doc.getElementsByTagName("node");
    //         int n = nodeList.getLength();
    //         int[][] matrix = initMatrix(n);
    //         int[] statuses = new int[n];

    //         for (int i = 0; i < n; i++) {
    //             Element nodeElement = (Element) nodeList.item(i);
    //             int from = Integer.parseInt(nodeElement.getAttribute("id"));

    //             statuses[from] = Integer.parseInt(nodeElement.getAttribute("status"));

    //             NodeList edges = nodeElement.getElementsByTagName("edge");
    //             for (int j = 0; j < edges.getLength(); j++) {
    //                 Element edgeElement = (Element) edges.item(j);
    //                 int to = Integer.parseInt(edgeElement.getAttribute("to"));
    //                 int weight = Integer.parseInt(edgeElement.getAttribute("weight"));

    //                 matrix[from][to] = weight;
    //                 matrix[to][from] = weight;
    //             }
    //         }
    //         return new Object[]{matrix, statuses};

    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return null;
    //     }
    // }

    // public int calDistance(){
        

    //     return 0;
    // }

    private static int[][] initMatrix(int n) {
        int[][] matrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(matrix[i], INF);
            matrix[i][i] = 0;
        }
        return matrix;
    }
}
