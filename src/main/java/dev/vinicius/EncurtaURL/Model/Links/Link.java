package dev.vinicius.EncurtaURL.Model.Links;

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

    private String customAlias;
    private Integer clickCount = 0;
    private String longUrl;
    private String shortUrl;
    private String urlQrCode;
    private LocalDateTime createdAt;

    public Link(LinkDTO linkDTO) {
        this.customAlias = linkDTO.customAlias();
        this.longUrl = linkDTO.longUrl();
        this.shortUrl = linkDTO.shortUrl();
        this.createdAt = linkDTO.createdAt();
        this.urlQrCode = linkDTO.urlQrCode();
    }

    public void addClickCount() {
        setClickCount(getClickCount() + 1);
    }

    @Override
    public String toString() {
        return "Link{" +
                "id=" + id +
                ", longUrl='" + longUrl + '\'' +
                ", shortUrl='" + shortUrl + '\'' +
                ", urlQrCode='" + urlQrCode + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

}
