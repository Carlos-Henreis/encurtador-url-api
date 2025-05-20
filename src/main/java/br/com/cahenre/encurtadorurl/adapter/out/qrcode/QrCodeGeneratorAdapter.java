package br.com.cahenre.encurtadorurl.adapter.out.qrcode;

import br.com.cahenre.encurtadorurl.domain.port.out.QrCodeGeneratorPort;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
public class QrCodeGeneratorAdapter implements QrCodeGeneratorPort {
    @Override
    public byte[] generate(String text) throws Exception {
        int width = 250;
        int height = 250;

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
            BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(qrImage, "PNG", baos);
            return baos.toByteArray();

        } catch (WriterException | IOException e) {
            throw new RuntimeException("Erro ao gerar QR Code", e);
        }
    }
}
