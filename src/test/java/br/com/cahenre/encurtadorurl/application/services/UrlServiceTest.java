package br.com.cahenre.encurtadorurl.application.services;

import br.com.cahenre.encurtadorurl.application.service.UrlService;
import br.com.cahenre.encurtadorurl.domain.model.Url;
import br.com.cahenre.encurtadorurl.domain.port.out.QrCodeGeneratorPort;
import br.com.cahenre.encurtadorurl.domain.port.out.RecaptchaVerifierPort;
import br.com.cahenre.encurtadorurl.domain.port.out.UrlRepositoryPort;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UrlServiceTest {

    @Mock
    private UrlRepositoryPort urlRepositoryPort;
    @Mock
    private QrCodeGeneratorPort qrCodeGeneratorPort;
    @Mock
    private RecaptchaVerifierPort recaptchaVerifierPort;
    @InjectMocks
    private UrlService urlService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        FixtureFactoryLoader.loadTemplates("br.com.cahenre.encurtadorurl.fixtures");
    }

    @Test
    void deveCriarUrlEncurtadaSucesso() {
        // arrange
        ReflectionTestUtils.setField(urlService, "baseUrl", "https://mockada.com/");
        Url url = Fixture.from(Url.class).gimme("valida");
        when(urlRepositoryPort.save(any(Url.class))).thenReturn(url);
        when(recaptchaVerifierPort.isValid(anyString())).thenReturn(true);

        // act
        String resultado = urlService.createShortUrl(url.getUrlOrigem(), anyString()).getUrlEncurtada();

        // assert
        assertNotNull(resultado);
        verify(urlRepositoryPort, times(1)).save(any(Url.class));
    }

    @Test
    void deveCriarUrlEncurtadaErroValidarRecaptcha() {
        // arrange
        ReflectionTestUtils.setField(urlService, "baseUrl", "https://mockada.com/");
        Url url = Fixture.from(Url.class).gimme("valida");
        when(recaptchaVerifierPort.isValid(anyString())).thenReturn(false);

        // act & assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            urlService.createShortUrl(url.getUrlOrigem(), anyString());
        });

        assertEquals("Somente humanos podem encurtar URLs. Por favor, complete o desafio de reCAPTCHA.", ex.getMessage());
    }

    @Test
    void deveCriarUrlHttpEncurtadaSucesso() {
        // arrange
        ReflectionTestUtils.setField(urlService, "baseUrl", "https://mockada.com/");
        Url url = Fixture.from(Url.class).gimme("valida");
        url.setUrlOrigem("http://www.mockada.com");
        when(urlRepositoryPort.save(any(Url.class))).thenReturn(url);
        when(recaptchaVerifierPort.isValid(anyString())).thenReturn(true);
        // act
        String resultado = urlService.createShortUrl(url.getUrlOrigem(), anyString()).getUrlEncurtada();

        // assert
        assertNotNull(resultado);
        verify(urlRepositoryPort, times(1)).save(any(Url.class));
    }

    @Test
    void deveCriarUrlSemProtocoloEncurtadaSucesso() {
        // arrange
        ReflectionTestUtils.setField(urlService, "baseUrl", "https://mockada.com/");
        Url url = Fixture.from(Url.class).gimme("valida");
        url.setUrlOrigem("mockada.com");
        when(urlRepositoryPort.save(any(Url.class))).thenReturn(url);
        when(recaptchaVerifierPort.isValid(anyString())).thenReturn(true);
        // act
        String resultado = urlService.createShortUrl(url.getUrlOrigem(), anyString()).getUrlEncurtada();

        // assert
        assertNotNull(resultado);
        verify(urlRepositoryPort, times(1)).save(any(Url.class));
    }

    @Test
    void deveRetornarUrlOriginalQuandoUrlEncurtadaExistir() {
        // arrange
        Url url = Fixture.from(Url.class).gimme("valida");
        when(urlRepositoryPort.findByUrlEncurtada(url.getUrlEncurtada())).thenReturn(Optional.of(url));
        when(recaptchaVerifierPort.isValid(anyString())).thenReturn(true);

        // act
        String resultado = urlService.getOriginalUrl(url.getUrlEncurtada());

        assertNotNull(resultado);
        assertEquals(resultado, url.getUrlOrigem());
        verify(urlRepositoryPort, times(1)).findByUrlEncurtada(url.getUrlEncurtada());
    }

    @Test
    void deveLancarExcecaoQuandoUrlNaoEncontrada() {
        // arrange
        when(urlRepositoryPort.findByUrlEncurtada("invalido"))
                .thenReturn(Optional.empty());
        when(recaptchaVerifierPort.isValid(anyString())).thenReturn(true);

        // act
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            urlService.getOriginalUrl("invalido");
        });

        // assert
        assertEquals("URL não encontrada", ex.getMessage());
    }

    @Test
    void deveRetornarEstatisticasQuandoUrlEncurtadaExistir() {
        // arrange
        Url url = Fixture.from(Url.class).gimme("valida");
        when(urlRepositoryPort.findByUrlEncurtada(url.getUrlEncurtada())).thenReturn(Optional.of(url));
        when(recaptchaVerifierPort.isValid(anyString())).thenReturn(true);

        // act
        var resultado = urlService.getStatistics(url.getUrlEncurtada(), anyString());

        // assert
        assertNotNull(resultado);
        assertEquals(resultado.getUrlOrigem(), url.getUrlOrigem());
        verify(urlRepositoryPort, times(1)).findByUrlEncurtada(url.getUrlEncurtada());
    }

    @Test
    void deveLancarExcecaoQuandoEstatisticasUrlNaoEncontrada() {
        // arrange
        when(urlRepositoryPort.findByUrlEncurtada("invalido"))
                .thenReturn(Optional.empty());
        when(recaptchaVerifierPort.isValid(anyString())).thenReturn(true);

        // act
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            urlService.getStatistics("invalido", anyString());
        });

        // assert
        assertEquals("URL não encontrada", ex.getMessage());
    }

    @Test
    void deveRetornarQrCodeQuandoUrlEncurtadaExistir() throws Exception {
        // arrange
        Url url = Fixture.from(Url.class).gimme("valida");
        when(urlRepositoryPort.findByUrlEncurtada(url.getUrlEncurtada())).thenReturn(Optional.of(url));
        when(qrCodeGeneratorPort.generate(anyString())).thenReturn(new byte[23]);
        when(recaptchaVerifierPort.isValid(anyString())).thenReturn(true);
        // act
        byte[] resultado = urlService.getQrCodeLink(url.getUrlEncurtada(), anyString());

        // assert
        assertNotNull(resultado);
    }

}
