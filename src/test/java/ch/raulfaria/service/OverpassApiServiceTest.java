package ch.raulfaria.service;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class OverpassApiServiceTest {

    final OverpassApiService service = OverpassApiService.getInstance();
    
    @Test
    void getCoordinates() {
        final OverpassApiQuery query = OverpassApiQuery.builder()
                .name("Co working City West")
                .city("Chur")
                .postcode("7000")
                .build();
        final Coordinates expected = new Coordinates(46.846373, 9.5086958);
        
        final Optional<Coordinates> result = service.getCoordinates(query);
        
        assertThat(result).isPresent();
        assertThat(result.get()).isNotNull();
        assertThat(result.get()).isEqualTo(expected);
    }
    
    @Test
    void getRestaurantNames() {
        final OverpassApiQuery query = OverpassApiQuery.builder()
                .name("Co working City West")
                .city("Chur")
                .postcode("7000")
                .radiusMeters(5000)
                .build();
        
        final List<String> result = service.getNearbyRestaurantNames(query);
        
        assertThat(result)
                .isNotEmpty()
                .contains("Coop Restaurant", "Burger King", "McDonald's", "Hans im Glück");
    }
}