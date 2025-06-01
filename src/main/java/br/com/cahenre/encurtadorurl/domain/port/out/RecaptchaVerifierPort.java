package br.com.cahenre.encurtadorurl.domain.port.out;

public interface RecaptchaVerifierPort {
    boolean isValid(String token);
}