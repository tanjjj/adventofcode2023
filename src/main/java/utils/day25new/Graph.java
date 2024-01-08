package utils.day25new;

import java.util.*;

public class Graph {
    public Map<String, List<String>> adjVertices = new HashMap<>();

    public Graph() {

    }

    public Graph(Map<String, List<String>> adjVertices) {
        this.adjVertices = adjVertices;
    }

    public void addVertex(String label) {
        adjVertices.putIfAbsent(label, new ArrayList<>());
    }

    public void removeVertex(String label) {
        adjVertices.values().forEach(e -> e.remove(label));
        adjVertices.remove(label);
    }

    public void addEdge(String label1, String label2) {
        adjVertices.get(label1).add(label2);
        adjVertices.get(label2).add(label1);
    }

    public void removeEdge(String label1, String label2) {
        List<String> eV1 = adjVertices.get(label1);
        List<String> eV2 = adjVertices.get(label2);
        if (eV1 != null)
            eV1.remove(label2);
        if (eV2 != null)
            eV2.remove(label1);
    }

    public List<String> getAdjVertices(String label) {
        return adjVertices.get(label);
    }

    public void merge(String v1, String v2) {
        // Remove the old edges between v1 and v2
        removeEdge(v1, v2);
        removeEdge(v2, v1);
        // Combines the other edges from v1 and v2 into one.
        List<String> edges1 = adjVertices.get(v1);
        List<String> edges2 = adjVertices.get(v2);
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
        for (Map.Entry<String, List<String>> entry : adjVertices.entrySet()) {
            entry.getValue().remove(v1);
            entry.getValue().remove(v2);
        }
        removeVertex(v1);
        removeVertex(v2);
    }


    public Graph getCopy() {
        Map<String, List<String>> cmap = new HashMap<>();
        cmap.putAll(adjVertices);
        return new Graph(cmap);
    }
}
