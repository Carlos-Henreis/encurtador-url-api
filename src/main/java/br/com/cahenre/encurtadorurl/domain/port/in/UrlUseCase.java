package br.com.cahenre.encurtadorurl.domain.port.in;

import br.com.cahenre.encurtadorurl.adapter.in.web.dto.response.EstatisticasResponse;
import br.com.cahenre.encurtadorurl.adapter.in.web.dto.response.UrlEncurtadaResponse;

public interface UrlUseCase {

    UrlEncurtadaResponse createShortUrl(String originalUrl);
    String getOriginalUrl(String shortCode);

    EstatisticasResponse getStatistics(String shortCode);
    byte[] getQrCodeLink(String shortCode) throws Exception;
}
