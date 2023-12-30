package utils.day19;

public class Rule2 {
    public final String operator;
    public final int value;
    public final String catagory;
    public final String result;

    public Rule2(String rule) {
        if (rule.contains(">")) {
            this.catagory = rule.substring(0, 1);
            rule = rule.substring(2);
            String[] splits = rule.split(":");
            this.value = Integer.parseInt(splits[0]);
            this.result = splits[1];
            this.operator = ">";
        } else if (rule.contains("<")) {
            this.catagory = rule.substring(0, 1);
            rule = rule.substring(2);
            String[] splits = rule.split(":");
            this.value = Integer.parseInt(splits[0]);
            this.result = splits[1];
            this.operator = "<";
        } else {
            this.operator = "null";
            this.value = 0;
            this.catagory = "";
            this.result = rule;
        }
    }

}
