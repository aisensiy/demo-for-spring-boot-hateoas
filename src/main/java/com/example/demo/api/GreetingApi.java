package com.example.demo.api;

import com.example.demo.domain.Greeting;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RequestMapping("greeting")
@RestController
public class GreetingApi {
    private static final String TEMPLATE = "Hello, %s";

    @GetMapping
    public GreetingResource getGreeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        GreetingResource resource = new GreetingResource(new Greeting(String.format(TEMPLATE, name)));
        resource.add(linkTo(methodOn(GreetingApi.class).getGreeting(name)).withSelfRel());
        return resource;
    }
}

class GreetingResource extends ResourceSupport {
    private Greeting greeting;

    public GreetingResource(Greeting greeting) {
        this.greeting = greeting;
    }

    public String getContent() {
        return greeting.getContent();
    }
}
