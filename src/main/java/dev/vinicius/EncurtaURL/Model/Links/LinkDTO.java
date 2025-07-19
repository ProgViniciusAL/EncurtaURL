package dev.vinicius.EncurtaURL.Model.Links;

import java.time.LocalDateTime;
import java.util.UUID;

public record LinkDTO(String customAlias, String longUrl, String shortUrl, byte[] urlQrCode, LocalDateTime createdAt) {

    public LinkDTO(Link link) {
        this(link.getCustomAlias(), link.getLongUrl(), link.getShortUrl(), link.getUrlQrCode(), link.getCreatedAt());
    }

}
