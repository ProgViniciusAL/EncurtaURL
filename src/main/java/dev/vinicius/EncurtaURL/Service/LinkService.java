package dev.vinicius.EncurtaURL.Service;

import dev.vinicius.EncurtaURL.Domain.User.User;
import dev.vinicius.EncurtaURL.Exceptions.InvalidUrlException;
import dev.vinicius.EncurtaURL.Exceptions.OriginalUrlException;
import dev.vinicius.EncurtaURL.Domain.Link.Link;
import dev.vinicius.EncurtaURL.Domain.Link.dto.LinkDTO;
import dev.vinicius.EncurtaURL.Repository.LinkRepository;
import dev.vinicius.EncurtaURL.Utils.UrlCode;
import dev.vinicius.EncurtaURL.Utils.UrlValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class LinkService {

    private static final Logger log = LoggerFactory.getLogger(LinkService.class);

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private QRCodeService qrCodeService;

    public Link shorterLink(String alias, String originalUrl) {
        if(!UrlValidation.isValidUrl(originalUrl)) {
            throw new InvalidUrlException("Invalid URL");
        }
        String shortCode = UrlCode.generate();

        User authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        LinkDTO data = new LinkDTO(alias, originalUrl, shortCode, qrCodeService.generateQRCode("/r/" + shortCode), LocalDateTime.now());
        Link link = new Link(data);
        link.setUser(authenticatedUser);
        log.info("Created shorten link: {}", link);

        return linkRepository.save(link);
    }

    public List<Link> getAllUrls() {
        User authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return linkRepository.findAllByUserId(authenticatedUser.getId());
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
