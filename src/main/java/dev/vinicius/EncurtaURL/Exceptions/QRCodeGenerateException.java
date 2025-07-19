package dev.vinicius.EncurtaURL.Exceptions;

public class QRCodeGenerateException extends RuntimeException {
    public QRCodeGenerateException(String message) {
        super(message);
    }
}
