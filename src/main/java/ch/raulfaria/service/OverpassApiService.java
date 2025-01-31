package ch.raulfaria.service;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public final class OverpassApiService {
    
    private static OverpassApiService instance;

    public static OverpassApiService getInstance() {
        if (instance == null) {
            instance = new OverpassApiService();
        }
        
        return instance;
    }
    
    private OverpassApiService() {}
    
    public List<String> getNearbyRestaurantNames() {
        return null;
    }
    
    public Optional<Coordinates> getCoordinatesOf(final String address) {
        final String overpassQuery = "[out:json];node[\"name\"=\"" + URLEncoder.encode(address, StandardCharsets.UTF_8) + "\"];out;";
        
        try {
            final URL url = new URL("https://overpass-api.de/api/interpreter?data=" + overpassQuery);
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection(); 
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            
            final Scanner scanner = new Scanner(connection.getInputStream());
            final String response = scanner.useDelimiter("\\A").next();
            scanner.close();
            connection.disconnect();

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
}
