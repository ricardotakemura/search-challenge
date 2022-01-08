package br.net.ricardotakemura.search.util;

import java.io.IOException;
import java.util.Properties;

public final class Configuration {

    private final Properties properties;

    private static Configuration instance;

    private Configuration() {
        properties = new Properties();
        try {
            properties.load(getClass().getResourceAsStream("/application.properties"));
        } catch (IOException e) {
            System.err.println("application.properties not found");
        }
    }

    public static Configuration getInstance() {
        if (instance == null) {
            instance = new Configuration();
        }
        return instance;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
