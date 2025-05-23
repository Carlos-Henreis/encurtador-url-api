package br.com.cahenre.encurtadorurl.adapter.in.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class CorsAndHeaderConfig implements WebMvcConfigurer, Filter {

    private static final String FRONTEND_ORIGIN = "https://encurtadorurl.cahenre.com.br";

    // Configuração CORS
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(FRONTEND_ORIGIN)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    // Filtro para validar Origin/Referer
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String origin = req.getHeader("Origin");
        String referer = req.getHeader("Referer");

        boolean isValidOrigin = (origin != null && origin.equals(FRONTEND_ORIGIN)) ||
                (referer != null && referer.startsWith(FRONTEND_ORIGIN)) ||
                (origin == null && referer == null); // permite requisições locais sem headers

        if (!isValidOrigin) {
            res.setStatus(HttpServletResponse.SC_FORBIDDEN);
            res.getWriter().write("Forbidden: invalid origin");
            return;
        }

        chain.doFilter(request, response);
    }
}
