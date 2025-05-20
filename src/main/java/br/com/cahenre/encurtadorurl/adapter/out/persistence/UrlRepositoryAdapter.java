package br.com.cahenre.encurtadorurl.adapter.out.persistence;

import br.com.cahenre.encurtadorurl.domain.model.Url;
import br.com.cahenre.encurtadorurl.domain.port.out.UrlRepositoryPort;
import br.com.cahenre.encurtadorurl.mapper.UrlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UrlRepositoryAdapter implements UrlRepositoryPort {

    @Autowired
    private UrlJpaRepository repository;

    @Autowired
    private UrlMapper urlMapper;

    @Override
    public Url save(Url url) {
        UrlEntity entity = urlMapper.toEntity(url);
        UrlEntity saved = repository.save(entity);
        return urlMapper.toDomain(saved);
    }

    @Override
    public Optional<Url> findByUrlEncurtada(String urlEncurtada) {
        return repository.findByUrlEncurtada(urlEncurtada).map(urlMapper::toDomain);
    }
}
