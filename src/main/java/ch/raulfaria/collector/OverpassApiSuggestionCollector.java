package ch.raulfaria.collector;

import ch.raulfaria.NumberedCandidate;
import ch.raulfaria.service.Coordinates;
import ch.raulfaria.service.OverpassApiQuery;
import ch.raulfaria.service.OverpassApiService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public final class OverpassApiSuggestionCollector implements CandidateCollector {
    
    private final OverpassApiService service;
    
    public OverpassApiSuggestionCollector() {
        service = OverpassApiService.getInstance();
    }
    
    @Override
    public List<NumberedCandidate> collect() {
        final Scanner scanner = new Scanner(System.in);
        
        final OverpassApiQuery.Builder builder = OverpassApiQuery.builder();
        System.out.println("Please enter the name of your location");
        builder.name(scanner.nextLine());
        System.out.println("Please enter the name of your city");
        builder.city(scanner.nextLine());
        System.out.println("Please enter the postcode of your city");
        builder.postcode(scanner.nextLine());
        System.out.println("Please enter the radius around your address in meters to search in");
        builder.radiusMeters(scanner.nextInt());
        final OverpassApiQuery query = builder.build();

        System.out.printf("""
                Loading coordinates for %s in %s %s
                %n""", query.getName(), query.getPostcode(), query.getCity());
        final Optional<Coordinates> coordinates = service.getCoordinates(query);
        if (coordinates.isEmpty()) {
            System.out.println("Coordinates not found.");
            return List.of();
        }

        System.out.println("Coordinates found!");

        System.out.println("Searching for restaurants within a " + query.getRadiusMeters() + "m radius around " + query.getName() + "...");
        final List<String> results = service.getNearbyRestaurantNames(coordinates.get(), query.getRadiusMeters());

        System.out.println("FOUND RESULTS: " + results);
        
        return NumberedCandidate.listOf(results);
    }
}
