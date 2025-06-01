package br.com.cahenre.encurtadorurl.adapter.in;

import br.com.cahenre.encurtadorurl.adapter.in.web.UrlController;
import br.com.cahenre.encurtadorurl.adapter.in.web.dto.request.UrlEncurtadaRequest;
import br.com.cahenre.encurtadorurl.adapter.in.web.dto.response.EstatisticasResponse;
import br.com.cahenre.encurtadorurl.adapter.in.web.dto.response.UrlEncurtadaResponse;
import br.com.cahenre.encurtadorurl.domain.port.in.UrlUseCase;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UrlController.class)
class UrlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UrlUseCase urlUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        FixtureFactoryLoader.loadTemplates("br.com.cahenre.encurtadorurl.fixtures");
    }

    @Test
    void deveCriarUrlEncurtada() throws Exception {
        // Arrange
        UrlEncurtadaRequest request = Fixture.from(UrlEncurtadaRequest.class).gimme("valida");
        UrlEncurtadaResponse response = Fixture.from(UrlEncurtadaResponse.class).gimme("valida");
        when(urlUseCase.createShortUrl(eq(request.getUrlOrigem()), anyString())).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Recaptcha-Token", "valid-token")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.urlEncurtada").value(response.getUrlEncurtada()));
    }

    @Test
    void deveRedirecionarParaUrlOriginal() throws Exception {
        when(urlUseCase.getOriginalUrl("abc123")).thenReturn("https://exemplo.com");

        mockMvc.perform(get("/abc123"))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "https://exemplo.com"));
    }

    @Test
    void deveRetornarEstatisticas() throws Exception {

        EstatisticasResponse estatisticasResponse = Fixture.from(EstatisticasResponse.class).gimme("valida");
        when(urlUseCase.getStatistics(eq(estatisticasResponse.getUrlEncurtada()), anyString())).thenReturn(estatisticasResponse);

        mockMvc.perform(get("/stats/" + estatisticasResponse.getUrlEncurtada())
                        .header("X-Recaptcha-Token", "valid-token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.urlOrigem").value(estatisticasResponse.getUrlOrigem()))
                .andExpect(jsonPath("$.totalAcessos").value(estatisticasResponse.getTotalAcessos()));
    }

    @Test
    void deveRetornarQrCode() throws Exception {
        String shortCode = "abc123";
        byte[] qrCode = new byte[]{1, 2, 3, 4, 5};

        when(urlUseCase.getQrCodeLink(eq(shortCode), anyString())).thenReturn(qrCode);

        mockMvc.perform(get("/qrcode/" + shortCode)
                        .header("X-Recaptcha-Token", "valid-token"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("image/png"))
                .andExpect(content().bytes(qrCode));
    }
}