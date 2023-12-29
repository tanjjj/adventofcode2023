package utils.day19;

import java.util.function.Function;

public class Rule {
    private final Function<Rating, Boolean> condition;
    public final String result;

    public Rule(String rule) {
        if (rule.contains(">")) {
            String catagory = rule.substring(0, 1);
            rule = rule.substring(2);
            String[] splits = rule.split(":");
            int value = Integer.parseInt(splits[0]);
            this.result = splits[1];
            this.condition = r -> r.rating.get(catagory) > value;
        } else if (rule.contains("<")) {
            String catagory = rule.substring(0, 1);
            rule = rule.substring(2);
            String[] splits = rule.split(":");
            int value = Integer.parseInt(splits[0]);
            this.result = splits[1];
            this.condition = r -> r.rating.get(catagory) < value;
        } else {
            this.condition = r -> true;
            this.result = rule;
        }
    }

    public boolean conditionMeet(Rating rating) {
        return condition.apply(rating);
    }
}
