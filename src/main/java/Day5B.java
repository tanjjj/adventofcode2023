import utils.Parser;

import java.util.*;
import java.util.stream.Collectors;

// 2023 puzzle 5
public class Day5B implements DayX {
    private final List<List<Long>> seedsToSoil = new ArrayList<>();
    private final List<List<Long>> soilToFertilizer = new ArrayList<>();
    private final List<List<Long>> fertilizerToWater = new ArrayList<>();
    private final List<List<Long>> waterToLight = new ArrayList<>();
    private final List<List<Long>> lightToTemp = new ArrayList<>();
    private final List<List<Long>> tempToHumidity = new ArrayList<>();
    private final List<List<Long>> humidityToLoc = new ArrayList<>();

    @Override
    public void run() {
        List<String> input = Parser.parseInputAsString("day5.txt");
        List<Long> seeds = getSeeds(input.get(0));
        parseMaps(input);

        List<List<Long>> seedRanges = new ArrayList<>();
        for (int s = 0; s < seeds.size(); s++) {
            long start = seeds.get(s);
            long range = seeds.get(++s);
            seedRanges.add(Arrays.asList(start, range));
        }

        List<List<Long>> locations = getLocations(seedRanges);
        long result = Long.MAX_VALUE;
        for (List<Long> location : locations) {
            result = Math.min(location.get(0), result);
        }

        System.out.println(result);
    }

    private List<Long> getSeeds(String str) {
        return Arrays.stream(str.split("seeds: ")[1].split(" "))
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }

    private void parseMaps(List<String> input) {
        int i = 1;
        i = getMappings(input, i, "seed-to-soil map:", seedsToSoil);
        i = getMappings(input, i, "soil-to-fertilizer map:", soilToFertilizer);
        i = getMappings(input, i, "fertilizer-to-water map:", fertilizerToWater);
        i = getMappings(input, i, "water-to-light map:", waterToLight);
        i = getMappings(input, i, "light-to-temperature map:", lightToTemp);
        i = getMappings(input, i, "temperature-to-humidity map:", tempToHumidity);
        getMappings(input, i, "humidity-to-location map:", humidityToLoc);
    }

    private int getMappings(List<String> input, int i, String prefix, List<List<Long>> soilToFertilizer) {
        while (!input.get(i).startsWith(prefix)) {
            i++;
        }
        i++;
        while (i < input.size() && !input.get(i).isBlank()) {
            soilToFertilizer.add(convertMapping(input.get(i)));
            i++;
        }
        return i;
    }

    private List<Long> convertMapping(String str) {
        String[] strings = str.split(" ");
        // destination start, source start, source length

        // diff, source start, source length
        List<Long> list = new ArrayList<>();
        list.add(Long.parseLong(strings[0]) - Long.parseLong(strings[1]));
        list.add(Long.parseLong(strings[1]));
        list.add(Long.parseLong(strings[2]));
        return list;
    }

    private List<List<Long>> getLocations(List<List<Long>> seedRanges) {
        System.out.println(seedRanges);
        List<List<Long>> soilRanges = new ArrayList<>();
        for (List<Long> seedRange : seedRanges) {
            getDestinationRangesForAGivenRange(seedRange.get(0), seedRange.get(1), soilRanges, seedsToSoil);
        }
        System.out.println(soilRanges);

        List<List<Long>> fertilizerRanges = new ArrayList<>();
        for (List<Long> soilRange : soilRanges) {
            getDestinationRangesForAGivenRange(soilRange.get(0), soilRange.get(1), fertilizerRanges, soilToFertilizer);
        }
        System.out.println(fertilizerRanges);

        List<List<Long>> waterRanges = new ArrayList<>();
        for (List<Long> fertilizerRange : fertilizerRanges) {
            getDestinationRangesForAGivenRange(fertilizerRange.get(0), fertilizerRange.get(1), waterRanges, fertilizerToWater);
        }
        System.out.println(waterRanges);

        List<List<Long>> lightRanges = new ArrayList<>();
        for (List<Long> waterRange : waterRanges) {
            getDestinationRangesForAGivenRange(waterRange.get(0), waterRange.get(1), lightRanges, waterToLight);
        }
        System.out.println(lightRanges);

        List<List<Long>> tempRanges = new ArrayList<>();
        for (List<Long> lightRange : lightRanges) {
            getDestinationRangesForAGivenRange(lightRange.get(0), lightRange.get(1), tempRanges, lightToTemp);
        }
        System.out.println(tempRanges);

        List<List<Long>> humidityRanges = new ArrayList<>();
        for (List<Long> tempRange : tempRanges) {
            getDestinationRangesForAGivenRange(tempRange.get(0), tempRange.get(1), humidityRanges, tempToHumidity);
        }
        System.out.println(humidityRanges);

        List<List<Long>> locRanges = new ArrayList<>();
        for (List<Long> humidityRange : humidityRanges) {
            getDestinationRangesForAGivenRange(humidityRange.get(0), humidityRange.get(1), locRanges, humidityToLoc);
        }
        System.out.println(locRanges);

        return locRanges;
    }

    private List<List<Long>> getDestinationRangesForAGivenRange(long start, long range, List<List<Long>> result, List<List<Long>> mappings) {
        if (range <= 0) {
            return result;
        }

        for (List<Long> mapping : mappings) {
            long sourceStart = mapping.get(1);
            long sourceRange = mapping.get(2);
            long diff = mapping.get(0);
            if (sourceStart <= start && start < sourceStart + sourceRange) {
                long destination = start + diff;
                if (range <= sourceRange) {
                    result.add(Arrays.asList(destination, range));
                    return getDestinationRangesForAGivenRange(start + range - 1, 0, result, mappings);
                } else {
                    //         start 5                   + range 20
                    // sourceStart 1 + sourceRange 10
                    long newRange = sourceStart + sourceRange - start;
                    result.add(Arrays.asList(destination, newRange));
                    long newStart = start + newRange;
                    return getDestinationRangesForAGivenRange(newStart, range - newRange, result, mappings);
                }
            }
        }

        // current start not in any source range
        for (List<Long> mapping : mappings) {
            long sourceStart = mapping.get(1);
            long sourceRange = mapping.get(2);
            long destinationStart = mapping.get(0);
            if (start < sourceStart && start + range - 1 > sourceStart && start + range - 1 >= sourceStart + sourceRange - 1) {
                // from current start to source start
                long gap = sourceStart - start;
                result.add(Arrays.asList(destinationStart, gap));
                return getDestinationRangesForAGivenRange(start + gap, range - gap, result, mappings);
            }
        }
        result.add(Arrays.asList(start, range));
        return getDestinationRangesForAGivenRange(start + range - 1, 0, result, mappings);
    }
}
