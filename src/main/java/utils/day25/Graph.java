package utils.day25;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
    public final Map<String, List<String>> vertices = new HashMap<>();

    public void addVertex(String vertex, List<String> adjacentVertices){
        List<String> adjacentList = vertices.getOrDefault(vertex, new ArrayList<>());
        adjacentList.addAll(adjacentVertices);
        vertices.put(vertex, adjacentList);
    }

}