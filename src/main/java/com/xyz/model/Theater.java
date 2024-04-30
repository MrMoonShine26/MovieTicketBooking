package com.xyz.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.search.engine.backend.types.Projectable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import java.util.Set;

@Indexed
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Theater extends BaseEntity {

    @NotNull
    @GenericField(projectable = Projectable.YES)
    private String state;

    @NotNull
    @GenericField(projectable = Projectable.YES)
    private String city;
    @NotNull
    @GenericField(projectable = Projectable.YES)
    private int pinCode;

    @OneToMany(mappedBy = "theater", cascade = CascadeType.ALL)
    @ElementCollection
    @ToString.Exclude
    @JsonIgnore
    private Set<ShowSlot> runningShows;

//    @ManyToMany(mappedBy = "theaters")
//    @ToString.Exclude
//    private List<Movie> movie;

}
