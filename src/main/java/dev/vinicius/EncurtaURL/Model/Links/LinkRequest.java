package dev.vinicius.EncurtaURL.Model.Links;

import java.util.Map;

public record LinkRequest(String originalUrl, String customAlias) {

    public LinkRequest(Map<String, String> map) {
        this(
                map.getOrDefault("originalUrl", null),
                map.getOrDefault("customAlias", null)
        );
    }

}
