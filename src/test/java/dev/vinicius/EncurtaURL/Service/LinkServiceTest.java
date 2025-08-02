package dev.vinicius.EncurtaURL.Service;

import dev.vinicius.EncurtaURL.Domain.User.User;
import dev.vinicius.EncurtaURL.Exceptions.InvalidUrlException;
import dev.vinicius.EncurtaURL.Domain.Link.Link;
import dev.vinicius.EncurtaURL.Repository.LinkRepository;
import dev.vinicius.EncurtaURL.Utils.UrlCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class LinkServiceTest {

    @Mock
    private LinkRepository linkRepository;
    @Mock
    private QRCodeService qrCodeService;
    @InjectMocks
    private LinkService linkService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should short the URL and return the Link successfully")
    void shortUrlCase1() {

        String name = "Google";
        String longUrl = "https://google.com";

        String shortCode = UrlCode.generate();
        String shortUrl = "https://localhost:8080/r/" + shortCode;
        byte[] fakeQR = new byte[]{1, 2, 3};

        Link expectedLink = new Link(UUID.randomUUID(), name, 0, longUrl, shortUrl, shortCode, fakeQR, LocalDateTime.now());
        when(linkRepository.save(any(Link.class))).thenReturn(expectedLink);

        Link result = linkService.shorterLink(name, longUrl);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(name, result.getAlias());
        assertEquals(longUrl, result.getLongUrl());
        assertEquals(shortCode, result.getShortCode());
        assertEquals(0, result.getClickCount());
        assertNotNull(result.getShortCode());
        assertNotNull(result.getQRCode());
        verify(linkRepository, times(1)).save(any(Link.class));

    }

    @Test
    @DisplayName("Should throw exception for invalid URL")
    void shortUrlCase2() throws Exception {
        String name = "Inválido";
        String invalidUrl = "htp://url_invalida";

        assertThrows(InvalidUrlException.class, () -> {
            linkService.shorterLink(name, invalidUrl);
        });
    }

}