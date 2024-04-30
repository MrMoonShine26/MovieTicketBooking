package com.xyz.model;

import lombok.Data;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FieldProjection;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IdProjection;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.ProjectionConstructor;

@Data
public class SearchDTO {

    private Long id;
    private String movieName;
    private String language;
//    private String state;
//    private String theatreName;

    @ProjectionConstructor
    public SearchDTO(@IdProjection Long id,
                     @FieldProjection String name,
                     @FieldProjection String language
//                     @FieldProjection String state
    ) {
        this.id = id;
        this.movieName = name;
        this.language = language;
//        this.state = state;
//        this.theatreName = theatreName;
    }
}
