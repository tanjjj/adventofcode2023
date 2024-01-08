package utils.day25;

import java.util.*;

public class Graph {
    public final Map<String, Set<String>> vertices = new HashMap<>();
    public final Map<String, Integer> edgeWeight = new HashMap<>();
    // WARNING: for convenience, this weight map may contain edges that no longer exists, and it contains both the weight of A->B and the weight of B->A

    public void addVertex(String vertex, Set<String> adjacentVertices) {
        Set<String> adjacentList = vertices.getOrDefault(vertex, new HashSet<>());
        adjacentList.addAll(adjacentVertices);
        vertices.put(vertex, adjacentList);


        for (String adjacentVertex : adjacentVertices) {
            adjacentList = vertices.getOrDefault(adjacentVertex, new HashSet<>());
            adjacentList.add(vertex);
            vertices.put(adjacentVertex, adjacentList);
        }
    }

    public void addEdgeWeight(String vertexA, String vertexB, int weight) {
        edgeWeight.put(vertexA + "+" + vertexB, weight);
        edgeWeight.put(vertexB + "+" + vertexA, weight);
    }

    public void removeVertex(String vertex) {
        vertices.remove(vertex);
        for (Set<String> adjacentVertices : vertices.values()) {
            adjacentVertices.remove(vertex);
        }
    }

}