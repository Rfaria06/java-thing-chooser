package ch.raulfaria;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class JavaThingChooser {
    public static void main(String[] args) {
        final List<String> candidates = new ArrayList<>();
        final List<Integer> numbers = new ArrayList<>();
        final Scanner scanner = new Scanner(System.in);

        System.out.println("Enter all candidates. Enter '-done' when you have added all.");
        int itemNumber = 1;
        while (true) {
            System.out.print(itemNumber + ". ");
            final String input = scanner.nextLine();
            if (input == null || input.equals("-done")) {
                break;
            }

            candidates.add(input);
            numbers.add(itemNumber);
            itemNumber++;
        }

        if (candidates.isEmpty()) {
            System.out.println("No candidates entered.");
            return;
        }

        final Random random = new Random();
        final int chosenIndex = random.nextInt(0, candidates.size());

        System.out.println("The winner is number " + numbers.get(chosenIndex) + ": " + candidates.get(chosenIndex));
    }
}