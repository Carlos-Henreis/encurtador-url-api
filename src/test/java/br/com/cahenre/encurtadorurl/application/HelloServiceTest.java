
package br.com.cahenre.encurtadorurl.application;

import br.com.cahenre.encurtadorurl.domain.port.HelloOutputPort;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class HelloServiceTest {

    @Test
    void shouldCallOutputPort() {
        // Arrange
        HelloOutputPort outputPort = mock(HelloOutputPort.class);
        HelloService service = new HelloService(outputPort);

        // Act
        service.sayHello();

        // Assert
        verify(outputPort).output("Hello World!");
    }
}
