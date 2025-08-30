package dev.vinicius.EncurtaURL.domain.model.VO;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
public class Url {
    
    private String value;

    public Url(String value) {
        if(!value.startsWith("ftp://") || !value.startsWith("file://")) {
            try {
                this.value = new URL(value).toURI().toString();
            } catch (MalformedURLException | URISyntaxException exception) {
                throw new RuntimeException(exception);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Url url)) return false;
        return Objects.equals(getValue(), url.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getValue());
    }
}
