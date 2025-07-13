package dev.vinicius.EncurtaURL.Service;

import dev.vinicius.EncurtaURL.Exceptions.InvalidUrlException;
import dev.vinicius.EncurtaURL.Model.Links.Link;
import dev.vinicius.EncurtaURL.Repository.LinkRepository;
import dev.vinicius.EncurtaURL.Utils.UrlCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class LinkServiceTest {

    @Mock
    private LinkRepository linkRepository;

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
        String url = "https://google.com";
        String shortCode = UrlCode.generate();

        Link expectedLink = new Link(UUID.randomUUID(), name, 0, url, shortCode, "QR Code in progress", LocalDateTime.now());
        when(linkRepository.save(any(Link.class))).thenReturn(expectedLink);

        Link result = linkService.shorterLink(name, url);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(name, result.getCustomAlias());
        assertEquals(url, result.getLongUrl());
        assertEquals(shortCode, result.getShortUrl());
        assertEquals(0, result.getClickCount());
        assertNotNull(result.getShortUrl());
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