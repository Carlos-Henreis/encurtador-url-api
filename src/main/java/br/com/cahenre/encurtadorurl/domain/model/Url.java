package br.com.cahenre.encurtadorurl.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Url {

    private Long id;
    private String urlOrigem;
    private String urlEncurtada;
    private LocalDateTime criadoEm;
    private LocalDateTime ultimoAcessoEm;
    private Long totalAcessos;
}
