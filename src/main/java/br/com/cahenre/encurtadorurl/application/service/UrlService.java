package br.com.cahenre.encurtadorurl.application.service;

import br.com.cahenre.encurtadorurl.adapter.in.web.dto.response.EstatisticasResponse;
import br.com.cahenre.encurtadorurl.adapter.in.web.dto.response.UrlEncurtadaResponse;
import br.com.cahenre.encurtadorurl.domain.model.Url;
import br.com.cahenre.encurtadorurl.domain.port.in.UrlUseCase;
import br.com.cahenre.encurtadorurl.domain.port.out.QrCodeGeneratorPort;
import br.com.cahenre.encurtadorurl.domain.port.out.UrlRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UrlService implements UrlUseCase {

    @Autowired
    private UrlRepositoryPort urlRepository;
    @Autowired
    private QrCodeGeneratorPort qrCodeGenerator;

    @Value("${app.base-url}")
    private String baseUrl;

    @Override
    public UrlEncurtadaResponse createShortUrl(String urlOrigem) {
        Url url = Url.builder()
                .urlOrigem(this.tratarUrl(urlOrigem))
                .urlEncurtada(this.gerarEndpointEncurtado())
                .ultimoAcessoEm(null)
                .criadoEm(LocalDateTime.now())
                .totalAcessos(0L)
                .build();
        urlRepository.save(url);
        return UrlEncurtadaResponse.builder()
                .urlEncurtada(this.gerarUrlEncurtada(url))
                .build();
    }

    @Override
    public String getOriginalUrl(String shortCode) {
        Url url = urlRepository.findByUrlEncurtada(shortCode)
                .orElseThrow(() -> new RuntimeException("URL não encontrada"));

        url.setTotalAcessos(url.getTotalAcessos() + 1);
        url.setUltimoAcessoEm(LocalDateTime.now());
        urlRepository.save(url);
        return url.getUrlOrigem();
    }

    @Override
    public EstatisticasResponse getStatistics(String shortCode) {
        Url url = urlRepository.findByUrlEncurtada(shortCode)
                .orElseThrow(() -> new RuntimeException("URL não encontrada"));

        return EstatisticasResponse.builder()
                .urlEncurtada(this.gerarUrlEncurtada(url))
                .totalAcessos(url.getTotalAcessos())
                .ultimoAcessoEm(url.getUltimoAcessoEm())
                .criadoEm(url.getCriadoEm())
                .urlOrigem(url.getUrlOrigem())
                .build();
    }

    @Override
    public byte[] getQrCodeLink(String shortCode) throws Exception {
        String link = this.baseUrl + shortCode;
        return qrCodeGenerator.generate(link);
    }

    private String gerarEndpointEncurtado() {
        return UUID.randomUUID().toString().substring(0, 6);
    }


    private String gerarUrlEncurtada(Url url) {
        return this.baseUrl + url.getUrlEncurtada();
    }

    private String tratarUrl(String url) {
        if (url.startsWith("http://") || url.startsWith("https://")) {
            return url;
        } else {
            return "http://" + url;
        }
    }
}

