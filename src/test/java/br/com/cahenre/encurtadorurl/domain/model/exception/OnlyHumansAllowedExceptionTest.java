package br.com.cahenre.encurtadorurl.domain.model.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OnlyHumansAllowedExceptionTest {

    @Test
    void deveCriarExcecaoComMensagemPadrao() {
        // arrange & act
        OnlyHumansAllowedException exception = assertThrows(
                OnlyHumansAllowedException.class,
                () -> { throw new OnlyHumansAllowedException(); }
        );

        // assert
        assertEquals(
                "Somente humanos podem encurtar URLs. Por favor, complete o desafio de reCAPTCHA.",
                exception.getMessage()
        );
    }

}
