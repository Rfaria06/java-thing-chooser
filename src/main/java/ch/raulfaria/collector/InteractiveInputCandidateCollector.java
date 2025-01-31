package ch.raulfaria.collector;

import ch.raulfaria.NumberedCandidate;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InteractiveInputCandidateCollector implements CandidateCollector {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public List<NumberedCandidate> collect() {
        final List<NumberedCandidate> numberedCandidates = new ArrayList<>();

        System.out.println("Enter all candidates. Enter '-done' when you have added all.");

        int number = 1;
        while (true) {
            System.out.print(number + ". ");
            final String input = scanner.nextLine();
            if (input == null || input.equals("-done")) {
                break;
            }

            numberedCandidates.add(new NumberedCandidate(input, number));
            number++;
        }

        return numberedCandidates;
    }
}
