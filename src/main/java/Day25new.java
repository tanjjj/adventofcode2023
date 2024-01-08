import utils.Parser;
import utils.day25new.Graph;
import utils.day25new.Result;

import java.util.List;
import java.util.Random;

// 2023 puzzle 25
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
            for (String dest : adjacent) {
                graph.addVertex(dest);
                graph.addEdge(vertex, dest);
            }
        }
        return graph;
    }

    public Result karger(Graph graph) {
        // Store the original configuration of this graph.
        Graph copy = graph.getCopy();

        // First combine random vertices until we have only two vertices left.
        while (graph.adjVertices.keySet().size() > 2) {
            Random random = new Random();
            int r = random.ints(0, graph.adjVertices.keySet().size()).findAny().getAsInt();
            String v = graph.adjVertices.keySet().toArray(new String[graph.adjVertices.keySet().size()])[r];
            int r2 = random.ints(0, graph.adjVertices.get(v).size()).findAny().getAsInt();
            String v2 = graph.adjVertices.get(v).get(r2);

            graph.merge(v, v2);
        }

        // Now count the number of edges which did exist in the original graph but do
        // not exist in the current.
        // Combined vertices have the format <old vertex label1>-<old vertex
        // label2>-<old vertex label3> etc.
        String v1 = graph.adjVertices.keySet().toArray(new String[graph.adjVertices.keySet().size()])[0];
        String v2 = graph.adjVertices.keySet().toArray(new String[graph.adjVertices.keySet().size()])[1];
        int nrCuts = 0;
        // Just count old connections between the first vertex group and the second...
        for (String part : v1.split("-")) {
            List<String> vertices = copy.getAdjVertices(part);
            for (String part2 : v2.split("-")) {
                if (vertices.contains(part2)) {
                    nrCuts++;
                }
            }
        }
        // ...and the other way around.
        for (String part : v2.split("-")) {
            List<String> vertices = copy.getAdjVertices(part);
            for (String part2 : v1.split("-")) {
                if (vertices.contains(part2)) {
                    nrCuts++;
                }
            }
        }

        return new Result(nrCuts, v1, v2);
    }
}
