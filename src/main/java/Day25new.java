import utils.Parser;
import utils.day25new.Graph;
import utils.day25new.Result;

import java.util.*;

// 2023 puzzle 25
// Karger's algorithm
// https://www.youtube.com/watch?v=c75gg0wicus
public class Day25new implements DayX {
    @Override
    public void run() {
        List<String> input = Parser.parseInputAsString("day25.txt");
        while (true) {
            Graph g = readGraph(input);
            Result result = karger(g);
            if (result.cuts == 3) {
                long size1 = result.group1.length;
                long size2 = result.group2.length;
                System.out.println(size1 * size2);
                break;
            }
        }
    }

    private Graph readGraph(List<String> input) {
        Graph graph = new Graph();
        for (String s : input) {
            String[] splits = s.split(": ");
            String vertex = splits[0];
            String[] adjacent = splits[1].split(" ");
            graph.addVertex(vertex);
            for (String adj : adjacent) {
                graph.addVertex(adj);
                graph.addEdge(vertex, adj);
            }
        }
        return graph;
    }

    public Result karger(Graph graph) {
        Graph originalGraph = new Graph(new HashMap<>(graph.vertices));

        // combine vertices randomly until only two vertices left.
        while (graph.vertices.keySet().size() > 2) {
            Random random = new Random();
            int r1 = random.ints(0, graph.vertices.keySet().size()).findAny().getAsInt();
            String v1 = graph.getVerticesAsArray()[r1];
            int r2 = random.ints(0, graph.vertices.get(v1).size()).findAny().getAsInt();
            String v2 = graph.vertices.get(v1).get(r2);
            graph.merge(v1, v2);
        }

        // count the number of edges which existed in the original graph but not in the current graph
        String[] group1 = graph.getVerticesAsArray()[0].split("-");
        String[] group2 = graph.getVerticesAsArray()[1].split("-");
        long cuts = 0;
        for (String v1 : group1) {
            List<String> adjacentVertices = originalGraph.getAdjacentVertices(v1);
            cuts += Arrays.stream(group2)
                    .filter(adjacentVertices::contains)
                    .count();
        }
        for (String v2 : group2) {
            List<String> adjacentVertices2 = originalGraph.getAdjacentVertices(v2);
            cuts += Arrays.stream(group1)
                    .filter(adjacentVertices2::contains)
                    .count();
        }

        return new Result(cuts, group1, group2);
    }
}
