
package br.com.cahenre.encurtadorurl.adapter.out;

import br.com.cahenre.encurtadorurl.domain.port.HelloOutputPort;
import org.springframework.stereotype.Component;

@Component
public class ConsoleOutputAdapter implements HelloOutputPort {

    @Override
    public void output(String message) {
        System.out.println(message);
    }
}
