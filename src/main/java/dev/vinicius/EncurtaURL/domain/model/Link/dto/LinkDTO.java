package dev.vinicius.EncurtaURL.domain.model.Link.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
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

    public void registerClick() {
        this.clickCount++;
    }

}
