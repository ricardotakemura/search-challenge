package br.net.ricardotakemura.search.util;

import java.io.IOException;
import java.util.Properties;

/**
 * Classe utilitária de configuração
 */
public final class Configuration {

    private static Configuration instance;
    private final Properties properties;

    private Configuration() {
        properties = new Properties();
        try {
            properties.load(getClass().getResourceAsStream("/application.properties"));
        } catch (IOException e) {
            System.err.println("application.properties not found");
        }
    }

    /**
     * Obtém uma instância desta classe
     *
     * @return Instância desta classe
     */
    public static Configuration getInstance() {
        if (instance == null) {
            instance = new Configuration();
        }
        return instance;
    }

    /**
     * Obtém o valor de uma propriedade através da sua chave que está no arquivo de resource "application.properties"
     *
     * @param key Nome da chave
     * @return Valor da propriedade
     */
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
