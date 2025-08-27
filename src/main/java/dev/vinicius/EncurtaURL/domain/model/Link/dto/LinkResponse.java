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

    @Override
    public UUID id() {
        return id;
    }

    @Override
    public String customAlias() {
        return customAlias;
    }

    @Override
    public Integer clickCount() {
        return clickCount;
    }

    @Override
    public String longUrl() {
        return longUrl;
    }

    @Override
    public String shortUrl() {
        return shortUrl;
    }

    @Override
    public String redirectUrl() {
        return redirectUrl;
    }

    @Override
    public byte[] urlQrCode() {
        return urlQrCode;
    }

    @Override
    public LocalDateTime createdAt() {
        return createdAt;
    }
}
