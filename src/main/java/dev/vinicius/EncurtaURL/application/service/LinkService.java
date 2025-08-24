package dev.vinicius.EncurtaURL.application.service;

import dev.vinicius.EncurtaURL.application.mapper.ObjectMapper;
import dev.vinicius.EncurtaURL.domain.model.User.User;
import dev.vinicius.EncurtaURL.domain.exception.InvalidUrlException;
import dev.vinicius.EncurtaURL.domain.exception.OriginalUrlException;
import dev.vinicius.EncurtaURL.domain.model.Link.Link;
import dev.vinicius.EncurtaURL.domain.model.Link.dto.LinkDTO;
import dev.vinicius.EncurtaURL.adapter.out.repository.LinkRepository;
import dev.vinicius.EncurtaURL.adapter.out.repository.UserRepository;
import dev.vinicius.EncurtaURL.Utils.UrlCode;
import dev.vinicius.EncurtaURL.Utils.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class LinkService {

    private static final Logger log = LoggerFactory.getLogger(LinkService.class);

    private static final Pattern ALIAS_PATTERN = Pattern.compile("^[a-zA-Z0-9-]+$");

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private QRCodeService qrCodeService;

    @Autowired
    private UserRepository userRepository;

    public LinkDTO shorterLink(String alias, String originalUrl) {
        if(!Validator.isValidUrl(originalUrl) && !Validator.isValidAlias(alias)) {
            throw new InvalidUrlException("Invalid URL");
        }
        String shortCode = UrlCode.generate();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName()).orElse(null);

        Link link = new Link(null, alias, 0, originalUrl, "http://localhost:8080/r/" + shortCode, shortCode, qrCodeService.generateQRCode("/r/" + shortCode), LocalDateTime.now());
        link.setUser(user);

        log.info("Created shorten link: {}", link);

        return ObjectMapper.parseObject(linkRepository.save(link),  LinkDTO.class);
    }

    public List<Link> getAllUrls() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Usuário não autenticado ou não encontrado no banco"));

        return linkRepository.findAllByUserId(user.getId());
    }

    public Link getOriginalUrl(String shortUrl) {
        try {
            log.info("Searching original URL by the shorten URL.");
            return linkRepository.findByShortUrl(shortUrl).orElseThrow(() -> new OriginalUrlException("URL not found in repository"));
        } catch (OriginalUrlException exception) {
            throw new OriginalUrlException("URL not found");
        }
    }

    public byte[] getQRCodeImage(UUID id) {
        Link link = linkRepository.findById(id).orElseThrow(() -> new RuntimeException("Link not found"));
        return link.getQRCode();
    }

    public Link updateLink(Link link) {
        return linkRepository.save(link);
    }

    public Link getOriginalUrlByAlias(String alias) {
        try {
            log.info("Searching original URL by the custom alias.");
            return linkRepository.findByAlias(alias);
        } catch (OriginalUrlException exception) {
            throw new OriginalUrlException("URL not found");
        }
    }

}
