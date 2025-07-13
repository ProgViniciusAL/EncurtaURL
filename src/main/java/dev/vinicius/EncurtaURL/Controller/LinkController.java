package dev.vinicius.EncurtaURL.Controller;

import dev.vinicius.EncurtaURL.Model.Links.Link;
import dev.vinicius.EncurtaURL.Model.Links.LinkRequest;
import dev.vinicius.EncurtaURL.Model.Links.LinkResponse;
import dev.vinicius.EncurtaURL.Service.LinkService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.XSlf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
public class LinkController {

    @Autowired
    private LinkService linkService;

    // Função para gerar URL encurtada
    @PostMapping("/encurta-url")
    public ResponseEntity<LinkResponse> generateShortUrl(@RequestBody LinkRequest request) {
        Link link = linkService.shorterLink(request.customAlias(), request.originalUrl());

        String generateUserRedirectUrl = "http://localhost:8080/r/" + link.getShortUrl();

        return ResponseEntity.status(HttpStatus.CREATED).body(new LinkResponse(link, generateUserRedirectUrl));

    }

    // Função para listar URL's
    @GetMapping("/urls")
    public ResponseEntity<List<Link>> getUrls() {
        List<Link> urlList = linkService.getAllUrls();
        return ResponseEntity.status(HttpStatus.OK).body(urlList);
    }

    // Função para redirecionar usuário para URL original através da URL curta
    @GetMapping("/r/{shortUrl}")
    public void redirectLink(@PathVariable String shortUrl, HttpServletResponse response) throws IOException {

        Link link = linkService.getOriginalUrl(shortUrl);

        if (link != null) {
            response.sendRedirect(link.getLongUrl());
            link.addClickCount();
            linkService.updateLink(link);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }

}
