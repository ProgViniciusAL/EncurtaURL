package dev.vinicius.EncurtaURL.domain.model.Link.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record LinkResponse(UUID id,
                           String customAlias,
                           Integer clickCount,
                           String longUrl,
                           String shortUrl,
                           String redirectUrl,
                           byte[] urlQrCode,
                           LocalDateTime createdAt) {
}
