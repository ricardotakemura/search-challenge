package br.net.ricardotakemura.search.util;

import java.util.Date;

public final class Log {

    private static final String TRUE = "true";

    private final Configuration configuration;
    private final Class<?> clazz;

    private static Log instance;

    private Log(Class<?> clazz) {
        configuration = Configuration.getInstance();
        this.clazz = clazz;
    }

    public static <T> Log getLog(Class<T> clazz) {
        if (instance == null) {
            instance = new Log(clazz);
        }
        return instance;
    }

    public void debug(String str) {
        if (TRUE.equals(configuration.getProperty("application.log.enabled"))) {
            System.out.println(new Date() + " " + clazz.getName() + " [DEBUG]" + str);
        }
    }

    public void warn(String str, Exception e) {
        if (TRUE.equals(configuration.getProperty("application.log.enabled"))) {
            System.out.println(new Date() + " " + clazz.getName() + " [WARN]" + str + ": " + e.getMessage());
            e.printStackTrace(System.out);
        }
    }

    public void error(String str, Exception e) {
        if (TRUE.equals(configuration.getProperty("application.log.enabled"))) {
            System.err.println(new Date() + " " + clazz.getName() + " [ERROR]" + str + ": " + e.getMessage());
            e.printStackTrace(System.out);
        }
    }
}
