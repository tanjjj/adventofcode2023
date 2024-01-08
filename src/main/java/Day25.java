import utils.Parser;
import utils.day25.Graph;
import utils.day25.PhaseResult;

import java.util.*;
import java.util.stream.Collectors;

// 2023 puzzle 25
// Stoer–Wagner algorithm
// https://www.youtube.com/watch?v=AtkEpr7dsW4
public class Day25 implements DayX {
    @Override
    public void run() {
        List<String> input = Parser.parseInputAsString("day25.txt");
        Graph graph = new Graph();
        for (String s : input) {
            String[] splits = s.split(": ");
            String vertex = splits[0];
            Set<String> adjacentVertices = Arrays.stream(splits[1].split(" "))
                    .collect(Collectors.toSet());
            graph.addVertex(vertex, adjacentVertices);

            for (String adjacentVertex : adjacentVertices) {
                graph.addEdgeWeight(vertex, adjacentVertex, 1);
            }
        }

        int totalVertices = graph.vertices.size();
        //String start = "jqt"; // the first vertex from the test input file, can be any vertex
        String start = "vtj"; // the first vertex from the input file day25.txt, can be any vertex

        int result = 0;
        while (graph.vertices.size() > 1) {
            PhaseResult phaseResult = nextPhase(graph, start);
            if (phaseResult.cutSize == 3) {
                int group1 = phaseResult.vertexToCutOff.split("-").length;
                int group2 = totalVertices - group1;
                result = group1 * group2;
                break;
            }
            if (graph.vertices.size() == 2) {
                break;
            }
        }

        System.out.println(result);
    }

    private PhaseResult nextPhase(Graph graph, String start) {
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

        int cutSize = 0;
        for (String adj : graph.vertices.get(vertexToCutOff)) {
            cutSize += graph.edgeWeight.get(vertexToCutOff + "+" + adj);
        }

        if (lastPicked != null) {
            // merge the last 2 nodes, unvisted and lastPicked
            mergeVertices(vertexToCutOff, lastPicked, graph);
            System.out.println("vertexToCutOff " + vertexToCutOff);
            System.out.println("lastPicked " + lastPicked);
            System.out.println("cutSize " + cutSize);
            System.out.println();
        }

        return new PhaseResult(vertexToCutOff, cutSize);
    }

    private void mergeVertices(String vertexA, String vertexB, Graph graph) {
        Set<String> adjacentVerticesA = graph.vertices.get(vertexA);
        adjacentVerticesA.remove(vertexB);
        Set<String> adjacentVerticesB = graph.vertices.get(vertexB);
        adjacentVerticesB.remove(vertexA);
        Set<String> allAdjacentVertices = new HashSet<>();
        allAdjacentVertices.addAll(adjacentVerticesA);
        allAdjacentVertices.addAll(adjacentVerticesB);

        graph.removeVertex(vertexA);
        graph.removeVertex(vertexB);

        String newVertex = vertexA + "-" + vertexB;
        graph.addVertex(newVertex, allAdjacentVertices);
        for (String a : adjacentVerticesA) {
            int weight = graph.edgeWeight.get(vertexA + "+" + a);
            weight += graph.edgeWeight.getOrDefault(vertexB + "+" + a, 0);
            graph.addEdgeWeight(newVertex, a, weight);
        }
        adjacentVerticesB.removeAll(adjacentVerticesA);
        for (String b : adjacentVerticesB) {
            int weight = graph.edgeWeight.get(vertexB + "+" + b);
            graph.addEdgeWeight(newVertex, b, weight);
        }
    }
}
