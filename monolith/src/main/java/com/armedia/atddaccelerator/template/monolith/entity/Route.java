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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "route")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String codeshare;

    @Column
    private Integer stops;

    @Column
    private String equipment;

    @Column
    private Double price;

    @ManyToOne
    @JoinColumn(name = "airline_id", referencedColumnName = "id")
    private Airline airline;

    @ManyToOne
    @JoinColumn(name = "src_airport_id", referencedColumnName = "id")
    private Airport src_airport;

    @ManyToOne
    @JoinColumn(name = "dst_airport_id", referencedColumnName = "id")
    private Airport dst_airport;

    public static RouteEntityBuilder builder() {
        return new RouteEntityBuilder();
    }

    public static class RouteEntityBuilder {
        private Long id;
        private String codeshare;
        private Integer stops;
        private String equipment;
        private Double price;
        private Airline airline;
        private Airport src_airport;
        private Airport dst_airport;

        public RouteEntityBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public RouteEntityBuilder codeshare(final String codeshare) {
            this.codeshare = codeshare;
            return this;
        }

        public RouteEntityBuilder stops(final Integer stops) {
            this.stops = stops;
            return this;
        }

        public RouteEntityBuilder equipment(final String equipment) {
            this.equipment = equipment;
            return this;
        }

        public RouteEntityBuilder price(final Double price) {
            this.price = price;
            return this;
        }

        public RouteEntityBuilder airline(final Airline airline) {
            this.airline = airline;
            return this;
        }

        public RouteEntityBuilder src_airport(final Airport src_airport) {
            this.src_airport = src_airport;
            return this;
        }

        public RouteEntityBuilder dst_airport(final Airport dst_airport) {
            this.dst_airport = dst_airport;
            return this;
        }

        public Route build() {
            return new Route(id, codeshare, stops, equipment, price, airline, src_airport, dst_airport);
        }
    }

}
