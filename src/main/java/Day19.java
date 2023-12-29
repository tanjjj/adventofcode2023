import utils.Parser;
import utils.day19.Rating;
import utils.day19.Rule;
import utils.day19.Workflow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 2023 puzzle 19
public class Day19 implements DayX {
    private static final String START = "in";

    @Override
    public void run() {
        List<String> input = Parser.parseInputAsString("day19.txt");

        Map<String, Workflow> workflows = new HashMap<>();
        int i = 0;
        while (i < input.size()) {
            if (input.get(i).isEmpty()) {
                break;
            }
            Workflow workflow = new Workflow(input.get(i));
            workflows.put(workflow.id, workflow);
            i++;
        }

        List<Rating> ratings = new ArrayList<>();
        i++;
        while (i < input.size()) {
            ratings.add(new Rating(input.get(i)));
            i++;
        }

        List<Rating> acceptedRatings = new ArrayList<>();
        for (Rating rating : ratings) {
            boolean accepted = process(rating, workflows.get(START), workflows);
            if (accepted) {
                acceptedRatings.add(rating);
            }
        }

        int result = 0;
        for (Rating rating : acceptedRatings) {
            result += rating.getTotal();
        }

        System.out.println(result);
    }

    private boolean process(Rating rating, Workflow current, Map<String, Workflow> workflows) {
        for (Rule rule : current.rules) {
            boolean conditionMeet = rule.conditionMeet(rating);
            if (conditionMeet) {
                if (rule.result.equals("R")) {
                    return false;
                } else if (rule.result.equals("A")) {
                    return true;
                } else {
                    return process(rating, workflows.get(rule.result), workflows);
                }
            }
        }
        throw new RuntimeException("unable to process");
    }
}
