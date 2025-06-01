package br.com.cahenre.encurtadorurl.domain.model.exception;

public class OnlyHumansAllowedException extends RuntimeException{

    public OnlyHumansAllowedException() {
        super("Somente humanos podem encurtar URLs. Por favor, complete o desafio de reCAPTCHA.");
    }

}
