import java.util.ArrayList;
import java.util.List;

// 2020 puzzle 1
public class Day0 implements DayX {
    @Override
    public void run() {
        List<Integer> input = Parser.parseInput("dat0.txt");
        //System.out.println(input);

        List<Integer> result = get2020(input);
        System.out.println(result);
    }


    public List<Integer> get2020(List<Integer> input){
        return new ArrayList<>();
    }
}
