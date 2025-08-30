package dev.vinicius.EncurtaURL.adapter.in.rest.docs;

import dev.vinicius.EncurtaURL.domain.model.Link.dto.LinkResponse;
import dev.vinicius.EncurtaURL.domain.model.User.dto.LoginDTO;
import dev.vinicius.EncurtaURL.domain.model.User.dto.LoginResponseDTO;
import dev.vinicius.EncurtaURL.domain.model.User.dto.RegisterResponseDTO;
import dev.vinicius.EncurtaURL.domain.model.User.dto.UserRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthenticationControllerDocs {

    @Operation(
            description = "Login",
            summary = "Login de usuário",
            tags = {"Link"},
            responses = {
                    @ApiResponse(
                            description = "Success", responseCode = "200",
                            content =  @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = LoginResponseDTO.class)
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
    @PostMapping("/login")
    ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO);

    @PostMapping("/register")
    ResponseEntity<RegisterResponseDTO> register(@RequestBody UserRequestDTO userRequestDTO);
}
