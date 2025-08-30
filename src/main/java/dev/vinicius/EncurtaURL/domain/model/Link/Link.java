package dev.vinicius.EncurtaURL.domain.model.Link;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.vinicius.EncurtaURL.domain.model.User.User;
import dev.vinicius.EncurtaURL.domain.model.VO.Url;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

@Entity
@Table(name = "users_links")
@AllArgsConstructor
@EqualsAndHashCode
@Getter @Setter
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String alias;
    private int clickCount = 0;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "long_url"))
    private Url longUrl;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "short_url"))
    private Url shortUrl;

    private byte[] QRCode;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private LocalDateTime createdAt;

    public Link() {
        this.setCreatedAt(LocalDateTime.now());
    }

    public void registerClick() {
        this.clickCount++;
    }

    public String getLongUrl() {
        return this.longUrl != null ? this.longUrl.getValue() : null;
    }

    public String getShortUrl() {
        return this.shortUrl != null ? this.shortUrl.getValue() : null;
    }

    @Override
    public String toString() {
        return "Link{" +
                "id=" + id +
                ", alias='" + alias + '\'' +
                ", clickCount=" + clickCount +
                ", longUrl=" + longUrl +
                ", shortUrl=" + shortUrl +
                ", QRCode=" + Arrays.toString(QRCode) +
                ", user=" + user +
                ", createdAt=" + createdAt +
                '}';
    }
}
