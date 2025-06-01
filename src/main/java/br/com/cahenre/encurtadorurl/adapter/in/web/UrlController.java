package br.com.cahenre.encurtadorurl.adapter.in.web;

import br.com.cahenre.encurtadorurl.adapter.in.web.dto.request.UrlEncurtadaRequest;
import br.com.cahenre.encurtadorurl.adapter.in.web.dto.response.EstatisticasResponse;
import br.com.cahenre.encurtadorurl.adapter.in.web.dto.response.UrlEncurtadaResponse;
import br.com.cahenre.encurtadorurl.domain.port.in.UrlUseCase;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.net.URI;

@RestController
public class UrlController {

    @Autowired
    private UrlUseCase urlUseCase;

    @PostMapping
    public ResponseEntity<UrlEncurtadaResponse> create(
            @RequestHeader("X-Recaptcha-Token") String recaptchaToken,
            @RequestBody @Valid UrlEncurtadaRequest request) {
        UrlEncurtadaResponse response = urlUseCase.createShortUrl(request.getUrlOrigem(), recaptchaToken);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<String> redirect(
            @PathVariable String shortCode) {
        String originalUrl = urlUseCase.getOriginalUrl(shortCode);
        return ResponseEntity.status(302).location(URI.create(originalUrl)).build();
    }

    @GetMapping("/stats/{shortCode}")
    public ResponseEntity<EstatisticasResponse> getStatistics(
            @RequestHeader("X-Recaptcha-Token") String recaptchaToken,
            @PathVariable String shortCode) {
        return ResponseEntity.ok(urlUseCase.getStatistics(shortCode, recaptchaToken));
    }

    @GetMapping("/qrcode/{shortCode}")
    public ResponseEntity<byte[]> getQrCode(
            @RequestHeader("X-Recaptcha-Token") String recaptchaToken,
            @PathVariable String shortCode) throws Exception {
        byte[] qrCode = urlUseCase.getQrCodeLink(shortCode, recaptchaToken);
        return ResponseEntity.ok()
                .header("Content-Type", "image/png")
                .body(qrCode);
    }

}
