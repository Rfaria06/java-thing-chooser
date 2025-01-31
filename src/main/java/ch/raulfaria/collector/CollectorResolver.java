package ch.raulfaria.collector;

import java.util.List;

public final class CollectorResolver {
    
    final List<String> argv;
    
    public CollectorResolver(final String[] args) {
        argv = List.of(args);
    }
    
    public CandidateCollector resolve() {
        if (argv.isEmpty()) {
            return new InteractiveInputCandidateCollector();
        } else if (argv.getFirst().equalsIgnoreCase("-suggest")) {
            return new OverpassApiSuggestionCollector();
        }
        
        return new ArgvCandidateCollector(argv);
    }
}
