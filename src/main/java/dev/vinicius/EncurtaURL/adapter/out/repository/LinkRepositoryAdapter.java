package dev.vinicius.EncurtaURL.adapter.out.repository;

import dev.vinicius.EncurtaURL.domain.model.Link.Link;
import dev.vinicius.EncurtaURL.domain.model.User.User;
import dev.vinicius.EncurtaURL.domain.model.VO.Url;
import dev.vinicius.EncurtaURL.domain.port.LinkRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class LinkRepositoryAdapter implements LinkRepositoryPort {

    @Autowired
    private SpringDataLinkRepository repository;

    @Override
    public Link save(Link link) {
        return repository.save(link);
    }

    @Override
    public Optional<Link> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Link> findByShortUrl(Url shortUrl) {
        return repository.findByShortUrl(shortUrl);
    }

    @Override
    public Optional<Link> findByAlias(String alias) {
        return repository.findByAlias(alias);
    }

    @Override
    public List<Link> findAllByUserId(Long userId) {
        return repository.findAllByUserId(userId);
    }

}
