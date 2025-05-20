package br.com.cahenre.encurtadorurl.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UrlEncurtadaRequest {

    @NotBlank(message = "URL de origem não pode ser nula ou vazia")
    private String urlOrigem;

}
