
package br.com.cahenre.encurtadorurl.application;

import br.com.cahenre.encurtadorurl.domain.port.HelloOutputPort;
import org.springframework.stereotype.Service;

@Service
public class HelloService {

    private final HelloOutputPort outputPort;

    public HelloService(HelloOutputPort outputPort) {
        this.outputPort = outputPort;
    }

    public void sayHello() {
        outputPort.output("Hello World!");
    }
}
