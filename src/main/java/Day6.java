import utils.Parser;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// 2023 puzzle 6
public class Day6 implements DayX {

    @Override
    public void run() {
        List<String> input = Parser.parseInputAsString("day6.txt");

        List<Integer> times = Arrays.stream(input.get(0).split("( )+"))
                .map(str -> str.startsWith("Time") ? null : Integer.parseInt(str))
                .collect(Collectors.toList());
        List<Integer> distances = Arrays.stream(input.get(1).split("( )+"))
                .map(str -> str.startsWith("Distance") ? null : Integer.parseInt(str))
                .collect(Collectors.toList());

        int result = 1;
        for (int i = 1; i < times.size(); i++) {
            result = result * getNumberOfSolutions(times.get(i), distances.get(i));
        }

        System.out.println(result);
    }

    private int getNumberOfSolutions(int time, int distance) {
        // x + (t-x) * x > distance
        // 12 - 44 = 33
        //37 - 60 = 24
        // 19-58 = 40
        //20-73= 54
        int max = getMaxSpeed(0, time, time, distance);
        System.out.println("time " + time + " max " + max);
        int min = getMinSpeed(0, time, time, distance, Integer.MIN_VALUE);
        System.out.println("time " + time + " min " + min);
        return max - min + 1;
    }

    // speed = time to accelerate
    private int getMaxSpeed(int mintime, int maxtime, int totaltime, int distance) {
        int timeToAcc = (maxtime - mintime) / 2 + mintime;
        if ((totaltime - timeToAcc) * timeToAcc > distance) {
            if (timeToAcc == maxtime || timeToAcc == mintime) {
                return timeToAcc;
            }
            return getMaxSpeed(timeToAcc, maxtime, totaltime, distance);
        } else {
            return getMaxSpeed(mintime, timeToAcc, totaltime, distance);
        }
    }

    private int getMinSpeed(int mintime, int maxtime, int totaltime, int distance, int minSpeed) {
        int timeToAcc = (maxtime - mintime) / 2 + mintime;
        if ((totaltime - timeToAcc) * timeToAcc > distance) {
            return getMinSpeed(mintime, timeToAcc, totaltime, distance, timeToAcc);
        } else {
            if (timeToAcc == maxtime || timeToAcc == mintime) {
                return minSpeed;
            }
            return getMinSpeed(timeToAcc, maxtime, totaltime, distance, minSpeed);
        }
    }


}
