package utils.day19;

import java.util.ArrayList;
import java.util.List;

public class Workflow2 {
    public final String id;
    public final List<Rule2> rules;

    public Workflow2(String workflow) {
        String[] splits = workflow.split("\\{");
        this.id = splits[0];
        String[] strs = splits[1].substring(0, splits[1].length()-1).split(",");
        this.rules = new ArrayList<>();
        for(String s : strs){
            rules.add(new Rule2(s));
        }
    }
}
