package dev.vinicius.EncurtaURL.domain.exception.handler;

import dev.vinicius.EncurtaURL.domain.exception.InvalidUrlException;
import dev.vinicius.EncurtaURL.domain.exception.QRCodeGenerateException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.security.sasl.AuthenticationException;

@RestControllerAdvice
public class CustomEntityResponseHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ProblemDetail> handleAllExceptions(Exception ex) {
        ProblemDetail problemDetails = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problemDetails.setTitle("Internal Server Error");
        problemDetails.setDetail(ex.getMessage());

        return new ResponseEntity<>(problemDetails, HttpStatusCode.valueOf(problemDetails.getStatus()));
    }

    @ExceptionHandler(value = InvalidUrlException.class)
    public ResponseEntity<ProblemDetail> handleInvalidUrlException(InvalidUrlException ex) {
        ProblemDetail problemDetails = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetails.setTitle("Invalid URL");
        problemDetails.setDetail(ex.getMessage());

        return new ResponseEntity<>(problemDetails, HttpStatusCode.valueOf(problemDetails.getStatus()));
    }

    @ExceptionHandler(value = QRCodeGenerateException.class)
    public ResponseEntity<ProblemDetail> handleQRCodeGenerateException(QRCodeGenerateException ex) {
        ProblemDetail problemDetails = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetails.setTitle("QR Code Generate Error");
        problemDetails.setDetail(ex.getMessage());

        return new ResponseEntity<>(problemDetails, HttpStatusCode.valueOf(problemDetails.getStatus()));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ProblemDetail> handleAuthenticationException(AuthenticationException ex) {
        ProblemDetail problemDetails = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
        problemDetails.setTitle("Authentication error");
        problemDetails.setDetail(ex.getMessage());

        return new ResponseEntity<>(problemDetails, HttpStatusCode.valueOf(problemDetails.getStatus()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ProblemDetail> handleConstraintViolationException(ConstraintViolationException ex) {
        ProblemDetail problemDetails = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetails.setTitle("Constraint violation");
        problemDetails.setDetail(ex.getConstraintViolations().iterator().next().getMessage());

        return new ResponseEntity<>(problemDetails, HttpStatusCode.valueOf(problemDetails.getStatus()));
    }

}
