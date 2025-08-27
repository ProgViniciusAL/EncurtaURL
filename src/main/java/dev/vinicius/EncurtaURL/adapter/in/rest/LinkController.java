package dev.vinicius.EncurtaURL.adapter.in.rest;

import dev.vinicius.EncurtaURL.adapter.in.rest.docs.LinkControllerDocs;
import dev.vinicius.EncurtaURL.application.mapper.ObjectMapper;
import dev.vinicius.EncurtaURL.domain.model.Link.Link;
import dev.vinicius.EncurtaURL.domain.model.Link.dto.LinkDTO;
import dev.vinicius.EncurtaURL.domain.model.Link.dto.LinkRequest;
import dev.vinicius.EncurtaURL.domain.model.Link.dto.LinkResponse;
import dev.vinicius.EncurtaURL.application.service.LinkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "https://preview-encurta-url-design-kzmpmj1hsx8t654p8wmu.vusercontent.net/")
@Tag(name = "Link")
public class LinkController implements LinkControllerDocs {

    @Autowired
    private LinkService linkService;

    @Value("${host.info.url}")
    private String hostname;

    @PostMapping(value = "/encurtar",
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Override
    public ResponseEntity<LinkResponse> generateShortUrl(@RequestBody LinkRequest request) {
        LinkDTO generatedLink = linkService.shorterLink(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(ObjectMapper.parseObject(generatedLink, LinkResponse.class));
    }

    // Função para listar URL's
    @GetMapping("/links")
    @Override
    public ResponseEntity<List<Link>> getLinks() {
        List<Link> linksList = linkService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(linksList);
    }

    @GetMapping("/links/{id}/qrcode")
    @Override
    public ResponseEntity<byte[]> getQRCodeImage(@PathVariable UUID id) {
        byte[] imageBytes = linkService.getQRCodeImage(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(imageBytes.length);

        return ResponseEntity.ok()
                .headers(headers)
                .body(imageBytes);
    }

    // Função para redirecionar usuário para URL original através da URL curta
    @GetMapping("/r/{shortCode}")
    @Override
    public void redirectLink(@PathVariable String shortCode, HttpServletResponse response) throws IOException {

        Link link = linkService.getOriginalUrl(shortCode);

        if (link != null) {
            response.sendRedirect(link.getLongUrl());
            link.registerClick();
            linkService.updateLink(link);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }

}
