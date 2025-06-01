package br.com.cahenre.encurtadorurl.adapter.in.web.config;

import io.github.bucket4j.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class RequestFilterConfig implements WebMvcConfigurer, Filter {

    private static final String FRONTEND_ORIGIN = "https://encurtadorurl.cahenre.com.br"; // http://localhost:4200 para desenvolvimento
    private final Map<String, Bucket> ipBuckets = new ConcurrentHashMap<>();

    private Bucket newBucket() {
        return Bucket4j.builder()
                .addLimit(Bandwidth.classic(50, Refill.greedy(50, Duration.ofMinutes(5))))
                .build();
    }

    // Configuração CORS
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(FRONTEND_ORIGIN)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String path = req.getRequestURI();
        String method = req.getMethod();

        // Ignora actuator
        if (path.startsWith("/actuator")) {
            chain.doFilter(request, response);
            return;
        }

        // Validação de origin
        String origin = req.getHeader("Origin");
        String referer = req.getHeader("Referer");

        boolean isValidOrigin = (origin != null && origin.equals(FRONTEND_ORIGIN)) ||
                (referer != null && referer.startsWith(FRONTEND_ORIGIN)) ||
                (origin == null && referer == null); // permite requisições locais

        if (!isValidOrigin) {
            res.setStatus(HttpServletResponse.SC_FORBIDDEN);
            res.getWriter().write("Forbidden: invalid origin");
            return;
        }

        // Aplica rate limit apenas para rotas específicas
        boolean isRateLimited =
                ("GET".equals(method) && (path.matches("^/stats/[^/]+$") || path.matches("^/qrcode/[^/]+$"))) ||
                        ("POST".equals(method) && path.equals("/"));

        if (isRateLimited) {
            String ip = req.getRemoteAddr();
            Bucket bucket = ipBuckets.computeIfAbsent(ip, k -> newBucket());

            if (!bucket.tryConsume(1)) {
                res.setStatus(429);
                res.getWriter().write("Rate limit exceeded");
                return;
            }
        }

        chain.doFilter(request, response);
    }
}
