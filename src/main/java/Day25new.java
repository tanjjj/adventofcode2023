import utils.Parser;
import utils.day25new.Graph;
import utils.day25new.Result;

import java.util.List;
import java.util.Random;

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
                long size1 = result.group1.split("-").length;
                long size2 = result.group2.split("-").length;
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
        Graph originalGraph = graph.getCopy();

        // combine random vertices until only two vertices left.
        while (graph.vertices.keySet().size() > 2) {
            Random random = new Random();
            int r1 = random.ints(0, graph.vertices.keySet().size()).findAny().getAsInt();
            String v1 = graph.getVerticesAsArray()[r1];
            int r2 = random.ints(0, graph.vertices.get(v1).size()).findAny().getAsInt();
            String v2 = graph.vertices.get(v1).get(r2);
            graph.merge(v1, v2);
        }

        // Now count the number of edges which did exist in the original graph but do not exist in the current
        String v1 = graph.getVerticesAsArray()[0];
        String v2 = graph.getVerticesAsArray()[1];
        int cuts = 0;
        // Just count old connections between the first vertex group and the second...
        for (String part : v1.split("-")) {
            List<String> vertices = originalGraph.getAdjVertices(part);
            for (String part2 : v2.split("-")) {
                if (vertices.contains(part2)) {
                    cuts++;
                }
            }
        }
        // ...and the other way around.
        for (String part : v2.split("-")) {
            List<String> vertices = originalGraph.getAdjVertices(part);
            for (String part2 : v1.split("-")) {
                if (vertices.contains(part2)) {
                    cuts++;
                }
            }
        }

        return new Result(cuts, v1, v2);
    }
}
