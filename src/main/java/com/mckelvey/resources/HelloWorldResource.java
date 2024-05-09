package com.mckelvey.resources;

import com.codahale.metrics.annotation.Timed;
import com.mckelvey.api.Saying;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Path("/hello-world")  // Specifies the URI
@Produces(MediaType.APPLICATION_JSON)  // Specifies it creates JSON data
public class HelloWorldResource {
    private final String template;  // Used to create the saying. Dunno what that means still
    private final String defaultName;  // Default name when user doesn't specify
    private final AtomicLong counter;  // Used for generating id's, prob will use UUID normally

    public HelloWorldResource(String template, String defaultName) {
        this.template = template;
        this.defaultName = defaultName;
        this.counter = new AtomicLong();
    }

    @GET
    @Timed
    public Saying sayHello(@QueryParam("name") Optional<String> name) {
        final String value = String.format(template, name.orElse(defaultName));
        return new Saying(counter.incrementAndGet(), value);
    }
}
