package br.com.cahenre.encurtadorurl.adapter.in.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EstatisticasResponse {

    private String urlEncurtada;
    private Long totalAcessos;
    private LocalDateTime ultimoAcessoEm;
    private LocalDateTime criadoEm;
    private String urlOrigem;

}
