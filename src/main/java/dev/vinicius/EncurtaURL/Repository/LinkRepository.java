package dev.vinicius.EncurtaURL.Repository;

import dev.vinicius.EncurtaURL.Domain.Link.Link;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LinkRepository extends JpaRepository<Link, UUID> {

    Optional<Link> findByShortUrl(String shortUrl);
    Link findByAlias(String alias);
    List<Link> findAllByUserId(Long user_id);

}
