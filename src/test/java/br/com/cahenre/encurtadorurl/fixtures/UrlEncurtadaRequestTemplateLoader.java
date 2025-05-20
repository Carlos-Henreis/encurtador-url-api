package br.com.cahenre.encurtadorurl.fixtures;

import br.com.cahenre.encurtadorurl.adapter.in.web.dto.request.UrlEncurtadaRequest;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class UrlEncurtadaRequestTemplateLoader implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(UrlEncurtadaRequest.class).addTemplate("valida", new Rule() {{
            add("urlOrigem", regex("https://(www\\.)?[a-z]{20,100}\\.com"));
        }});
    }


}
