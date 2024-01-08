package utils.day25new;

import java.util.*;

public class Graph {
    public Map<String, List<String>> vertices = new HashMap<>();

    public Graph() {

    }

    public Graph(Map<String, List<String>> vertices) {
        this.vertices = vertices;
    }

    public String[] getVerticesAsArray() {
        return vertices.keySet().toArray(new String[vertices.keySet().size()]);
    }

    public void addVertex(String vertex) {
        vertices.putIfAbsent(vertex, new ArrayList<>());
    }

    public void addEdge(String v1, String v2) {
        vertices.get(v1).add(v2);
        vertices.get(v2).add(v1);
    }

    public List<String> getAdjacentVertices(String label) {
        return vertices.get(label);
    }

    public void merge(String v1, String v2) {
        String newVertex = v1 + "-" + v2;
        addVertex(newVertex);

        List<String> adj1 = vertices.get(v1);
        adj1.remove(v2);
        List<String> adj2 = vertices.get(v2);
        adj2.remove(v1);
        Set<String> allAdjacentVertices = new HashSet<>();
        allAdjacentVertices.addAll(adj1);
        allAdjacentVertices.addAll(adj2);
        allAdjacentVertices.forEach(a -> addEdge(newVertex, a));

        // remove reference to v1 and v2.
        vertices.remove(v1);
        vertices.remove(v2);
        for (List<String> list : vertices.values()) {
            list.remove(v1);
            list.remove(v2);
        }
    }
}
