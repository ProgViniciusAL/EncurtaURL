package dev.vinicius.EncurtaURL.Domain.Link.dto;


import dev.vinicius.EncurtaURL.Domain.Link.Link;

import java.time.LocalDateTime;

public record LinkDTO(String customAlias, String longUrl, String shortUrl, byte[] urlQrCode, LocalDateTime createdAt) {

    public LinkDTO(Link link) {
        this(link.getAlias(), link.getLongUrl(), link.getShortCode(), link.getQRCode(), link.getCreatedAt());
    }

}
