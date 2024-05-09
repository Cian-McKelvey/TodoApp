package com.mckelvey.health;

import com.codahale.metrics.health.HealthCheck;

public class ApplicationHealthCheck extends HealthCheck {

    // todo expand on this heavily
    // Very basic healthcheck
    @Override
    protected Result check() throws Exception {
        return Result.healthy();
    }

}
