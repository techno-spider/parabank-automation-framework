package com.parabank.automation.stepdefinitions;

import io.cucumber.spring.ScenarioScope;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@ScenarioScope
public class TestContext {

    @Getter
    @Setter
    private String customerId;
    private final Map<String, Object> contextData = new HashMap<>();

    public void set(String key, Object value) {
        contextData.put(key, value);
    }

    public Object get(String key) {
        return contextData.get(key);
    }

    public <T> T get(String key, Class<T> type) {
        return type.cast(contextData.get(key));
    }
}
