package dev.vinicius.EncurtaURL.domain.model.Link.dto;


import dev.vinicius.EncurtaURL.domain.model.Link.Link;

import java.time.LocalDateTime;

public record LinkDTO(String customAlias, String longUrl, String shortUrl, byte[] urlQrCode, LocalDateTime createdAt) {
}
