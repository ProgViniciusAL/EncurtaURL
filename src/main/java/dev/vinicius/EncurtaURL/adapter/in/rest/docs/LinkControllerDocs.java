package dev.vinicius.EncurtaURL.adapter.in.rest.docs;

import dev.vinicius.EncurtaURL.domain.model.Link.Link;
import dev.vinicius.EncurtaURL.domain.model.Link.dto.LinkRequest;
import dev.vinicius.EncurtaURL.domain.model.Link.dto.LinkResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface LinkControllerDocs {

    @Operation(
            description = "Endpoint encurtamento de URL",
            summary = "Encurta a URL, gera entidade de Link e armazena em banco de dados",
            tags = {"Link"},
            responses = {
                    @ApiResponse(
                            description = "Success", responseCode = "200",
                            content =  @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = LinkResponse.class)
                            )
                    ),
                    @ApiResponse(description = "No content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(
                            description = "Not found", responseCode = "404", content = @Content(schema = @Schema(implementation = ProblemDetail.class))
                    ),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    ResponseEntity<LinkResponse> generateShortUrl(@RequestBody LinkRequest request);

    @Operation(
            description = "Endpoint encurtamento de URL",
            summary = "Encurta a URL, gera entidade de Link e armazena em banco de dados",
            tags = {"Link"},
            responses = {
                    @ApiResponse(
                            description = "Success", responseCode = "200",
                            content =  @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = LinkResponse.class)
                            )
                    )
            }
    )
    ResponseEntity<List<Link>> getLinks();

    @Operation(
            description = "Endpoint encurtamento de URL",
            summary = "Encurta a URL, gera entidade de Link e armazena em banco de dados",
            tags = {"Link"},
            responses = {
                    @ApiResponse(
                            description = "Success", responseCode = "200",
                            content =  @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = LinkResponse.class)
                            )
                    )
            }
    )
    ResponseEntity<byte[]> getQRCodeImage(@PathVariable UUID id);

    @Operation(
            description = "Endpoint encurtamento de URL",
            summary = "Encurta a URL, gera entidade de Link e armazena em banco de dados",
            tags = {"Link"},
            responses = {
                    @ApiResponse(
                            description = "Success", responseCode = "200",
                            content =  @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = LinkResponse.class)
                            )
                    )
            }
    )
    void redirectLink(@PathVariable String shortCode, HttpServletResponse response) throws IOException;
}
