package com.xyz.model;

import lombok.Data;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FieldProjection;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IdProjection;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.ProjectionConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class SearchShoSlotDTO {
    private String id;
    private String movieName;
    private String language;
    private String city;
    private String theatreName;
    private LocalDate date;
    private LocalTime startTime;

    @ProjectionConstructor
    public SearchShoSlotDTO(@IdProjection String id,
                            @FieldProjection String name,
                            @FieldProjection String language,
                            @FieldProjection String city,
                            @FieldProjection String theatreName,
                            @FieldProjection LocalDate date,
                            @FieldProjection LocalTime starTime

    ) {
        this.id = id;
        this.movieName = name;
        this.language = language;
        this.city = city;
        this.theatreName = theatreName;
        this.date = date;
        this.startTime = starTime;
    }
}
