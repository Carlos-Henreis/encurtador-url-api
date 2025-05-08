
package br.com.cahenre.encurtadorurl.adapter.in;

import br.com.cahenre.encurtadorurl.application.HelloService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private final HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping("/hello")
    public String hello() {
        helloService.sayHello();
        return "Hello sent!";
    }
}
