import utils.Parser;
import utils.day25.Graph;

import java.util.*;
import java.util.stream.Collectors;

// 2023 puzzle 25
// Stoer–Wagner algorithm
// https://www.youtube.com/watch?v=AtkEpr7dsW4
public class Day25 implements DayX {
    @Override
    public void run() {
        List<String> input = Parser.parseInputAsString("test.txt");
        Graph graph = new Graph();
        for (String s : input) {
            String[] splits = s.split(": ");
            String vertex = splits[0];
            Set<String> adjacentVertices = Arrays.stream(splits[1].split(" "))
                    .collect(Collectors.toSet());
            graph.addVertex(vertex, adjacentVertices);

            for (String adjacentVertex : adjacentVertices) {
                graph.addEdgeWeight(vertex, adjacentVertex);
            }
        }

        int totalVertices = graph.vertices.size();
        String start = "jqt"; // the first vertex from the input file, can be any vertex
        //String start = "vtj"; // the first vertex from the input file, can be any vertex

        int result = 0;
        while (graph.vertices.size() > 1) {
            int cutSize = nextPhase(graph, start);
            if (cutSize == 3) {
                int group1 = graph.vertices.size() - 1;
                int group2 = totalVertices - group1;
                result = group1 * group2;
                break;
            }
        }

        System.out.println(result);
    }

    private int nextPhase(Graph graph, String start) {
        Set<String> unvisited = new HashSet<>(graph.vertices.keySet());
        Set<String> visited = new HashSet<>();
        visited.add(start);
        unvisited.removeAll(visited);
        String lastPicked = null;
        while (unvisited.size() > 1) {
            int maxWeight = 0;
            String vertexWithMaxWeight = null;
            for (String vertex : visited) {
                Set<String> adjacentVertices = graph.vertices.get(vertex);
                for (String adjacentVertex : adjacentVertices) {
                    if (!visited.contains(adjacentVertex)) {
                        int weight = graph.edgeWeight.get(vertex + "+" + adjacentVertex);
                        if (maxWeight < weight) {
                            maxWeight = weight;
                            vertexWithMaxWeight = adjacentVertex;
                        }
                    }
                }
            }
            lastPicked = vertexWithMaxWeight;
            unvisited.remove(vertexWithMaxWeight);
            visited.add(vertexWithMaxWeight);
        }

        String vertexToCutOff = unvisited.iterator().next();

        // merge the last 2 nodes, unvisted and lastPicked
        mergeVertices(vertexToCutOff, lastPicked, graph);

        int cutSize = graph.edgeWeight.get(vertexToCutOff + "+" + lastPicked);
        return cutSize;
    }

    private void mergeVertices(String vertexA, String vertexB, Graph graph) {
        Set<String> adjacentVertices = graph.vertices.get(vertexA);
        adjacentVertices.addAll(graph.vertices.get(vertexB));

        graph.removeVertex(vertexA);
        graph.removeVertex(vertexB);

        String newVertex = vertexA + "-" + vertexB;
        graph.addVertex(newVertex, adjacentVertices);
        for(String a : adjacentVertices){
            graph.addEdgeWeight(newVertex, a);
        }
    }
}
