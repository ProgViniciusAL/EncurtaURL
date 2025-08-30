package dev.vinicius.EncurtaURL.application.service;

import dev.vinicius.EncurtaURL.adapter.out.security.auth.AuthenticatedUserProvider;
import dev.vinicius.EncurtaURL.application.mapper.ObjectMapper;
import dev.vinicius.EncurtaURL.domain.model.Link.dto.LinkRequest;
import dev.vinicius.EncurtaURL.domain.model.User.User;
import dev.vinicius.EncurtaURL.domain.exception.OriginalUrlException;
import dev.vinicius.EncurtaURL.domain.model.Link.Link;
import dev.vinicius.EncurtaURL.domain.model.Link.dto.LinkDTO;
import dev.vinicius.EncurtaURL.adapter.out.repository.SpringDataUserRepository;
import dev.vinicius.EncurtaURL.domain.model.VO.ShortCode;
import dev.vinicius.EncurtaURL.domain.model.VO.Url;
import dev.vinicius.EncurtaURL.domain.port.LinkRepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class LinkService {

    private static final Logger log = LoggerFactory.getLogger(LinkService.class);

    private static final Pattern ALIAS_PATTERN = Pattern.compile("^[a-zA-Z0-9-]+$");

    @Value("${host.info.url}")
    private String hostname;

    @Autowired
    private LinkRepositoryPort linkRepositoryPort;

    @Autowired
    private SpringDataUserRepository springDataUserRepository;

    @Autowired
    private QRCodeService qrCodeService;

    @Autowired
    private AuthenticatedUserProvider authenticatedUserProvider;

    public LinkDTO shorterLink(LinkRequest request) {

        Link createdLink = new Link();
        createdLink.setLongUrl(new Url(request.originalUrl()));
        createdLink.setAlias(request.customAlias());
        createdLink.setUser(authenticatedUserProvider.getUser());

        ShortCode code = new ShortCode();
        Url shortUrl = new Url(hostname + "/r/" + code.generate());

        createdLink.setShortUrl(shortUrl);

        log.info("Created shorten link: {}", createdLink);

        return ObjectMapper.parseObject(linkRepositoryPort.save(createdLink), LinkDTO.class);
    }

    public List<Link> findAll() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = springDataUserRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Usuário não autenticado ou não encontrado no banco"));

        return linkRepositoryPort.findAllByUserId(user.getId());
    }

    public Link getOriginalUrl(Url shortUrl) {
        try {
            log.info("Searching original URL by the shorten URL: {}", shortUrl);
            return linkRepositoryPort.findByShortUrl(shortUrl).orElseThrow(() -> new OriginalUrlException("URL not found in repository"));
        } catch (OriginalUrlException exception) {
            throw new OriginalUrlException("URL not found");
        }
    }

    public byte[] getQRCodeImage(UUID id) {
        Link link = linkRepositoryPort.findById(id).orElseThrow(() -> new RuntimeException("Link not found"));
        link.setQRCode(qrCodeService.generateQRCode(link.getShortUrl()));
        linkRepositoryPort.save(link);
        return link.getQRCode();
    }

    public Link updateLink(Link link) {
        return linkRepositoryPort.save(link);
    }

    public Link getOriginalUrlByAlias(String alias) {
        try {
            log.info("Searching original URL by the custom alias.");
            return linkRepositoryPort.findByAlias(alias).orElseThrow(() -> new OriginalUrlException("URL not found in repository"));
        } catch (OriginalUrlException exception) {
            throw new OriginalUrlException("URL not found");
        }
    }

}
