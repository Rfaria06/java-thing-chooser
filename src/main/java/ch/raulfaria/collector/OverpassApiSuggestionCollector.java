package ch.raulfaria.collector;

import ch.raulfaria.NumberedCandidate;

import java.util.List;

public class OverpassApiSuggestionCollector implements CandidateCollector {
    
    private static OverpassApiSuggestionCollector instance;

    public static OverpassApiSuggestionCollector getInstance() {
        if (instance == null) {
            instance = new OverpassApiSuggestionCollector();
        }
        
        return instance;
    }
    
    private OverpassApiSuggestionCollector() {}
    
    @Override
    public List<NumberedCandidate> collect() {
        return List.of();
    }
}
