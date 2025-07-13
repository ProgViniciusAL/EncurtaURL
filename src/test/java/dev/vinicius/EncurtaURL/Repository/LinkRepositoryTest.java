package dev.vinicius.EncurtaURL.Repository;

import dev.vinicius.EncurtaURL.Model.Links.Link;
import dev.vinicius.EncurtaURL.Model.Links.LinkDTO;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class LinkRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    LinkRepository linkRepository;

    @Test
    @DisplayName("Should get Link successfully from DB")
    void findLinkByShortUrlCase1() {
        String shortUrl = "AJFG2";
        LinkDTO data = new LinkDTO("Google", "https://google.com", shortUrl, "QR Code in progress", LocalDateTime.now());
        this.createLink(data);

        Optional<Link> foundedLink = this.linkRepository.findByShortUrl(data.shortUrl());

        assertThat(foundedLink.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should not get Link successfully from DB when Link does not exist")
    void findLinkByShortUrlCase2() {
        String shortUrl = "AJFG2";
        Optional<Link> foundedLink = this.linkRepository.findByShortUrl(shortUrl);

        assertThat(foundedLink.isPresent()).isFalse();
    }

    private Link createLink(LinkDTO linkDTO) {

        Link newLink = new Link(linkDTO);

        this.entityManager.persist(newLink);
        return newLink;
    }

}