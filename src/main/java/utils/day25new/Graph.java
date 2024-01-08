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

    public List<String> getAdjVertices(String label) {
        return vertices.get(label);
    }

    public void merge(String v1, String v2) {
        List<String> edges1 = vertices.get(v1);
        List<String> edges2 = vertices.get(v2);
        Set<String> allEdges = new HashSet<>();
        allEdges.addAll(edges1);
        allEdges.addAll(edges2);
        allEdges.remove(v1);
        allEdges.remove(v2);

        String newVertex = v1 + "-" + v2;
        addVertex(newVertex);
        for (String e : allEdges) {
            addEdge(newVertex, e);
        }
        // remove reference to v1 and v2.
        vertices.remove(v1);
        vertices.remove(v2);
        for (Map.Entry<String, List<String>> entry : vertices.entrySet()) {
            entry.getValue().remove(v1);
            entry.getValue().remove(v2);
        }
    }
}
