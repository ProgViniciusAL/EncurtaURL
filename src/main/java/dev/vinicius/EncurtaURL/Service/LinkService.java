package dev.vinicius.EncurtaURL.Service;

import dev.vinicius.EncurtaURL.Exceptions.InvalidUrlException;
import dev.vinicius.EncurtaURL.Exceptions.OriginalUrlException;
import dev.vinicius.EncurtaURL.Model.Links.Link;
import dev.vinicius.EncurtaURL.Model.Links.LinkDTO;
import dev.vinicius.EncurtaURL.Model.Links.LinkResponse;
import dev.vinicius.EncurtaURL.Repository.LinkRepository;
import dev.vinicius.EncurtaURL.Utils.UrlCode;
import dev.vinicius.EncurtaURL.Utils.UrlValidation;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LinkService {

    private static final Logger log = LoggerFactory.getLogger(LinkService.class);

    @Autowired
    private LinkRepository linkRepository;

    //TODO: Incluir geração de QRCode futuramente
    public Link shorterLink(String alias, String originalUrl) {
        if(UrlValidation.isValidUrl(originalUrl)) {
            LinkDTO data = new LinkDTO(alias, originalUrl, UrlCode.generate(), "QRCODE missing from now", LocalDateTime.now());
            Link link = new Link(data);
            log.info("Created shorten link: {}", link);

            return linkRepository.save(link);
        }

        throw new InvalidUrlException("Invalid URL exception");
    }

    public List<Link> getAllUrls() {
        return linkRepository.findAll();
    }

    public Link getOriginalUrl(String shortUrl) {
        try {
            log.info("Searching original URL by the shorten URL.");
            return linkRepository.findByShortUrl(shortUrl).orElseThrow(() -> new OriginalUrlException("URL not found in repository"));
        } catch (OriginalUrlException exception) {
            throw new OriginalUrlException("URL not found");
        }
    }

    public Link updateLink(Link link) {
        return linkRepository.save(link);
    }

    public Link getOriginalUrlByAlias(String alias) {
        try {
            log.info("Searching original URL by the custom alias.");
            return linkRepository.findByCustomAlias(alias);
        } catch (OriginalUrlException exception) {
            throw new OriginalUrlException("URL not found");
        }
    }

}
