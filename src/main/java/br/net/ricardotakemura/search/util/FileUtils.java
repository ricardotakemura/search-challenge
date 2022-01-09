package br.net.ricardotakemura.search.util;

import java.io.*;

/**
 * Classe utilitária para arquivos
 */
public final class FileUtils {

    private FileUtils() {
    }

    /**
     * Obtem os arquivos que estão de dentro do diretório
     *
     * @param dir Diretório para obter os arquivos (dentro dele)
     * @return Lista (<i>array</i>) com os arquivos
     */
    public static File[] getFilesByDirectory(File dir) {
        if (dir.isDirectory() && dir.listFiles() != null) {
            return dir.listFiles();
        }
        return new File[0];
    }

    /**
     * Cria um arquivo de dados
     *
     * @param file Arquivo de dados a ser criado
     * @param data Dados a serem gravados
     * @throws IOException Exceção de I/O
     */
    public static void createFileData(File file, Serializable data) throws IOException {
        try (var output = new ObjectOutputStream(new FileOutputStream(file))) {
            output.writeObject(data);
        }
    }

    /**
     * Verifica se o arquivo pode ser lido
     *
     * @param file Arquivo a ser verificado
     * @return Se pode ser lido, retorna <b>true</b>, senão <b>false</b>
     */
    public static boolean canRead(File file) {
        return file.isFile() && file.canRead() && !file.isHidden();
    }

    /**
     * Lê o arquivo texto completo e coloca numa única linha
     *
     * @param file Arquivo texto a ser lido
     * @return Conteúdo do arquivo texto (única linha)
     * @throws IOException Exceção de I/O
     */
    public static String readFully(File file) throws IOException {
        try (var reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            return reader.lines().reduce((l1, l2) -> l1 + ' ' + l2).orElse("");
        }
    }

    /**
     * Lê o arquivo de dados e coloca num objeto do tipo &lt;T&gt;
     *
     * @param file Arquivo de dados a ser lido
     * @param <T>  Tipo do objeto a ver retornado
     * @return Objeto do tipo &lt;T&gt; que possui os dados lidos
     * @throws IOException            Exceção de I/O
     * @throws ClassNotFoundException Classe não encontrada
     */
    public static <T extends Serializable> T readFileData(File file) throws IOException, ClassNotFoundException {
        try (var output = new ObjectInputStream(new FileInputStream(file))) {
            return (T) output.readObject();
        }
    }

    /**
     * Exclui um arquivo
     *
     * @param filePath Caminho do arquivo a ser excluído
     * @return <b>true</b> se o arquivo for excluído, <b>false</b> caso o contrárioß
     */
    public static boolean delete(String filePath) {
        var file = new File(filePath);
        if (file.isFile()) {
            return file.delete();
        }
        return false;
    }
}
