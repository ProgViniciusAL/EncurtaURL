package dev.vinicius.EncurtaURL.Domain.Link.dto;


import dev.vinicius.EncurtaURL.Domain.Link.Link;

import java.time.LocalDateTime;
import java.util.UUID;

public record LinkResponse(UUID id, String customAlias, Integer clickCount, String longUrl, String shortUrl, String shortCode, String redirectUrl, byte[] urlQrCode, LocalDateTime createdAt) {
    public LinkResponse(Link link, String redirectUrl) {
        this(link.getId(), link.getAlias(), link.getClickCount(), link.getLongUrl(), link.getShortUrl(), link.getShortCode(), redirectUrl, link.getQRCode(), link.getCreatedAt());
    }

}
