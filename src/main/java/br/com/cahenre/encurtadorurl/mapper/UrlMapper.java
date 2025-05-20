package br.com.cahenre.encurtadorurl.mapper;


import br.com.cahenre.encurtadorurl.adapter.out.persistence.UrlEntity;
import br.com.cahenre.encurtadorurl.domain.model.Url;
import org.mapstruct.Mapper;
@Mapper(componentModel = "spring")
public interface UrlMapper {

    Url toDomain(UrlEntity urlEntity);
    UrlEntity toEntity(Url url);

}
