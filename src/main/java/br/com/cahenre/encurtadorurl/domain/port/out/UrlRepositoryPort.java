package br.com.cahenre.encurtadorurl.domain.port.out;

import br.com.cahenre.encurtadorurl.domain.model.Url;

import java.util.Optional;

public interface UrlRepositoryPort {
    Url save(Url url);
    Optional<Url> findByUrlEncurtada(String urlEncurtada);
}