package ch.raulfaria;

import java.util.*;

public class JavaThingChooser {

    public static void main(String[] args) throws InterruptedException {
        final JavaThingChooser javaThingChooser = new JavaThingChooser();
        javaThingChooser.run(new ArrayList<>(List.of(args)));
    }

    private final Scanner scanner = new Scanner(System.in);
    private final Random random = new Random();

    public void run(final List<String> argvInput) throws InterruptedException {
        final List<NumberedCandidate> candidates = collectCandidates(argvInput);

        chooseWinner(candidates);
    }

    private List<NumberedCandidate> collectCandidates(final List<String> argvInput) {
        final List<NumberedCandidate> candidates = new ArrayList<>();

        int itemNumber = 1;
        if (argvInput.isEmpty()) {
            System.out.println("Enter all candidates. Enter '-done' when you have added all.");

            while (true) {
                System.out.print(itemNumber + ". ");
                final String input = scanner.nextLine();
                if (input == null || input.equals("-done")) {
                    break;
                }

                candidates.add(new NumberedCandidate(input, itemNumber));
                itemNumber++;
            }
        } else {
            for (final String item : argvInput) {
                candidates.add(new NumberedCandidate(item, itemNumber));
                itemNumber++;
            }
        }

        return candidates;
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

        System.out.println("Number " + chosenCandidate.number() + ": " + chosenCandidate.candidate());
    }
}