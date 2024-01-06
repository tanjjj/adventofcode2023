import utils.Parser;
import utils.day19.*;

import java.util.*;

// 2023 puzzle 19 part 2
public class Day19B implements DayX {
    private static final String START = "in";

    @Override
    public void run() {
        List<String> input = Parser.parseInputAsString("day19.txt");

        Map<String, Workflow2> workflows = new HashMap<>();
        int i = 0;
        while (i < input.size()) {
            if (input.get(i).isEmpty()) {
                break;
            }
            Workflow2 workflow = new Workflow2(input.get(i));
            workflows.put(workflow.id, workflow);
            i++;
        }


        RatingRange rating = new RatingRange(
                Arrays.asList(1, 4000),
                Arrays.asList(1, 4000),
                Arrays.asList(1, 4000),
                Arrays.asList(1, 4000));
        long result = process(rating, workflows.get(START), workflows);

        System.out.println(result);
    }

    private long process(RatingRange ratingRange, Workflow2 current, Map<String, Workflow2> workflows) {
        long result = 0;
        for (Rule2 rule : current.rules) {
            if (rule.operator.equals("null")) {
                return processResult(ratingRange, workflows, rule);
            } else if (rule.operator.equals(">")) {
                List<Integer> range = ratingRange.rating.get(rule.category);
                if (range.get(0) > rule.value) {
                    return processResult(ratingRange, workflows, rule);
                } else if (range.get(range.size() - 1) < rule.value) {
                    continue; // continue with next rule
                } else if (range.get(0) < rule.value && rule.value < range.get(range.size() - 1)) {
                    List<RatingRange> newRanges = splitRange(ratingRange, rule);
                    ratingRange = newRanges.get(0); // process the range that doesn't meet condition with next rule
                    result += processResult(newRanges.get(1), workflows, rule);
                }
            } else if (rule.operator.equals("<")) {
                List<Integer> range = ratingRange.rating.get(rule.category);
                if (range.get(range.size() - 1) < rule.value) {
                    return processResult(ratingRange, workflows, rule);
                } else if (range.get(0) > rule.value) {
                    continue; // continue with next rule
                } else if (range.get(0) < rule.value && rule.value < range.get(range.size() - 1)) {
                    List<RatingRange> newRanges = splitRange(ratingRange, rule);
                    result += processResult(newRanges.get(0), workflows, rule);
                    ratingRange = newRanges.get(1); // process the range that doesn't meet condition with next rule
                }
            }
        }

        return result;
    }

    private long processResult(RatingRange ratingRange, Map<String, Workflow2> workflows, Rule2 rule) {
        if (rule.result.equals("R")) {
            return 0;
        } else if (rule.result.equals("A")) {
            return ratingRange.getTotal();
        } else {
            return process(ratingRange, workflows.get(rule.result), workflows);
        }
    }

    // split range by threshold
    private List<RatingRange> splitRange(RatingRange range, Rule2 rule) {
        List<Integer> split1 = new ArrayList<>();
        List<Integer> split2 = new ArrayList<>();
        List<Integer> oldRange = range.rating.get(rule.category);
        if (rule.operator.equals(">")) {
            split1.add(oldRange.get(0));
            split1.add(rule.value);
            split2.add(rule.value + 1);
            split2.add(oldRange.get(oldRange.size() - 1));
        } else if (rule.operator.equals("<")) {
            split1.add(oldRange.get(0));
            split1.add(rule.value - 1);
            split2.add(rule.value);
            split2.add(oldRange.get(oldRange.size() - 1));
        }
        List<Integer> x1 = rule.category.equals("x") ? split1 : range.rating.get("x");
        List<Integer> m1 = rule.category.equals("x") ? split1 : range.rating.get("m");
        List<Integer> a1 = rule.category.equals("x") ? split1 : range.rating.get("a");
        List<Integer> s1 = rule.category.equals("x") ? split1 : range.rating.get("s");

        List<Integer> x2 = rule.category.equals("x") ? split2 : range.rating.get("x");
        List<Integer> m2 = rule.category.equals("x") ? split2 : range.rating.get("m");
        List<Integer> a2 = rule.category.equals("x") ? split2 : range.rating.get("a");
        List<Integer> s2 = rule.category.equals("x") ? split2 : range.rating.get("s");
        return List.of(
                new RatingRange(x1, m1, a1, s1),
                new RatingRange(x2, m2, a2, s2)
        );
    }
}
