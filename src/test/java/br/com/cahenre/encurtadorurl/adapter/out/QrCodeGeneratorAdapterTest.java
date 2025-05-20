package br.com.cahenre.encurtadorurl.adapter.out;

import br.com.cahenre.encurtadorurl.adapter.out.qrcode.QrCodeGeneratorAdapter;
import com.google.zxing.WriterException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class QrCodeGeneratorAdapterTest {

    @InjectMocks
    private QrCodeGeneratorAdapter generator;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveGerarQrCodeComSucesso() throws Exception {
        // Arrange
        String url = "https://carlos-henreis.com.br";

        // Act
        byte[] qrCode = generator.generate(url);

        // Assert
        assertNotNull(qrCode);
        assertTrue(qrCode.length > 0);
    }

    @Test
    void deveLancarExcecaoSeTextoForInvalido() {
        assertThrows(RuntimeException.class, () -> {
            generator.generate(null); // texto inválido
        });
    }
}