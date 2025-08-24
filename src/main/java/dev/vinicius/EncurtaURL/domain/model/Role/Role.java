package dev.vinicius.EncurtaURL.domain.model.Role;

import dev.vinicius.EncurtaURL.domain.model.Enum.RoleName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleName name;

    public Role(RoleName roleUser) {
        this.name = roleUser;
    }

    public String getRoleName() {
        return name.toString();
    }

}
