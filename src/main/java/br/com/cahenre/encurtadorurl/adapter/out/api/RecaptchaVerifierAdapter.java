package br.com.cahenre.encurtadorurl.adapter.out.api;

import br.com.cahenre.encurtadorurl.domain.port.out.RecaptchaVerifierPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.Map;

@Component
public class RecaptchaVerifierAdapter implements RecaptchaVerifierPort {

    private static final String VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${recaptcha.secret}")
    private String secretKey;

    @Value("${recaptcha.expected-hostname}")
    private String expectedHostname;

    @Override
    public boolean isValid(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("secret", secretKey);
        map.add("response", token);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(VERIFY_URL, request, Map.class);

        Map<String, Object> result = response.getBody();
        System.out.println("Recaptcha verification response: " + result);
        boolean success = result != null && Boolean.TRUE.equals(result.get("success"));
        String hostname = result != null ? (String) result.get("hostname") : null;

        return success && expectedHostname.equals(hostname);
    }
}