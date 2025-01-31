package ch.raulfaria.collector;

import ch.raulfaria.NumberedCandidate;

import java.util.List;

public interface CandidateCollector {
    /**
     * Collect the candidates from the given list. Should the
     * @return A List of candidates with their number (list index + 1)
     */
    List<NumberedCandidate> collect();
}
