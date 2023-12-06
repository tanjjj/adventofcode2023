import utils.Parser;

import java.util.*;
import java.util.stream.Collectors;

// 2023 puzzle 5
public class Day5B implements DayX {
    private List<List<Long>> seedsToSoil = new ArrayList<>();
    private List<List<Long>> soilToFertilizer = new ArrayList<>();
    private List<List<Long>> fertilizerToWater = new ArrayList<>();
    private List<List<Long>> waterToLight = new ArrayList<>();
    private List<List<Long>> lightToTemp = new ArrayList<>();
    private List<List<Long>> tempToHumidity = new ArrayList<>();
    private List<List<Long>> humidityToLoc = new ArrayList<>();

    @Override
    public void run() {
        List<String> input = Parser.parseInputAsString("test.txt");
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
        for (int s = 0; s < seeds.size(); s++) {
            long start = seeds.get(s);
            long range = seeds.get(++s);
            long loc = getMinLoc(start, range);
            result = Math.min(loc, result);
        }

        System.out.println(result);
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

    private long getMinLoc(long start, long range) {
        List<List<Long>> soilRanges = new ArrayList<>();
        getDestinationRangesForAGivenRange(start, range, soilRanges, seedsToSoil);


        List<List<Long>> fertilizerRanges = new ArrayList<>();
        for (List<Long> soilRange : soilRanges) {
            getDestinationRangesForAGivenRange(soilRange.get(0), soilRange.get(1) - soilRange.get(0), fertilizerRanges, soilToFertilizer);
        }

        List<List<Long>> waterRanges = new ArrayList<>();
        for (List<Long> fertilizerRange : fertilizerRanges) {
            getDestinationRangesForAGivenRange(fertilizerRange.get(0), fertilizerRange.get(1) - fertilizerRange.get(0), waterRanges, fertilizerToWater);
        }

        List<List<Long>> lightRanges = new ArrayList<>();
        for (List<Long> waterRange : waterRanges) {
            getDestinationRangesForAGivenRange(waterRange.get(0), waterRange.get(1) - waterRange.get(0), lightRanges, waterToLight);
        }

        List<List<Long>> tempRanges = new ArrayList<>();
        for (List<Long> lightRange : lightRanges) {
            getDestinationRangesForAGivenRange(lightRange.get(0), lightRange.get(1) - lightRange.get(0), tempRanges, lightToTemp);
        }

        List<List<Long>> humidityRanges = new ArrayList<>();
        for (List<Long> tempRange : tempRanges) {
            getDestinationRangesForAGivenRange(tempRange.get(0), tempRange.get(1) - tempRange.get(0), humidityRanges, tempToHumidity);
        }

        List<List<Long>> locRanges = new ArrayList<>();
        for (List<Long> humidityRange : humidityRanges) {
            getDestinationRangesForAGivenRange(humidityRange.get(0), humidityRange.get(1) - humidityRange.get(0), locRanges, humidityToLoc);
        }

        long min = Long.MAX_VALUE;
        for (List<Long> locRange : locRanges) {
            min = Math.min(min, locRange.get(0));
        }

        return min;
    }

    private List<List<Long>> getDestinationRangesForAGivenRange(long start, long range, List<List<Long>> result, List<List<Long>> mappings) {
        if (range <= 0) {
            return result;
        }

        for (List<Long> mapping : mappings) {
            long sourceStart = mapping.get(1);
            long sourceRange = mapping.get(2);
            long destinationStart = mapping.get(0);
            if (sourceStart <= start && start <= sourceStart + sourceRange) {
                long destination = destinationStart + (start - sourceStart);
                if (range <= sourceRange) {
                    result.add(Arrays.asList(destination, destination + range));
                    return getDestinationRangesForAGivenRange(start + range, 0, result, mappings);
                } else {
                    result.add(Arrays.asList(destination, destination + sourceRange));
                    return getDestinationRangesForAGivenRange(start + sourceRange, range - sourceRange, result, mappings);
                }
            }
        }

        result.add(Arrays.asList(start, start + 1));
        return getDestinationRangesForAGivenRange(start + 1, range - 1, result, mappings);
    }

    // get min location for a humidity range
    private long getMinLocForHumidityRange(long startH, long rangeH, long minLoc) {
        if (rangeH <= 0) {
            return minLoc;
        }

        for (List<Long> h2l : humidityToLoc) {
            if (h2l.get(1) <= startH && startH <= h2l.get(1) + h2l.get(2)) {
                minLoc = Math.min(minLoc, h2l.get(0) + (startH - h2l.get(1)));

                if (rangeH <= h2l.get(2)) {
                    return getMinLocForHumidityRange(startH + rangeH, 0, minLoc);
                } else {
                    return getMinLocForHumidityRange(startH + h2l.get(2), rangeH - h2l.get(2), minLoc);
                }
            }
        }

        return Math.min(minLoc, startH);
    }
}
