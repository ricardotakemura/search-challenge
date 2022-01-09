package br.net.ricardotakemura.search.util;

import java.util.Date;

/**
 * Classe utilitária para logs
 */
public final class Log {

    private static final String TRUE = "true";

    private final Configuration configuration;
    private final Class<?> clazz;

    private Log(Class<?> clazz) {
        configuration = Configuration.getInstance();
        this.clazz = clazz;
    }

    /**
     * Obtém uma instância desta classe
     *
     * @param clazz Indica a classe que usa este log
     * @return Instância desta classe
     */
    public static Log getLog(Class<?> clazz) {
        return new Log(clazz);
    }

    /**
     * Método para logs de depuração
     *
     * @param str Mensagem de depuração
     */
    public void debug(String str) {
        if (TRUE.equals(configuration.getProperty("application.log.enabled"))) {
            System.out.println("[DEBUG] " + new Date() + " " + clazz.getName() + " - " + str);
        }
    }

    /**
     * Método para logs de erros
     *
     * @param str Mensgam de erro
     * @param e   Exceção ocorrida
     */
    public void error(String str, Exception e) {
        if (TRUE.equals(configuration.getProperty("application.log.enabled"))) {
            System.err.println("[ERROR] " + new Date() + " " + clazz.getName() + " - " + str + ": " + e.getMessage());
            e.printStackTrace(System.out);
        }
    }
}
