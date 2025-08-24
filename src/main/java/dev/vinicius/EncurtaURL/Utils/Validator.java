package dev.vinicius.EncurtaURL.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.regex.Pattern;

public class Validator {

    private static final Logger log = LoggerFactory.getLogger(Validator.class);
    private static final Pattern ALIAS_PATTERN = Pattern.compile("^[a-zA-Z0-9-]+$");

    public static boolean isValidUrl(String url) {

        if(!url.startsWith("ftp://") || !url.startsWith("file://")) {
            try {
                log.info("Verifying URL: {}", url);
                new URL(url).toURI();
                return true;
            } catch (MalformedURLException | URISyntaxException exception) {
                log.info("URL doesn't match: {}", exception.getMessage());
                return false;
            }
        }

        return false;
    }

    public static boolean isValidAlias(String alias) {

        if (alias != null && !alias.trim().isEmpty() && !ALIAS_PATTERN.matcher(alias).matches()) {
            try {
                log.info("Verifying Alias: {}", alias);
                return true;
            } catch (IllegalArgumentException exception) {
                log.info("Alias malformed: {}", exception.getMessage());
                return false;
            }
        }

        return false;
    }

}
