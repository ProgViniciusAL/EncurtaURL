package dev.vinicius.EncurtaURL.Utils;

import dev.vinicius.EncurtaURL.Service.LinkService;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UrlCode {

    private static final Logger log = LoggerFactory.getLogger(UrlCode.class);

    //TODO: Modificar biblioteca depracated para lógica sem a biblioteca Commons Lang
    public static String generate() {
        log.info("Generating random URL for shorten process.");
        return RandomStringUtils.randomAlphanumeric(5, 10);
    }

}
