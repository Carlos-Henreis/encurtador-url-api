package br.com.cahenre.encurtadorurl.adapter.in.exception;


import br.com.cahenre.encurtadorurl.adapter.in.web.UrlController;
import br.com.cahenre.encurtadorurl.domain.port.in.UrlUseCase;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UrlController.class)
public class RestExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    UrlUseCase urlUseCase;

    @Test
    void deveTratarRuntimeExceptionComoErro500() throws Exception {
        // Arrange
        String shortCode = "invalid-code";
        when(urlUseCase.getOriginalUrl(shortCode)).thenThrow(new RuntimeException("Erro interno"));

        // Act and Assert
        mockMvc.perform(get("/" + shortCode))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(containsString("Erro interno")));

    }
}
