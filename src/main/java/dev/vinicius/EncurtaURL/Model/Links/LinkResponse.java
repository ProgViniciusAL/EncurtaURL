package dev.vinicius.EncurtaURL.Model.Links;

import java.time.LocalDateTime;
import java.util.UUID;

public record LinkResponse(UUID id, String customAlias, Integer clickCount, String longUrl, String shortUrl, String redirectUrl, byte[] urlQrCode, LocalDateTime createdAt) {
    public LinkResponse(Link link, String redirectUrl) {
        this(link.getId(), link.getCustomAlias(), link.getClickCount(), link.getLongUrl(), link.getShortUrl(), redirectUrl, link.getUrlQrCode(), link.getCreatedAt());
    }

}
