import utils.Parser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// 2023 puzzle 5
public class Day5Brute implements DayX {
    private static final List<List<Long>> seedsToSoil = new ArrayList<>();
    private static final List<List<Long>> soilToFertilizer = new ArrayList<>();
    private static final List<List<Long>> fertilizerToWater = new ArrayList<>();
    private static final List<List<Long>> waterToLight = new ArrayList<>();
    private static final List<List<Long>> lightToTemp = new ArrayList<>();
    private static final List<List<Long>> tempToHumidity = new ArrayList<>();
    private static final List<List<Long>> humidityToLoc = new ArrayList<>();

    @Override
    public void run() {
        List<String> input = Parser.parseInputAsString("day5.txt");
        List<Long> seeds = getSeeds(input.get(0));

        int i = 1;
        while (!input.get(i).startsWith("seed-to-soil map:")) {
            i++;
        }
        i++;
        while (!input.get(i).isBlank()) {
            seedsToSoil.add(convertMapping(input.get(i)));
            i++;
        }


        while (!input.get(i).startsWith("soil-to-fertilizer map:")) {
            i++;
        }
        i++;
        while (!input.get(i).isBlank()) {
            soilToFertilizer.add(convertMapping(input.get(i)));
            i++;
        }


        while (!input.get(i).startsWith("fertilizer-to-water map:")) {
            i++;
        }
        i++;
        while (!input.get(i).isBlank()) {
            fertilizerToWater.add(convertMapping(input.get(i)));
            i++;
        }


        while (!input.get(i).startsWith("water-to-light map:")) {
            i++;
        }
        i++;
        while (!input.get(i).isBlank()) {
            waterToLight.add(convertMapping(input.get(i)));
            i++;
        }


        while (!input.get(i).startsWith("light-to-temperature map:")) {
            i++;
        }
        i++;
        while (!input.get(i).isBlank()) {
            lightToTemp.add(convertMapping(input.get(i)));
            i++;
        }


        while (!input.get(i).startsWith("temperature-to-humidity map:")) {
            i++;
        }
        i++;
        while (!input.get(i).isBlank()) {
            tempToHumidity.add(convertMapping(input.get(i)));
            i++;
        }


        while (!input.get(i).startsWith("humidity-to-location map:")) {
            i++;
        }
        i++;
        while (i < input.size() && !input.get(i).isBlank()) {
            humidityToLoc.add(convertMapping(input.get(i)));
            i++;
        }

        long result = Long.MAX_VALUE;
        for(int index = 0; index < seeds.size(); index++){
            long start = seeds.get(index);
            index++;
            long range = seeds.get(index);
            System.out.println("seed " + start + " range " + range);
            System.out.println("time " + LocalDateTime.now());
            for (long s = 0; s < range; s++) {
                long loc = getLoc(start + s);
                result = Math.min(loc, result);
            }
            System.out.println(result);
        }

    }

    private List<Long> getSeeds(String str) {
        return Arrays.stream(str.split("seeds: ")[1].split(" "))
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }

    private List<Long> convertMapping(String str) {
        String[] strings = str.split(" ");
        // soil, seed, range

        List<Long> list = new ArrayList<>();
        list.add(Long.parseLong(strings[0]));
        list.add(Long.parseLong(strings[1]));
        list.add(Long.parseLong(strings[2]));
        return list;
    }

    private long getLoc(long seed) {
        long soil = seed;
        for (List<Long> seedToSoil : seedsToSoil) {
            if (seedToSoil.get(1) <= seed && seed < seedToSoil.get(1) + seedToSoil.get(2)) {
                soil = seedToSoil.get(0) + (seed - seedToSoil.get(1));
            }
        }

        long fertilizer = soil;
        for (List<Long> s2f : soilToFertilizer) {
            if (s2f.get(1) <= soil && soil < s2f.get(1) + s2f.get(2)) {
                fertilizer = s2f.get(0) + (soil - s2f.get(1));
            }
        }

        long water = fertilizer;
        for (List<Long> f2w : fertilizerToWater) {
            if (f2w.get(1) <= fertilizer && fertilizer <= f2w.get(1) + f2w.get(2)) {
                water = f2w.get(0) + (fertilizer - f2w.get(1));
            }
        }

        long light = water;
        for (List<Long> w2l : waterToLight) {
            if (w2l.get(1) <= water && water < w2l.get(1) + w2l.get(2)) {
                light = w2l.get(0) + (water - w2l.get(1));
            }
        }

        long temp = light;
        for (List<Long> l2t : lightToTemp) {
            if (l2t.get(1) <= light && light < l2t.get(1) + l2t.get(2)) {
                temp = l2t.get(0) + (light - l2t.get(1));
            }
        }

        long humidity = temp;
        for (List<Long> l2t : tempToHumidity) {
            if (l2t.get(1) <= temp && temp < l2t.get(1) + l2t.get(2)) {
                humidity = l2t.get(0) + (temp - l2t.get(1));
            }
        }

        long loc = humidity;
        for (List<Long> h2l : humidityToLoc) {
            if (h2l.get(1) <= humidity && humidity < h2l.get(1) + h2l.get(2)) {
                loc = h2l.get(0) + (humidity - h2l.get(1));
            }
        }

        return loc;
    }

}
