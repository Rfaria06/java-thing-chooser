package ch.raulfaria;

import ch.raulfaria.collector.ArgvCandidateCollector;
import ch.raulfaria.collector.CandidateCollector;
import ch.raulfaria.collector.InteractiveInputCandidateCollector;
import ch.raulfaria.collector.OverpassApiSuggestionCollector;

import java.util.*;

public class JavaThingChooser {

    public static void main(String[] args) throws InterruptedException {
        final List<String> argv = new ArrayList<>(List.of(args));

        final CandidateCollector collector;
        if (argv.isEmpty()) {
            collector = new InteractiveInputCandidateCollector();
        } else if (argv.getFirst().equalsIgnoreCase("-suggest")) {
            collector = OverpassApiSuggestionCollector.getInstance();
        } else {
            collector = new ArgvCandidateCollector(argv);
        }

        new JavaThingChooser().run(collector);
    }

    public void run(final CandidateCollector collector) throws InterruptedException {
        final List<NumberedCandidate> candidates = collector.collect();

        chooseWinner(candidates);
    }

    private void chooseWinner(final List<NumberedCandidate> candidates) throws InterruptedException {
        if (candidates.isEmpty()) {
            System.out.println("No candidates entered.");
            return;
        }

        final Random random = new Random();
        final int chosenIndex = random.nextInt(0, candidates.size());
        final NumberedCandidate chosenCandidate = candidates.get(chosenIndex);

        System.out.println("Waiting for quantum particles to be aligned...");
        Thread.sleep(1500L);
        System.out.println("Calculating answer...");
        Thread.sleep(1500L);
        System.out.println("Taking decision...");
        Thread.sleep(1000L);

        System.out.print("The winner is");
        for (int i = 0; i < 5; i++) {
            Thread.sleep(800L);
            System.out.print(".");
        }
        System.out.println();

        System.out.println("Number " + chosenCandidate.number() + ": " + chosenCandidate.name());
    }
}