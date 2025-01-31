package ch.raulfaria.service;

import java.util.Currency;

public final class OverpassApiQuery {
    public static Builder builder() {
        return new Builder();
    }
    
    private final String name, city, postcode;
    private final int radiusMeters;

    private OverpassApiQuery(final String name, final String city, final String postcode, final int radiusMeters) {
        this.name = name;
        this.city = city;
        this.postcode = postcode;
        this.radiusMeters = radiusMeters;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getPostcode() {
        return postcode;
    }

    public int getRadiusMeters() {
        return radiusMeters;
    }

    /**
     * Get the "node" query part for the overpass api request
     * @return The query in form of the overpass api "node" parameter
     */
    @Override
    public String toString() {
        return "node[\"name\"=\"" + name + "\"][\"addr:city\"=\"" + city + "\"][\"addr:postcode\"=\"" + postcode +"\"];";
    }

    public static final class Builder {
        private String name, city, postcode;
        private Integer radiusMeters;
        
        public Builder() {}
        
        public OverpassApiQuery build() {
            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException("name must not be blank");
            }
            if (city == null || city.isBlank()) {
                throw new IllegalArgumentException("city must not be blank");
            }
            if (postcode == null || postcode.isBlank()) {
                throw new IllegalArgumentException("postcode must not be blank");
            }
            if (radiusMeters == null) {
                radiusMeters = 5000;
            }
            return new OverpassApiQuery(name, city, postcode, radiusMeters);
        }
        
        public Builder name(final String name) {
            this.name = name;
            return this;
        }
        
        public Builder city(final String city) {
            this.city = city;
            return this;
        }
        
        public Builder postcode(final String postcode) {
            this.postcode = postcode;
            return this;
        }
        
        public Builder radiusMeters(final Integer radiusMeters) {
            this.radiusMeters = radiusMeters;
            return this;
        }
    }
}
