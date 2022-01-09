package br.net.ricardotakemura.search.util;

/**
 * Classe utilitária para String
 */
public final class StringUtils {

    private StringUtils() {
    }

    /**
     * Verifica se a string é vazia
     *
     * @param str String a ser verificada
     * @return <b>true</b> se está vazia, <b>false</b> caso o contrário
     */
    public static boolean isBlank(String str) {
        return str == null || str.isBlank();
    }

    /**
     * Verifica se a string não é vazia
     *
     * @param str String a ser verificada
     * @return <b>false</b> se está vazia, <b>true</b> caso o contrário
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }
}
