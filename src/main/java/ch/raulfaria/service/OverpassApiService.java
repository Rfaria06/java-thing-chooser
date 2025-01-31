package ch.raulfaria.service;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public final class OverpassApiService {
    
    private static OverpassApiService instance;
    private static final String API_ADDRESS = "https://overpass-api.de/api/interpreter?data=";

    public static OverpassApiService getInstance() {
        if (instance == null) {
            instance = new OverpassApiService();
        }
        
        return instance;
    }
    
    private OverpassApiService() {}
    
    public List<String> getNearbyRestaurantNames(final OverpassApiQuery query) {
        return getCoordinates(query)
                .map(coordinates -> getNearbyRestaurantNames(coordinates, query.getRadiusMeters()))
                .orElseGet(List::of);
    }
    
    public List<String> getNearbyRestaurantNames(final Coordinates coordinates, final int radiusMeters) {
        final String overpassFastFoodUrl = buildAmenityQuery("fast_food", coordinates, radiusMeters);
        final List<String> queries = List.of(
                buildAmenityQuery("restaurant", coordinates, radiusMeters),
                buildAmenityQuery("fast_food", coordinates, radiusMeters)
        );
     
        final List<String> names = new ArrayList<>();
        queries.forEach(q -> names.addAll(getElements(q)));
        return names;
    }
    
    private String buildAmenityQuery(final String amenity, final Coordinates coordinates, final int radiusMeters) {
        final String query = String.format(
                "[out:json];node[\"amenity\"=\"" + amenity + "\"](around:%d, %f, %f);out;",
                radiusMeters, coordinates.latitude(), coordinates.longitude()
        );
        final String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        return API_ADDRESS + encodedQuery;
    }
    
    private List<String> getElements(final String requestUrl) {
        try {
            final URL url = new URI(requestUrl).toURL();
            final String response = makeJsonRequest(url);

            final JSONArray elements = new JSONObject(response).getJSONArray("elements");
            if (elements.isEmpty()) {
                return List.of();
            }

            final List<String> names = new ArrayList<>();
            for (int i = 0; i < elements.length(); i++) {
                final JSONObject element = elements.getJSONObject(i);
                if (element.has("tags")) {
                    final JSONObject tags = element.getJSONObject("tags");
                    if (tags.has("name")) {
                        final String name = tags.getString("name");
                        names.add(name);
                    }
                }
            }
            if (!names.isEmpty() && names.contains("Coop Restaurant")) {
                // Not on OpenStreetMap, but it should be included
                names.add("Hans im Glück");
            }

            return names;
        } catch (IOException e) {
            System.out.println("Error during restaurant retreival: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An error ocurred: " + e.getMessage());
        }
        
        return List.of();
    }
    
    public Optional<Coordinates> getCoordinates(final OverpassApiQuery query) {
        final String overpassQuery = "[out:json];" + query.toString() + "out;";
        final String encodedQuery = URLEncoder.encode(overpassQuery, StandardCharsets.UTF_8);
        final String overpassUrl = API_ADDRESS + encodedQuery;
        
        try {
            final URL url = new URI(overpassUrl).toURL();
            final String response = makeJsonRequest(url);

            JSONObject json = new JSONObject(response);
            JSONArray elements = json.getJSONArray("elements");
            
            if (elements.isEmpty()) {
                System.out.println("No results for address found.");
                return Optional.empty();
            }
            
            final JSONObject firstResult = elements.getJSONObject(0);
            final double latitude = firstResult.getDouble("lat");
            final double longitude = firstResult.getDouble("lon");
            
            return Optional.of(new Coordinates(latitude, longitude));
        } catch (IOException e) {
            System.out.println("Error during coordinate retreival: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("URI syntax exception: " + e.getMessage());
        }
        
        return Optional.empty();
    }
    
    private String makeJsonRequest(final URL url) throws IOException {
        final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");

        final Scanner scanner = new Scanner(connection.getInputStream());
        final String response = scanner.useDelimiter("\\A").next();
        scanner.close();
        connection.disconnect();
        
        return response;
    }
}
