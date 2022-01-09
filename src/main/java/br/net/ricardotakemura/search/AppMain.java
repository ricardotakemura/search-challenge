package br.net.ricardotakemura.search;

import br.net.ricardotakemura.search.view.SearchView;

/**
 * Classe principal
 */
public final class AppMain {

    private AppMain() {
    }

    /**
     * Método principal (start da aplicação)
     *
     * @param args Argumentos passados na linha de comando
     * @throws Exception Exceção genérica (erro)
     */
    public static void main(String... args) throws Exception {
        var searchView = SearchView.getInstance();
        searchView.start(args);
    }

}