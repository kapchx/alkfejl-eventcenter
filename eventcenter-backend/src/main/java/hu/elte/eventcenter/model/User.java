package hu.elte.eventcenter.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @NotNull
    private String name;

    @Column
    @NotNull
    private String username;

    @Column
    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String password;

    @Column
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public enum UserRole {
        ROLE_GUEST, ROLE_USER, ROLE_ADMIN
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Participation> participation;

    @OneToMany(mappedBy = "organizer", cascade = CascadeType.REMOVE)
    private List<Event> events;
}
