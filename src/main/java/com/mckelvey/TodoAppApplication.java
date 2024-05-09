package com.mckelvey;

import com.mckelvey.Repository.BrandRepository;
import com.mckelvey.api.Brand;
import com.mckelvey.resources.BrandResource;
import com.mckelvey.resources.HelloWorldResource;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;

import java.util.ArrayList;
import java.util.List;

public class TodoAppApplication extends Application<TodoAppConfiguration> {

    public static void main(final String[] args) throws Exception {
        new TodoAppApplication().run(args);
    }

    @Override
    public String getName() {
        return "TodoApp";
    }

    @Override
    public void initialize(final Bootstrap<TodoAppConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final TodoAppConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application

        // getting-started: HelloWorldApplication#run->HelloWorldResource
        HelloWorldResource resource = new HelloWorldResource(
                configuration.getTemplate(),
                configuration.getDefaultName()
        );
        environment.jersey().register(resource);
        // getting-started: HelloWorldApplication#run->HelloWorldResource

        // The brands initialisation
        int defaultSize = configuration.getDefaultSize();  // Gets the default fetch size

        // Fake list made to use the brand Repository
        List<Brand> brands = new ArrayList<>();

        brands.add(new Brand(1L, "Nike"));
        brands.add(new Brand(2L, "Adidas"));
        brands.add(new Brand(3L, "Apple"));
        brands.add(new Brand(4L, "Samsung"));
        brands.add(new Brand(5L, "Google"));
        brands.add(new Brand(6L, "Microsoft"));
        brands.add(new Brand(7L, "Coca-Cola"));
        brands.add(new Brand(8L, "Pepsi"));
        brands.add(new Brand(9L, "Amazon"));
        brands.add(new Brand(10L, "Sony"));

        BrandRepository brandRepository = new BrandRepository(brands);
        BrandResource brandResource = new BrandResource(defaultSize, brandRepository);

        environment.jersey().register(brandResource);
    }

}
