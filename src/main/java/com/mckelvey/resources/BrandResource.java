package com.mckelvey.resources;

import com.mckelvey.Repository.BrandRepository;
import com.mckelvey.api.Brand;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.Optional;

@Path("/brands")
@Produces(MediaType.APPLICATION_JSON)
public class BrandResource {

    private final int defaultSize;
    private final BrandRepository brandRepository;

    public BrandResource(int defaultSize, BrandRepository brandRepository) {
        this.defaultSize = defaultSize;
        this.brandRepository = brandRepository;
    }

    @GET
    public List<Brand> getBrands(@QueryParam("size") Optional<Integer> size) {
        return brandRepository.findAmount(size.orElse(defaultSize));
    }

    @GET
    @Path("/{id}")
    public Brand getBrandById(@PathParam("id") Long id) {
        return brandRepository.findById(id).orElseThrow(RuntimeException::new);
    }

}
