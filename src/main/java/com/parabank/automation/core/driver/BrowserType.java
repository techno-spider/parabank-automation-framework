package com.parabank.automation.core.driver;

import com.parabank.automation.core.exception.ConfigurationException;
import lombok.Getter;

@Getter
public enum BrowserType {

    CHROME("chrome"), FIREFOX("firefox"), EDGE("edge");

    private final String value;

    BrowserType(String value) {
        this.value = value;
    }

    public static BrowserType fromString(String browser) {
        for (BrowserType type : values()) {
            if (type.value.equalsIgnoreCase(browser)) {
                return type;
            }
        }
        throw new ConfigurationException("browser", "chrome, firefox, or edge", browser);
    }
}
