package dev.vinicius.EncurtaURL.unit.mocks;

import dev.vinicius.EncurtaURL.domain.model.Link.Link;
import dev.vinicius.EncurtaURL.domain.model.Link.dto.LinkDTO;
import dev.vinicius.EncurtaURL.domain.model.VO.Url;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class MockLink {

    private static final Logger logger = LoggerFactory.getLogger(MockLink.class);

    public Link mockEntity() {
        return mockEntity(new UUID(0, 0));
    }

    public LinkDTO mockDTO() {
        return mockDTO(new UUID(0, 0));
    }

    public Link mockEntity(UUID id) {
        Link link = new Link();
        link.setId(id);
        link.setAlias("alias");
        link.setLongUrl(new Url("https://www.google.com"));
        link.setClickCount(0);

        logger.info("Mocking entity: {}", link);

        return link;
    }

    public LinkDTO mockDTO(UUID id) {
        LinkDTO linkDTO = new LinkDTO();
        linkDTO.setId(id);
        linkDTO.setAlias("alias");
        linkDTO.setLongUrl("https://www.google.com");
        linkDTO.setClickCount(0);

        return linkDTO;
    }

}
