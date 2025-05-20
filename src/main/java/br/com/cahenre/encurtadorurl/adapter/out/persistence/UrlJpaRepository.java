package br.com.cahenre.encurtadorurl.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlJpaRepository extends JpaRepository<UrlEntity, Long> {
    Optional<UrlEntity> findByUrlEncurtada(String urlEncurtada);
}
