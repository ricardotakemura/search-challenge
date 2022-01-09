package br.net.ricardotakemura.search.model;

import java.io.File;
import java.util.Set;

/**
 * <i>Interface</i> de modelo do sistema (como os dados são trabalhados/obtidos)
 */
public interface SearchModel {

    /**
     * Cria o arquivo de indices
     * @param dataDir Diretório dos arquivos a serem indexados
     * @throws Exception Exceção genérica (erro)
     */
    void createIndexes(File dataDir) throws Exception;

    /**
     * Carrega o arquivo de indices
     * @throws Exception Exceção genérica (erro)
     */
    void loadIndexes() throws Exception;

    /**
     * Busca os arquivos através das palavras passadas por parâmetro (conteúdo)
     * @param words Lista de palavras (array)
     * @return Lista (<i>java.util.Set</i>) de arquivos que contém estas palavras (<i>AND</i>)
     */
    Set<File> search(String... words);
}
