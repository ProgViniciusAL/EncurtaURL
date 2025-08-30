package dev.vinicius.EncurtaURL.domain.port;

import dev.vinicius.EncurtaURL.domain.model.Link.Link;
import dev.vinicius.EncurtaURL.domain.model.User.User;
import dev.vinicius.EncurtaURL.domain.model.VO.Url;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LinkRepositoryPort {
    Link save(Link link);
    Optional<Link> findById(UUID id);
    Optional<Link> findByShortUrl(Url shortUrl);
    Optional<Link> findByAlias(String alias);
    List<Link> findAllByUserId(Long userId);

}
