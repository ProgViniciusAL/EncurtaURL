package dev.vinicius.EncurtaURL.domain.model.Link;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.vinicius.EncurtaURL.domain.model.User.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users_links")
@AllArgsConstructor
@NoArgsConstructor
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
    private String shortCode;
    private byte[] QRCode;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    private LocalDateTime createdAt;

    public Link(UUID uuid, String name, int clickCount, String longUrl, String shortUrl, String shortCode, byte[] qrCode, LocalDateTime createdAt) {
        this.alias = name;
        this.clickCount = clickCount;
        this.longUrl = longUrl;
        this.shortCode = shortCode;
        this.shortUrl = shortUrl;
        this.createdAt = createdAt;
        this.QRCode = qrCode;
    }

    public void registerClick() {
        this.clickCount++;
    }

    @Override
    public String toString() {
        return "Link{" +
                "id=" + id +
                ", longUrl='" + longUrl + '\'' +
                ", shortCode='" + shortCode + '\'' +
                ", QRCode='" + QRCode + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

}
