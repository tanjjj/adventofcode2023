import utils.Parser;
import utils.day25.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// 2023 puzzle 25
public class Day25 implements DayX {
    @Override
    public void run() {
        List<String> input = Parser.parseInputAsString("test.txt");
        Graph graph = new Graph();
        for (String s : input) {
            String[] splits = s.split(": ");
            String vertex = splits[0];
            List<String> adjacentVertices = Arrays.stream(splits[1].split(" "))
                    .collect(Collectors.toList());
            graph.addVertex(vertex, adjacentVertices);

            for (String adjacentVertex : adjacentVertices) {
                graph.addVertex(adjacentVertex, List.of(vertex));
            }
        }


    }


}
