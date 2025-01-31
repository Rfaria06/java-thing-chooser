package ch.raulfaria.collector;

import ch.raulfaria.NumberedCandidate;

import java.util.ArrayList;
import java.util.List;

public final class ArgvCandidateCollector implements CandidateCollector {

    private final List<String> argvInput;

    public ArgvCandidateCollector(final List<String> argvInput) {
        this.argvInput = argvInput;
    }

    @Override
    public List<NumberedCandidate> collect() {
        return NumberedCandidate.listOf(argvInput);
    }
}
