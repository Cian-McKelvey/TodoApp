package com.mckelvey;

import io.dropwizard.core.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.logging.common.DefaultLoggingFactory;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;


public class TodoAppConfiguration extends Configuration {

    @NotEmpty
    private String template;

    @NotEmpty
    private String defaultName;

    @NotNull
    private int defaultSize;

    @NotNull
    private String defaultTodoUserId;

    @Valid
    @NotNull
    private DefaultLoggingFactory loggingFactory = new DefaultLoggingFactory();

    // Getters and setters for configuration properties

    @JsonProperty
    public int getDefaultSize() {
        return defaultSize;
    }

    @JsonProperty
    public void setDefaultSize(int defaultSize) {
        this.defaultSize = defaultSize;
    }

    @JsonProperty
    public String getDefaultTodoUserId() {
        return defaultTodoUserId;
    }

    @JsonProperty
    public void setDefaultTodoUserId(String defaultTodoUserId) {
        this.defaultTodoUserId = defaultTodoUserId;
    }

    @JsonProperty
    public String getTemplate() {
        return template;
    }

    @JsonProperty
    public void setTemplate(String template) {
        this.template = template;
    }

    @JsonProperty
    public String getDefaultName() {
        return defaultName;
    }

    @JsonProperty
    public void setDefaultName(String defaultName) {
        this.defaultName = defaultName;
    }

    @JsonProperty("logging")
    public DefaultLoggingFactory getLoggingFactory() {
        return loggingFactory;
    }

    @JsonProperty("logging")
    public void setLoggingFactory(DefaultLoggingFactory loggingFactory) {
        this.loggingFactory = loggingFactory;
    }


}
