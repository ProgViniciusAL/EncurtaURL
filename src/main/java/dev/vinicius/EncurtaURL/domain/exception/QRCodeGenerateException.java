package dev.vinicius.EncurtaURL.domain.exception;

public class QRCodeGenerateException extends RuntimeException {
    public QRCodeGenerateException(String message) {
        super(message);
    }
}
