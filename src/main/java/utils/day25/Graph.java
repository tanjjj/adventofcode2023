package utils.day25;

import java.util.*;

public class Graph {
    public final Map<String, Set<String>> vertices = new HashMap<>();
    public final Map<String, Integer> edgeWeight = new HashMap<>();
    // WARNING: for convenience, this weight map may contain edges that no longer exists, and it contains both the weight of A->B and the weight of B->A

    public void addVertex(String vertex, List<String> adjacentVertices) {
        Set<String> adjacentList = vertices.getOrDefault(vertex, new HashSet<>());
        adjacentList.addAll(adjacentVertices);
        vertices.put(vertex, adjacentList);
        for (String adjacentVertex : adjacentList) {
            edgeWeight.put(vertex + "+" + adjacentVertex, 1);
            edgeWeight.put(adjacentVertex + "+" + vertex, 1);
        }
    }

}