package dev.vinicius.EncurtaURL.Repository;

import dev.vinicius.EncurtaURL.adapter.out.repository.LinkRepository;
import dev.vinicius.EncurtaURL.application.mapper.ObjectMapper;
import dev.vinicius.EncurtaURL.domain.model.Link.Link;
import dev.vinicius.EncurtaURL.domain.model.Link.dto.LinkDTO;
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
        LinkDTO link = new LinkDTO();
        link.setAlias("Google");
        link.setLongUrl("https://google.com");
        link.setShortUrl("http:localhost:8080/r/AJFG2");

        this.createLink(link);

        Optional<Link> foundedLink = this.linkRepository.findByShortUrl(link.getShortUrl());

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

        Link createdLink = ObjectMapper.parseObject(linkDTO, Link.class);

        this.entityManager.persist(createdLink);
        return createdLink;
    }

}