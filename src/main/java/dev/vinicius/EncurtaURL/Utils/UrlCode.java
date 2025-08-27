package dev.vinicius.EncurtaURL.Utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.text.RandomStringGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UrlCode {

    private static final Logger log = LoggerFactory.getLogger(UrlCode.class);

    public static String generate() {
        log.info("Generating random URL for shorten process.");

        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .withinRange('0', 'z')
                .filteredBy(Character::isLetterOrDigit)
                .get();

        return generator.generate(5, 10);
    }

}
