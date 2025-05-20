package br.com.cahenre.encurtadorurl.fixtures;

import br.com.cahenre.encurtadorurl.adapter.in.web.dto.response.UrlEncurtadaResponse;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class UrlEncurtadaResponseTemplateLoader implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(UrlEncurtadaResponse.class).addTemplate("valida", new Rule() {{
            add("urlEncurtada", regex("[a-zA-Z0-9]{6}"));
        }});
    }
}
