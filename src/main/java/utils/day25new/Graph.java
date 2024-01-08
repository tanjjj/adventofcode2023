package utils.day25new;

import java.util.*;

public class Graph {
    public Map<String, List<String>> vertices = new HashMap<>();

    public Graph() {

    }

    public Graph(Map<String, List<String>> vertices) {
        this.vertices = vertices;
    }

    public String[] getVerticesAsArray(){
        return vertices.keySet().toArray(new String[vertices.keySet().size()]);
    }

    public void addVertex(String vertex) {
        vertices.putIfAbsent(vertex, new ArrayList<>());
    }

    public void removeVertex(String vertex) {
        vertices.values().forEach(e -> e.remove(vertex));
        vertices.remove(vertex);
    }

    public void addEdge(String v1, String v2) {
        vertices.get(v1).add(v2);
        vertices.get(v2).add(v1);
    }

    public List<String> getAdjVertices(String label) {
        return vertices.get(label);
    }

    public void merge(String v1, String v2) {
        List<String> eV1 = vertices.get(v2);
        List<String> eV2 = vertices.get(v1);
        if (eV1 != null)
            eV1.remove(v1);
        if (eV2 != null)
            eV2.remove(v2);
        // Combines the other edges from v1 and v2 into one.
        List<String> edges1 = vertices.get(v1);
        List<String> edges2 = vertices.get(v2);
        Set<String> destinations = new HashSet<>();
        destinations.addAll(edges1);
        destinations.addAll(edges2);
        // Build the new label
        String newLabel = v1 + "-" + v2;
        addVertex(newLabel);
        for (String e : destinations) {
            addEdge(newLabel, e);
        }
        // Now remove every remaining reference to v1 or v2.
        for (Map.Entry<String, List<String>> entry : vertices.entrySet()) {
            entry.getValue().remove(v1);
            entry.getValue().remove(v2);
        }
        removeVertex(v1);
        removeVertex(v2);
    }


    public Graph getCopy() {
        Map<String, List<String>> cmap = new HashMap<>();
        cmap.putAll(vertices);
        return new Graph(cmap);
    }
}
