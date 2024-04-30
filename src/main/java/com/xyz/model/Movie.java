package com.xyz.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.search.engine.backend.types.Projectable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import java.util.List;
import java.util.Set;

@Indexed
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Movie extends BaseEntity {


    @NotNull
    @GenericField(projectable = Projectable.YES)
    @Enumerated(EnumType.STRING)
    private Genre genre;
    @NotNull
    @GenericField(projectable = Projectable.YES)
    private String language;

    @OneToMany(mappedBy = "movie")
    @ToString.Exclude
    @JsonIgnore
    private Set<ShowSlot> showSlot;

//    @ManyToMany
//    @JoinTable(
//            name = "movie_theater",
//            joinColumns = @JoinColumn(name = "movie_id"),
//            inverseJoinColumns = @JoinColumn(name = "theater_id")
//    )
//    @ToString.Exclude
//    private List<Theater> theaters;

}
