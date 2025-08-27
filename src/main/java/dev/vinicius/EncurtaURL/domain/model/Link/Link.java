package dev.vinicius.EncurtaURL.domain.model.Link;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.vinicius.EncurtaURL.domain.model.User.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users_links")
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String alias;
    private int clickCount = 0;
    private String longUrl;
    private String shortUrl;
    private byte[] QRCode;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    private LocalDateTime createdAt;

    public Link() {
        this.setCreatedAt(LocalDateTime.now());
    }

    public void registerClick() {
        this.clickCount++;
    }

}
