package br.com.cahenre.encurtadorurl.mapper;

import br.com.cahenre.encurtadorurl.adapter.out.persistence.UrlEntity;
import br.com.cahenre.encurtadorurl.domain.model.Url;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;


public class UrlMapperTest{

    private final UrlMapper mapper = Mappers.getMapper(UrlMapper.class);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        FixtureFactoryLoader.loadTemplates("br.com.cahenre.encurtadorurl.fixtures");
    }

    @Test
    public void testToDomain() {
        // Arrange
        Url url = Fixture.from(Url.class).gimme("valida");

        // Act
        UrlEntity urlEntity = mapper.toEntity(url);

        // Assert
        assertNotNull(urlEntity);
        assertEquals(url.getUrlOrigem(), urlEntity.getUrlOrigem());
        assertEquals(url.getUrlEncurtada(), urlEntity.getUrlEncurtada());
        assertEquals(url.getCriadoEm(), urlEntity.getCriadoEm());
        assertEquals(url.getTotalAcessos(), urlEntity.getTotalAcessos());
        assertEquals(url.getUltimoAcessoEm(), urlEntity.getUltimoAcessoEm());
        assertEquals(url.getId(), urlEntity.getId());
    }

    @Test
    public void testToEntityNull() {
        // Arrange
        Url url = null;

        // Act
        UrlEntity urlEntity = mapper.toEntity(url);

        // Assert
        assertNull(urlEntity);
    }

    @Test
    public void testToEntity() {
        // Arrange
        UrlEntity urlEntity = Fixture.from(UrlEntity.class).gimme("valida");

        // Act
        Url url = mapper.toDomain(urlEntity);

        // Assert
        assertNotNull(url);
        assertEquals(urlEntity.getUrlOrigem(), url.getUrlOrigem());
        assertEquals(urlEntity.getUrlEncurtada(), url.getUrlEncurtada());
        assertEquals(urlEntity.getCriadoEm(), url.getCriadoEm());
        assertEquals(urlEntity.getTotalAcessos(), url.getTotalAcessos());
        assertEquals(urlEntity.getUltimoAcessoEm(), url.getUltimoAcessoEm());
        assertEquals(urlEntity.getId(), url.getId());
    }

    @Test
    public void testToDomainNull() {
        // Arrange
        UrlEntity urlEntity = null;

        // Act
        Url url = mapper.toDomain(urlEntity);

        // Assert
        assertNull(url);
    }

}
