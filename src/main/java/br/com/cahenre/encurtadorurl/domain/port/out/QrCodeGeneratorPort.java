package br.com.cahenre.encurtadorurl.domain.port.out;

    public interface QrCodeGeneratorPort {

    byte[] generate(String text) throws Exception;

}
