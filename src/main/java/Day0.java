import utils.Parser;

import java.util.ArrayList;
import java.util.List;

// 2020 puzzle 1
public class Day0 implements DayX {
    @Override
    public void run() {
        List<Integer> input = Parser.parseInputAsInt("dat0.txt");
        //System.out.println(input);

        List<Integer> result = get2020(input);
        System.out.println(result);

        System.out.println(result.get(0) * result.get(1));
    }

    private List<Integer> get2020(List<Integer> input) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            int x = 2020-input.get(i);
            for(int j = i+1; j < input.size(); j++){
              if(input.get(j) == x){
                  result.add(input.get(i));
                  result.add(input.get(j));
                  break;
              }
            }
        }

        return result;
    }
}
