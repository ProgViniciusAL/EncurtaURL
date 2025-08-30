package dev.vinicius.EncurtaURL.domain.model.VO;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.text.RandomStringGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter @Setter
public class ShortCode {

    public String value;

    private static final Logger log = LoggerFactory.getLogger(ShortCode.class);

    public String generate() {
        log.info("Generating random URL for shorten process.");

        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .withinRange('0', 'z')
                .filteredBy(Character::isLetterOrDigit)
                .get();

        this.setValue(generator.generate(5, 5));

        return value;
    }

}
