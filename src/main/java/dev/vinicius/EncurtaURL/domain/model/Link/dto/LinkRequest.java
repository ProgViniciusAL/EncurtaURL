package dev.vinicius.EncurtaURL.domain.model.Link.dto;

import java.util.Map;

public record LinkRequest(String originalUrl, String customAlias) {

    public LinkRequest(Map<String, String> map) {
        this(
                map.getOrDefault("originalUrl", null),
                map.getOrDefault("alias", null)
        );
    }

}
