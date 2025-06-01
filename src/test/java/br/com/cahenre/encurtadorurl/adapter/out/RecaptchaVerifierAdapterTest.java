package br.com.cahenre.encurtadorurl.adapter.out;

import br.com.cahenre.encurtadorurl.adapter.out.api.RecaptchaVerifierAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RecaptchaVerifierAdapterTest {

    private RestTemplate restTemplate;
    private RecaptchaVerifierAdapter adapter;

    @BeforeEach
    void setup() {
        restTemplate = mock(RestTemplate.class);
        adapter = new RecaptchaVerifierAdapter();

        // Injeta dependências via reflexão
        ReflectionTestUtils.setField(adapter, "restTemplate", restTemplate);
        ReflectionTestUtils.setField(adapter, "secretKey", "segredo-fake");
        ReflectionTestUtils.setField(adapter, "expectedHostname", "meusite.com.br");
    }


    @Test
    void deveRetornarTrueSeTokenForValidoEHostnameCorreto() {
        Map<String, Object> body = Map.of(
                "success", true,
                "hostname", "meusite.com.br"
        );

        ResponseEntity<Map> response = new ResponseEntity<>(body, HttpStatus.OK);
        when(restTemplate.postForEntity(anyString(), any(), eq(Map.class))).thenReturn(response);

        boolean valido = adapter.isValid("token-teste");

        assertTrue(valido);
    }

    @Test
    void deveRetornarFalseSeTokenForInvalido() {
        Map<String, Object> body = Map.of(
                "success", false,
                "hostname", "meusite.com.br"
        );

        ResponseEntity<Map> response = new ResponseEntity<>(body, HttpStatus.OK);
        when(restTemplate.postForEntity(anyString(), any(), eq(Map.class))).thenReturn(response);

        assertFalse(adapter.isValid("token-invalido"));
    }

    @Test
    void deveRetornarFalseSeHostnameForDiferente() {
        Map<String, Object> body = Map.of(
                "success", true,
                "hostname", "malicioso.com"
        );

        ResponseEntity<Map> response = new ResponseEntity<>(body, HttpStatus.OK);
        when(restTemplate.postForEntity(anyString(), any(), eq(Map.class))).thenReturn(response);

        assertFalse(adapter.isValid("token-valido"));
    }

}
