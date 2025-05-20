package br.com.cahenre.encurtadorurl.fixtures;

import br.com.cahenre.encurtadorurl.adapter.in.web.dto.response.EstatisticasResponse;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

import java.time.LocalDateTime;

public class EstatisticasResponseTemplateLoader implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(EstatisticasResponse.class).addTemplate("valida", new Rule() {{
            add("urlOrigem", regex("https://(www\\.)?[a-z]{20,100}\\.com"));
            add("urlEncurtada", regex("[a-zA-Z0-9]{6}"));
            add("criadoEm", LocalDateTime.now());
            add("totalAcessos", random(Long.class, range(0L, 100L)));
            add("ultimoAcessoEm", LocalDateTime.now());
        }});
    }
}
