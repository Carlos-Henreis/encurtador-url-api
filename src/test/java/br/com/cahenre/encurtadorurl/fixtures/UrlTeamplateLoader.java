package br.com.cahenre.encurtadorurl.fixtures;

import br.com.cahenre.encurtadorurl.domain.model.Url;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

import java.time.LocalDateTime;

public class UrlTeamplateLoader implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(Url.class).addTemplate("valida", new Rule() {{
            add("id", random(Long.class, range(1L, 100L)));
            add("urlOrigem", regex("https://(www\\.)?[a-z]{20,100}\\.com"));
            add("urlEncurtada", regex("[a-zA-Z0-9]{6}"));
            add("criadoEm", LocalDateTime.now());
            add("ultimoAcessoEm", LocalDateTime.now());
            add("totalAcessos", random(Long.class, range(0L, 100L)));
        }});
    }
}