package br.com.cahenre.encurtadorurl.adapter.out.persistence;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class UrlEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String urlOrigem;
    @Column(unique = true)
    private String urlEncurtada;
    private LocalDateTime criadoEm;
    private LocalDateTime ultimoAcessoEm;
    private Long totalAcessos;

}
