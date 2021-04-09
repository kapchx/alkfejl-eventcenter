package hu.elte.eventcenter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @NotNull
    private String title;

    @Column
    @NotNull
    private String description;

    @Column
    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime startAt;

    @ManyToOne
    @JsonIgnore
    private User organizer;

    @OneToMany(mappedBy = "event")
    private List<Participation> participation;

    @ManyToMany
    @JoinTable
    private List<Location> locations;

    public enum Status {
        UNPUBLISHED, ACTIVE, CANCELLED
    }
}
