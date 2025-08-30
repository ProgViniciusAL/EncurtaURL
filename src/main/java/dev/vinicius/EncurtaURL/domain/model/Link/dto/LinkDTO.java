package dev.vinicius.EncurtaURL.domain.model.Link.dto;

import dev.vinicius.EncurtaURL.domain.model.VO.Url;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class LinkDTO {

    private UUID id;
    private String alias;
    private int clickCount = 0;
    private String longUrl;
    private String shortUrl;
    private byte[] QRCode;
    private LocalDateTime createdAt;

    public LinkDTO() {
        this.createdAt = LocalDateTime.now();
    }

    public LinkDTO(UUID id, String alias, int clickCount, Url longUrl, Url shortUrl, LocalDateTime createdAt) {
        this.id = id;
        this.alias = alias;
        this.clickCount = clickCount;
        this.longUrl = longUrl.getValue();
        this.shortUrl = shortUrl.getValue();
        this.QRCode = QRCode;
        this.createdAt = createdAt;
    }

    public void registerClick() {
        this.clickCount++;
    }

}
