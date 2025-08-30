package dev.vinicius.EncurtaURL.domain.model.User;

import dev.vinicius.EncurtaURL.domain.model.Role.Role;
import dev.vinicius.EncurtaURL.domain.model.Link.Link;
import dev.vinicius.EncurtaURL.domain.model.User.dto.UserRequestDTO;
import dev.vinicius.EncurtaURL.domain.model.enums.RoleName;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "users")
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
    @Pattern(regexp = "^[\\p{L}\\p{N}]+$")
    private String username;

    @Column(unique = false)
    private String password;

    @Column(unique = true)
    @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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
        this.roles = Collections.singletonList(new Role(RoleName.ROLE_ADMIN));
    }

}
