package br.com.cahenre.encurtadorurl.adapter.out;

import br.com.cahenre.encurtadorurl.adapter.out.persistence.UrlEntity;
import br.com.cahenre.encurtadorurl.adapter.out.persistence.UrlJpaRepository;
import br.com.cahenre.encurtadorurl.adapter.out.persistence.UrlRepositoryAdapter;
import br.com.cahenre.encurtadorurl.domain.model.Url;
import br.com.cahenre.encurtadorurl.mapper.UrlMapper;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;



class UrlRepositoryAdapterTest {

    @InjectMocks
    private UrlRepositoryAdapter adapter;

    @Mock
    private UrlJpaRepository repository;

    @Mock
    private UrlMapper urlMapper;


    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        FixtureFactoryLoader.loadTemplates("br.com.cahenre.encurtadorurl.fixtures");
    }

    @Test
    void deveSalvarEConverterParaDomain() {
        // Arrange
        UrlEntity entity = Fixture.from(UrlEntity.class).gimme("valida");
        Url domainUrl = Fixture.from(Url.class).gimme("valida");
        when(urlMapper.toEntity(domainUrl)).thenReturn(entity);
        when(urlMapper.toDomain(entity)).thenReturn(domainUrl);
        when(repository.save(entity)).thenReturn(entity);

        // Act
        Url result = adapter.save(domainUrl);

        // Assert
        assertEquals(result, domainUrl);
        verify(repository).save(entity);
    }

    @Test
    void deveBuscarUrlPorUrlEncurtada() {
        // Arrange
        String urlEncurtada = "https://www.mockada.com";
        UrlEntity entity = Fixture.from(UrlEntity.class).gimme("valida");
        Url domainUrl = Fixture.from(Url.class).gimme("valida");
        when(repository.findByUrlEncurtada(urlEncurtada)).thenReturn(java.util.Optional.of(entity));
        when(urlMapper.toDomain(entity)).thenReturn(domainUrl);

        // Act
        Url result = adapter.findByUrlEncurtada(urlEncurtada).orElse(null);

        // Assert
        assertEquals(result, domainUrl);
        verify(repository).findByUrlEncurtada(urlEncurtada);
    }


}
