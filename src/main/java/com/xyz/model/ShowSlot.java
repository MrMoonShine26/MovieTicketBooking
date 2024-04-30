package com.xyz.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.search.engine.backend.types.Projectable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Indexed
@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
public class ShowSlot {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(nullable = false)
    private String id;

    @ManyToOne
    @ToString.Exclude
    @IndexedEmbedded
    @JoinTable(
            name = "slot_movie",
            joinColumns = @JoinColumn(name = "showslot_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id")
    )
//    @IndexedEmbedded(includePaths = {"name,language"})
    private Movie movie;

    @ManyToOne
    @IndexedEmbedded
//    @IndexedEmbedded(includePaths = {"city"})
    @JoinTable(
            name = "movie_theater",
            joinColumns = @JoinColumn(name = "showslot_id"),
            inverseJoinColumns = @JoinColumn(name = "theater_id")
    )
    private Theater theater;

    private int totalNumberOfSeats = Util.TOTAL_SEATS_PER_SHOW;

    private int bookedSeats;

    @GenericField
    private LocalDate date;


    @NotNull
    private LocalTime showStartTime;
    @NotNull
    private LocalTime showEndTime;

//    @ManyToMany
//    @JoinTable(
//            name = "movie_theater",
//            joinColumns = @JoinColumn(name = "movie_id"),
//            inverseJoinColumns = @JoinColumn(name = "theater_id")
//    )
//    @ToString.Exclude
//    @JsonIgnore
//    private List<Theater> theaters;

//    @ElementCollection
//    private Set<LocalDateTime> dayRunningTimeSlot;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        ShowSlot that = (ShowSlot) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
