package dev.vinicius.EncurtaURL.Controller;

import dev.vinicius.EncurtaURL.Domain.Link.Link;
import dev.vinicius.EncurtaURL.Domain.Link.dto.LinkRequest;
import dev.vinicius.EncurtaURL.Domain.Link.dto.LinkResponse;
import dev.vinicius.EncurtaURL.Domain.User.User;
import dev.vinicius.EncurtaURL.Service.LinkService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "https://preview-encurta-url-design-kzmpmj1hsx8t654p8wmu.vusercontent.net/")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @Value("${host.info.url}")
    private String hostname;

    // Função para gerar URL encurtada
    @PostMapping("/short")
    public ResponseEntity<LinkResponse> generateShortUrl(@RequestBody LinkRequest request) {
        Link link = linkService.shorterLink(request.customAlias(), request.originalUrl());

        String generateUserRedirectUrl = hostname + "/r/" + link.getShortCode();

        return ResponseEntity.status(HttpStatus.CREATED).body(new LinkResponse(link, generateUserRedirectUrl));

    }

    // Função para listar URL's
    @GetMapping("/links")
    public ResponseEntity<List<Link>> getLinks() {
        List<Link> linksList = linkService.getAllUrls();
        return ResponseEntity.status(HttpStatus.OK).body(linksList);
    }

    @GetMapping("/links/{id}/qrcode")
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
