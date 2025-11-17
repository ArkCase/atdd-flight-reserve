package com.armedia.atddaccelerator.template.monolith.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString(exclude = {"inbound_routes", "outbound_routes"})
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "airport")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Airport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String iata;

    @Column
    private String icao;

    @Column
    private Double latitude;

    @Column
    private Double longitude;

    @Column
    private Integer altitude;

    @Column
    private String timezone;

    @Column
    private String dst;

    @Column
    private String tz;

    @Column
    private String type;

    @Column
    private String source;

    @OneToMany(mappedBy = "src_airport")
    private List<Route> inbound_routes;

    @OneToMany(mappedBy = "dst_airport")
    private List<Route> outbound_routes;

    @ManyToOne
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private City homeCity;

    @ManyToOne
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    private Country homeCountry;

    public static AirportEntityBuilder builder() {
        return new AirportEntityBuilder();
    }

    public static class AirportEntityBuilder {

        private Long id;
        private String name;
        private String iata;
        private String icao;
        private Double latitude;
        private Double longitude;
        private Integer altitude;
        private String timezone;
        private String dst;
        private String tz;
        private String type;
        private String source;
        private List<Route> inbound_routes;
        private List<Route> outbound_routes;
        private City homeCity;
        private Country homeCountry;

        public AirportEntityBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public AirportEntityBuilder name(final String name) {
            this.name = name;
            return this;
        }

        public AirportEntityBuilder iata(final String iata) {
            this.iata = iata;
            return this;
        }

        public AirportEntityBuilder icao(final String icao) {
            this.icao = icao;
            return this;
        }

        public AirportEntityBuilder latitude(Double latitude) {
            this.latitude = latitude;
            return this;
        }

        public AirportEntityBuilder longitude(Double longitude) {
            this.longitude = longitude;
            return this;
        }

        public AirportEntityBuilder altitude(Integer altitude) {
            this.altitude = altitude;
            return this;
        }

        public AirportEntityBuilder timezone(String timezone) {
            this.timezone = timezone;
            return this;
        }

        public AirportEntityBuilder dst(String dst) {
            this.dst = dst;
            return this;
        }

        public AirportEntityBuilder tz(String tz) {
            this.tz = tz;
            return this;
        }

        public AirportEntityBuilder type(String type) {
            this.type = type;
            return this;
        }

        public AirportEntityBuilder source(String source) {
            this.source = source;
            return this;
        }

        public AirportEntityBuilder inbound_routes(List<Route> inbound_routes) {
            this.inbound_routes = inbound_routes;
            return this;
        }

        public AirportEntityBuilder outbound_routes(List<Route> outbound_routes) {
            this.outbound_routes = outbound_routes;
            return this;
        }

        public AirportEntityBuilder homeCity(City home_city) {
            this.homeCity = home_city;
            return this;
        }

        public AirportEntityBuilder homeCountry(Country home_country) {
            this.homeCountry = home_country;
            return this;
        }

        public Airport build() {
            return new Airport(id, name, iata, icao, latitude, longitude, altitude, timezone, dst, tz, type, source, inbound_routes, outbound_routes,
                    homeCity, homeCountry);
        }
    }
}
