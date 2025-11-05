package com.armedia.atddaccelerator.template.monolith.entity;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

@Data
@ToString(exclude = {"country", "airports"})
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "city")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    private Country country;

    @OneToMany(mappedBy = "city")
    private List<Comment> comments;

    @OneToMany(mappedBy = "home_city")
    private List<Airport> airports;
}
