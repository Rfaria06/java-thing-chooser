package ch.raulfaria.collector;

import ch.raulfaria.NumberedCandidate;

import java.util.ArrayList;
import java.util.List;

public class ArgvCandidateCollector implements CandidateCollector {

    private final List<String> argvInput;

    public ArgvCandidateCollector(final List<String> argvInput) {
        this.argvInput = argvInput;
    }

    @Override
    public List<NumberedCandidate> collect() {
        final List<NumberedCandidate> numberedCandidates = new ArrayList<>();

        int number = 1;
        for (final String input : argvInput) {
            numberedCandidates.add(new NumberedCandidate(input, number));
            number++;
        }

        return numberedCandidates;
    }
}
