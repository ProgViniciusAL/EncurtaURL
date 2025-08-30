package dev.vinicius.EncurtaURL.adapter.out.repository;

import dev.vinicius.EncurtaURL.domain.model.Link.Link;
import dev.vinicius.EncurtaURL.domain.model.User.User;
import dev.vinicius.EncurtaURL.domain.model.VO.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SpringDataLinkRepository extends JpaRepository<Link, UUID> {

    Optional<Link> findByShortUrl(Url shortUrl);
    Optional<Link> findByAlias(String alias);
    List<Link> findAllByUserId(Long user_id);

}
