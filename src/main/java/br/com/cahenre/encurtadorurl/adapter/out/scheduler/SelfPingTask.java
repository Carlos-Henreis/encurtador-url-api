package br.com.cahenre.encurtadorurl.adapter.out.scheduler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SelfPingTask {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${app.base-url}")
    private String appUrl;

    @Scheduled(fixedRate = 600_000) // a cada 10 minutos
    public void ping() {
        try {
            String url = appUrl + "/actuator/health";
            restTemplate.getForObject(url, String.class);
            System.out.println("Ping realizado com sucesso.");
        } catch (Exception e) {
            System.err.println("Erro no ping: " + e.getMessage());
        }
    }

}
