package dev.vinicius.EncurtaURL.Repository;

import dev.vinicius.EncurtaURL.adapter.out.repository.SpringDataLinkRepository;
import dev.vinicius.EncurtaURL.application.mapper.ObjectMapper;
import dev.vinicius.EncurtaURL.domain.model.Link.Link;
import dev.vinicius.EncurtaURL.domain.model.Link.dto.LinkDTO;
import dev.vinicius.EncurtaURL.domain.model.VO.Url;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class LinkRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    SpringDataLinkRepository springDataLinkRepository;

    @Test
    @DisplayName("Should get Link successfully from DB")
    void findLinkByShortUrlCase1() {
        LinkDTO link = new LinkDTO();
        link.setAlias("Google");
        link.setLongUrl("https://google.com");
        link.setShortUrl("http://localhost:8080/r/AJFG2");

        this.createLink(link);

        Optional<Link> foundedLink = this.springDataLinkRepository.findByShortUrl(new Url(link.getShortUrl()));

        assertThat(foundedLink.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should not get Link successfully from DB when Link does not exist")
    void findLinkByShortUrlCase2() {
        Url shortUrl = new Url("http://localhost:8080/r/AJFG2");
        Optional<Link> foundedLink = this.springDataLinkRepository.findByShortUrl(shortUrl);

        assertThat(foundedLink.isPresent()).isFalse();
    }

    private Link createLink(LinkDTO linkDTO) {

        Link createdLink = ObjectMapper.parseObject(linkDTO, Link.class);

        this.entityManager.persist(createdLink);
        return createdLink;
    }

}