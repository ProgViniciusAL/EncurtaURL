package dev.vinicius.EncurtaURL.unit.service;

import dev.vinicius.EncurtaURL.adapter.out.security.auth.AuthenticatedUserProvider;
import dev.vinicius.EncurtaURL.application.mapper.ObjectMapper;
import dev.vinicius.EncurtaURL.application.service.LinkService;
import dev.vinicius.EncurtaURL.domain.exception.InvalidUrlException;
import dev.vinicius.EncurtaURL.domain.model.Link.Link;
import dev.vinicius.EncurtaURL.adapter.out.repository.LinkRepository;
import dev.vinicius.EncurtaURL.domain.model.Link.dto.LinkDTO;
import dev.vinicius.EncurtaURL.domain.model.Link.dto.LinkRequest;
import dev.vinicius.EncurtaURL.domain.model.User.User;
import dev.vinicius.EncurtaURL.unit.mocks.MockLink;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class LinkServiceTest {

    MockLink mockLink;

    private static final Logger logger = LoggerFactory.getLogger(LinkServiceTest.class);

    @Mock
    LinkRepository linkRepository;

    @Mock
    AuthenticatedUserProvider authenticatedUserProvider;

    @InjectMocks
    private LinkService linkService;

    @BeforeEach
    void setUp() {
        mockLink = new MockLink();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should short the URL and return the Link successfully")
    void shorterLinkTest() {
        Link mockedLink = mockLink.mockEntity();

        when(linkRepository.save(any(Link.class))).thenReturn(mockedLink);

        LinkDTO result = linkService.shorterLink(new LinkRequest(mockedLink.getLongUrl(), mockedLink.getAlias()));

        assertNotNull(result);
        assertNotNull(result.getId());

        assertEquals(mockedLink.getAlias(), result.getAlias());
        assertEquals(mockedLink.getLongUrl(), result.getLongUrl());
        assertEquals(mockedLink.getShortUrl(), result.getShortUrl());
        assertEquals(mockedLink.getCreatedAt(), result.getCreatedAt());
        assertEquals(0, result.getClickCount());

        verify(linkRepository, times(1)).save(mockedLink);
    }

    @Test
    @DisplayName("Should throw exception for invalid URL")
    void shortUrlCase2() throws Exception {
        Link mockedLink = mockLink.mockEntity();
        mockedLink.setLongUrl("htps://invalid.co");
        LinkRequest mockedLinkRequest = new LinkRequest(mockedLink.getLongUrl(), mockedLink.getAlias());

        assertThrows(InvalidUrlException.class, () -> {
            linkService.shorterLink(mockedLinkRequest);
        });
    }

}