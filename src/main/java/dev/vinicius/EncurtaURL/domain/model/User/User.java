package dev.vinicius.EncurtaURL.domain.model.User;

import dev.vinicius.EncurtaURL.domain.model.Role.Role;
import dev.vinicius.EncurtaURL.domain.model.Link.Link;
import dev.vinicius.EncurtaURL.domain.model.User.dto.UserRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(unique = false)
    private String password;

    @Column(unique = true)
    private String email;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Link> links;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable( name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;

    public User(UserRequestDTO userRequestDTO, PasswordEncoder passwordEncoder) {
        this.id = null;
        this.username = userRequestDTO.username();
        this.password = passwordEncoder.encode(userRequestDTO.password());
        this.email = userRequestDTO.email();
        this.links = new ArrayList<>();
    }

}
