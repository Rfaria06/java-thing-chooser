package ch.raulfaria.service;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class OverpassApiServiceTest {

    @Test
    void getCoordinatesOf() {
        final String address = "Eiffel Tower";
        final Coordinates expected = new Coordinates(30.7489907, 76.7853286);
        final OverpassApiService service = OverpassApiService.getInstance();
        
        final Optional<Coordinates> result = service.getCoordinatesOf(address);
        
        assertThat(result).isPresent();
        assertThat(result.get()).isNotNull();
        assertThat(result.get()).isEqualTo(expected);
    }
}