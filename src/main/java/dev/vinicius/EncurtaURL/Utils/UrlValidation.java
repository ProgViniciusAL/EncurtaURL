package dev.vinicius.EncurtaURL.Utils;

import dev.vinicius.EncurtaURL.Exceptions.InvalidUrlException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class UrlValidation {

    private static final Logger log = LoggerFactory.getLogger(UrlValidation.class);

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

}
