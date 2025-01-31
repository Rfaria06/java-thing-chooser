package ch.raulfaria;

import java.util.ArrayList;
import java.util.List;

public record NumberedCandidate(String name, int number) {
    public static List<NumberedCandidate> listOf(final Iterable<String> names) {
        final List<NumberedCandidate> candidates = new ArrayList<>();
        int itemNumber = 1;
        for (final String name : names) {
            candidates.add(new NumberedCandidate(name, itemNumber));
            itemNumber++;
        }
        
        return candidates;
    }
}
