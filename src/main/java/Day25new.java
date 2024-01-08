import utils.Parser;
import utils.day25new.Graph;
import utils.day25new.Result;

import java.util.List;
import java.util.Random;

// 2023 puzzle 25
// randomly split the vertices into 2 sets and verify if we have 3 cuts
public class Day25new implements DayX {
    @Override
    public void run() {
        List<String> input = Parser.parseInputAsString("day25.txt");

        Result result;
        while(true){
            Graph g = readGraph(input);
            result = karger(g);
            if(result.nrCuts == 3){
                break;
            }
        }

        System.out.println(result.result);
    }

    private Graph readGraph(List<String> input) {
        Graph graph = new Graph();
        for (String line : input) {
            String start = line.substring(0, line.indexOf(":"));
            String[] dests = line.substring(line.indexOf(":") + 2).split(" ");
            graph.addVertex(start);
            for (String dest : dests) {
                graph.addVertex(dest);
                graph.addEdge(start, dest);
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

        long result = 0;
        if (nrCuts == 3) {
            // Number of cuts is 3, so store the result
            long size1 = v1.split("-").length;
            long size2 = v2.split("-").length;
            result = size1 * size2;
            System.out.println(v1);
            System.out.println(v2);
            System.out.println(size1);
            System.out.println(size2);
            System.out.println();
        }
        return new Result(nrCuts, result);
    }
}
