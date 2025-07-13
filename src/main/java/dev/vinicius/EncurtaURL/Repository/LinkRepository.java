package dev.vinicius.EncurtaURL.Repository;

import dev.vinicius.EncurtaURL.Model.Links.Link;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LinkRepository extends JpaRepository<Link, UUID> {

    Optional<Link> findByShortUrl(String shortUrl);
    Link findByCustomAlias(String customAlias);

}
