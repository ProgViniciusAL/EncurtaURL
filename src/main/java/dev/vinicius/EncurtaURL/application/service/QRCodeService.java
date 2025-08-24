package dev.vinicius.EncurtaURL.application.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import dev.vinicius.EncurtaURL.domain.exception.QRCodeGenerateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class QRCodeService {

    private static final Logger log = LoggerFactory.getLogger(QRCodeService.class);

    public byte[] generateQRCode(String url) {

        try {
            int width = 600;
            int height = 600;

            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, width, height);

            BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "PNG", byteArrayOutputStream);

            log.info("Created QR Code: {}", byteArrayOutputStream);

            return byteArrayOutputStream.toByteArray();
        } catch(WriterException | IOException exception) {
            throw new QRCodeGenerateException("Error in QR Code generation: " + exception.getMessage());
        }

    }

}
